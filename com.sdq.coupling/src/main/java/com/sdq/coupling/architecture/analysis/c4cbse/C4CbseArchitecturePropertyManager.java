package com.sdq.coupling.architecture.analysis.c4cbse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.architecture.analysis.IArchitecturePropertyManager;
import com.sdq.coupling.util.Location;

/**
 * Implements the IArchitecturePropertyManager Interface for the C4CSE
 * Framework.
 *
 * @author Laura
 *
 */
public class C4CbseArchitecturePropertyManager implements IArchitecturePropertyManager {

  /**
   * Accepts a list of files and looks for the file with the given file ending.
   *
   * @param modelFiles List of model files representing the c4cbse architecture
   *                   model
   * @param fileEnding The file ending for which the list is searched for.
   * @return The model file with the given file ending
   */
  private String findModelSubFileByEnding(List<String> modelFiles, String fileEnding) {
    List<String> modelFilesFiltered = modelFiles.stream().filter(f -> f.endsWith(fileEnding))
        .collect(Collectors.toList());
    if (modelFilesFiltered.size() != 1) {
      // TODO: throw exception
      return null;
    }
    return modelFilesFiltered.get(0);
  }

  /**
   * Adds components to their corresponding resource container.
   *
   * @param resourceEnvironment       The resource environment of the c4cbse
   *                                  model.
   * @param components                The list of components to add.
   * @param allocationContextFilePath Path to the allocation context file of the
   *                                  c4cbse model, which contains the link
   *                                  between components and resource containers.
   * @param systemFilePath            The file to the system.default file.
   */
  private void addComponentsToResourceContainers(ResourceEnvironment resourceEnvironment, 
      List<Component> components,
      String allocationContextFilePath, String systemFilePath)
      throws ParserConfigurationException, SAXException, IOException {
    List<ResourceContainer> resourceContainers = resourceEnvironment.getResourceContainers();

    Document allocationXmlDocument = readXmlDocument(allocationContextFilePath);
    List<Element> allocationElements = 
        getXmlElements("allocationContexts_Allocation", allocationXmlDocument);

    Document systemXmlDocument = readXmlDocument(systemFilePath);
    List<Element> systemElements = 
        getXmlElements("assemblyContexts__ComposedStructure", systemXmlDocument);

    for (Element allocationElement : allocationElements) {
      // get resource container id
      Element resourceContainerElement = getFirstChildOfElementByTagName(allocationElement,
          "resourceContainer_AllocationContext");
      String resourceContainerId = resourceContainerElement.getAttribute("href").split("#")[1].substring(1);
      // get id for composed structure element in system
      Element assemblyElement = 
          getFirstChildOfElementByTagName(allocationElement, "assemblyContext_AllocationContext");
      String systemId = assemblyElement.getAttribute("href").split("#")[1];

      for (ResourceContainer resourceContainer : resourceContainers) {
        if (resourceContainer.getId().equals(resourceContainerId)) {
          List<String> componentIds = new ArrayList<>();

          //
          for (Element systemElement : systemElements) {
            String assemblyId = systemElement.getAttribute("id");
            if (systemId.equals(assemblyId)) {
              Element componentElement = getFirstChildOfElementByTagName(systemElement,
                  "encapsulatedComponent__AssemblyContext");
              String componentId = componentElement.getAttribute("href").split("#")[1];
              componentIds.add(componentId);
            }
          }

          for (String componentId : componentIds) {
            for (Component component : components) {
              if (component.getId().equals(componentId)) {
                resourceContainer.addComponent(component);
              }
            }
          }

        }
      }
    }
  }

  /**
   * Parses the components of the default.repository file.
   *
   * @param repositoryFilePath The path to the repository file.
   * @return Returns a list of components.
   */
  private List<Component> parseComponents(String repositoryFilePath) {

    List<Component> componentList = new ArrayList<>();

    Document xmlDocument = readXmlDocument(repositoryFilePath);
    NodeList componentElements = xmlDocument.getElementsByTagName("components__Repository");

    for (int i = 0; i < componentElements.getLength(); i++) {
      Node node = componentElements.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        System.out.println(element.getAttribute("xsi:type"));
        if (element.getAttribute("xsi:type").equals("repository:BasicComponent")) {
          String id = element.getAttribute("id");
          String entityName = element.getAttribute("entityName");
          Component component = new Component(id, entityName);
          componentList.add(component);
        }
      }
    }

    return componentList;
  }

  
  /**
   * Parses the resource environment of the c4cbse model.
   * @param resourceEnvironmentFilePath Path to the default.resourceenvironment file.
   * @return Returns the parsed resource environment containing the linking resources. 
   * and the resource containers.
   */
  private ResourceEnvironment parseResourceEnvironment(String resourceEnvironmentFilePath)
      throws ParserConfigurationException, SAXException, IOException {
    List<LinkingResource> linkingResources = new ArrayList<>();
    List<ResourceContainer> resourceContainers = new ArrayList<>();

    Document xmlDocument = readXmlDocument(resourceEnvironmentFilePath);
    List<Element> resourceElements = 
        getXmlElements("resourceContainer_ResourceEnvironment", xmlDocument);
    
    for (Element element : resourceElements) {
      String id = element.getAttribute("id").substring(1);
      String entityName = element.getAttribute("entityName");
      ResourceContainer resourceContainer = new ResourceContainer(id, entityName);
      resourceContainers.add(resourceContainer);
    }
    NodeList linkingElements = xmlDocument
        .getElementsByTagName("linkingResources__ResourceEnvironment");
    
    for (int j = 0; j < linkingElements.getLength(); j++) {
      Node node = linkingElements.item(j);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        String id = element.getAttribute("id");
        String entityName = element.getAttribute("entityName");
        String connectedResources = element
            .getAttribute("connectedResourceContainers_LinkingResource");
        String sourceId = connectedResources.split(" ")[0].substring(1);
        String targetId = connectedResources.split(" ")[1].substring(1);
        ResourceContainer source = null;
        ResourceContainer target = null;
        
        for (ResourceContainer container : resourceContainers) {
          String resourceId = container.getId();
          if (resourceId.equals(sourceId)) {
            source = container;
          } else if (resourceId.equals(targetId)) {
            target = container;
          }
        }
        LinkingResource linkingResource = new LinkingResource(id, entityName, 
            source, target);
        List<Element> stereoTypes = getXmlElements("stereotypeApplications", xmlDocument);
        
        for (Element stereoTypeElement: stereoTypes) {
          String propertyType = stereoTypeElement.getAttribute("xsi:type").split(":")[1];
          //TODO check id
          if (propertyType.equals("Encryption")) {
            linkingResource.addConfidentialityProperty(ConfidentialityProperty.ENCRYPTED);
          }
        }
        linkingResources.add(linkingResource);
      }
    }
    
    ResourceEnvironment resourceEnvironment = 
        new ResourceEnvironment(resourceContainers, linkingResources);
    return resourceEnvironment;
  }

  /**
   * Extracts the architecture properties from the passed model file. 
   */
  @Override
  public List<AbstractArchitectureProperty> getProperties(String modelDirectoryPath) {
    List<String> modelFiles = Stream.of(new File(modelDirectoryPath)
        .listFiles()).filter(file -> !file.isDirectory())
        .map(File::getName).collect(Collectors.toList());

    String repositoryFileName = findModelSubFileByEnding(modelFiles, ".repository");
    List<Component> components;

    try {
      components = parseComponents(modelDirectoryPath + repositoryFileName);
      String resourceEnvironmentFileName = 
          findModelSubFileByEnding(modelFiles, ".resourceenvironment");
      ResourceEnvironment resourceEnvironment = parseResourceEnvironment(
          modelDirectoryPath + resourceEnvironmentFileName);

      String allocationContextFileName = findModelSubFileByEnding(modelFiles, ".allocation");
      String systemFileName = findModelSubFileByEnding(modelFiles, ".system");
      addComponentsToResourceContainers(resourceEnvironment, components, 
          modelDirectoryPath + allocationContextFileName, modelDirectoryPath + systemFileName);
      List<AbstractArchitectureProperty> propertyList = 
          new LinkedList<AbstractArchitectureProperty>();
      
      for (LinkingResource linkingResource : resourceEnvironment.getLinkingResources()) {
        if (!linkingResource.hasConfidentialityProperty(ConfidentialityProperty.ENCRYPTED)) {
          continue; // irrelevant linking resource, can skip }
        }
        for (Component sourceComponent : linkingResource.getSource().getComponents()) {
          for (Component targetComponent : linkingResource.getTarget().getComponents()) {
            propertyList.add(new C4CbseLinkingResourceProperty(ArchitecturePropertyType
                .ENCRYPTED, new Location(sourceComponent.getEntityName()), 
                new Location(targetComponent.getEntityName()), 
                linkingResource.getId()));
            propertyList.add(new C4CbseLinkingResourceProperty(ArchitecturePropertyType.ENCRYPTED,
                new Location(targetComponent.getEntityName()), 
                new Location(sourceComponent.getEntityName()),
                linkingResource.getId()));
          }
        }
      }
      return propertyList;
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  

  /**
   * Removes the passed properties from the c4cbse model that are violated by pattern based
   * code violations.
   */
  @Override
  public void removeProperties(String modelFilePath, 
      List<AbstractArchitectureProperty> violatedProperties) {

    List<String> modelFiles = Stream.of(new File(modelFilePath)
        .listFiles()).filter(file -> !file.isDirectory())
        .map(File::getName).collect(Collectors.toList());
    String resourceEnvironmentFileName = 
        findModelSubFileByEnding(modelFiles, ".resourceenvironment");
    Document xmlDocument = readXmlDocument(modelFilePath + resourceEnvironmentFileName);

    for (AbstractArchitectureProperty property : violatedProperties) {
      C4CbseLinkingResourceProperty c4CbseProperty = 
          (C4CbseLinkingResourceProperty) property;
      List<Element> stereotypeApplications = 
          getXmlElements("stereotypeApplications", xmlDocument);
      for (Element stereotype : stereotypeApplications) {
        if (stereotype.getAttribute("appliedTo").equals(c4CbseProperty.getLinkingResourceId())
            && stereotype.getAttribute("xsi:type").split(":")[1].equals("Encryption")
            && c4CbseProperty.getArchitecturePropertyType() == ArchitecturePropertyType.ENCRYPTED) {
          stereotype.getParentNode().removeChild(stereotype);
          xmlDocument.normalize();
          break;
        }
      }

    }

    try (FileOutputStream output = new FileOutputStream(
        modelFilePath + resourceEnvironmentFileName.split("\\.")[0] 
            + ".coupledresourceenvironment")) {
      writeXml(xmlDocument, output);
    } catch (IOException | TransformerException e) {
      e.printStackTrace();
    }
  }
  
  private Element getFirstChildOfElementByTagName(Element element, String tagName) {
    return (Element) element.getElementsByTagName(tagName).item(0);
  }  
  
  private List<Element> getXmlElements(String tagName, Document xmlDocument) {
    List<Element> elements = new ArrayList<>();
    NodeList nodes = xmlDocument.getElementsByTagName(tagName);
    for (int index = 0; index < nodes.getLength(); index++) {
      Node node = nodes.item(index);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        elements.add(element);
      }
    }
    return elements;
  }
  
  private Document readXmlDocument(String filePath) {
    Document xmlDocument = null;
    File file = new File(filePath);
    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder;
    try {
      documentBuilder = documentFactory.newDocumentBuilder();
      xmlDocument = documentBuilder.parse(file);
      xmlDocument.getDocumentElement().normalize();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return xmlDocument;
  }

  private static void writeXml(Document doc, OutputStream output) throws TransformerException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);

    transformer.transform(source, result);

  }
}

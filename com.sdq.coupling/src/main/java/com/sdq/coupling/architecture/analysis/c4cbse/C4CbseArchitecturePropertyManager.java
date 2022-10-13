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
   * @param resourceEnvironment The resource environment of the c4cbse
   * model.
   * @param components The list of components to add.
   * @param allocationContextFilePath Path to the allocation context file of the
   * c4cbse model, which contains the link between components and resource containers.
   * @param systemFilePath The file to the system.default file.
   * @throws ParserConfigurationException 
   * @throws SAXException
   * @throws IOException
   */
  private void addComponentsToResourceContainers(ResourceEnvironment resourceEnvironment, 
      List<Component> components,
      String allocationContextFilePath, String systemFilePath)
      throws ParserConfigurationException, SAXException, IOException {
    List<ResourceContainer> resourceContainers = resourceEnvironment.getResourceContainers();

    File allocationXmlFile = new File(allocationContextFilePath);
    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    Document allocationXmlDocument = documentBuilder.parse(allocationXmlFile);
    allocationXmlDocument.getDocumentElement().normalize();
    NodeList allocationElements = allocationXmlDocument
        .getElementsByTagName("allocationContexts_Allocation");

    File systemXmlFile = new File(systemFilePath);
    Document systemXmlDocument = documentBuilder.parse(systemXmlFile);
    systemXmlDocument.getDocumentElement().normalize();
    NodeList systemElements = systemXmlDocument
        .getElementsByTagName("assemblyContexts__ComposedStructure");

    for (int i = 0; i < allocationElements.getLength(); i++) {
      Node node = allocationElements.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element allocationElement = (Element) node;
        Element resourceContainerElement = (Element) allocationElement
            .getElementsByTagName("resourceContainer_AllocationContext").item(0);
        String resourceContainerId = resourceContainerElement.getAttribute("href").split("#")[1].substring(1);
        Element assemblyElement = (Element) allocationElement
            .getElementsByTagName("assemblyContext_AllocationContext")
            .item(0);
        String systemId = assemblyElement.getAttribute("href").split("#")[1];
        
        for (ResourceContainer resourceContainer : resourceContainers) {
          if (resourceContainer.getId().equals(resourceContainerId)) {
            List<String> componentIds = new ArrayList<>();
            
            for (int j = 0; j < systemElements.getLength(); j++) {
              Node systemNode = systemElements.item(j);
              Element systemElement = (Element) systemNode;
              String assemblyId = systemElement.getAttribute("id");
              if (systemId.equals(assemblyId)) {
                Element componentElement = (Element) systemElement
                    .getElementsByTagName("encapsulatedComponent__AssemblyContext").item(0);
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
  }

  /**
   * Parses the components of the default.repository file.
   *
   * @param repositoryFilePath The path to the repository file.
   * @return Returns a list of components.
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  private List<Component> parseComponents(String repositoryFilePath) {
    
    List<Component> componentList = new ArrayList<>();
    File repositoryXmlFile = new File(repositoryFilePath);

    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder;
    try {
      documentBuilder = documentFactory.newDocumentBuilder();
      Document xmlDocument = documentBuilder.parse(repositoryXmlFile);
      xmlDocument.getDocumentElement().normalize();
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
    } catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SAXException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return componentList;
  }

  
  /**
   * Parses the resource environment of the c4cbse model.
   * @param resourceEnvironmentFilePath Path to the default.resourceenvironment file.
   * @return Returns the parsed resource environment containing the linking resources 
   * and the resource containers
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  private ResourceEnvironment parseResourceEnvironment(String resourceEnvironmentFilePath)
      throws ParserConfigurationException, SAXException, IOException {
    List<LinkingResource> linkingResources = new ArrayList<>();
    List<ResourceContainer> resourceContainers = new ArrayList<>();
    File resourceEnvironmentFile = new File(resourceEnvironmentFilePath);
    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    Document xmlDocument = documentBuilder.parse(resourceEnvironmentFile);
    xmlDocument.getDocumentElement().normalize();
    NodeList resourceElements = xmlDocument
        .getElementsByTagName("resourceContainer_ResourceEnvironment");
    
    for (int i = 0; i < resourceElements.getLength(); i++) {
      Node resourceNode = resourceElements.item(i);
      if (resourceNode.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) resourceNode;
        String id = element.getAttribute("id").substring(1);
        String entityName = element.getAttribute("entityName");
        ResourceContainer resourceContainer = new ResourceContainer(id, entityName);
        resourceContainers.add(resourceContainer);
      }
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
        NodeList stereoTypes = xmlDocument.getElementsByTagName("stereotypeApplications");
        
        for (int i = 0; i < stereoTypes.getLength(); i++) {
          Node stereoTypeNode = stereoTypes.item(0);
          Element stereoTypeElement = (Element) stereoTypeNode;
          String propertyType = stereoTypeElement.getAttribute("xsi:type").split(":")[1];
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
      // TODO Auto-generated catch block
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
    File resourceEnvironmentFile = new File(modelFilePath + resourceEnvironmentFileName);
    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder;
    
    try {
      documentBuilder = documentFactory.newDocumentBuilder();
      Document xmlDocument = documentBuilder.parse(resourceEnvironmentFile);
      xmlDocument.getDocumentElement().normalize();
      
      for (int i = 0; i < violatedProperties.size(); i++) {
        C4CbseLinkingResourceProperty property = 
            (C4CbseLinkingResourceProperty) violatedProperties.get(i);
        NodeList stereotypeApplications = xmlDocument
            .getElementsByTagName("stereotypeApplications");
        for (int j = 0; j < stereotypeApplications.getLength(); j++) {
          Element stereotype = (Element) stereotypeApplications.item(j);
          if (stereotype.getAttribute("appliedTo").equals(property.getLinkingResourceId())
              && stereotype.getAttribute("xsi:type").split(":")[1].equals("Encryption")
              && property.getArchitecturePropertyType() == ArchitecturePropertyType.ENCRYPTED) {
            stereotype.getParentNode().removeChild(stereotype);
            xmlDocument.normalize();
            break;
          }
        }
        
      }
      
      try (FileOutputStream output = new FileOutputStream(modelFilePath 
            + resourceEnvironmentFileName.split("\\.")[0] + ".coupledresourceenvironment")) {
        writeXml(xmlDocument, output);
      } catch (IOException | TransformerException e) {
        e.printStackTrace();
      }
    } catch (ParserConfigurationException | SAXException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static void writeXml(Document doc, OutputStream output) throws TransformerException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);

    transformer.transform(source, result);

  }
}

package com.sdq.coupling.architecture.analysis.c4cbse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.architecture.analysis.IArchitecturePropertyManager;
import com.sdq.coupling.util.Location;

public class C4CbseArchitecturePropertyManager implements IArchitecturePropertyManager {

	private String findModelSubFileByEnding(List<String> modelFiles, String fileEnding) {
		List<String> modelFilesFiltered = modelFiles.stream().filter(f -> f.endsWith(fileEnding))
				.collect(Collectors.toList());
		if (modelFilesFiltered.size() != 1) {
			// TODO: throw exception or so, only one repository file should be there
			return null;
		}
		return modelFilesFiltered.get(0);
	}

	private void addComponentsToResourceContainers(ResourceEnvironment resourceEnvironment, List<Component> components,
			String allocationContextFilePath, String systemFilePath) throws ParserConfigurationException, SAXException, IOException {
		List<ResourceContainer> resourceContainers = resourceEnvironment.getResourceContainers();
		
		File allocationXmlFile = new File(allocationContextFilePath);
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		Document allocationXmlDocument = documentBuilder.parse(allocationXmlFile);
		allocationXmlDocument.getDocumentElement().normalize();
		NodeList allocationElements = allocationXmlDocument.getElementsByTagName("allocationContexts_Allocation");
		
		File systemXmlFile = new File(systemFilePath);
		Document systemXmlDocument = documentBuilder.parse(systemXmlFile);
		systemXmlDocument.getDocumentElement().normalize();
		NodeList systemElements = systemXmlDocument.getElementsByTagName("assemblyContexts__ComposedStructure");
		
		//iterate through all allocation elements and 
		
			for(int i = 0; i < allocationElements.getLength(); i++) {
				Node node = allocationElements.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element allocationElement = (Element) node;
					Element resourceContainerElement = (Element) allocationElement.getElementsByTagName("resourceContainer_AllocationContext").item(0);
					String resourceContainerId = resourceContainerElement.getAttribute("href").split("#")[1].substring(1);
					System.out.println("resConId:" + resourceContainerId);
					Element assemblyElement = (Element) allocationElement.getElementsByTagName("assemblyContext_AllocationContext").item(0);
					String systemId = assemblyElement.getAttribute("href").split("#")[1];
					System.out.println("sysId:" + systemId);
					for(ResourceContainer resourceContainer : resourceContainers) {
						if(resourceContainer.getId().equals(resourceContainerId)) {
							System.out.println("found equal resource container for allocation");
							List<String> componentIds = new ArrayList<>();
							for(int j = 0; j < systemElements.getLength(); j++) {
								Node systemNode = systemElements.item(j);
								Element systemElement = (Element) systemNode;
								String assemblyId = systemElement.getAttribute("id");
								System.out.println(systemId + "  equals  " + assemblyId);
								if(systemId.equals(assemblyId)) {
									System.out.println("success");
									Element componentElement = (Element) systemElement.getElementsByTagName("encapsulatedComponent__AssemblyContext")
											.item(0);
									String componentId = componentElement.getAttribute("href").split("#")[1];
									System.out.println("adding:" + componentId);
									componentIds.add(componentId);
								}
							}
							
							for(String componentId : componentIds) {
								for(Component component : components) {
									System.out.println(component.getId() + "  equals  " + componentId);
									if(component.getId().equals(componentId)) {
										resourceContainer.addComponent(component);
									}
								}
							}
							
						}
					}
				}	
				
						
		}
		for(ResourceContainer con : resourceContainers) {
			System.out.println("res size:" + con.getComponents().size());
			for(Component com : con.getComponents()) {
				System.out.println(com.getEntityName());
			}
		}
	}

	private List<Component> parseComponents(String repositoryFilePath) throws ParserConfigurationException, 
	SAXException, IOException {
		List<Component> componentList = new ArrayList<>();
		File repositoryXmlFile = new File(repositoryFilePath);
		
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		Document xmlDocument = documentBuilder.parse(repositoryXmlFile);
		xmlDocument.getDocumentElement().normalize();
		NodeList componentElements = xmlDocument.getElementsByTagName("components__Repository");
		for(int i = 0; i < componentElements.getLength(); i++) {
			Node node = componentElements.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				System.out.println(element.getAttribute("xsi:type"));
				if (element.getAttribute("xsi:type").equals("repository:BasicComponent")) {
					String id = element.getAttribute("id");
					System.out.println(id);
					String entityName = element.getAttribute("entityName");
					Component component = new Component(id, entityName);
					componentList.add(component);
				}
			}
		}
		System.out.println(componentList.size());
		return componentList;
	}

	private ResourceEnvironment parseResourceEnvironment(String resourceEnvironmentFilePath) throws ParserConfigurationException, 
	SAXException, IOException {
		List<LinkingResource> linkingResources = new ArrayList<>();
		List<ResourceContainer> resourceContainers = new ArrayList<>();
		File resourceEnvironmentFile = new File(resourceEnvironmentFilePath);
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		Document xmlDocument = documentBuilder.parse(resourceEnvironmentFile);
		xmlDocument.getDocumentElement().normalize();
		NodeList resourceElements = xmlDocument.getElementsByTagName("resourceContainer_ResourceEnvironment");
		for(int i = 0; i < resourceElements.getLength(); i++) {
			Node resourceNode = resourceElements.item(i);
			if (resourceNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) resourceNode;
				String id = element.getAttribute("id").substring(1);
				System.out.println("resourceId:" + id);
				String entityName = element.getAttribute("entityName");
				ResourceContainer resourceContainer = new ResourceContainer(id, entityName);
				resourceContainers.add(resourceContainer);
			}
		}
		NodeList linkingElements = xmlDocument.getElementsByTagName("linkingResources__ResourceEnvironment");
		for(int j = 0; j < linkingElements.getLength(); j++) {
			Node node = linkingElements.item(j);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String id = element.getAttribute("id");
				System.out.println("linkingId:" + id);
				String entityName = element.getAttribute("entityName");
				String connectedResources = element.getAttribute("connectedResourceContainers_LinkingResource");
				String sourceId = connectedResources.split(" ")[0].substring(1);
				String targetId = connectedResources.split(" ")[1].substring(1);
				ResourceContainer source = null;
				ResourceContainer target = null;
				for(ResourceContainer container : resourceContainers) {
					String resourceId = container.getId();
					System.out.println("linkingSource:" + resourceId + "  equals  " + sourceId);
					System.out.println("linkingTarget:" + resourceId + "  equals  " + targetId);
					if(resourceId.equals(sourceId)) {
						source = container;
					} else if(resourceId.equals(targetId)) {
						target = container;						
					}
				}
				LinkingResource linkingResource = new LinkingResource(id, entityName, source, target);
				NodeList stereoTypes = xmlDocument.getElementsByTagName("stereotypeApplications");
				for(int i = 0; i < stereoTypes.getLength(); i++) {
					Node stereoTypeNode = stereoTypes.item(0);
					Element stereoTypeElement = (Element) stereoTypeNode;
					String propertyType = stereoTypeElement.getAttribute("xsi:type").split(":")[1];
					if(propertyType.equals("Encryption")) {
						linkingResource.addConfidentialityProperty(ConfidentialityProperty.ENCRYPTED);
					}
				}
				linkingResources.add(linkingResource);
				}
			}
		System.out.println(linkingResources.toString());
		ResourceEnvironment resourceEnvironment = new ResourceEnvironment(resourceContainers, linkingResources);
		return resourceEnvironment;
	}

	private void getArchitectureProperties(String directory) {
		
	}

	@Override
	public List<AbstractArchitectureProperty> getProperties(String modelDirectoryPath) {
		List<String> modelFiles = Stream.of(new File(modelDirectoryPath).listFiles())
				 .filter(file -> !file.isDirectory())
				 .map(File::getName)
				 .collect(Collectors.toList());
		
		String repositoryFileName = findModelSubFileByEnding(modelFiles, ".repository");
		List<Component> components;
		//TODO fix this
		try {
			components = parseComponents(modelDirectoryPath + repositoryFileName);
			String resourceEnvironmentFileName = findModelSubFileByEnding(modelFiles, ".resourceenvironment"); 
			ResourceEnvironment resourceEnvironment = parseResourceEnvironment(modelDirectoryPath + 
					resourceEnvironmentFileName);
			
			String allocationContextFileName = findModelSubFileByEnding(modelFiles, ".allocation");
			String systemFileName = findModelSubFileByEnding(modelFiles, ".system");
			addComponentsToResourceContainers(resourceEnvironment, components,
					modelDirectoryPath + allocationContextFileName, modelDirectoryPath + systemFileName);
			List<AbstractArchitectureProperty> propertyList = new LinkedList<AbstractArchitectureProperty>();
			for (LinkingResource linkingResource : resourceEnvironment.getLinkingResources()) { 
				if (!linkingResource.hasConfidentialityProperty(ConfidentialityProperty.ENCRYPTED)) { 
					continue; // irrelevant linking resource, can skip }
				}
				
				for (Component sourceComponent : linkingResource.getSource().getComponents()) { 
					for (Component targetComponent : linkingResource.getTarget().getComponents()) { 
						propertyList.add(new C4CbseEncryptedLinkingResourceProperty(ArchitecturePropertyType.ENCRYPTED, 
								new Location(sourceComponent.getEntityName()), 
								new Location(targetComponent.getEntityName())));
						propertyList.add(new C4CbseEncryptedLinkingResourceProperty(ArchitecturePropertyType.ENCRYPTED, 
								new Location(targetComponent.getEntityName()), 
								new Location(sourceComponent.getEntityName())));					
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

	@Override
	public void removeProperties(String modelFilePath, List<AbstractArchitectureProperty> violatedProperties) {
		// TODO Auto-generated method stub

	}

}

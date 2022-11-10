package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse.LinkingResource;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse.ResourceContainer;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse.ResourceEnvironment;

public class ResourceEnvironmentTest {

  private ResourceContainer resourceContainer1 = new ResourceContainer("123", "Test");
  private ResourceContainer resourceContainer2 = new ResourceContainer("321", "Caller");
  private ResourceContainer resourceContainer3 = new ResourceContainer("000", "Callee");

  private LinkingResource linkingResource1 = new LinkingResource("456", 
      "Internet", resourceContainer1, resourceContainer2);
  private LinkingResource linkingResource2 = new LinkingResource("654", "4G", 
      resourceContainer2, resourceContainer3);

  private ResourceEnvironment generateResourceEnvironment() {
    List<ResourceContainer> resourceContainers = new LinkedList<ResourceContainer>();
    resourceContainers.add(resourceContainer1);
    resourceContainers.add(resourceContainer2);

    List<LinkingResource> linkingResources = new LinkedList<LinkingResource>();
    linkingResources.add(linkingResource1);

    ResourceEnvironment resourceEnvironment = 
        new ResourceEnvironment(resourceContainers, linkingResources);
    return resourceEnvironment;
  }

  @Test
  public void testConstructor() {
    ResourceEnvironment resourceEnvironment = generateResourceEnvironment();
    assertTrue(resourceEnvironment.getResourceContainers().size() == 2);
    assertTrue(resourceEnvironment.getLinkingResources().size() == 1);
  }

  @Test
  public void testAddLinkingResource() {
    ResourceEnvironment resourceEnvironment = generateResourceEnvironment();
    assertTrue(resourceEnvironment.getLinkingResources().size() == 1);
    resourceEnvironment.addLinkingResource(linkingResource1);
    assertTrue(resourceEnvironment.getLinkingResources().size() == 1);
    resourceEnvironment.addLinkingResource(linkingResource2);
    assertTrue(resourceEnvironment.getLinkingResources().size() == 2);
  }

  @Test
  public void testAddResourceContainer() {
    ResourceEnvironment resourceEnvironment = generateResourceEnvironment();
    assertTrue(resourceEnvironment.getResourceContainers().size() == 2);
    resourceEnvironment.addResourceContainer(resourceContainer1);
    assertTrue(resourceEnvironment.getResourceContainers().size() == 2);
    resourceEnvironment.addResourceContainer(resourceContainer3);
    assertTrue(resourceEnvironment.getResourceContainers().size() == 3);
  }

}

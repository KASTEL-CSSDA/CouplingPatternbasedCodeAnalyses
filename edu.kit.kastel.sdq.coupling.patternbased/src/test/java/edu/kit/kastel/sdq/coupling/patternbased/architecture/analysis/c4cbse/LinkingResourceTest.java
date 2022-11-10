package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse.ConfidentialityProperty;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse.LinkingResource;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse.ResourceContainer;

public class LinkingResourceTest {

  @Test
  public void testConstructor() {
    ResourceContainer resourceContainer1 = new ResourceContainer("123", "container1");
    ResourceContainer resourceContainer2 = new ResourceContainer("456", "container2");
    LinkingResource linkingResource = new LinkingResource("789", "Internet", 
        resourceContainer1, resourceContainer2);
    assertTrue(linkingResource.getId().equals("789"));
    assertTrue(linkingResource.getEntityName().equals("Internet"));
    assertTrue(linkingResource.getSource().equals(resourceContainer1));
    assertTrue(linkingResource.getTarget().equals(resourceContainer2));
  } 

  @Test
  public void testAddConfidentialityProperty() {
    ResourceContainer resourceContainer1 = new ResourceContainer("123", "container1");
    ResourceContainer resourceContainer2 = new ResourceContainer("456", "container2");
    LinkingResource linkingResource = new LinkingResource("789", "Internet", 
        resourceContainer1, resourceContainer2);
    assertTrue(linkingResource.getConfidentialityProperties().size() == 0);
    linkingResource.addConfidentialityProperty(ConfidentialityProperty.ENCRYPTED);
    assertTrue(linkingResource.getConfidentialityProperties().size() == 1);
    linkingResource.addConfidentialityProperty(ConfidentialityProperty.ENCRYPTED);
    assertTrue(linkingResource.getConfidentialityProperties().size() == 1);
  }

  @Test
  public void testEquals() {
    ResourceContainer resourceContainer1 = new ResourceContainer("123", "container1");
    ResourceContainer resourceContainer2 = new ResourceContainer("456", "container2");
    LinkingResource linkingResource1 = new LinkingResource("789", "Internet", 
        resourceContainer1, resourceContainer2);
    LinkingResource linkingResource2 = new LinkingResource("789", "Server", 
        resourceContainer1, resourceContainer2);
    assertTrue(linkingResource1.equals(linkingResource2));
  }
}

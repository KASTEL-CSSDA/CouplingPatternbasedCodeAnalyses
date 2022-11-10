package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse.Component;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse.ResourceContainer;

public class ResourceContainerTest {

  @Test
  public void testConstructor() {
    ResourceContainer resourceContainer = new ResourceContainer("123", "Mobile Phone");
    assertTrue(resourceContainer.getId().equals("123"));
    assertTrue(resourceContainer.getEntityName().equals("Mobile Phone"));
  }

  @Test
  public void testAddComponent() {
    ResourceContainer resourceContainer = new ResourceContainer("123", "Mobile Phone");
    Component component1 = new Component("123", "Test");
    Component component2 = new Component("321", "Caller");
    resourceContainer.addComponent(component1);
    assertTrue(resourceContainer.getComponents().size() == 1);
    resourceContainer.addComponent(component1);
    assertTrue(resourceContainer.getComponents().size() == 1);
    resourceContainer.addComponent(component2);
    assertTrue(resourceContainer.getComponents().size() == 2);
  }

  @Test
  public void testEquals() {
    ResourceContainer resourceContainer1 = new ResourceContainer("123", "Test");
    ResourceContainer resourceContainer2 = new ResourceContainer("123", "Caller");
    assertTrue(resourceContainer1.equals(resourceContainer2)); 
    // equals only based on id similarity!
  }

}

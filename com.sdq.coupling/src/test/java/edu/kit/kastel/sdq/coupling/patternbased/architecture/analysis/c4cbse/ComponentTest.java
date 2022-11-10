package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse.Component;

public class ComponentTest {


@Test
public void testConstructor() {
    Component component = new Component("123", "Test");
    assertTrue(component.getId().equals("123"));
    assertTrue(component.getEntityName().equals("Test"));
}

@Test
public void testEquals() {
    Component component1 = new Component("123", "Test");
    Component component2 = new Component("123", "Caller");
    assertTrue(component1.equals(component2)); // equals only based on id similarity!
}
}

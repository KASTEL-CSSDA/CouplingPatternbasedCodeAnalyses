package edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgEdge;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.JoanaSdgEdge;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.JoanaSdgEdgeSupplier;

public class JoanaSdgEdgeSupplierTest {

  @Test
  public void testGet() {
    JoanaSdgEdgeSupplier joanaSdgEdgeSupplier = new JoanaSdgEdgeSupplier();
    AbstractSdgEdge abstractSdgEdge = joanaSdgEdgeSupplier.get();
    assertTrue(abstractSdgEdge instanceof JoanaSdgEdge);
  }


}

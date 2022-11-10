package edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgVertex;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.JoanaSdgVertex;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.JoanaSdgVertexSupplier;

public class JoanaSdgVertexSupplierTest {

  @Test 
  public void testGet() {
      JoanaSdgVertexSupplier joanaSdgVertexSupplier = new JoanaSdgVertexSupplier();
      AbstractSdgVertex abstractSdgVertex = joanaSdgVertexSupplier.get();
      assertTrue(abstractSdgVertex instanceof JoanaSdgVertex);
  }

}

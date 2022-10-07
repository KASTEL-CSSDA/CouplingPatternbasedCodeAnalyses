package com.sdq.coupling.sdg.joana.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sdq.coupling.sdg.AbstractSdgVertex;
import com.sdq.coupling.sdg.joana.JoanaSdgVertex;
import com.sdq.coupling.sdg.joana.JoanaSdgVertexSupplier;

public class JoanaSdgVertexSupplierTest {

  @Test 
  public void testGet() {
      JoanaSdgVertexSupplier joanaSdgVertexSupplier = new JoanaSdgVertexSupplier();
      AbstractSdgVertex abstractSdgVertex = joanaSdgVertexSupplier.get();
      assertTrue(abstractSdgVertex instanceof JoanaSdgVertex);
  }

}

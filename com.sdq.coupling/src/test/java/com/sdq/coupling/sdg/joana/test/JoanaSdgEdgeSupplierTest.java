package com.sdq.coupling.sdg.joana.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.joana.JoanaSdgEdge;
import com.sdq.coupling.sdg.joana.JoanaSdgEdgeSupplier;

public class JoanaSdgEdgeSupplierTest {

  @Test
  public void testGet() {
    JoanaSdgEdgeSupplier joanaSdgEdgeSupplier = new JoanaSdgEdgeSupplier();
    AbstractSdgEdge abstractSdgEdge = joanaSdgEdgeSupplier.get();
    assertTrue(abstractSdgEdge instanceof JoanaSdgEdge);
  }


}

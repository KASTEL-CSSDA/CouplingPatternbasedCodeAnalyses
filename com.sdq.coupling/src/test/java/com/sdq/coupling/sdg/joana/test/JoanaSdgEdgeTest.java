package com.sdq.coupling.sdg.joana.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sdq.coupling.sdg.SdgEdgeType;
import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.joana.JoanaSdgEdge;

public class JoanaSdgEdgeTest {

  @Test
  public void testSetEdgeKindDD() {
    JoanaSdgEdge joanaSdgEdge = new JoanaSdgEdge();
    joanaSdgEdge.setEdgeKind("DD");
    assertTrue(((AbstractSdgEdge) joanaSdgEdge).getEdgeType() == SdgEdgeType.DD);
  }

  @Test
  public void testSetEdgeKindPS() {
    JoanaSdgEdge joanaSdgEdge = new JoanaSdgEdge();
    joanaSdgEdge.setEdgeKind("PS");
    assertTrue(((AbstractSdgEdge) joanaSdgEdge).getEdgeType() == SdgEdgeType.PS);
  }

  @Test
  public void testSetEdgeKindCL() {
    JoanaSdgEdge joanaSdgEdge = new JoanaSdgEdge();
    joanaSdgEdge.setEdgeKind("CL");
    assertTrue(((AbstractSdgEdge)joanaSdgEdge).getEdgeType() == SdgEdgeType.CL);
  }

  @Test
  public void testSetEdgeKindUnspecified() {
    JoanaSdgEdge joanaSdgEdge1 = new JoanaSdgEdge();
    joanaSdgEdge1.setEdgeKind(null);
    assertTrue(((AbstractSdgEdge)joanaSdgEdge1).getEdgeType() == SdgEdgeType.UNSPECIFIED);

    JoanaSdgEdge joanaSdgEdge2 = new JoanaSdgEdge();
    joanaSdgEdge2.setEdgeKind("ABC");
    assertTrue(((AbstractSdgEdge)joanaSdgEdge2).getEdgeType() == SdgEdgeType.UNSPECIFIED);
  }

}

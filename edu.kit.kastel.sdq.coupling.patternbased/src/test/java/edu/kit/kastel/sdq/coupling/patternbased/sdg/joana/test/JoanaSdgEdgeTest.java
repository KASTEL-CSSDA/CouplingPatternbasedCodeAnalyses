package edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgEdge;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.SdgEdgeType;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.JoanaSdgEdge;

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

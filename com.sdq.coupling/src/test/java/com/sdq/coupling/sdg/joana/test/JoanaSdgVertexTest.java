package com.sdq.coupling.sdg.joana.test;

import static org.junit.Assert.*;

import com.sdq.coupling.sdg.AbstractSdgVertex;
import com.sdq.coupling.sdg.SdgVertexType;
import com.sdq.coupling.sdg.joana.JoanaSdgVertex;

import org.junit.Test;

public class JoanaSdgVertexTest {
  
  @Test
  public void testSetNodeBcIndex() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeBcIndex(-2);
    assertTrue(vertex.getNodeBcIndex() == -2);
    vertex.setNodeBcIndex(3);
    assertTrue(vertex.getNodeBcIndex() == 3);
    vertex.setNodeBcIndex(0);
    assertTrue(vertex.getNodeBcIndex() == 0);
  }
  
  @Test
  public void testSetNodeBcName() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeBcName("&lt;exception&gt;");
    assertTrue(vertex.getNodeBcName().equals("&lt;exception&gt;"));
    vertex.setNodeBcName(null);
    assertTrue(vertex.getNodeBcName() == null);
  }
  
  @Test
  public void testSetNodeEc() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeEc(0);
    assertTrue(vertex.getNodeEc() == 0);
    vertex.setNodeEc(5);
    assertTrue(vertex.getNodeEc() == 5);
    vertex.setNodeEc(-2);
    assertTrue(vertex.getNodeEc() == -2);
  }
  
  @Test
  public void testSetNodeEr() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeEr(0);
    assertTrue(vertex.getNodeEr() == 0);
    vertex.setNodeEr(5);
    assertTrue(vertex.getNodeEr() == 5);
    vertex.setNodeEr(-2);
    assertTrue(vertex.getNodeEr() == -2);
  }
  
  @Test
  public void testSetNodeKindCALL() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeKind("CALL");
    assertTrue(((AbstractSdgVertex) vertex).getVertexType() == SdgVertexType.CALL);
  }
  
  @Test
  public void testSetNodeKindACTI() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeKind("ACTI");
    assertTrue(((AbstractSdgVertex) vertex).getVertexType() == SdgVertexType.ACTI);
  }
  
  @Test
  public void testSetNodeKindACTO() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeKind("ACTO");
    assertTrue(((AbstractSdgVertex) vertex).getVertexType() == SdgVertexType.ACTO);
  }
  
  @Test
  public void testSetNodeKindENTR() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeKind("ENTR");
    assertTrue(((AbstractSdgVertex) vertex).getVertexType() == SdgVertexType.ENTR);
  }
  
  @Test
  public void testSetNodeKindUNSPECIFIED() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeKind(null);
    assertTrue(((AbstractSdgVertex) vertex).getVertexType() == SdgVertexType.UNSPECIFIED);
    vertex.setNodeKind("randomString");
    assertTrue(((AbstractSdgVertex) vertex).getVertexType() == SdgVertexType.UNSPECIFIED);
  }
  
  @Test
  public void testSetNodeLabel() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeLabel("_exception_");
    assertTrue(vertex.getNodeLabel().equals("_exception_"));
    vertex.setNodeLabel(null);
    assertTrue(vertex.getNodeLabel() == null);
  }
  
  @Test
  public void testSetNodeLocalDef() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeLocalDef("[args]");
    assertTrue(vertex.getNodeLocalDef().equals("[args]"));
    vertex.setNodeLocalDef(null);
    assertTrue(vertex.getNodeLocalDef() == null);
  }
  
  @Test
  public void testSetLocalUse() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeLocalUse("[client]");
    assertTrue(vertex.getNodeLocalUse().equals("[client]"));
    vertex.setNodeLocalUse(null);
    assertTrue(vertex.getNodeLocalUse() == null);
  }
  
  @Test
  public void testSetOperation() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeOperation("call");
    assertTrue(vertex.getNodeOperation().equals("call"));
    vertex.setNodeOperation(null);
    assertTrue(vertex.getNodeOperation() == null);
  }
  
  @Test
  public void testSetNodeProc() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeProc(0);
    assertTrue(vertex.getNodeProc() == 0);
    vertex.setNodeProc(5);
    assertTrue(vertex.getNodeProc() == 5);
  }
  
  @Test
  public void testSetNodeSc() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeSc(0);
    assertTrue(vertex.getNodeSc() == 0);
    vertex.setNodeSc(235);
    assertTrue(vertex.getNodeSc() == 235);
  }
  
  @Test
  public void testSetNodeSr() {
    JoanaSdgVertex vertex = new JoanaSdgVertex();
    vertex.setNodeSr(0);
    assertTrue(vertex.getNodeSr() == 0);
    vertex.setNodeSr(235);
    assertTrue(vertex.getNodeSr() == 235);
  }
  

}

package com.sdq.coupling.sdg.joana;

import java.util.Arrays;

import com.sdq.coupling.sdg.AbstractSdgVertex;
import com.sdq.coupling.sdg.SdgVertexType;
import com.sdq.coupling.util.Location;

/**
 * Represents a vertex in the system dependence graph with specific variables
 * for joana.
 *
 * @author Laura
 *
 */
public class JoanaSdgVertex extends AbstractSdgVertex {

  private String nodeKind = "";
  private String nodeSource = "";
  private int nodeProc;
  private String nodeOperation = "";
  private String nodeLabel = "";
  private String nodeBcName = "";
  private int nodeBcIndex;
  private int nodeSr;
  private int nodeSc;
  private int nodeEr;
  private int nodeEc;
  private String nodeLocalDef = "";
  private String nodeLocalUse = "";

  public String getNodeKind() {
    return nodeKind;
  }

  /**
   * Sets the internal node kind string and also the sdg vertex type
   * of the abstract superclass based on the passed string.
   *
   * @param nodeKind The node type to set.
   */
  public void setNodeKind(String nodeKind) {
    this.nodeKind = nodeKind;
    
    if (nodeKind == null) {
      this.setVertexType(SdgVertexType.UNSPECIFIED);
      return;
    }
    switch (nodeKind) {
      case "CALL":
        this.setVertexType(SdgVertexType.CALL);
        break;
      case "ENTR":
        this.setVertexType(SdgVertexType.ENTR);
        break;
      case "ACTI":
        this.setVertexType(SdgVertexType.ACTI);
        break;
      case "ACTO":
        this.setVertexType(SdgVertexType.ACTO);
        break;
      case "NORM":
        this.setVertexType(SdgVertexType.NORM);
        break;
      default:
        this.setVertexType(SdgVertexType.UNSPECIFIED);
        break;
        // TODO: other cases
    }
  }

  public String getNodeSource() {
    return nodeSource;
  }

  public void setNodeSource(String nodeSource) {
    this.nodeSource = nodeSource;
  }

  public int getNodeProc() {
    return nodeProc;
  }

  public void setNodeProc(int nodeProc) {
    this.nodeProc = nodeProc;
  }

  public String getNodeOperation() {
    return nodeOperation;
  }

  public void setNodeOperation(String nodeOperation) {
    this.nodeOperation = nodeOperation;
  }

  public String getNodeLabel() {
    return nodeLabel;
  }

  public void setNodeLabel(String nodeLabel) {
    this.nodeLabel = nodeLabel;
  }

  public String getNodeBcName() {
    return nodeBcName;
  }

  /**
   * Parses the location of the vertex based on the joana naming scheme.
   *
   * @param nodeBcName
   */
  public void setNodeBcName(String nodeBcName) {
    this.nodeBcName = nodeBcName;
    if (nodeBcName == null || nodeBcName.equals("")) {
      return;
    }

    String[] parts = nodeBcName.split("\\.");
    int length = parts.length;
    if (length >= 3) {
      String methodName = parts[length - 1].split("\\(")[0];
      String className = parts[length - 2];
      String packageName = String.join(".", Arrays.copyOfRange(parts, 0, length - 2));
      Location location = new Location(packageName, className, methodName);
      super.setLocation(location);
    }
  }

  public int getNodeBcIndex() {
    return nodeBcIndex;
  }

  public void setNodeBcIndex(int nodeBcIndex) {
    this.nodeBcIndex = nodeBcIndex;
  }

  public int getNodeSr() {
    return nodeSr;
  }

  public void setNodeSr(int nodeSr) {
    this.nodeSr = nodeSr;
    super.setSourceCodeLine(nodeSr);
  }

  public int getNodeSc() {
    return nodeSc;
  }

  public void setNodeSc(int nodeSc) {
    this.nodeSc = nodeSc;
  }

  public int getNodeEr() {
    return nodeEr;
  }

  public void setNodeEr(int nodeEr) {
    this.nodeEr = nodeEr;
  }

  public int getNodeEc() {
    return nodeEc;
  }

  public void setNodeEc(int nodeEc) {
    this.nodeEc = nodeEc;
  }

  public String getNodeLocalDef() {
    return nodeLocalDef;
  }

  public void setNodeLocalDef(String nodeLocalDef) {
    this.nodeLocalDef = nodeLocalDef;
  }

  public String getNodeLocalUse() {
    return nodeLocalUse;
  }

  public void setNodeLocalUse(String nodeLocalUse) {
    this.nodeLocalUse = nodeLocalUse;
  }

  @Override
  public String toString() {
    String properties;
    String violations;
    if (!super.getPatternViolations().isEmpty()) {
      violations = super.getPatternViolations().toString();
    } else {
      violations = "no violations";
    }
    if (!super.getArchitectureProperties().isEmpty()) {
      properties = super.getArchitectureProperties().toString();
    } else {
      properties = "no properties";
    }
    return "{Vertex (" + super.getVertexType() + "): location=" 
        + super.getLocation().toString() + ", line="
        + super.getSourceCodeLine() + ", label=\"" 
        + this.nodeLabel + " ,properties: " + properties + " ,violations: "
        + violations + "\"}";
  }
}

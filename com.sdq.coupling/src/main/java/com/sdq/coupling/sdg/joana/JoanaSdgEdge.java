package com.sdq.coupling.sdg.joana;

import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.SdgEdgeType;

/**
 * Represents a vertex in the system dependence graph with specific variables
 * for joana.
 *
 * @author Laura
 *
 */
public class JoanaSdgEdge extends AbstractSdgEdge {
  private String edgeKind;

  public String getEdgeKind() {
    return edgeKind;
  }

  /**
   * Sets the internal edge kind string and also the sdg edge type
   * of the abstract superclass based on the passed string.
   *
   * @param edgeKind The edge kind to be set.
   */
  public void setEdgeKind(String edgeKind) {
    this.edgeKind = edgeKind;
    
    if (edgeKind == null) {
      this.setEdgeType(SdgEdgeType.UNSPECIFIED);
      return;
    }
    switch (edgeKind) {
      case "DD":
        this.setEdgeType(SdgEdgeType.DD);
        break;
      case "PS":
        this.setEdgeType(SdgEdgeType.PS);
        break;
      case "CL":
        this.setEdgeType(SdgEdgeType.CL);
        break;
      case "CF":
        this.setEdgeType(SdgEdgeType.CF);
        break;
      default:
        this.setEdgeType(SdgEdgeType.UNSPECIFIED);
        break;
        // TODO: other cases
    }
  }
}

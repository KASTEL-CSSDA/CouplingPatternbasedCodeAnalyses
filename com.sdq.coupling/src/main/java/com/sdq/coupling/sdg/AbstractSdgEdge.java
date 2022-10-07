package com.sdq.coupling.sdg;

/**
 * Represents an abstract sdg edge.
 *
 * @author Laura
 *
 */
public abstract class AbstractSdgEdge {
  private SdgEdgeType edgeType;

  public SdgEdgeType getEdgeType() {
    return this.edgeType;
  }

  public void setEdgeType(SdgEdgeType edgeType) {
    this.edgeType = edgeType;
  }
}

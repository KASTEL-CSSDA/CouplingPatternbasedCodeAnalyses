package com.sdq.coupling.sdg;

import java.util.HashSet;
import java.util.Set;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.code.analysis.AbstractPatternViolation;

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

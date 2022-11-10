package edu.kit.kastel.sdq.coupling.patternbased.sdg;

import java.util.HashSet;
import java.util.Set;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.ArchitecturePropertyType;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.AbstractPatternViolation;

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

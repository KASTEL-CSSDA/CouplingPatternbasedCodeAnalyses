package edu.kit.kastel.sdq.coupling.patternbased.sdg;

import java.util.HashSet;
import java.util.Set;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.ArchitecturePropertyType;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.AbstractPatternViolation;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.PatternViolationType;
import edu.kit.kastel.sdq.coupling.patternbased.util.Location;

/**
 * Represents an abstract sdg vertex. A sdg vertex is always associated 
 * with a location in the code. It can be assigned a set of architecture
 * properties and pattern violations.
 *
 * @author Laura
 *
 */
public abstract class AbstractSdgVertex {
  private SdgVertexType vertexType = SdgVertexType.UNSPECIFIED;
  private Location location = new Location("", "", "");
  private int sourceCodeLine;
  private Set<AbstractArchitectureProperty> architectureProperties = 
      new HashSet<AbstractArchitectureProperty>();
  private Set<AbstractPatternViolation> patternViolations = 
      new HashSet<AbstractPatternViolation>();

  public void setVertexType(SdgVertexType vertexType) {
    this.vertexType = vertexType;
  }

  public SdgVertexType getVertexType() {
    return this.vertexType;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Location getLocation() {
    return this.location;
  }

  public void setSourceCodeLine(int sourceCodeLine) {
    this.sourceCodeLine = sourceCodeLine;
  }

  public int getSourceCodeLine() {
    return this.sourceCodeLine;
  }

  public Set<AbstractArchitectureProperty> getArchitectureProperties() {
    return this.architectureProperties;
  }

  /**
   * Adds an architecture property to the vertex.
   *
   * @param architectureProperty The property to add.
   */
  public void addArchitectureProperty(AbstractArchitectureProperty architectureProperty) {
    if (!this.architectureProperties.contains(architectureProperty)) {
      this.architectureProperties.add(architectureProperty);
    }
  }

  /**
   * Determines if the vertex has the passed architecture property.
   *
   * @param architecturePropertyType The property to check for.
   * @return Returns true if the vertex has the passed property.
   */
  public boolean hasArchitecturePropertyType(ArchitecturePropertyType architecturePropertyType) {
    for (AbstractArchitectureProperty architectureProperty : this.architectureProperties) {
      if (architectureProperty.getArchitecturePropertyType().equals(architecturePropertyType)) {
        return true;
      }
    }
    return false;
  }

  public Set<AbstractPatternViolation> getPatternViolations() {
    return this.patternViolations;
  }

  /**
   * Adds a pattern violation to the vertex.
   *
   * @param patternViolation The violation to add.
   */
  public void addPatternViolation(AbstractPatternViolation patternViolation) {
    if (!this.patternViolations.contains(patternViolation)) {
      this.patternViolations.add(patternViolation);
    }
  }
  
  /**
   * Determines if the vertex has the passed pattern violation.
   *
   * @param patternViolationType The pattern violation to check for.
   * @return Returns true if the vertex has the passed pattern violation.
   */
  public boolean hasPatternViolationType(PatternViolationType patternViolationType) {
    for (AbstractPatternViolation patternViolation : this.patternViolations) {
      if (patternViolation.getViolationType().equals(patternViolationType)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "{Vertex (" + this.vertexType + "): location=" + this.location.toString() 
        + ", line=" + this.sourceCodeLine + "}";
  }
}

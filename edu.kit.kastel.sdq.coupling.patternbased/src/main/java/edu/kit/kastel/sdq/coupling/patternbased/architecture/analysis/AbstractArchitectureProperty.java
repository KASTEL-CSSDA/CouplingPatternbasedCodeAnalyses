package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis;

/**
 * Represent an abstract architecture property that can be violated.
 *
 * @author Laura
 *
 */
public abstract class AbstractArchitectureProperty {
  private ArchitecturePropertyType architecturePropertyType;
  private boolean isViolated;

  public AbstractArchitectureProperty(ArchitecturePropertyType architecturePropertyType) {
    this.architecturePropertyType = architecturePropertyType;
  }

  public ArchitecturePropertyType getArchitecturePropertyType() {
    return this.architecturePropertyType;
  }

  public void setIsViolated(boolean isViolated) {
    this.isViolated = isViolated;
  }

  public boolean getIsViolated() {
    return this.isViolated;
  }
}

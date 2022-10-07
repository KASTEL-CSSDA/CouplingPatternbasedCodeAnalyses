package com.sdq.coupling.code.analysis;

import java.util.List;

import com.sdq.coupling.util.Location;

/**
 * Represents an abstract pattern based code violation. 
 *
 * @author Laura
 *
 */
public abstract class AbstractPatternViolation {
  private Location errorMethod;
  private Location violatedMethod;
  private PatternViolationType violationType;
  private List<Integer> affectedLines;

  /**
   * Constructs an abstract pattern violation.
   *
   * @param errorMethod The method in which the violation occurs.
   * @param violatedUsageLocation The class and method which is used incorrectly.
   * @param violationType The violation type of the error.
   * @param affectedLines The lines affected by the error.
   */
  public AbstractPatternViolation(Location errorMethod, Location violatedUsageLocation,
      PatternViolationType violationType, List<Integer> affectedLines) {
    this.errorMethod = errorMethod; 
    this.violatedMethod = violatedUsageLocation;
    this.violationType = violationType;
    this.affectedLines = affectedLines;
  }

  public Location getErrorMethodLocation() {
    return this.errorMethod;
  }

  public boolean isErrorMethod(Location location) {
    return this.errorMethod.equals(location);
  }

  public Location getViolatedUsageLocation() {
    return this.violatedMethod;
  }

  public boolean isViolatedMethod(Location location) {
    return this.violatedMethod.equals(location);
  }

  public PatternViolationType getViolationType() {
    return this.violationType;
  }

  public List<Integer> getAffectedLines() {
    return this.affectedLines;
  }

  @Override
  public String toString() {
    return "{PatternViolation (" + this.getViolationType() + "): errorMethod=" 
      + this.errorMethod.toString() + ", violatedMethod=" + this.violatedMethod.toString() 
      + ", lines=" + this.affectedLines.toString() + "}";
  }
}

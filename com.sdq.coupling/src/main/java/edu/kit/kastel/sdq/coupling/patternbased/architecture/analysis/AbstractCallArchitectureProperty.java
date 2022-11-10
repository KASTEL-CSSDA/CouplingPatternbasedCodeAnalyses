package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis;

import edu.kit.kastel.sdq.coupling.patternbased.util.Location;

/**
 * Represents an abstract call architecture property that realizes the 
 * AbstractArchitectureProperty class. This class represents a architecture property
 * that is defined on a method call from a class (caller) to another class (callee).
 *
 * @author Laura
 *
 */
public abstract class AbstractCallArchitectureProperty extends AbstractArchitectureProperty {

  private Location caller;
  private Location callee;

  /**
   * Constructs a call architecture property.
   *
   * @param architecturePropertyType The type of the property.
   * @param caller The location of the caller.
   * @param callee the location of the callee.
   */
  public AbstractCallArchitectureProperty(ArchitecturePropertyType architecturePropertyType, 
      Location caller, Location callee) {
    super(architecturePropertyType);
    this.caller = caller;
    this.callee = callee;

  }

  public abstract boolean isCaller(Location location);

  public abstract boolean isCallee(Location location);

  public Location getCaller() {
    return this.caller;
  }

  public Location getCallee() {
    return this.callee;
  }

  @Override
  public String toString() {
    return "{CallArchitectureProperty (" + this.getArchitecturePropertyType() 
      + "): caller=" + this.caller.toString() + ", callee=" + this.callee.toString() + "}";
  }
}

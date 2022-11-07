package com.sdq.coupling.architecture.analysis;

import com.sdq.coupling.util.Location;

/**
 * Represents an abstract method architecture property that realizes the
 * AbstractArchitectureProperty class. This class represents a property that is
 * defined on a single method.
 *
 * @author Laura
 *
 */
public abstract class AbstractMethodArchitectureProperty extends AbstractArchitectureProperty {

  private Location location;

  public AbstractMethodArchitectureProperty(ArchitecturePropertyType architecturePropertyType, 
      Location location) {
    super(architecturePropertyType);
    this.location = location;
  }

  public Location getLocation() {
    return this.location;
  }
}

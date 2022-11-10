package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.carisma;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractCallArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.ArchitecturePropertyType;
import edu.kit.kastel.sdq.coupling.patternbased.util.Location;

/**
 * Implements the AbstractCallArchitectureProperty for Carisma secure
 * dependencies. A Carisma secure dependency defines a security property for
 * calls from one class (caller) to a method of another class (callee).
 *
 * @author Laura
 *
 */
public class CarismaSecureDependencyProperty extends AbstractCallArchitectureProperty {

  public CarismaSecureDependencyProperty(ArchitecturePropertyType architecturePropertyType, 
      Location caller, Location callee) {
    super(architecturePropertyType, caller, callee);
  }

  @Override
  public boolean isCaller(Location location) {
    return location.hasSameClassAndPackage(super.getCaller());
  }

  @Override
  public boolean isCallee(Location location) {
    return location.equals(super.getCallee());
  }

}

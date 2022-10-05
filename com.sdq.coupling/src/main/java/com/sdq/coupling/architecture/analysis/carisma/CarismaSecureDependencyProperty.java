package com.sdq.coupling.architecture.analysis.carisma;

import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.architecture.analysis.AbstractCallArchitectureProperty;
import com.sdq.coupling.util.Location;

/***
 * Implements the AbstractCallArchitectureProperty for Carisma secure dependencies.
 * A Carisma secure dependency defines a security property for calls from one class (caller) to a method of another class (callee).
 * @author Laura
 *
 */
public class CarismaSecureDependencyProperty extends AbstractCallArchitectureProperty {

	public CarismaSecureDependencyProperty(ArchitecturePropertyType architecturePropertyType, Location caller,
			Location callee) {
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

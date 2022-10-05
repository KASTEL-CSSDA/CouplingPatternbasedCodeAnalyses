package com.sdq.coupling.architecture.analysis;

import com.sdq.coupling.util.Location;

public abstract class AbstractMethodArchitectureProperty extends AbstractArchitectureProperty {
	
	private Location location;

	public AbstractMethodArchitectureProperty(ArchitecturePropertyType architecturePropertyType, Location location) {
		super(architecturePropertyType);
		this.location = location;
	}

	public Location getLocation() {
		return this.location;
	}
}

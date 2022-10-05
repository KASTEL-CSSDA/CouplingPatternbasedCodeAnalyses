package com.sdq.coupling.architecture.analysis;

import com.sdq.coupling.util.Location;

public abstract class AbstractCallArchitectureProperty extends AbstractArchitectureProperty {
	
	private Location caller;
	private Location callee;

	public AbstractCallArchitectureProperty(ArchitecturePropertyType architecturePropertyType, Location caller, Location callee) {
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
		return "{CallArchitectureProperty (" + this.getArchitecturePropertyType() + "): caller=" 
				+ this.caller.toString() + ", callee=" + this.callee.toString() + "}";
	}
}

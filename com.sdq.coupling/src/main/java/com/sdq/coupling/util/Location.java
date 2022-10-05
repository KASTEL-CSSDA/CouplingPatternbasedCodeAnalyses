package com.sdq.coupling.util;

public class Location {
	private String packageName;
	private String className;
	private String methodName;
	
	public Location(String className) {
		this.className = className; // TODO: not ideal, maybe have classname as first everywhere
	}
	
	public Location(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}

	public Location(String packageName, String className, String methodName) {
		this.packageName = packageName;
		this.className = className;
		this.methodName = methodName;
	}
	
	public String getPackageName() {
		return this.packageName;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public String getMethodName() {
		return this.methodName;
	}
	
	public boolean hasSameClass(Location location) {
		return location.className.equals(this.className);
	}
	
	public boolean hasSameClassAndPackage(Location location) {
		return location.getPackageName().equals(this.packageName) && location.getClassName().equals(this.className);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (!(obj instanceof Location)) {
			return false;
	    }
		
		Location t = (Location) obj;
	    return t.getPackageName().equals(this.packageName) && t.getClassName().equals(this.className) 
	    		&& t.getMethodName().equals(this.methodName);
	}
	
	
	@Override
	public String toString() {
		if (this.getMethodName() == null) {
			return this.getPackageName() + "." + this.getClassName();
		}
		return this.getPackageName() + "." + this.getClassName() + "." + this.getMethodName() + "()";
	}
}

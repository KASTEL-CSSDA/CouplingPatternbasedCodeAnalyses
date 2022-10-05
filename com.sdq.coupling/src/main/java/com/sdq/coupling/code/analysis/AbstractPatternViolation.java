package com.sdq.coupling.code.analysis;

import java.util.List;

import com.sdq.coupling.util.Location;

public abstract class AbstractPatternViolation {
	private Location errorMethod;
	private Location violatedMethod;
	private PatternViolationType violationType;
	private List<Integer> affectedLines;
	
	public AbstractPatternViolation(Location errorMethod, Location violatedUsageLocation, PatternViolationType violationType,
			List<Integer> affectedLines) {
		this.errorMethod = errorMethod; // location where the error occurs in the program
		this.violatedMethod = violatedUsageLocation; // class and method which is used incorrectly
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
	
	public boolean isErrorMethodLocation(Location location) {
		return this.errorMethod.equals(location);
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

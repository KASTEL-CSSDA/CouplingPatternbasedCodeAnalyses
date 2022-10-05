package com.sdq.coupling.sdg;

import java.util.HashSet;
import java.util.Set;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.code.analysis.PatternViolationType;
import com.sdq.coupling.util.Location;

public abstract class AbstractSdgVertex {
	private SdgVertexType vertexType = SdgVertexType.UNSPECIFIED;
	private Location location = new Location("", "", "");
	private int sourceCodeLine;
	private Set<AbstractArchitectureProperty> architectureProperties = new HashSet<AbstractArchitectureProperty>();
	private Set<AbstractPatternViolation> patternViolations = new HashSet<AbstractPatternViolation>();
	
	public void setVertexType(SdgVertexType vertexType) {
		this.vertexType = vertexType;
	}
	
	public SdgVertexType getVertexType() {
		return this.vertexType;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setSourceCodeLine(int sourceCodeLine) {
		this.sourceCodeLine = sourceCodeLine;
	}
	
	public int getSourceCodeLine() {
		return this.sourceCodeLine;
	}
	
	public Set<AbstractArchitectureProperty> getArchitectureProperties() {
		return this.architectureProperties;
	}
	
	public void addArchitectureProperty(AbstractArchitectureProperty architectureProperty) {
		if (!this.architectureProperties.contains(architectureProperty)) {
			this.architectureProperties.add(architectureProperty);
		}
	}
	
	public boolean hasArchitecturePropertyType(ArchitecturePropertyType architecturePropertyType) {
		for (AbstractArchitectureProperty architectureProperty : this.architectureProperties) {
			if (architectureProperty.getArchitecturePropertyType().equals(architecturePropertyType)) {
				return true;
			}
		}
		return false;
	}
	
	public Set<AbstractPatternViolation> getPatternViolations() {
		return this.patternViolations;
	}
	
	public void addPatternViolation(AbstractPatternViolation patternViolation) {
		if (!this.patternViolations.contains(patternViolation)) {
			this.patternViolations.add(patternViolation);
		}
	}
	
	public boolean hasPatternViolationType(PatternViolationType patternViolationType) {
		for (AbstractPatternViolation patternViolation : this.patternViolations) {
			if (patternViolation.getViolationType().equals(patternViolationType)) {
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public String toString() {
		return "{Vertex (" + this.vertexType + "): location=" + 
		this.location.toString() + ", line=" + this.sourceCodeLine + "}";
	}
}


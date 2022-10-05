package com.sdq.coupling.mapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.code.analysis.PatternViolationType;

public abstract class AbstractPropertyViolationMapping {
	private HashMap<ArchitecturePropertyType, List<PatternViolationType>> mapping = new HashMap<ArchitecturePropertyType, List<PatternViolationType>>();
	
	public void addPropertyToViolationMapping(ArchitecturePropertyType architecturePropertyType, PatternViolationType violationType) {
		if (!mapping.containsKey(architecturePropertyType)) {
			mapping.put(architecturePropertyType, new LinkedList<PatternViolationType>());
		}
		
		if (!mapping.get(architecturePropertyType).contains(violationType)) {
			mapping.get(architecturePropertyType).add(violationType);
		}
	}
	
	public boolean isPropertyViolatedByViolation(ArchitecturePropertyType architecturePropertyType, PatternViolationType violationType) {
		if (mapping.containsKey(architecturePropertyType)) {
			return mapping.get(architecturePropertyType).contains(violationType);
		}
		
		return false;
	}
	
	public boolean isPropertyViolatedByViolation(AbstractArchitectureProperty architectureProperty, AbstractPatternViolation violation) {
		return this.isPropertyViolatedByViolation(architectureProperty.getArchitecturePropertyType(), violation.getViolationType());
	}
}

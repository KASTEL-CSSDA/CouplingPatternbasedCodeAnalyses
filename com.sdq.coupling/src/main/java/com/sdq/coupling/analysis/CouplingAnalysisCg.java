package com.sdq.coupling.analysis;

import java.util.List;

import org.jgrapht.Graph;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.mapping.AbstractPropertyViolationMapping;
import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.AbstractSdgVertex;

public class CouplingAnalysisCg implements ICouplingAnalysis {
	public CouplingAnalysisCg() {
		
	}

	
	public List<AbstractArchitectureProperty> getViolatedProperties(
			List<AbstractArchitectureProperty> architecturePropertyList,
			List<AbstractPatternViolation> patternViolations, Graph<AbstractSdgVertex, AbstractSdgEdge> sdg,
			AbstractPropertyViolationMapping propertyPatternMapping) {
		List<AbstractArchitectureProperty> violatedProperties;
			//TODO implement
		return null;
	}
}

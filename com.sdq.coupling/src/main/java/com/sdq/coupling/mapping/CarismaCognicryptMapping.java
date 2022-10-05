package com.sdq.coupling.mapping;

import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.code.analysis.PatternViolationType;

public class CarismaCognicryptMapping extends AbstractPropertyViolationMapping {
	public CarismaCognicryptMapping() {
		super.addPropertyToViolationMapping(
				ArchitecturePropertyType.ENCRYPTED, 
				PatternViolationType.ENCRYPTION);
	}
}

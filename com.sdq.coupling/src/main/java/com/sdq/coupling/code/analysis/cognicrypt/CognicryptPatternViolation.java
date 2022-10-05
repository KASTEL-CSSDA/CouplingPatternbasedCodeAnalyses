package com.sdq.coupling.code.analysis.cognicrypt;

import java.util.List;

import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.code.analysis.PatternViolationType;
import com.sdq.coupling.util.Location;

public class CognicryptPatternViolation extends AbstractPatternViolation {

	//TODO change affectedLines from List to int
	public CognicryptPatternViolation(Location errorLocation, Location violatedUsageLocation,
			PatternViolationType violationType, List<Integer> affectedLines) {
		super(errorLocation, violatedUsageLocation, violationType, affectedLines);
	}


}

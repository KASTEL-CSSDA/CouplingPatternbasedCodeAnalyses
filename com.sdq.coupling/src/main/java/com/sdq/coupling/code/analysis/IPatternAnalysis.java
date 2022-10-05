package com.sdq.coupling.code.analysis;

import java.util.List;

public interface IPatternAnalysis {
	public List<AbstractPatternViolation> findViolations(String projectFolderPath);
}

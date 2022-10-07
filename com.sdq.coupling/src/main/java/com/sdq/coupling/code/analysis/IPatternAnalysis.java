package com.sdq.coupling.code.analysis;

import java.util.List;

/**
 * Interface for a pattern based code analysis.
 *
 * @author Laura
 *
 */
public interface IPatternAnalysis {
  public List<AbstractPatternViolation> findViolations(String projectFolderPath);
}

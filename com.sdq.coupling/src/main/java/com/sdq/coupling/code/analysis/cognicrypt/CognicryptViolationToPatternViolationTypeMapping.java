package com.sdq.coupling.code.analysis.cognicrypt;

import com.sdq.coupling.code.analysis.PatternViolationType;

/**
 * Maps cognicrypt pattern violations to a pattern violation type.
 *
 * @author Laura
 *
 */
public class CognicryptViolationToPatternViolationTypeMapping {

  public CognicryptViolationToPatternViolationTypeMapping() {

  }

  /**
   * Accepts a String representing a violated class and returns the corresponding
   * pattern violation type.
   *
   * @param violatedClass The class that has been violated in the source code
   * analyzed by cognicrypt.
   * @return Returns the corresponding pattern violation type.
   */
  public PatternViolationType getViolationType(String violatedClass) {
    switch (violatedClass) {
      case "Cipher":
        return PatternViolationType.ENCRYPTION;
      default:
        return null;
    }
  }

}

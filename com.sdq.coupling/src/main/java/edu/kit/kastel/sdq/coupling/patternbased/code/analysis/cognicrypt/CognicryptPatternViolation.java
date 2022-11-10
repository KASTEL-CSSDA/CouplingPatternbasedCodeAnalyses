package edu.kit.kastel.sdq.coupling.patternbased.code.analysis.cognicrypt;

import java.util.List;

import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.AbstractPatternViolation;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.PatternViolationType;
import edu.kit.kastel.sdq.coupling.patternbased.util.Location;

/**
 * Realizes the AbstractPatternViolation class for a cognicrypt violation.
 *
 * @author Laura
 *
 */
public class CognicryptPatternViolation extends AbstractPatternViolation {

  // TODO change affectedLines from List to int
  public CognicryptPatternViolation(Location errorLocation, Location violatedUsageLocation,
      PatternViolationType violationType, List<Integer> affectedLines) {
    super(errorLocation, violatedUsageLocation, violationType, affectedLines);
  }

}

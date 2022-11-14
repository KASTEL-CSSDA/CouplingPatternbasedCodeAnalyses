package edu.kit.kastel.sdq.coupling.patternbased.mapping;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.ArchitecturePropertyType;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.PatternViolationType;

/**
 * Realizes the AbstractPropertyViolationMapping class for
 * c4cbse and cognicrypt.  
 *
 * @author Laura
 *
 */
public class C4CbseCognicryptMapping extends AbstractPropertyViolationMapping {
  public C4CbseCognicryptMapping() {
    super.addPropertyToViolationMapping(ArchitecturePropertyType.ENCRYPTED, 
        PatternViolationType.ENCRYPTION);
  }
}

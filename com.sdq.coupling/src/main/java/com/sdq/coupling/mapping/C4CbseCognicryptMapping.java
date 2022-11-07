package com.sdq.coupling.mapping;

import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.code.analysis.PatternViolationType;

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

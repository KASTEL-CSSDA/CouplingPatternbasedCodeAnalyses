package edu.kit.kastel.sdq.coupling.patternbased.mapping.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.ArchitecturePropertyType;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.PatternViolationType;
import edu.kit.kastel.sdq.coupling.patternbased.mapping.C4CbseCognicryptMapping;

public class C4CbseCognicryptMappingTest {

  @Test
  public void testIsPropertyViolatedByViolation() {
    C4CbseCognicryptMapping mapping = new C4CbseCognicryptMapping();
    assertTrue(mapping.isPropertyViolatedByViolation(ArchitecturePropertyType.ENCRYPTED, 
        PatternViolationType.ENCRYPTION));
    assertFalse(mapping.isPropertyViolatedByViolation(ArchitecturePropertyType.HIGH_SENSITIVITY, 
        PatternViolationType.ENCRYPTION));
  }


}

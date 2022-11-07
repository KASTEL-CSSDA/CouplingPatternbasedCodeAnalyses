package com.sdq.coupling.mapping.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.code.analysis.PatternViolationType;
import com.sdq.coupling.mapping.C4CbseCognicryptMapping;

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

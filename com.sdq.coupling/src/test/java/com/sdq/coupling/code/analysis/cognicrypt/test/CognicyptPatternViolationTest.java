package com.sdq.coupling.code.analysis.cognicrypt.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.sdq.coupling.code.analysis.PatternViolationType;
import com.sdq.coupling.code.analysis.cognicrypt.CognicryptPatternViolation;
import com.sdq.coupling.util.Location;

public class CognicyptPatternViolationTest {

  private Location errorLocation = new Location("com.sdq.example", "Test", "operation");
  private Location violatedUsageLocation = new Location("com.sdq.example", "Caller", "doSomething");
  private PatternViolationType violationType = PatternViolationType.ENCRYPTION;
  private List<Integer> affectedLines = new LinkedList<Integer>();

  @Test
  public void testConstructor() {
    CognicryptPatternViolation cpv = new CognicryptPatternViolation(errorLocation, 
        violatedUsageLocation, violationType,
        affectedLines);
    assertTrue(cpv.getErrorMethodLocation().equals(errorLocation));
    assertTrue(cpv.getViolatedUsageLocation().equals(violatedUsageLocation));
    assertTrue(cpv.getViolationType().equals(violationType));
    assertTrue(cpv.getAffectedLines().equals(affectedLines));
  }

  @Test
  public void testIsErrorMethod() {
    CognicryptPatternViolation cpv = new CognicryptPatternViolation(errorLocation, violatedUsageLocation, violationType,
        affectedLines);
    assertTrue(cpv.isErrorMethod(new Location("com.sdq.example", "Test", "operation")));
    assertFalse(cpv.isErrorMethod(new Location(null, "Caller", null)));
    assertFalse(cpv.isErrorMethod(new Location("com.sdq.example", null, "operation")));
    assertFalse(cpv.isErrorMethod(new Location("com.sdq.example", null, null)));
  }

  @Test
  public void testIsViolatedMethod() {
    CognicryptPatternViolation cpv = new CognicryptPatternViolation(errorLocation, 
        violatedUsageLocation, violationType,
        affectedLines);
    assertTrue(cpv.isViolatedMethod(new Location("com.sdq.example", "Caller", "doSomething")));
    assertFalse(cpv.isViolatedMethod(new Location(null, "Test", null)));
    assertFalse(cpv.isViolatedMethod(new Location("com.sdq.example", null, "doSomething")));
    assertFalse(cpv.isViolatedMethod(new Location("com.sdq.example", "Caller", null)));
  }
}

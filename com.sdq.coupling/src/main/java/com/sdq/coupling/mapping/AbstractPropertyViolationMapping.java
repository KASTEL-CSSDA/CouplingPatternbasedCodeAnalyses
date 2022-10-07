package com.sdq.coupling.mapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.code.analysis.PatternViolationType;

/**
 * Represents an abstract mapping between the properties of 
 * a architecture modeling framework and a patternbased
 * security code analysis framework. Maps architecture propertie types
 * to pattern violation types.
 *
 * @author Laura
 *
 */
public abstract class AbstractPropertyViolationMapping {
  private HashMap<ArchitecturePropertyType, List<PatternViolationType>> mapping = 
      new HashMap<ArchitecturePropertyType, List<PatternViolationType>>();

  /**
   * Adds a architecture property type violation type pair to the mapping.
   *
   * @param architecturePropertyType The architecture property type to add.
   * @param violationType The violation type to add.
   */
  public void addPropertyToViolationMapping(ArchitecturePropertyType architecturePropertyType,
      PatternViolationType violationType) {
    if (!mapping.containsKey(architecturePropertyType)) {
      mapping.put(architecturePropertyType, new LinkedList<PatternViolationType>());
    }

    if (!mapping.get(architecturePropertyType).contains(violationType)) {
      mapping.get(architecturePropertyType).add(violationType);
    }
  }

  /**
   * Checks if a passed architecture property type is violated by the 
   * passed pattern violation type.
   *
   * @param architecturePropertyType The architecture property type to check.
   * @param violationType The pattern violation type to check.
   * @return Returns true if the mapping contains an entry for the passed types.
   */
  public boolean isPropertyViolatedByViolation(ArchitecturePropertyType architecturePropertyType,
      PatternViolationType violationType) {
    if (mapping.containsKey(architecturePropertyType)) {
      return mapping.get(architecturePropertyType).contains(violationType);
    }

    return false;
  }

  public boolean isPropertyViolatedByViolation(AbstractArchitectureProperty architectureProperty,
      AbstractPatternViolation violation) {
    return this.isPropertyViolatedByViolation(architectureProperty.getArchitecturePropertyType(),
        violation.getViolationType());
  }
}

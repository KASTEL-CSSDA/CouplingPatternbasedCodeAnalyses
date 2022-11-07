package com.sdq.coupling.analysis;

import java.util.List;

import org.jgrapht.Graph;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.mapping.AbstractPropertyViolationMapping;
import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.AbstractSdgVertex;

/**
 * Interface for a coupling analysis.
 *
 * @author Laura
 *
 */
public interface ICouplingAnalysis {
  
  /**
   * Determines which architecture properties are violated by the code
   * violations found by the pattern based code analysis.
   *
   * @param architecturePropertyList The architecture properties defines on the 
   * architecture model.
   * @param patternViolations The violations found by the code analysis.
   * @param sdg The system dependence graph of the analyzed jar file .
   * @param propertyPatternMapping Maps architecture property types to violation types.
   * @return Returns a list containing the violated properties.
   */
  List<AbstractArchitectureProperty> getViolatedProperties(
      List<AbstractArchitectureProperty> architecturePropertyList,
      List<AbstractPatternViolation> patternViolations, Graph<AbstractSdgVertex, 
      AbstractSdgEdge> sdg, AbstractPropertyViolationMapping propertyPatternMapping);
}

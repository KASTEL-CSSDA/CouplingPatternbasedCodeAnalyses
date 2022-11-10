package edu.kit.kastel.sdq.coupling.patternbased.analysis;

import java.util.List;

import org.jgrapht.Graph;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.AbstractPatternViolation;
import edu.kit.kastel.sdq.coupling.patternbased.mapping.AbstractPropertyViolationMapping;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgEdge;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgVertex;

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

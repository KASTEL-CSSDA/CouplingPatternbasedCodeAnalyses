package edu.kit.kastel.sdq.coupling.patternbased.analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.jgrapht.Graph;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractCallArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.AbstractPatternViolation;
import edu.kit.kastel.sdq.coupling.patternbased.mapping.AbstractPropertyViolationMapping;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgEdge;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgVertex;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.SdgEdgeType;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.SdgVertexType;

/**
 * Implements the coupling analysis interface using a call graph.
 *
 * @author Laura
 *
 */
public class CouplingAnalysisCg implements ICouplingAnalysis {
  /**
   * Checks the type of the properties an calls the corresponding method to assign the
   * property.
   *
   * @param architecturePropertyList The architecture properties to map.
   * @param sdg The sdg that represents the analyzed jar file.
   * @return Returns a list of vertices with architecture properties.
   */
  private List<AbstractSdgVertex> generateVerticesWithArchitectureProperties(
      List<AbstractArchitectureProperty> architecturePropertyList, 
      Graph<AbstractSdgVertex, AbstractSdgEdge> sdg) {
    List<AbstractSdgVertex> verticesWithProperties = new LinkedList<AbstractSdgVertex>();
    for (AbstractArchitectureProperty property : architecturePropertyList) {
      List<AbstractSdgVertex> temp = new LinkedList<>();
      if (property instanceof AbstractCallArchitectureProperty) {
        temp = assignCallArchitectureProperty((AbstractCallArchitectureProperty) property, sdg);
      }

      for (AbstractSdgVertex vertex : temp) {
        if (!verticesWithProperties.contains(vertex)) {
          verticesWithProperties.add(vertex);
        }
      }
    }
    return verticesWithProperties;
  }

  /**
   * Assigns a call architecture property to the corresponding sdg vertices.
   *
   * @param callProperty The property to assign.
   * @param sdg The sdg that represents the analyzed jar file.
   * @return Returns the vertices with the assigned call architecture property.
   */
  private List<AbstractSdgVertex> assignCallArchitectureProperty(
      AbstractCallArchitectureProperty callProperty,
      Graph<AbstractSdgVertex, AbstractSdgEdge> sdg) {
    List<AbstractSdgVertex> verticesToReturn = new LinkedList<AbstractSdgVertex>();
    for (AbstractSdgVertex vertex : sdg.vertexSet()) {
      // check if vertex is call of other method

      if (vertex.getVertexType() != null && vertex.getVertexType().equals(SdgVertexType.CALL)
          && vertex.getLocation() != null && callProperty.isCaller(vertex.getLocation())) {
        // get edges from call vertex to other vertices
        Set<AbstractSdgEdge> outgoingEdges = sdg.outgoingEdgesOf(vertex);

        // iterate over edges to find edge to callee
        for (AbstractSdgEdge edge : outgoingEdges) {
          if (!edge.getEdgeType().equals(SdgEdgeType.CL)) {
            continue;
          }

          // get target vertex of the edge
          AbstractSdgVertex targetVertex = sdg.getEdgeTarget(edge);

          // if target vertex is not entry to target method, skip vertex
          if (targetVertex.getVertexType() != null 
              && targetVertex.getVertexType().equals(SdgVertexType.ENTR)
              && callProperty.isCallee(targetVertex.getLocation())) {
            vertex.addArchitectureProperty(callProperty);
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Mapped architecture property to vertex:\n" 
                + callProperty.toString() + "\n" + vertex.toString() + "\n");
            verticesToReturn.add(vertex);
          }

        }
      }
    }
    return verticesToReturn;
  }
  
  /**
   * Determines the violated properties and returns them.
   */
  public List<AbstractArchitectureProperty> getViolatedProperties(
      List<AbstractArchitectureProperty> architecturePropertyList, 
      List<AbstractPatternViolation> patternViolations,
      Graph<AbstractSdgVertex, AbstractSdgEdge> sdg, 
      AbstractPropertyViolationMapping propertyPatternMapping) {
    List<AbstractArchitectureProperty> violatedProperties = new LinkedList<>();
    
    // assign architecture properties to the SDG vertices
    List<AbstractSdgVertex> verticesWithProperties = generateVerticesWithArchitectureProperties(
        architecturePropertyList, sdg);
    
    for (AbstractPatternViolation patternViolation : patternViolations) {
      for (AbstractSdgVertex vertexWithProperty : verticesWithProperties) {
        if (patternViolation.getErrorMethodLocation().equals(vertexWithProperty.getLocation())) {
          for (AbstractArchitectureProperty property : vertexWithProperty.getArchitectureProperties()) {
            if (propertyPatternMapping.isPropertyViolatedByViolation(property, patternViolation)) {
              if (!violatedProperties.contains(property)) { 
                violatedProperties.add(property);
              }
            }
          }
        }
      }
    }
    
    // returns all properties which are violated by code violations
    return violatedProperties; 
  }
}

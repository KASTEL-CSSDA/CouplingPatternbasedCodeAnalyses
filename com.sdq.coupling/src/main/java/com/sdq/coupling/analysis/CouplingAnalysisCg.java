package com.sdq.coupling.analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.AbstractCallArchitectureProperty;
import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.mapping.AbstractPropertyViolationMapping;
import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.AbstractSdgVertex;
import com.sdq.coupling.sdg.SdgEdgeType;
import com.sdq.coupling.sdg.SdgVertexType;

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
            System.out.println("Mapped architecture property to vertex:\n" 
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
            if (propertyPatternMapping.isPropertyViolatedByViolation(property, patternViolation) 
                && !violatedProperties.contains(property)) {
              violatedProperties.add(property);
              System.out.println();
            }
          }
        }
      }
    }
    
    // returns all properties which are violated by code violations
    return violatedProperties; 
  }
}

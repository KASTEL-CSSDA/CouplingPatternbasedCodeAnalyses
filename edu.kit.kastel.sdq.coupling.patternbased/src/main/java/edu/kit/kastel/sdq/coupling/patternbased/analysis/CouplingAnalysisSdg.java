package edu.kit.kastel.sdq.coupling.patternbased.analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractCallArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.AbstractPatternViolation;
import edu.kit.kastel.sdq.coupling.patternbased.mapping.AbstractPropertyViolationMapping;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgEdge;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgVertex;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.SdgEdgeType;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.SdgVertexType;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.JoanaSdgEdge;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.JoanaSdgVertex;

/**
 * Implements the coupling analysis interface using a system dependence graph.
 *
 * @author Laura
 *
 */
public class CouplingAnalysisSdg implements ICouplingAnalysis {

  /**
   * Determines the violated properties and returns them.
   */
  public List<AbstractArchitectureProperty> getViolatedProperties(
      List<AbstractArchitectureProperty> architecturePropertyList, 
      List<AbstractPatternViolation> patternViolations,
      Graph<AbstractSdgVertex, AbstractSdgEdge> sdg, 
      AbstractPropertyViolationMapping propertyPatternMapping) {

    // assign architecture properties to the SDG vertices
    List<AbstractSdgVertex> verticesWithProperties = generateVerticesWithArchitectureProperties(
        architecturePropertyList, sdg);
    List<AbstractSdgVertex> verticesWithViolations = 
        generateVerticesWithPatternViolations(patternViolations, sdg);

    // remove useless edges from sdg for path finding and flips ps edges from act-in
    // vertices
    cleanUpSdgForMapping(sdg);

    // returns all properties which are violated by code violations
    return mapPatternViolationsToProperties(verticesWithViolations, 
        verticesWithProperties, propertyPatternMapping, sdg);
  }

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
   * Maps the pattern violations to sdg vertices and returns the vertices.
   *
   * @param patternViolations The violations to map.
   * @param sdg The sdg that represents the analyzed jar file.
   * @return Returns a list of vertices with the passed violations.
   */
  private List<AbstractSdgVertex> generateVerticesWithPatternViolations(
      List<AbstractPatternViolation> patternViolations, Graph<AbstractSdgVertex, 
      AbstractSdgEdge> sdg) {
    List<AbstractSdgVertex> verticesWithViolations = new LinkedList<AbstractSdgVertex>();
    for (AbstractPatternViolation patternViolation : patternViolations) {
      for (AbstractSdgVertex vertex : sdg.vertexSet()) {
        if (vertex.getVertexType() != null && vertex.getVertexType().equals(SdgVertexType.CALL)
            && vertex.getLocation() != null 
            && patternViolation.isErrorMethod(vertex.getLocation())) {
          Set<AbstractSdgEdge> outgoingEdges = sdg.outgoingEdgesOf(vertex);

          for (AbstractSdgEdge edge : outgoingEdges) {
            if (!edge.getEdgeType().equals(SdgEdgeType.CL)) {
              continue;
            }

            // get target vertex of the edge
            AbstractSdgVertex targetVertex = sdg.getEdgeTarget(edge);
            // if target vertex is not incorrectly used method, continue. otherwise add
            // violation
            if (targetVertex.getVertexType() != null 
                && targetVertex.getVertexType().equals(SdgVertexType.ENTR)
                // && patternViolation.isViolatedMethod(targetVertex.getLocation())
                && patternViolation.getAffectedLines().contains(vertex.getSourceCodeLine())) {
              vertex.addPatternViolation(patternViolation);
              Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Mapped pattern violation to vertex:\n" 
                  + patternViolation.toString() + "\n" + vertex.toString() + "\n");
              if (!verticesWithViolations.contains(vertex)) {
                verticesWithViolations.add(vertex);
              }
            }

          }
        }
      }
    }
    return verticesWithViolations;
  }

  /**
   * Removes unnecessary edges from the sdg and flips PS edges which is necessary
   * to traverse the graph.
   *
   * @param sdg The sdg that represents the analyzed jar file.
   */
  private void cleanUpSdgForMapping(Graph<AbstractSdgVertex, AbstractSdgEdge> sdg) {
    // THIS IS SPECIFIC TO JOANA, maybe move somewhere else in the future
    Set<AbstractSdgEdge> edges = sdg.edgeSet();
    Set<AbstractSdgVertex> vertices = sdg.vertexSet();
    List<AbstractSdgEdge> edgesToRemove = new LinkedList<AbstractSdgEdge>();
    List<AbstractSdgEdge> edgesToFlip = new LinkedList<AbstractSdgEdge>();
    for (AbstractSdgEdge edge : edges) {
      if (edge.getEdgeType().equals(SdgEdgeType.PS)) {
        AbstractSdgVertex targetVertex = sdg.getEdgeTarget(edge);
        if (targetVertex.getVertexType().equals(SdgVertexType.ACTI)) {
          edgesToFlip.add(edge);
        }
      } else if (!edge.getEdgeType().equals(SdgEdgeType.DD)) {
        edgesToRemove.add(edge);
      }
    }
    
    for (AbstractSdgVertex vertex : vertices) {
      if (vertex.getVertexType() == SdgVertexType.NORM) {
        JoanaSdgVertex joanaVertex = (JoanaSdgVertex) vertex;
        
        if (joanaVertex.getNodeOperation().equals("declaration")) {
          String variableName = joanaVertex.getNodeLabel().split(" =")[0];
          for (AbstractSdgVertex possibleCallVertex : vertices) {
            JoanaSdgVertex joanaCallVertex = (JoanaSdgVertex) possibleCallVertex;
            String callVariableName = joanaCallVertex.getNodeLabel().split("\\.")[0];
            if (possibleCallVertex.getVertexType() == SdgVertexType.CALL &&
                callVariableName.equals(variableName)) {
              AbstractSdgEdge edge = sdg.addEdge(possibleCallVertex, vertex);
              edge.setEdgeType(SdgEdgeType.DD);
            }
          }
        }
      }
    }

    for (AbstractSdgEdge edge : edgesToRemove) {
      sdg.removeEdge(edge);
    }

    for (AbstractSdgEdge edge : edgesToFlip) {
      AbstractSdgVertex targetVertex = sdg.getEdgeTarget(edge);
      AbstractSdgVertex sourceVertex = sdg.getEdgeSource(edge);
      AbstractSdgEdge newEdge = new JoanaSdgEdge();
      newEdge.setEdgeType(SdgEdgeType.PS);
      sdg.addEdge(targetVertex, sourceVertex, newEdge);
      sdg.removeEdge(edge);
    }

  }

  /**
   * Traverses the graph and looks for paths from vertices with violations to
   * vertices with properties that are violated by the code violation.
   *
   * @param verticesWithViolations The vertices with code violations.
   * @param verticesWithProperties The vertices with architecture properties.
   * @param propertyPatternMapping Maps the property types to the corresponding violation
   * types.
   * @param sdg The sdg that represents the analyzed jar file.
   * @return Returns a list of violated properties.
   */
  private List<AbstractArchitectureProperty> mapPatternViolationsToProperties(
      List<AbstractSdgVertex> verticesWithViolations, 
      List<AbstractSdgVertex> verticesWithProperties,
      AbstractPropertyViolationMapping propertyPatternMapping, 
      Graph<AbstractSdgVertex, AbstractSdgEdge> sdg) {
    List<AbstractArchitectureProperty> violatedProperties 
        = new LinkedList<AbstractArchitectureProperty>();
    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Assigned violations: " + verticesWithViolations.size());
    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Assigned properties: " + verticesWithProperties.size() + "\n");
    for (AbstractSdgVertex v : verticesWithProperties) {
      Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(v.toString());
      Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(v.getArchitectureProperties().toString());
    }
    for (AbstractSdgVertex vertexWithProperty : verticesWithProperties) {
      for (AbstractArchitectureProperty property : vertexWithProperty.getArchitectureProperties()) {
        
        for (AbstractSdgVertex vertexWithViolation : verticesWithViolations) {
          for (AbstractPatternViolation violation : vertexWithViolation.getPatternViolations()) {
            if (propertyPatternMapping.isPropertyViolatedByViolation(property, violation)) {
              DijkstraShortestPath<AbstractSdgVertex, AbstractSdgEdge> dijkstraShortestPath = 
                  new DijkstraShortestPath<AbstractSdgVertex, AbstractSdgEdge>(
                  sdg);
              GraphPath<AbstractSdgVertex, AbstractSdgEdge> shortestPath = dijkstraShortestPath
                  .getPath(vertexWithViolation, vertexWithProperty);
              if (shortestPath != null) {
                String shortestPathString = shortestPath.getVertexList().toString();
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Path found: " + shortestPathString);
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(property + " " + violation);
                
                if (!property.getIsViolated()) {
                  violatedProperties.add(property);
                }
                property.setIsViolated(true);
              }
            }
          }
        }
      }
    }
    return violatedProperties;
  }
}

package com.sdq.coupling.analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.AbstractCallArchitectureProperty;
import com.sdq.coupling.architecture.analysis.AbstractMethodArchitectureProperty;
import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.mapping.AbstractPropertyViolationMapping;
import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.AbstractSdgVertex;
import com.sdq.coupling.sdg.SdgEdgeType;
import com.sdq.coupling.sdg.SdgVertexType;
import com.sdq.coupling.sdg.joana.JoanaSdgEdge;


public class CouplingAnalysisSdg implements ICouplingAnalysis {

	public CouplingAnalysisSdg() {
		
	}

	
	public List<AbstractArchitectureProperty> getViolatedProperties(
			List<AbstractArchitectureProperty> architecturePropertyList,
			List<AbstractPatternViolation> patternViolations, Graph<AbstractSdgVertex, AbstractSdgEdge> sdg,
			AbstractPropertyViolationMapping propertyPatternMapping) {
		
		// assign architecture properties to the SDG vertices
		List<AbstractSdgVertex> verticesWithProperties = generateVerticesWithArchitectureProperties(architecturePropertyList, sdg);
		List<AbstractSdgVertex> verticesWithViolations = generateVerticesWithPatternViolations(patternViolations, sdg);
		
		// remove useless edges from sdg for path finding and flips ps edges from act-in vertices
		cleanUpSdgForMapping(sdg);
		
		// returns all properties which are violated by code violations
		return mapPatternViolationsToProperties(verticesWithViolations, verticesWithProperties, propertyPatternMapping, sdg);
	}

	
	private List<AbstractSdgVertex> generateVerticesWithArchitectureProperties(List<AbstractArchitectureProperty> architecturePropertyList, 
			Graph<AbstractSdgVertex, AbstractSdgEdge> sdg) {
		List<AbstractSdgVertex> verticesWithProperties = new LinkedList<AbstractSdgVertex>();
		for(AbstractArchitectureProperty property : architecturePropertyList) {
			List<AbstractSdgVertex> temp = new LinkedList<>();
			if (property instanceof AbstractCallArchitectureProperty) {
				temp = assignCallArchitectureProperty((AbstractCallArchitectureProperty) property, sdg);
			}
			
			for (AbstractSdgVertex vertex : temp) {
				if (!verticesWithProperties.contains(vertex)){
					verticesWithProperties.add(vertex);
				}
			}
		}
		return verticesWithProperties;
	}
		
	private List<AbstractSdgVertex> assignCallArchitectureProperty(AbstractCallArchitectureProperty callProperty, 
			Graph<AbstractSdgVertex, AbstractSdgEdge> sdg) {
		List<AbstractSdgVertex> verticesToReturn = new LinkedList<AbstractSdgVertex>();
		for (AbstractSdgVertex vertex : sdg.vertexSet()) {
			// check if vertex is call of other method
	
			if (vertex.getVertexType() != null && vertex.getVertexType().equals(SdgVertexType.CALL)
					&& vertex.getLocation() != null && callProperty.isCaller(vertex.getLocation())) {
				System.out.println("Found match: " + vertex.getLocation());
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
					if (targetVertex.getVertexType() != null && targetVertex.getVertexType().equals(SdgVertexType.ENTR)
							&& callProperty.isCallee(targetVertex.getLocation())) {
						vertex.addArchitectureProperty(callProperty);
						System.out.println("Mapped architecture property to vertex:\n" + 
								callProperty.toString() + "\n" + vertex.toString() + "\n");
						verticesToReturn.add(vertex);
					}
						
				}
			}
		} 
		return verticesToReturn;
	}

	
	private List<AbstractSdgVertex> generateVerticesWithPatternViolations(List<AbstractPatternViolation> patternViolations, 
			Graph<AbstractSdgVertex, AbstractSdgEdge> sdg) {
		List<AbstractSdgVertex> verticesWithViolations = new LinkedList<AbstractSdgVertex>();
		for (AbstractPatternViolation patternViolation : patternViolations) {
			for (AbstractSdgVertex vertex : sdg.vertexSet()) {
				if (vertex.getVertexType() != null && vertex.getVertexType().equals(SdgVertexType.CALL)
						&& vertex.getLocation() != null && patternViolation.isErrorMethodLocation(vertex.getLocation())) {
					
					Set<AbstractSdgEdge> outgoingEdges = sdg.outgoingEdgesOf(vertex);
					
					for (AbstractSdgEdge edge : outgoingEdges) {
						if (!edge.getEdgeType().equals(SdgEdgeType.CL)) {
							continue;
						}
						
						// get target vertex of the edge
						AbstractSdgVertex targetVertex = sdg.getEdgeTarget(edge);
						// if target vertex is not incorrectly used method, continue. otherwise add violation
						if (targetVertex.getVertexType() != null && targetVertex.getVertexType().equals(SdgVertexType.ENTR)
								//&& patternViolation.isViolatedMethod(targetVertex.getLocation())
								&& patternViolation.getAffectedLines().contains(vertex.getSourceCodeLine())) {
								vertex.addPatternViolation(patternViolation);
								System.out.println("Mapped pattern violation to vertex:\n" + 
										patternViolation.toString() + "\n" + vertex.toString() + "\n");
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
	
	private void cleanUpSdgForMapping(Graph<AbstractSdgVertex, AbstractSdgEdge> sdg) {
		Set<AbstractSdgEdge> edges = sdg.edgeSet();
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
	
	private List<AbstractArchitectureProperty> mapPatternViolationsToProperties(List<AbstractSdgVertex> verticesWithViolations, 
			List<AbstractSdgVertex> verticesWithProperties, AbstractPropertyViolationMapping propertyPatternMapping, Graph<AbstractSdgVertex, AbstractSdgEdge> sdg) {
		List<AbstractArchitectureProperty> violatedProperties = new LinkedList<AbstractArchitectureProperty>();
		System.out.println("Vertices with violations: " + verticesWithViolations.size());
		System.out.println("Vertices with properties: " + verticesWithProperties.size() + "\n");
		for(AbstractSdgVertex vertexWithProperty : verticesWithProperties) {
			for(AbstractArchitectureProperty property : vertexWithProperty.getArchitectureProperties()) {
				if (property.getIsViolated()) {
					continue;
				}
				for(AbstractSdgVertex vertexWithViolation : verticesWithViolations) {
					for(AbstractPatternViolation violation : vertexWithViolation.getPatternViolations()) {
						if (propertyPatternMapping.isPropertyViolatedByViolation(property, violation)) {
							DijkstraShortestPath<AbstractSdgVertex, AbstractSdgEdge> dijkstraShortestPath 
						      = new DijkstraShortestPath<AbstractSdgVertex, AbstractSdgEdge>(sdg);
						    GraphPath<AbstractSdgVertex, AbstractSdgEdge> shortestPath = dijkstraShortestPath
						      .getPath(vertexWithViolation, vertexWithProperty);
							if (shortestPath != null) {
								String shortestPathString = shortestPath.getVertexList().toString();
								System.out.println("Path found: " + shortestPathString);
								System.out.println(property + " " +  violation);
								property.setIsViolated(true);
								violatedProperties.add(property);
								break;
							}
						}
					}
					if (property.getIsViolated()) {
						break;
					}
				}
			}
		}
		return violatedProperties;
	}
}

package com.sdq.coupling.sdg;

import org.jgrapht.Graph;

/**
 * Interface for a sdg generator.
 *
 * @author Laura
 *
 */
public interface ISdgGenerator {
  public Graph<AbstractSdgVertex, AbstractSdgEdge> generate(String graphmlFilePath);
}

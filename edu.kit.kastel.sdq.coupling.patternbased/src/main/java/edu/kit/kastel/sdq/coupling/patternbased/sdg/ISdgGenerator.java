package edu.kit.kastel.sdq.coupling.patternbased.sdg;

import org.jgrapht.Graph;

/**
 * Interface for a sdg generator.
 *
 * @author Laura
 *
 */
public interface ISdgGenerator {
  public Graph<AbstractSdgVertex, AbstractSdgEdge> generate(String jarFilePath);
}

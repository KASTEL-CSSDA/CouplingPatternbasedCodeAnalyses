package edu.kit.kastel.sdq.coupling.patternbased.sdg.joana;

import java.util.function.Supplier;

import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgEdge;

/**
 * Creates an empty JoanaSdgEdge. Used for reading in graphml files with
 * JGraphT. 
 *
 * @author Laura
 *
 */
public class JoanaSdgEdgeSupplier implements Supplier<AbstractSdgEdge> {

  public JoanaSdgEdgeSupplier() {

  }

  public AbstractSdgEdge get() {
    return new JoanaSdgEdge();
  }

}

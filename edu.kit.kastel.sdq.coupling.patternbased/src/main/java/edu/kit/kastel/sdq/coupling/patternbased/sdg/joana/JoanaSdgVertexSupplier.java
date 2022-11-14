package edu.kit.kastel.sdq.coupling.patternbased.sdg.joana;

import java.util.function.Supplier;

import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgVertex;


/**
 * Creates an empty JoanaSdgVertex. Used for reading in graphml files with
 * JGraphT.
 *
 * @author Laura
 *
 */
public class JoanaSdgVertexSupplier implements Supplier<AbstractSdgVertex> {

  public JoanaSdgVertexSupplier() {

  }

  public AbstractSdgVertex get() {
    return new JoanaSdgVertex();
  }

}

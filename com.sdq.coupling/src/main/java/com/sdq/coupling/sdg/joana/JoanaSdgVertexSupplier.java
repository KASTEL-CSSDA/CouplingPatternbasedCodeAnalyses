package com.sdq.coupling.sdg.joana;

import com.sdq.coupling.sdg.AbstractSdgVertex;
import java.util.function.Supplier;


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

package com.sdq.coupling.sdg.joana;

import com.sdq.coupling.sdg.AbstractSdgEdge;
import java.util.function.Supplier;

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

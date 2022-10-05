package com.sdq.coupling.sdg.joana;

import java.util.function.Supplier;

import com.sdq.coupling.sdg.AbstractSdgEdge;

public class JoanaSdgEdgeSupplier implements Supplier<AbstractSdgEdge> {

	
	public JoanaSdgEdgeSupplier() {

	}
	
	public AbstractSdgEdge get() {
		return new JoanaSdgEdge();
	}

}

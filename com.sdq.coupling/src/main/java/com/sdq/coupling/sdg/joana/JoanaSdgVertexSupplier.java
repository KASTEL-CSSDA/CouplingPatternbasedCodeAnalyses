package com.sdq.coupling.sdg.joana;

import java.util.function.Supplier;

import com.sdq.coupling.sdg.AbstractSdgVertex;

public class JoanaSdgVertexSupplier implements Supplier<AbstractSdgVertex> {

	
	public JoanaSdgVertexSupplier() {

	}
	
	public AbstractSdgVertex get() {
		return new JoanaSdgVertex();
	}
	
}

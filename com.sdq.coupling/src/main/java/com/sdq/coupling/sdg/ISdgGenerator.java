package com.sdq.coupling.sdg;

import org.jgrapht.Graph;

public interface ISdgGenerator {
	public Graph<AbstractSdgVertex, AbstractSdgEdge> generate(String graphmlFilePath);
}

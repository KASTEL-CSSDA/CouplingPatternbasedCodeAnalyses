package com.sdq.coupling.sdg.joana;

import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.SdgEdgeType;

public class JoanaSdgEdge extends AbstractSdgEdge {
	private String edgeKind;

	public String getEdgeKind() {
		return edgeKind;
	}

	public void setEdgeKind(String edgeKind) {
		this.edgeKind = edgeKind;
		
		switch(edgeKind) {
		case "DD":
			this.setEdgeType(SdgEdgeType.DD);
			break;
		case "PS":
			this.setEdgeType(SdgEdgeType.PS);
			break;
		case "CL":
			this.setEdgeType(SdgEdgeType.CL);
			break;
		default:
			this.setEdgeType(SdgEdgeType.UNSPECIFIED);
			break;
		// TODO: other cases
	}
	}
}

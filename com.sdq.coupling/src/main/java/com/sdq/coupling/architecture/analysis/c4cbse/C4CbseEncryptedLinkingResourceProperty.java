package com.sdq.coupling.architecture.analysis.c4cbse;

import com.sdq.coupling.architecture.analysis.AbstractCallArchitectureProperty;
import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.util.Location;

public class C4CbseEncryptedLinkingResourceProperty extends AbstractCallArchitectureProperty {

	public C4CbseEncryptedLinkingResourceProperty(ArchitecturePropertyType architecturePropertyType, 
			Location caller,
			Location callee) {
		super(architecturePropertyType, caller, callee);
	}

	@Override
	public boolean isCaller(Location location) {
		return location.hasSameClass(super.getCaller());
	}

	@Override
	public boolean isCallee(Location location) {
		return location.hasSameClass(super.getCallee());
	}

}

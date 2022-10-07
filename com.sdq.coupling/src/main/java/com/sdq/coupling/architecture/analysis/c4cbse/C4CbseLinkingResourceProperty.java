package com.sdq.coupling.architecture.analysis.c4cbse;

import com.sdq.coupling.architecture.analysis.AbstractCallArchitectureProperty;
import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.util.Location;

/**
 * Extends the AbstractCallArchitectureProperty class for an encrypted linking 
 * resource property for the c4cbse framework.
 *
 * @author Laura
 *
 */
public class C4CbseLinkingResourceProperty extends AbstractCallArchitectureProperty {
  
  private String linkingResourceId;

  public C4CbseLinkingResourceProperty(ArchitecturePropertyType architecturePropertyType, 
      Location caller, Location callee, String linkingResourceId) {
    super(architecturePropertyType, caller, callee);
    this.linkingResourceId = linkingResourceId;
  }
  
  public String getLinkingResourceId() {
    return this.linkingResourceId;
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

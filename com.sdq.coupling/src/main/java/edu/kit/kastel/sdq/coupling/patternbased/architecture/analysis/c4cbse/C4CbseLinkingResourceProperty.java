package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractCallArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.ArchitecturePropertyType;
import edu.kit.kastel.sdq.coupling.patternbased.util.Location;

/**
 * Extends the AbstractCallArchitectureProperty class for an encrypted linking 
 * resource property for the c4cbse framework.
 *
 * @author Laura
 *
 */
public class C4CbseLinkingResourceProperty extends AbstractCallArchitectureProperty {
  
  private LinkingResource linkingResource;
  
  public C4CbseLinkingResourceProperty(ArchitecturePropertyType architecturePropertyType, 
      Location caller, Location callee, LinkingResource linkingResource) {
    super(architecturePropertyType, caller, callee);
    this.linkingResource = linkingResource;
  }
  
  public LinkingResource getLinkingResource() {
    return this.linkingResource;
  }
  
  public String getLinkingResourceId() {
    return this.linkingResource.getId();
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

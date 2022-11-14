package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the resource environment of a c4cbse model containing 
 * the resource containers and linking resource
 *
 * @author Laura
 *
 */
public class ResourceEnvironment {
  private List<ResourceContainer> resourceContainers = new LinkedList<ResourceContainer>();
  private List<LinkingResource> linkingResources = new LinkedList<LinkingResource>();

  public ResourceEnvironment(List<ResourceContainer> resourceContainers, 
      List<LinkingResource> linkingResources) {
    this.resourceContainers = resourceContainers;
    this.linkingResources = linkingResources;
  }

  /**
   * Adds a resource container to the resource environment.
   *
   * @param resourceContainer The container to add.
   */
  public void addResourceContainer(ResourceContainer resourceContainer) {
    if (this.resourceContainers.contains(resourceContainer)) {
      return;
    }

    this.resourceContainers.add(resourceContainer);
  }

  public List<ResourceContainer> getResourceContainers() {
    return this.resourceContainers;
  }

  /**
   * Adds a linking resource to the resource environment.
   *
   * @param linkingResource The linking resource to add.
   */
  public void addLinkingResource(LinkingResource linkingResource) {
    if (this.linkingResources.contains(linkingResource)) {
      return;
    }

    this.linkingResources.add(linkingResource);
  }

  public List<LinkingResource> getLinkingResources() {
    return this.linkingResources;
  }

}

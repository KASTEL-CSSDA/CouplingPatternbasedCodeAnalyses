package com.sdq.coupling.architecture.analysis.c4cbse;

import java.util.LinkedList;
import java.util.List;

public class ResourceEnvironment {
    private List<ResourceContainer> resourceContainers = new LinkedList<ResourceContainer>();
    private List<LinkingResource> linkingResources = new LinkedList<LinkingResource>();

    public ResourceEnvironment(List<ResourceContainer> resourceContainers, List<LinkingResource> linkingResources) {
        this.resourceContainers = resourceContainers;
        this.linkingResources = linkingResources;
    }

    public void addResourceContainer(ResourceContainer resourceContainer) {
        if (this.resourceContainers.contains(resourceContainer)) {
            return;
        }

        this.resourceContainers.add(resourceContainer);
    }

    public List<ResourceContainer> getResourceContainers() {
        return this.resourceContainers;
    }

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

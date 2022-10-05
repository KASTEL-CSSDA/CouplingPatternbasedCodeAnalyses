package com.sdq.coupling.architecture.analysis.c4cbse;

import java.util.LinkedList;
import java.util.List;

public class LinkingResource {
    private String id;
    private String entityName;

    private ResourceContainer source;
    private ResourceContainer target;
    private List<ConfidentialityProperty> confidentialityProperties = new LinkedList<ConfidentialityProperty>();

    protected LinkingResource(String id, String entityName, ResourceContainer source, ResourceContainer target) {
        this.id = id;
        this.entityName = entityName;
        this.source = source;
        this.target = target;
    }

    protected String getId() {
        return this.id;
    }

    protected String getEntityName() {
        return this.entityName;
    }

    protected ResourceContainer getSource() {
        return this.source;
    }

    protected ResourceContainer getTarget() {
        return this.target;
    }

    protected List<ConfidentialityProperty> getConfidentialityProperties() {
        return this.confidentialityProperties;
    }

    protected void addConfidentialityProperty(ConfidentialityProperty confidentialityProperty) {
        if (this.confidentialityProperties.contains(confidentialityProperty)) {
            return;
        } 

        this.confidentialityProperties.add(confidentialityProperty);
    }

    protected boolean hasConfidentialityProperty(ConfidentialityProperty confidentialityProperty) {
        return this.confidentialityProperties.contains(confidentialityProperty);
    }
    
    @Override
    public String toString() {
    	return "Id: " + this.id + "; Name: " + this.entityName + "; Source: " + this.source + "; Target: " + this.target;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof LinkingResource)) {
            return false;
        }

        LinkingResource linkingResource = (LinkingResource) object;
        return linkingResource.getId().equals(this.getId());
    }
}


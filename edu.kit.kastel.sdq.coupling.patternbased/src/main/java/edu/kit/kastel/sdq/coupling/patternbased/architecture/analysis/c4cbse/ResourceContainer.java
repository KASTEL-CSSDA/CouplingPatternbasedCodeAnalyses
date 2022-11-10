package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a c4cbse resource container.
 *
 * @author Laura
 *
 */
public class ResourceContainer {
  private String id;
  private String entityName;

  private List<Component> components = new LinkedList<Component>();

  protected ResourceContainer(String id, String entityName) {
    this.id = id;
    this.entityName = entityName;
  }

  protected String getId() {
    return this.id;
  }

  protected String getEntityName() {
    return this.entityName;
  }

  protected void addComponent(Component component) {
    if (this.components.contains(component)) {
      return;
    }

    this.components.add(component);
  }

  protected List<Component> getComponents() {
    return this.components;
  }

  @Override
  public String toString() {
    return String.format("Id: %s; Name: %s", this.id, this.entityName);
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    } else if (!(object instanceof ResourceContainer)) {
      return false;
    }

    ResourceContainer resourceContainer = (ResourceContainer) object;
    return resourceContainer.getId().equals(this.getId());
  }
}

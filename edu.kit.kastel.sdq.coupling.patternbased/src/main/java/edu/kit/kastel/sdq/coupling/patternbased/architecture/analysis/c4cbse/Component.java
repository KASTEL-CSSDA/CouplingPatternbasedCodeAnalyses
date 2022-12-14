package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse;

/**
 * Represents a palladio component.
 *
 * @author Laura
 *
 */
public class Component {
  private String id;
  private String entityName;

  protected Component(String id, String entityName) {
    this.id = id;
    this.entityName = entityName;
  }

  protected String getId() {
    return this.id;
  }

  protected String getEntityName() {
    return this.entityName;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    } else if (!(object instanceof Component)) {
      return false;
    }

    Component component = (Component) object;
    return component.getId().equals(this.getId());
  }
}
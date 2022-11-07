package com.sdq.coupling.architecture.analysis.carisma;

import java.util.LinkedList;
import java.util.List;

import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.ArchitecturePropertyType;
import com.sdq.coupling.architecture.analysis.IArchitecturePropertyManager;
import com.sdq.coupling.util.Location;

/**
 * Implements an architecture property manager for the Carisma framework.
 *
 * @author Laura
 *
 */
public class CarismaArchitecturePropertyManager implements IArchitecturePropertyManager {

  public CarismaArchitecturePropertyManager() {

  }
  
  //TODO mocked implementation
  public List<AbstractArchitectureProperty> getProperties(String modelFilePath) {
    List<AbstractArchitectureProperty> propertyList = 
        new LinkedList<AbstractArchitectureProperty>();
    propertyList.add(new CarismaSecureDependencyProperty(ArchitecturePropertyType.ENCRYPTED,
        new Location("com.sdq.example", "Client"), 
        new Location("com.sdq.example", "Supplier", "operation")));
    return propertyList;
  }

  public void removeProperties(String modelFilePath, 
      List<AbstractArchitectureProperty> violatedProperties) {
  }

}

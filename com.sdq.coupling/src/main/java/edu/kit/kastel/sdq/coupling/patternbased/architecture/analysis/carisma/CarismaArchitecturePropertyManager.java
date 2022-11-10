package edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.carisma;

import java.util.LinkedList;
import java.util.List;

import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.ArchitecturePropertyType;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.IArchitecturePropertyManager;
import edu.kit.kastel.sdq.coupling.patternbased.util.Location;

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

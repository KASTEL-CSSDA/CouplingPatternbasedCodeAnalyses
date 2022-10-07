package com.sdq.coupling.architecture.analysis;

import java.util.List;

/**
 * Interface to extract and remove architecture security properties from model
 * files.
 *
 * @author Laura
 *
 */
public interface IArchitecturePropertyManager {

  /**
   * Extracts properties from the architecture model.
   *
   * @param modelDirectoryPath Path to model file directory.
   * @return List of architecture properties.
   */
  List<AbstractArchitectureProperty> getProperties(String modelDirectoryPath);

  /**
   * Feeds back the violated properties into the architecture model by deleting
   * them.
   *
   * @param modelDirectoryPath Path to model file directory.
   * @param violatedProperties List of violated properties to be deleted.
   */
  void removeProperties(String modelDirectoryPath, 
      List<AbstractArchitectureProperty> violatedProperties);
}

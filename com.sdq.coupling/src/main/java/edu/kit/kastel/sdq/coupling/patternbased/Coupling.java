package edu.kit.kastel.sdq.coupling.patternbased;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.Properties;
import org.jgrapht.Graph;

import edu.kit.kastel.sdq.coupling.patternbased.analysis.CouplingAnalysisCg;
import edu.kit.kastel.sdq.coupling.patternbased.analysis.CouplingAnalysisSdg;
import edu.kit.kastel.sdq.coupling.patternbased.analysis.ICouplingAnalysis;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.AbstractArchitectureProperty;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.IArchitecturePropertyManager;
import edu.kit.kastel.sdq.coupling.patternbased.architecture.analysis.c4cbse.C4CbseArchitecturePropertyManager;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.AbstractPatternViolation;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.IPatternAnalysis;
import edu.kit.kastel.sdq.coupling.patternbased.code.analysis.cognicrypt.CognicryptPatternAnalysis;
import edu.kit.kastel.sdq.coupling.patternbased.mapping.AbstractPropertyViolationMapping;
import edu.kit.kastel.sdq.coupling.patternbased.mapping.C4CbseCognicryptMapping;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgEdge;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgVertex;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.ISdgGenerator;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.joana.JoanaSdgGenerator;
import edu.kit.kastel.sdq.coupling.patternbased.ui.UserInterface;


/**
 * Runs the analysis.
 *
 * @author Laura
 *
 */
public class Coupling {
  
  /**
   * Runs the coupling analysis.
   *
   * @param modelDirectoryPath Path to the architecture model.
   * @param jarFilePath Path to the analyzed jar file.
   */
  public static void run(String modelDirectoryPath, String jarFilePath, boolean useCgCoupling) {
    Properties properties = loadProperties();
    // (1) Extract architecture security properties.
    IArchitecturePropertyManager architecturePropertyManager = 
        new C4CbseArchitecturePropertyManager();
    List<AbstractArchitectureProperty> architectureProperties = 
        architecturePropertyManager.getProperties(modelDirectoryPath);
    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(architectureProperties + "\n");

    // (2) Extract pattern violations.
    IPatternAnalysis patternAnalysis = new CognicryptPatternAnalysis(properties);
    List<AbstractPatternViolation> patternViolations = 
        patternAnalysis.findViolations(jarFilePath); 
    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(patternViolations + "\n");

    // (3) Generate SDG.
    ISdgGenerator sdgGenerator = new JoanaSdgGenerator(properties);
    Graph<AbstractSdgVertex, AbstractSdgEdge> sdg = sdgGenerator.generate(jarFilePath);

    // (4) Load property to pattern violation mapping.
    AbstractPropertyViolationMapping mapping = new C4CbseCognicryptMapping(); 

    // (5) Find violated properties.
    //ICouplingAnalysis ca = new CouplingAnalysisSdg();
    ICouplingAnalysis couplingAnalysis;
    if (useCgCoupling) {
      Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Running coupling analysis with CG ...");
      couplingAnalysis = new CouplingAnalysisCg();
    } else {
      Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Running coupling analysis with SDG ...");
      couplingAnalysis = new CouplingAnalysisSdg();
    }
    
    List<AbstractArchitectureProperty> violatedProperties = 
        couplingAnalysis.getViolatedProperties(architectureProperties, 
            patternViolations, sdg, mapping);
    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Found " + violatedProperties == null ? "0" : violatedProperties.size() 
        + " violated properties.\n");

    // (6) Remove the properties that are violated.
    architecturePropertyManager.removeProperties(modelDirectoryPath, violatedProperties);
  }

  public static void main(String[] args) throws IOException {
    UserInterface ui = new UserInterface();
    ui.show();
  }
  
  private static Properties loadProperties() {
    Properties properties = null;
    try {
      String configFilePath = "src/config.properties";
      FileInputStream propertiesInput = new FileInputStream(configFilePath);
      properties = new Properties();
      properties.load(propertiesInput);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }
}

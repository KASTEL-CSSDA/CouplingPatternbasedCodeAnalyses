package com.sdq.coupling;

import java.io.IOException;

import java.util.List;

import org.jgrapht.Graph;

import com.sdq.coupling.analysis.CouplingSdg;
import com.sdq.coupling.analysis.ICoupling;
import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.IArchitecturePropertyManager;
import com.sdq.coupling.architecture.analysis.c4cbse.C4CbseArchitecturePropertyManager;
import com.sdq.coupling.architecture.analysis.carisma.CarismaArchitecturePropertyManager;
import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.code.analysis.IPatternAnalysis;
import com.sdq.coupling.code.analysis.cognicrypt.CognicryptPatternAnalysis;
import com.sdq.coupling.mapping.AbstractPropertyViolationMapping;
import com.sdq.coupling.mapping.C4CbseCognicryptMapping;
import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.AbstractSdgVertex;
import com.sdq.coupling.sdg.ISdgGenerator;
import com.sdq.coupling.sdg.joana.JoanaSdgGenerator;
import com.sdq.coupling.ui.UserInterface;

/**
 * Runs the analysis.
 *
 * @author Laura
 *
 */
public class CouplingAnalysis {
  
  /**
   * Runs the coupling analysis.
   *
   * @param modelDirectoryPath Path to the architecture model.
   * @param jarFilePath Path to the analyzed jar file.
   */
  public static void run(String modelDirectoryPath, String jarFilePath) {
    // (1) Extract architecture security properties.
    IArchitecturePropertyManager architecturePropertyManager = 
        new C4CbseArchitecturePropertyManager();
    List<AbstractArchitectureProperty> architectureProperties = 
        architecturePropertyManager.getProperties(modelDirectoryPath);
    System.out.println(architectureProperties + "\n");

    // (2) Extract pattern violations.
    IPatternAnalysis patternAnalysis = new CognicryptPatternAnalysis();
    List<AbstractPatternViolation> patternViolations = 
        patternAnalysis.findViolations(jarFilePath); 
    System.out.println(patternViolations + "\n");

    // (3) Generate SDG.
    ISdgGenerator sdgGenerator = new JoanaSdgGenerator();
    Graph<AbstractSdgVertex, AbstractSdgEdge> sdg = sdgGenerator.generate(jarFilePath);

    // (4) Load property to pattern violation mapping.
    AbstractPropertyViolationMapping mapping = new C4CbseCognicryptMapping(); 

    // (5) Find violated properties.
    ICoupling ca = new CouplingSdg();
    List<AbstractArchitectureProperty> violatedProperties = 
        ca.getViolatedProperties(architectureProperties, patternViolations, sdg, mapping);
    System.out.println("Found " + violatedProperties.size() + " violated properties.\n");

    // (6) Remove the properties that are violated.
    architecturePropertyManager.removeProperties(modelDirectoryPath, violatedProperties);
  }

  public static void main(String[] args) throws IOException {
    UserInterface ui = new UserInterface();
    ui.show();
  }
}

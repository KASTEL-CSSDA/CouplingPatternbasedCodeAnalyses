package com.sdq.coupling;

import java.io.IOException;

import java.util.List;

import org.jgrapht.Graph;

import com.sdq.coupling.analysis.CouplingAnalysisSdg;
import com.sdq.coupling.analysis.ICouplingAnalysis;
import com.sdq.coupling.architecture.analysis.AbstractArchitectureProperty;
import com.sdq.coupling.architecture.analysis.IArchitecturePropertyManager;
import com.sdq.coupling.architecture.analysis.c4cbse.C4CbseArchitecturePropertyManager;
import com.sdq.coupling.architecture.analysis.carisma.CarismaArchitecturePropertyManager;
import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.code.analysis.IPatternAnalysis;
import com.sdq.coupling.code.analysis.cognicrypt.CognicryptPatternAnalysis;
import com.sdq.coupling.mapping.AbstractPropertyViolationMapping;
import com.sdq.coupling.mapping.CarismaCognicryptMapping;
import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.AbstractSdgVertex;
import com.sdq.coupling.sdg.ISdgGenerator;
import com.sdq.coupling.sdg.joana.JoanaSdgGenerator;
import com.sdq.coupling.ui.UserInterface;

public class Main {
  public static void run(String modelDirectoryPath, String jarFilePath) {
    // (1) Extract architecture security properties.
    IArchitecturePropertyManager apm = new C4CbseArchitecturePropertyManager();
    List<AbstractArchitectureProperty> aps = apm.getProperties(modelDirectoryPath);
    System.out.println(aps + "\n");

    // (2) Extract pattern violations.
    IPatternAnalysis pa = new CognicryptPatternAnalysis();
    List<AbstractPatternViolation> pvs = pa.findViolations(jarFilePath); // returns mocked, but "real" violations
    System.out.println(pvs + "\n");

    // (3) Generate SDG.
    ISdgGenerator sg = new JoanaSdgGenerator();
    Graph<AbstractSdgVertex, AbstractSdgEdge> sdg = sg.generate(jarFilePath);

    // (4) Load property to pattern violation mapping.
    AbstractPropertyViolationMapping ppm = new CarismaCognicryptMapping(); // already maps common errors to common
                                                                           // properties internally

    // (5) Find violated properties.
    ICouplingAnalysis ca = new CouplingAnalysisSdg();
    List<AbstractArchitectureProperty> vp = ca.getViolatedProperties(aps, pvs, sdg, ppm);
    System.out.println("Found " + vp.size() + " violated properties.\n");
    
    // (6) Remove the properties that are violated.
    apm.removeProperties(modelDirectoryPath, vp);
  }

  public static void main(String[] args) throws IOException {
    UserInterface ui = new UserInterface();
    ui.show();
  }
}

package com.sdq.coupling.code.analysis.cognicrypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sdq.coupling.code.analysis.AbstractPatternViolation;
import com.sdq.coupling.code.analysis.IPatternAnalysis;
import com.sdq.coupling.code.analysis.PatternViolationType;
import com.sdq.coupling.util.Location;

import crypto.HeadlessCryptoScanner;
import crypto.HeadlessCryptoScannerOptions;
import fj.data.Array;

/**
 * Implements the pattern analysis interface for cognicrypt. This class uses the
 * HeadlessCryptoScanner class to perform a code analysis with cognicrypt and
 * parses and returns the result.
 *
 * @author Laura
 *
 */
public class CognicryptPatternAnalysis implements IPatternAnalysis {

  public CognicryptPatternAnalysis() {
  }

  /**
   * Performs a code analysis on the passed jar file and
   * returns the parsed pattern violations
   *
   * @param jarFilePath The path of the file to be analyzed.
   */
  public List<AbstractPatternViolation> findViolations(String jarFilePath) {
    List<AbstractPatternViolation> violations = new LinkedList<AbstractPatternViolation>();
    try {
      HeadlessCryptoScanner hcs = HeadlessCryptoScanner
          .createFromOptions(new String[] { "--rulesDir", ".\\src\\main\\resources\\crypto-rules", 
              "--applicationCp", jarFilePath, "--reportDir", ".\\src\\main\\resources\\tmp", 
              "--sarifReport", "" });
      hcs.exec();

      // read txt file with result json
      BufferedReader reader = new BufferedReader(
          new FileReader(".\\src\\main\\resources\\tmp\\CogniCrypt-SARIF-Report.txt"));
      String json = "";
      try {
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();

        while (line != null) {
          sb.append(line);
          sb.append("\n");
          line = reader.readLine();
        }
        json = sb.toString();
      } finally {
        reader.close();
      }

      // parse JSON file to CogniCryptViolation
      JSONObject object = new JSONObject(json);
      JSONArray array = object.getJSONArray("runs");
      JSONObject resultsObject = array.getJSONObject(0);
      JSONArray resultsArray = resultsObject.getJSONArray("results");
      for (int i = 0; i < resultsArray.length(); i++) {
        JSONObject locationsObject = resultsArray.getJSONObject(i);
        JSONObject messageObject = locationsObject.optJSONObject("message");
        String message = messageObject.getString("richText");

        // get violated class
        String[] messageSplitted = (message.split(" "));
        String s = messageSplitted[messageSplitted.length - 1];
        String violatedClass = s.substring(0, s.length() - 1);

        // get error location and code line
        JSONArray locationsArray = locationsObject.getJSONArray("locations");
        JSONObject regionObject = locationsArray.getJSONObject(0);
        JSONObject pysicalLocationObject = regionObject.getJSONObject("physicalLocation");
        JSONObject startLineObject = pysicalLocationObject.getJSONObject("region");
        int startLine = (int) startLineObject.optInt("startLine");
        String qualifiedName = regionObject.optString("fullyQualifiedLogicalName");
        String[] qualifiedNameSplitted = qualifiedName.split("::");
        String methodName = qualifiedNameSplitted[qualifiedNameSplitted.length - 1];
        String className = qualifiedNameSplitted[qualifiedNameSplitted.length - 2];

        // remove class and method from array
        String[] copy = new String[qualifiedNameSplitted.length - 2];
        for (int j = 0; j < qualifiedNameSplitted.length - 2; j++) {
          copy[j] = qualifiedNameSplitted[j];
        }

        // combine array to package name String
        String packageName = String.join(".", copy);

        // instantiate CognicryptPatterViolation
        // build violatedPackage and Class String for Location
        String[] violatedClassSplitted = violatedClass.split("\\.");
        String violatedClassName = violatedClassSplitted[violatedClassSplitted.length - 1];
        // remove class name from array
        String[] copy2 = new String[violatedClassSplitted.length - 1];
        for (int j = 0; j < violatedClassSplitted.length - 1; j++) {
          copy2[j] = violatedClassSplitted[j];
        }
        String violatedPackageName = String.join(".", copy2);
        Location violatedClassLocation = new Location(violatedPackageName, violatedClassName);

        // build errorLocation
        Location errorLocation = new Location(packageName, className, methodName);
        CognicryptViolationToPatternViolationTypeMapping mapping = 
            new CognicryptViolationToPatternViolationTypeMapping();
        PatternViolationType violationType = mapping.getViolationType(violatedClassName);

        // build codeline list
        List<Integer> codeLines = new LinkedList<Integer>();
        codeLines.add(startLine);
        CognicryptPatternViolation violation = 
            new CognicryptPatternViolation(errorLocation, violatedClassLocation,
            violationType, codeLines);

        // add PatterViolation to ViolationList
        violations.add(violation);
      }

      for (AbstractPatternViolation violation : violations) {
        System.out.print(violation + "\n");
      }

      // TODO: clear tmp folder

    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException 
        | IllegalAccessException | IllegalArgumentException | InvocationTargetException 
        | ParseException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return violations;
  }

}

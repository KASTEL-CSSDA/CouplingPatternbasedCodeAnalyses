package edu.kit.kastel.sdq.coupling.patternbased.sdg.joana;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Logger;

import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.nio.graphml.GraphMLImporter;

import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgEdge;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.AbstractSdgVertex;
import edu.kit.kastel.sdq.coupling.patternbased.sdg.ISdgGenerator;

/**
 * Generates the system dependence graph using joana.
 *
 * @author Laura
 *
 */
public class JoanaSdgGenerator implements ISdgGenerator {

  private Properties properties;
  
  public JoanaSdgGenerator(Properties properties) {
    this.properties = properties;
  }

  /**
   * Generates the system dependence graph for a jar file. 
   *
   * @param jarFilePath The jar file from which the sdg will
   *        be computed.
   * @return Returns the system dependence graph as a directed pseudograph.
   */
  public Graph<AbstractSdgVertex, AbstractSdgEdge> generate(String jarFilePath) {
    try {
      ProcessBuilder pb = new ProcessBuilder(
          properties.getProperty("JOANA_JDK_8_PATH"), "-jar",
          properties.getProperty("JOANA_JAR_PATH"), "\"classPath " + jarFilePath + "\"",
          properties.getProperty("JOANA_ENTRY_METHOD_TAG"), "\"buildSDG\"", "\"exportSDG " 
          + properties.getProperty("JOANA_OUTPUT_PATH_DESTINATION"));
      pb.directory(new File(System.getProperty("user.dir")));

      Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Computing SDG (this may take a while) ...");
      Process pr = pb.start();
      pr.waitFor();
      Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Finished computing SDG.");

    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    String graphmlFilePath = properties.getProperty("JOANA_SDG_PATH");

    GraphMLImporter<AbstractSdgVertex, AbstractSdgEdge> gml = 
        new GraphMLImporter<AbstractSdgVertex, AbstractSdgEdge>();
    gml.addVertexAttributeConsumer((p, attrValue) -> {
      JoanaSdgVertex v = (JoanaSdgVertex) p.getFirst();
      String attrName = p.getSecond();

      if (v == null) {
        return;
      } else if (attrName.equals("nodeKind")) {
        v.setNodeKind(attrValue.getValue());
      } else if (attrName.equals("nodeSource")) {
        v.setNodeSource(attrValue.getValue());
      } else if (attrName.equals("nodeProc")) {
        v.setNodeProc(Integer.parseInt(attrValue.getValue()));
      } else if (attrName.equals("nodeOperation")) {
        v.setNodeOperation(attrValue.getValue());
      } else if (attrName.equals("nodeLabel")) {
        v.setNodeLabel(attrValue.getValue());
      } else if (attrName.equals("nodeBcName")) {
        v.setNodeBcName(attrValue.getValue());
      } else if (attrName.equals("nodeBCIndex")) {
        v.setNodeBcIndex(Integer.parseInt(attrValue.getValue()));
      } else if (attrName.equals("nodeSr")) {
        v.setNodeSr(Integer.parseInt(attrValue.getValue()));
      } else if (attrName.equals("nodeSc")) {
        v.setNodeSc(Integer.parseInt(attrValue.getValue()));
      } else if (attrName.equals("nodeEr")) {
        v.setNodeEr(Integer.parseInt(attrValue.getValue()));
      } else if (attrName.equals("nodeEc")) {
        v.setNodeEc(Integer.parseInt(attrValue.getValue()));
      } else if (attrName.equals("nodeLocalDef")) {
        v.setNodeLocalDef(attrValue.getValue());
      } else if (attrName.equals("nodeLocalUse")) {
        v.setNodeLocalUse(attrValue.getValue());
      }

    });

    gml.addEdgeAttributeConsumer((p, attrValue) -> {
      JoanaSdgEdge e = (JoanaSdgEdge) p.getFirst();
      String attrName = p.getSecond();
      if (e == null) {
        return;
      } else if (attrName.equals("edgeKind")) {
        e.setEdgeKind(attrValue.getValue());
      }
    });

    Graph<AbstractSdgVertex, AbstractSdgEdge> graph = 
        new DirectedPseudograph<AbstractSdgVertex, AbstractSdgEdge>(
            new JoanaSdgVertexSupplier(), new JoanaSdgEdgeSupplier(), false);
    gml.importGraph(graph, new File(graphmlFilePath));

    return graph;
  }
}

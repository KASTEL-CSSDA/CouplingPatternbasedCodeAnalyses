package com.sdq.coupling.sdg.joana;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.nio.graphml.GraphMLImporter;

import com.sdq.coupling.sdg.AbstractSdgEdge;
import com.sdq.coupling.sdg.AbstractSdgVertex;
import com.sdq.coupling.sdg.ISdgGenerator;

/**
 * Generates the system dependence graph using joana.
 *
 * @author Laura
 *
 */
public class JoanaSdgGenerator implements ISdgGenerator {

  public JoanaSdgGenerator() {

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
          "\"C:\\Program Files\\Eclipse Adoptium\\jdk-8.0.345.1-hotspot\\bin\\java.exe\"", "-jar",
          ".\\src\\main\\resources\\joana.ui.ifc.wala.cli.jar", "\"classPath " + jarFilePath + "\"",
          "\"entry select coupling\"", "\"buildSDG\"", "\"exportSDG " + 
          "./src/main/resources/tmp/sdg.pdg.graphml\"");
      pb.directory(new File(System.getProperty("user.dir")));

      System.out.println("Computing SDG (this may take a while) ...");
      Process pr = pb.start();
      pr.waitFor();
      System.out.println("Finished computing SDG.");

    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    String graphmlFilePath = ".\\src\\main\\resources\\tmp\\sdg.pdg.graphml";

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

package cellsociety.util;

import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The XMLParser class generates simulation metadata, initial grid states, and simulation
 * parameters given a filepath to a simulation configuration XML file.
 *
 * Usage:
 * <code>
 *   try {
 *     File f = new File("path/to/file.xml");
 *     XMLParser p = new XMLParser(f); // may throw an Exception
 *     HashMap<String, String> simulationMetadata = p.getSimulationMetadata();
 *     HashMap<String, Double> simulationParameters = p.getSimulationParameters();
 *     ArrayList<int[]> cellsWithInitialNonDefaultStates = p.getInitialNonDefaultStates();
 *     // entry point for simulation using the above values
 *   } catch (Exception e) {
 *     // pipe Exception to GUI
 *   }
 * </code>
 *
 * @author David Coffman
 */
public class XMLParser {
  private final Document doc;
  private HashMap<String, String> simulationMetadata;
  private ArrayList<int[]> initialNonDefaultStates;
  private HashMap<String, Double> simulationParameters;

  /**
   * Sole constructor for XMLParser. Called with a String parameter indicating the filepath of
   * the file to be parsed.
   *
   * @param f the XML file to parse
   * @throws Exception thrown if the XML file is malformed
   */
  public XMLParser(File f) throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setIgnoringElementContentWhitespace(true);
    dbf.setCoalescing(true);
    dbf.setIgnoringComments(true);
    dbf.setExpandEntityReferences(true);

    DocumentBuilder db = dbf.newDocumentBuilder();
    try {
      db.setErrorHandler(null);
      this.doc = db.parse(f);

    } catch (Exception e) {
      throw new Exception("malformed XML file: are you sure the file you selected is an XML file?");
    }
    parseSimulationInformation();
  }

  // Gets the XML root from the top-level document nodes.
  private Node getXMLRoot() {
    Node xmlNode = null;
    for(int i = 0; i < doc.getChildNodes().getLength(); i++) {
      String name = doc.getChildNodes().item(i).getNodeName();
      if(name.equals("xml")) xmlNode =  doc.getChildNodes().item(i);
    }
    return xmlNode;
  }

  // Parses the top-level nodes within the XML root
  private void parseSimulationInformation() throws Exception {
    Node xmlRoot = getXMLRoot();

    for(int i = 0; i < xmlRoot.getChildNodes().getLength(); i++) {
      Node n = xmlRoot.getChildNodes().item(i);
      String nodeName = n.getNodeName();
      if (nodeName == null) continue;
      switch(nodeName) {
        case "General" -> parseMetadata(n);
        case "GeometricConfiguration" -> parseGeometricConfiguration(n);
        case "SimulationParameters" -> parseSimulationParameters(n);
        case "InitialStates" -> parseInitialStates(n);
      }
    }
  }

  // Parses the metadata node in the XML root
  private void parseMetadata(Node gennode) {
    HashMap<String, String> metadata = new HashMap<String, String>();
    for(int i = 0; i < gennode.getChildNodes().getLength(); i++) {
      Node n = gennode.getChildNodes().item(i);
      String nodeName = n.getNodeName();
      String childValue = primaryChildNodeValueAsString(n);
      if (nodeName == null || childValue == null) continue;
      switch(nodeName) {
        case "Name", "Type", "Author", "Description" -> metadata.put(nodeName,
            childValue);
      }
    }
    this.simulationMetadata = metadata;
  }

  // Parses the geometric configuration node in the XML root
  private void parseGeometricConfiguration(Node gcnode) {
    for(int i = 0; i < gcnode.getChildNodes().getLength(); i++) {
      Node n = gcnode.getChildNodes().item(i);
      String nodeName = n.getNodeName();
      String childValue = primaryChildNodeValueAsString(n);
      if(nodeName == null || childValue == null) continue;

      switch(nodeName) {
        case "CellShape" -> {}
        case "Height" -> {}
        case "Width" -> {}
      }
    }
  }

  // Parses the initial grid state node in the XML root
  private void parseInitialStates(Node initialGridStateNode) throws Exception {
    ArrayList<int[]> initialNonDefaultStates = new ArrayList<>();
    for(int i = 0; i < initialGridStateNode.getChildNodes().getLength(); i++) {
      Node n = initialGridStateNode.getChildNodes().item(i);
      String nodeName = n.getNodeName();
      if (nodeName.equals("Cell")) {
        initialNonDefaultStates.add(parseInitialCellState(n));
      }
    }
    this.initialNonDefaultStates = initialNonDefaultStates;
  }

  // Parses an individual initial cell state node in the XML root
  private int[] parseInitialCellState(Node initialCellStateNode) throws Exception {
    try {
      int[] ret = new int[] {-1, -1, -1};
      for(int i = 0; i < initialCellStateNode.getChildNodes().getLength(); i++) {
        Node n = initialCellStateNode.getChildNodes().item(i);
        String nodeName = n.getNodeName();
        String childValue = primaryChildNodeValueAsString(n);
        if(nodeName == null || childValue == null) continue;

        switch(nodeName) {
          case "Row" -> ret[0] = Integer.parseInt(childValue);
          case "Column" -> ret[1] = Integer.parseInt(childValue);
          case "State" -> ret[2] = Integer.parseInt(childValue);
        }
      }
      for (int j : ret) {
        if (j == -1) {
          throw new Exception();
        }
      }
      return ret;
    }
    catch (Exception e) {
      throw new Exception("malformed XML: one or more initial cell states are invalid.");
    }
  }

  // Parses the simulation parameters node in the XML root.
  private void parseSimulationParameters(Node simParamsNode) throws Exception {
    HashMap<String, Double> simulationParameters = new HashMap<>();
    for(int i = 0; i < simParamsNode.getChildNodes().getLength(); i++) {
      try {
        Node n = simParamsNode.getChildNodes().item(i);
        String nodeName = n.getNodeName();
        String childValue = primaryChildNodeValueAsString(n);
        if(childValue == null) {
          continue;
        }
        simulationParameters.put(nodeName, Double.parseDouble(childValue));
      } catch (Exception e) {
        throw new Exception("malformed XML: one or more simulation parameters are invalid.");
      }
    }
    this.simulationParameters = simulationParameters;
  }

  // For a node whose children are known to be terminal nodes (i.e. nodes with a VALUE), extract
  // that VALUE as a String, but ignore nodes with empty or solely whitespace VALUEs.
  private String primaryChildNodeValueAsString(Node n) {
    for(int i = 0; i < n.getChildNodes().getLength(); i++) {
      String value = n.getChildNodes().item(i).getNodeValue();
      if(value != null && !(value = value.trim()).equals("")) {
        return value;
      }
    }
    return null;
  }

  /**
   * Exposes the simulation metadata map.
   * @return the simulation metadata map
   */
  public HashMap<String, String> getSimulationMetadata() {
    return simulationMetadata;
  }

  /**
   * Exposes the initial states of cells with non-default initial states.
   * @return the cells with non-default initial states
   */
  public ArrayList<int[]> getInitialNonDefaultStates() {
    return initialNonDefaultStates;
  }

  /**
   * Exposes the simulation parameter map.
   * @return the simulation parameter map.
   */
  public HashMap<String, Double> getSimulationParameters() {
    return simulationParameters;
  }
}

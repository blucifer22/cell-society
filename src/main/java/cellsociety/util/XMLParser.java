package cellsociety.util;

import cellsociety.util.SimulationConfiguration.CellNeighborhoodSize;
import cellsociety.util.SimulationConfiguration.RandomGridGenerationType;
import cellsociety.util.SimulationConfiguration.SimulationEdgeType;
import cellsociety.util.SimulationConfiguration.SimulationType;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The XMLParser class configures a {@link cellsociety.util.SimulationConfiguration} for use by
 * classes in the {@link cellsociety.simulation} package. It loads simulation settings from an XML
 * file, then sends them to a {@link cellsociety.util.SimulationConfiguration} instance for
 * validation and storage. The {@link cellsociety.util.SimulationConfiguration} instance can then be
 * retrieved with {@link XMLParser#getSimulationConfiguration()}.
 *
 * <p>Usage: <code>
 * try { File f = new File("path/to/file.xml"); XMLParser p = new XMLParser(f); // may throw an
 * Exception SimulationConfiguration config = p.getSimulationConfiguration(); } catch (Exception e)
 * { // pipe Exception to GUI }
 * </code>
 *
 * @author David Coffman
 */
public class XMLParser {

  private final Document doc;
  private final SimulationConfiguration simulationConfiguration;

  /**
   * Constructor for XMLParser. Called with a String parameter indicating the filepath of the file
   * to be parsed.
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
    this.simulationConfiguration = new SimulationConfiguration();
    parseSimulationInformation();
    simulationConfiguration.validateConfiguration();
  }

  // Gets the XML root from the top-level document nodes.
  private Node getXMLRoot() {
    Node xmlNode = null;
    for (int i = 0; i < doc.getChildNodes().getLength(); i++) {
      String name = doc.getChildNodes().item(i).getNodeName();
      if (name.equals("xml")) {
        xmlNode = doc.getChildNodes().item(i);
      }
    }
    return xmlNode;
  }

  // Parses the top-level nodes within the XML root
  private void parseSimulationInformation() throws Exception {
    Node xmlRoot = getXMLRoot();
    for (int i = 0; i < xmlRoot.getChildNodes().getLength(); i++) {
      Node n = xmlRoot.getChildNodes().item(i);
      String nodeName = n.getNodeName();
      if (nodeName == null) {
        continue;
      }
      switch (formattedNodeName(nodeName)) {
        case "GENERAL" -> parseMetadata(n);
        case "GEOMETRICCONFIGURATION" -> parseGeometricConfiguration(n);
        case "SIMULATIONPARAMETERS" -> parseSimulationParameters(n);
        case "INITIALSTATES" -> parseInitialStates(n);
        case "RANDOMINITIALSTATES" -> parseRandomInitialStates(n);
      }
    }
  }

  // Parses the metadata node in the XML root
  private void parseMetadata(Node gennode) {
    for (int i = 0; i < gennode.getChildNodes().getLength(); i++) {
      Node n = gennode.getChildNodes().item(i);
      String nodeName = n.getNodeName();
      String childValue = primaryChildNodeValueAsString(n);
      if (nodeName == null || childValue == null) {
        continue;
      }
      switch (formattedNodeName(nodeName)) {
        case "NAME" -> simulationConfiguration.setSimulationName(childValue);
        case "TYPE" -> simulationConfiguration.setSimulationType(
            SimulationType.fromStringEncoding(childValue));
        case "AUTHOR" -> simulationConfiguration.setSimulationAuthor(childValue);
        case "DESCRIPTION" -> simulationConfiguration.setSimulationDescription(childValue);
      }
    }
  }

  // Parses the geometric configuration node in the XML root
  private void parseGeometricConfiguration(Node gcnode) throws Exception {
    for (int i = 0; i < gcnode.getChildNodes().getLength(); i++) {
      Node n = gcnode.getChildNodes().item(i);
      String nodeName = n.getNodeName();
      String childValue = primaryChildNodeValueAsString(n);
      if (nodeName == null || childValue == null) {
        continue;
      }
      try {
        switch (formattedNodeName(nodeName)) {
          case "CELLSHAPE" -> simulationConfiguration.setCellShape(
              SimulationConfiguration.CellShape.fromStringEncoding(childValue));
          case "HEIGHT" -> simulationConfiguration.setHeight(Integer.parseInt(childValue));
          case "WIDTH" -> simulationConfiguration.setWidth(Integer.parseInt(childValue));
          case "EDGETYPE" -> simulationConfiguration.setEdgeType(
              SimulationEdgeType.fromStringEncoding(childValue));
          case "NEIGHBORHOODSIZE" -> simulationConfiguration.setNeighborhoodSize(
              CellNeighborhoodSize.fromStringEncoding(childValue));
        }
      } catch (NumberFormatException e) {
        throw new Exception("malformed XML: field <" + nodeName + "> is formatted incorrectly.");
      }
    }
  }

  // Parses the initial grid state node in the XML root
  private void parseInitialStates(Node initialGridStateNode) throws Exception {
    for (int i = 0; i < initialGridStateNode.getChildNodes().getLength(); i++) {
      Node n = initialGridStateNode.getChildNodes().item(i);
      String nodeName = n.getNodeName();
      if (formattedNodeName(nodeName).equals("CELL")) {
        simulationConfiguration.addInitialCellState(parseInitialCellState(n));
      }
    }
  }

  // Parses an individual initial cell state node (extract the value from <Row>, <Column>, and
  // <State>) in the <InitialStates> tree
  private int[] parseInitialCellState(Node initialCellStateNode) throws Exception {
    try {
      int[] ret = new int[]{-1, -1, -1};
      for (int i = 0; i < initialCellStateNode.getChildNodes().getLength(); i++) {
        Node n = initialCellStateNode.getChildNodes().item(i);
        String nodeName = n.getNodeName();
        String childValue = primaryChildNodeValueAsString(n);
        if (nodeName == null || childValue == null) {
          continue;
        }

        switch (formattedNodeName(nodeName)) {
          case "ROW" -> ret[0] = Integer.parseInt(childValue);
          case "COLUMN" -> ret[1] = Integer.parseInt(childValue);
          case "STATE" -> ret[2] = Integer.parseInt(childValue);
        }
      }
      for (int j : ret) {
        if (j == -1) {
          throw new Exception("All row, column, and state parameters must be specified for each "
              + "declared non-default cell.");
        }
      }
      return ret;
    } catch (NumberFormatException e) {
      throw new Exception("malformed XML: one or more initial cell states is formatted "
          + "incorrectly.");
    }
  }

  // Parse the <InitialStates> element
  private void parseRandomInitialStates(Node initialRandomStateNode) throws Exception {
    for (int i = 0; i < initialRandomStateNode.getChildNodes().getLength(); i++) {
      Node n = initialRandomStateNode.getChildNodes().item(i);
      String nodeName = n.getNodeName();
      String childValue = primaryChildNodeValueAsString(n);

      switch (formattedNodeName(nodeName)) {
        case "METHOD" -> simulationConfiguration.setRandomGridGenerationType(
            RandomGridGenerationType.fromStringEncoding(childValue));
        case "COUNTS" -> parseRandomInitialStateCounts(n);
      }
    }
  }

  // Parse the initial random state count (<Frequency>, <Count>, <State>) tags from the
  // a <StateFrequency> or <StateCount> sub-tree
  private void parseRandomInitialStateCount(Node randomStateCountNode) throws Exception {
    try {
      Integer state = null;
      Double frequency = null;
      for (int i = 0; i < randomStateCountNode.getChildNodes().getLength(); i++) {
        Node n = randomStateCountNode.getChildNodes().item(i);
        String nodeName = n.getNodeName();
        String childValue = primaryChildNodeValueAsString(n);
        if (nodeName == null || childValue == null) {
          continue;
        }

        switch (formattedNodeName(nodeName)) {
          case "STATE" -> state = Integer.parseInt(childValue);
          case "FREQUENCY", "COUNT" -> frequency = Double.parseDouble(childValue);
        }
      }
      simulationConfiguration.addInitialStateFrequency(state, frequency);
    } catch (NumberFormatException e) {
      throw new Exception("malformed XML: one or more initial cell states is formatted "
          + "incorrectly.");
    }
  }

  // Parse the <StateFrequency> or <StateCount> subtrees, if present
  private void parseRandomInitialStateCounts(Node randomStateCountsNode) throws Exception {
    for (int i = 0; i < randomStateCountsNode.getChildNodes().getLength(); i++) {
      Node n = randomStateCountsNode.getChildNodes().item(i);
      String nodeName = n.getNodeName();

      switch (formattedNodeName(nodeName)) {
        case "STATEFREQUENCY", "STATECOUNT" -> parseRandomInitialStateCount(n);
      }
    }
  }

  // Parses the getSimulation parameters node in the XML root.
  private void parseSimulationParameters(Node simParamsNode) throws Exception {
    for (int i = 0; i < simParamsNode.getChildNodes().getLength(); i++) {
      try {
        Node n = simParamsNode.getChildNodes().item(i);
        String nodeName = n.getNodeName();
        String childValue = primaryChildNodeValueAsString(n);
        if (childValue == null) {
          continue;
        }
        simulationConfiguration.addSimulationParameter(nodeName, Double.parseDouble(childValue));
      } catch (NumberFormatException e) {
        throw new Exception("malformed XML: one or more simulation parameters is formatted "
            + "incorrectly.");
      }
    }
  }

  // For a node whose children are known to be terminal nodes (i.e. nodes with a VALUE), extract
  // that VALUE as a String, but ignore nodes with empty or solely whitespace VALUEs.
  private String primaryChildNodeValueAsString(Node n) {
    for (int i = 0; i < n.getChildNodes().getLength(); i++) {
      String value = n.getChildNodes().item(i).getNodeValue();
      if (value != null && !(value = value.trim()).equals("")) {
        return value;
      }
    }
    return null;
  }

  // Helper method used to retrieve a non-case-or-whitespace-sensitive version of a Node's name
  private String formattedNodeName(String s) {
    return s.trim().toUpperCase();
  }

  /**
   * Primary data retrieval API for the XMLParser. Returns the {@link
   * cellsociety.util.SimulationConfiguration} configured by the parser.
   *
   * @return the simulation configuration parsed by the <code>XMLParser</code>
   */
  public SimulationConfiguration getSimulationConfiguration() {
    return this.simulationConfiguration;
  }
}

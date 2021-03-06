package cellsociety.util;

import cellsociety.simulation.Cell;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Class responsible for writing a {@link SimulationConfiguration} and set of {@link Cell}s back to
 * disk after they have been (potentially) modified.
 *
 * @author David Coffman
 */
public class SimulationWriter {

  private final SimulationConfiguration config;
  private final List<Cell> cells;
  private final Document doc;

  /**
   * Constructs a DOM tree based on the input {@link SimulationConfiguration} and {@link Cell}s
   * which can then be written to disk with a call to {@link SimulationWriter#writeToFile(File)}.
   *
   * @param config the {@link SimulationConfiguration} to write to disk
   * @param cells  the list of {@link Cell}s to write to disk
   * @throws Exception if the DOM tree construction fails
   */
  public SimulationWriter(SimulationConfiguration config, List<Cell> cells) throws Exception {
    this.config = config;
    this.cells = cells;
    this.doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    buildDocument();
  }

  // Builds the DOM tree
  private void buildDocument() {
    Node root = doc.createElement("xml");
    doc.appendChild(root);

    buildMetadata(root);
    buildGeometricConfiguration(root);
    buildSimulationParameters(root);
    buildInitialStates(root);
  }

  // Builds the <General> tree based on the SimulationConfiguration
  private void buildMetadata(Node root) {
    Node generalElement = doc.createElement("General");
    root.appendChild(generalElement);

    Node nameElement = doc.createElement("Name");
    nameElement.appendChild(doc.createTextNode(config.getSimulationName()));

    Node typeElement = doc.createElement("Type");
    typeElement.appendChild(doc.createTextNode(config.getSimulationType().name()));

    Node authorElement = doc.createElement("Author");
    authorElement.appendChild(doc.createTextNode(config.getSimulationAuthor()));

    Node descriptionElement = doc.createElement("Description");
    descriptionElement.appendChild(doc.createTextNode(config.getSimulationDescription()));

    Node[] generalElementNodes = new Node[]{nameElement, typeElement, authorElement,
        descriptionElement};

    for (Node n : generalElementNodes) {
      generalElement.appendChild(n);
    }
  }

  // Builds the <GeometricConfiguration> tree based on the SimulationConfiguration
  private void buildGeometricConfiguration(Node root) {
    Node geometricConfigElement = doc.createElement("GeometricConfiguration");
    root.appendChild(geometricConfigElement);

    Node cellShapeElement = doc.createElement("CellShape");
    cellShapeElement.appendChild(doc.createTextNode(config.getCellShape().name()));

    Node heightElement = doc.createElement("Height");
    heightElement.appendChild(doc.createTextNode(String.format("%d", config.getHeight())));

    Node widthElement = doc.createElement("Width");
    widthElement.appendChild(doc.createTextNode(String.format("%d", config.getWidth())));

    Node edgeTypeElement = doc.createElement("EdgeType");
    edgeTypeElement.appendChild(doc.createTextNode(config.getEdgeType().name()));

    Node neighborhoodSizeElement = doc.createElement("NeighborhoodSize");
    neighborhoodSizeElement.appendChild(doc.createTextNode(config.getNeighborhodSize().name()));

    Node[] geometricConfigurationNodes = new Node[]{cellShapeElement, heightElement,
        widthElement, edgeTypeElement, neighborhoodSizeElement};
    for (Node n : geometricConfigurationNodes) {
      geometricConfigElement.appendChild(n);
    }
  }

  // Builds the <SimulationParameters> tree based on the SimulationConfiguration's parameter map
  private void buildSimulationParameters(Node root) {
    Node simulationParamElement = doc.createElement("SimulationParameters");
    root.appendChild(simulationParamElement);

    for (String key : config.getSimulationParameters().keySet()) {
      Node paramElement = doc.createElement(key);
      String value = String.format("%f", config.getSimulationParameters().get(key));
      paramElement.appendChild(doc.createTextNode(value));
      simulationParamElement.appendChild(paramElement);
    }
  }

  // Builds the <InitialStates> tree based on the list of cells given as a constructor parameter
  private void buildInitialStates(Node root) {
    Node initialStateElement = doc.createElement("InitialStates");
    root.appendChild(initialStateElement);

    for (Cell c : cells) {
      if (c.getEncoding() == 0) {
        continue;
      }
      Node cellElement = doc.createElement("Cell");

      Node rowElement = doc.createElement("Row");
      rowElement.appendChild(doc.createTextNode(String.format("%d", c.getY())));

      Node colElement = doc.createElement("Column");
      colElement.appendChild(doc.createTextNode(String.format("%d", c.getX())));

      Node stateElement = doc.createElement("State");
      stateElement.appendChild(doc.createTextNode(String.format("%d", c.getEncoding())));

      Node[] cellStateElements = new Node[]{rowElement, colElement, stateElement};
      for (Node n : cellStateElements) {
        cellElement.appendChild(n);
      }

      initialStateElement.appendChild(cellElement);
    }
  }

  /**
   * Writes the DOM tree to disk after it is constructed (by the object's constructor).
   *
   * @param f the {@link File} to which the {@link SimulationConfiguration}'s contents should be
   *          written
   * @throws Exception if the write fails
   */
  public void writeToFile(File f) throws Exception {
    // Adapted from tutorial at https://www.baeldung.com/java-write-xml-document-file.
    f.createNewFile();
    BufferedWriter w = new BufferedWriter(new FileWriter(f));
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    DOMSource source = new DOMSource(this.doc);
    FileWriter writer = new FileWriter(f);
    StreamResult result = new StreamResult(writer);
    transformer.transform(source, result);
  }
}

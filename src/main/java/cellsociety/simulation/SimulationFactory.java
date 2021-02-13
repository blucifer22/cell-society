package cellsociety.simulation;

import cellsociety.graphics.GraphicalCell;
import cellsociety.util.XMLParser;
import java.io.File;
import java.util.List;
import java.util.Map;

public class SimulationFactory {
  private Simulation sim;
  private List<GraphicalCell> graphicalCells;

  /**
   * Creates a {@link cellsociety.simulation.Simulation} with the configurations specified from an
   * XML file.
   *
   * @param file - An XML file that will be parsed to create a new simulation.
   */
  public void loadSimulationFile(File file) throws Exception {
    this.sim = null;
    this.graphicalCells = null;

    XMLParser parser = new XMLParser(file);
    Map<String, String> metaData = parser.getSimulationMetadata();
    Map<String, Double> config = parser.getSimulationParameters();
    List<int[]> nonDefaultStates = parser.getInitialNonDefaultStates();
    Simulation simulation;

    switch (metaData.get("Name")) {
      case "Fire" -> simulation = new FireSimulation(metaData, config, nonDefaultStates);
      case "Conway" -> simulation = new ConwaySimulation(metaData, config, nonDefaultStates);

      default -> throw new Exception("Invalid simulation type specified.");
    }
    simulation.initialize();
    this.sim = simulation;
  }

  /**
   * Returns a successfully constructed {@link cellsociety.simulation.Simulation}.
   *
   * @return the factory's constructed {@link cellsociety.simulation.Simulation}
   */
  public Simulation getSimulation() {
    assert this.sim != null;
    return this.sim;
  }

  /**
   * Returns a successfully constructed {@link cellsociety.simulation.Simulation}'s associated
   * {@link cellsociety.graphics.GraphicalCell}s.
   *
   * @return the simulated {@link cellsociety.graphics.GraphicalCell}s
   */
  public List<GraphicalCell> getGraphicalCells() {
    assert this.graphicalCells != null;
    return this.graphicalCells;
  }

}

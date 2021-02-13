package cellsociety.simulation;

import cellsociety.graphics.GraphicalCell;
import cellsociety.util.XMLParser;
import java.io.File;
import java.util.List;
import java.util.Map;

public class SimulationFactory {
  private Simulation sim;

  /**
   * Creates a {@link cellsociety.simulation.Simulation} with the configurations specified from an
   * XML file.
   *
   * @param file - An XML file that will be parsed to create a new simulation.
   */
  public void loadSimulationFile(File file) throws Exception {
    this.sim = null;

    XMLParser parser = new XMLParser(file);
    Map<String, String> metadata = parser.getSimulationMetadata();
    Map<String, Double> config = parser.getSimulationParameters();
    List<int[]> nonDefaultStates = parser.getInitialNonDefaultStates();
    Simulation simulation;

    switch (metadata.get("Type")) {
      case "Fire" -> simulation = new FireSimulation(metadata, config, nonDefaultStates);
      case "Conway" -> simulation = new ConwaySimulation(metadata, config, nonDefaultStates);
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
}

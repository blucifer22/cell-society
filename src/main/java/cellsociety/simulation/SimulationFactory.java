package cellsociety.simulation;

import cellsociety.util.XMLParser;
import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Paint;

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
    Map<String, String> metaData = parser.getSimulationMetadata();
    Map<String, Double> config = parser.getSimulationParameters();
    List<int[]> nonDefaultStates = parser.getInitialNonDefaultStates();
    Simulation simulation = null;

    switch (metaData.get("Name")) {
      case "Fire Simulation":
        simulation = new FireSimulation(metaData, config, nonDefaultStates);
        break;
      default:
    }
    if(simulation == null) throw new Exception("Invalid getSimulation type specified.");
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

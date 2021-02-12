package cellsociety.simulation;

import cellsociety.util.XMLParser;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * The controller class that creates the simulation model for the view to use.
 *
 * <p>This class contains an XML parser and can create {@link cellsociety.simulation.Simulation}
 * objects.
 */
public class SimulationManager {
  private XMLParser parser;

  public SimulationManager() {
    parser = new XMLParser();
  }

  public Simulation createSimulation(File file) {
    try {
      parser.createConfiguration(file);
      Map<String, String> metaData = parser.getSimulationMetadata();
      Map<String, Double> config = parser.getSimulationParameters();
      List<int[]> nonDefaultStates = parser.getInitialNonDefaultStates();
      return new Simulation(metaData, config, nonDefaultStates);
    } catch (Exception e) {
      return null;
    }
  }
}

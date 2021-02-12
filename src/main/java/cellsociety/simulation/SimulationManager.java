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
  private Simulation simulation;

  public SimulationManager(File f) throws Exception {
    parser = new XMLParser(f);
    // one of you guys is going to need to fix this; I don't know what's going on here
    // this codebase is a labyrinth
  }

  /**
   * Creates a simulation with the configurations specified from an XML file.
   *
   * @param file - An XML file that will be parsed to create a new simulation.
   */
  public Simulation createSimulation(File file) {
    try {
      parser.createConfiguration(file);
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
      simulation.initialize();
      return simulation;
    } catch (Exception e) {
      return null;
    }
  }
}

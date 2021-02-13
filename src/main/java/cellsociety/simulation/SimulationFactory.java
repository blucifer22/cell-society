package cellsociety.simulation;

import cellsociety.util.XMLParser;
import java.io.File;
import java.util.List;
import java.util.Map;

public class SimulationFactory {

  /**
   * Creates a simulation with the configurations specified from an XML file.
   *
   * @param file - An XML file that will be parsed to create a new simulation.
   */
  public Simulation createSimulation(File file) throws Exception {
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
    if(simulation == null) throw new Exception("Invalid simulation type specified.");
    simulation.initialize();
    return simulation;
  }

}

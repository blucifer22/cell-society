package cellsociety.simulation;

import cellsociety.util.XMLParser;
import java.io.File;
import java.util.List;
import java.util.Map;

public class SimulationManager {
  private XMLParser parser;

  public Simulation createSimulation(File file) {
	  try {
		  XMLParser parser = new XMLParser(file);
		  Map<String, String> metaData = parser.getSimulationMetadata();
		  Map<String, Double> config = parser.getSimulationParameters();
		  List<int[]> nonDefaultStates = parser.getInitialNonDefaultStates();
		  return new Simulation(metaData, config, nonDefaultStates);
	  } catch(Exception e) {
		  return null;
	  }
  }
}

package cellsociety.simulation;

import java.util.List;
import java.util.Map;

public class Simulation {
  protected CellGrid cellGrid;
  protected List<Cell> cells;

  public Simulation(
      Map<String, String> metaData, Map<String, Double> config, List<int[]> nonDefaultStates) {
	
  }

  public void computeState() {}

  public void commitState() {}

  public void step() {}
}

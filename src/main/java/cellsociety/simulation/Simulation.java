package cellsociety.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Simulation<T extends Cell> {
  public static final double DEFAULT_CELL_NUMBER = 10;
  protected CellGrid<T> cellGrid;
  protected List<T> cells;
  protected String name;
  protected double numCells;
  protected Map<String, Double> config;
  protected Map<String, String> metaData;
  List<int[]> nonDefaultStates;

  public Simulation(
      Map<String, String> metaData, Map<String, Double> config, List<int[]> nonDefaultStates) {
    this.name = metaData.getOrDefault("Name", "UnknowName");
    this.nonDefaultStates = nonDefaultStates;
    this.config = config;
    double x = config.getOrDefault("Width", DEFAULT_CELL_NUMBER);
    double y = config.getOrDefault("Height", DEFAULT_CELL_NUMBER);
    this.numCells = x * y;
    cells = new ArrayList<>();
  }

  /**
   * Allows the simulation to initialize it's specific cells.
   *
   * <p>Allows the simulation to create its needed fields and rules to run.
   */
  protected abstract void initialize();

  protected void computeState() {}

  protected void commitState() {}

  public List<T> getCells() {
	  return this.cells;
  }

  public void step() {}
}

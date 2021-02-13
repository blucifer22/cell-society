package cellsociety.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Simulation {
  public static final double DEFAULT_CELL_NUMBER = 10;
  protected CellGrid cellGrid;
  protected List<Cell> cells;
  protected String name;
  protected double numCells;
  protected Map<String, Double> config;
  protected Map<String, String> metaData;
  private int numRows;
  private int numCols;
  List<int[]> nonDefaultStates;

  public Simulation(
      Map<String, String> metaData, Map<String, Double> config, List<int[]> nonDefaultStates) {
    this.name = metaData.getOrDefault("Name", "UnknowName");
    this.nonDefaultStates = nonDefaultStates;
    this.config = config;
    this.numCols = (int) (double) config.getOrDefault("Width", DEFAULT_CELL_NUMBER);
    this.numRows = (int) (double) config.getOrDefault("Height", DEFAULT_CELL_NUMBER);
    this.numCells = numCols * numRows;
    cells = new ArrayList<>();
  }

  /**
   * Allows the simulation to initialize it's specific cells.
   *
   * <p>Allows the simulation to create its needed fields and rules to run.
   */
  protected abstract void initialize();

  protected void computeState() {
	  for (Cell cell : cells) {
		  cell.computeNextCellState();
	  }
  }

  protected void commitState() {
	  for (Cell cell : cells) {
	    assert(cell.nextCellState != null);
		  cell.updateCellState();
	  }
  }

  public List<Cell> getCells() {
	  return this.cells;
  }

  public int getNumRows() {
    return numRows;
  }

  public int getNumCols() {
    return numCols;
  }

  public void step() {
	  computeState();
	  commitState();
  }
}

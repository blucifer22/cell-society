package cellsociety.simulation;

import cellsociety.util.CellShape;
import java.util.List;
import java.util.Map;

/**
 * This class holds all simulation data and fields.
 *
 * @author Joshua Petitma
 *     <p>The simulation class is a general class that holds all the cells used within a simulation
 *     and is capable of stepping simulations forward.
 */
public class Simulation {
  public static final double DEFAULT_CELL_NUMBER = 10;
  protected CellGrid cellGrid;
  protected List<Cell> cells;
  protected String name;
  protected double numCells;
  protected Map<String, Double> config;
  protected Map<String, String> metaData;
  private final int numRows;
  private final int numCols;
  List<int[]> nonDefaultStates;

  public Simulation(
      Map<String, String> metaData, Map<String, Double> config, List<int[]> nonDefaultStates) {
    this.name = metaData.getOrDefault("Name", "UnknownName");
    this.nonDefaultStates = nonDefaultStates;
    this.config = config;
    this.numCols = (int) (double) config.getOrDefault("Width", DEFAULT_CELL_NUMBER);
    this.numRows = (int) (double) config.getOrDefault("Height", DEFAULT_CELL_NUMBER);
    this.numCells = numCols * numRows;
  }

  /**
   * Allows the simulation to initialize it's specific cells.
   *
   * <p>Allows the simulation to create its needed fields and rules to run.
   */
  protected void initialize(List<Cell> cells) {
    this.cells = cells;
    this.cellGrid = new CellGrid(cells, config);
    for (int[] arr : nonDefaultStates) {
      Cell cell = cellGrid.getCell(arr[1], arr[0]);
      cell.setCellState(arr[2]);
    }
  }

  /**
   * Set cells in a state to be prepared to advanced to their next state.
   *
   * <p>This method calls cells to compute their next state.
   */
  protected void computeState() {
    for (Cell cell : cells) {
      cell.computeNextCellState();
    }
  }

  /**
   * Returns the number of cells within this simulation
   *
   * @return - The number of cells this simulation uses.
   */
  public double getNumCells() {
    return numCells;
  }

  /**
   * Returns the name of a simulation
   *
   * @return - The name of the simulation from within the XML file.
   */
  public String getName() {
	  return this.name;
  }

  /**
   * Advances cells to their next state.
   *
   * <p>This method is usually called after calling {@link #computeState}
   */
  protected void commitState() {
    for (Cell cell : cells) {
      cell.updateCellState();
    }
  }

  /**
   * Returns a list of cells.
   *
   * @return - A list of cells currently used in the simulation.
   */
  public List<Cell> getCells() {
    return this.cells;
  }

  /**
   * Returns the number of rows within the simulation.
   *
   * @return - The number of rows within the simulation.
   */
  public int getNumRows() {
    return numRows;
  }

  /**
   * The number of columns within the simulation.
   *
   * @return - The number of columns within this simulation.
   */
  public int getNumCols() {
    return numCols;
  }

  /**
   * Steps the simulation forward by one.
   *
   * <p>This method computes and commits the next state of all the cells in the simulation.
   */
  public void step() {
    computeState();
    commitState();
  }

  public void pokeCell(int x, int y) {
	  cellGrid.pokeCell(x, y);
  }

  public CellShape getCellShape() {
    return CellShape.TRIANGLE;
  }
}

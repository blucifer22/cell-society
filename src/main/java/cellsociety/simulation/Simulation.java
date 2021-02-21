package cellsociety.simulation;

import cellsociety.util.CellShape;
import cellsociety.util.SimulationConfiguration;
import cellsociety.util.SimulationWriter;
import java.io.File;
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
  protected CellGrid cellGrid;
  protected List<Cell> cells;
  private final List<int[]> nonDefaultStates;
  private final SimulationConfiguration configuration;
  protected int numCells;

  public Simulation(SimulationConfiguration config) {
    this.nonDefaultStates = config.getInitialNonDefaultCellStates();
    this.numCells = config.getHeight() * config.getWidth();
    this.configuration = config;
  }

  /**
   * Allows the simulation to initialize it's specific cells.
   *
   * <p>Allows the simulation to create its needed fields and rules to run.
   */
  protected void initialize(List<Cell> cells) {
    this.cells = cells;
    this.cellGrid = new CellGrid(cells, configuration.getWidth(), configuration.getHeight(),
        configuration.getCellShape());
    for (int[] arr : nonDefaultStates) {
      Cell cell = cellGrid.getCell(arr[0], arr[1]);
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
   * Sets the specific simulation parameter.
   *
   * Modifies the current simulation to set its
   * parameter mid-simulation.
   *
   * @param param - The parameter to set.
   * @param value - The value to replace within the parameter
   */
  public void setParameter(String param, double value) {
    this.configuration.updateSimulationParameter(param, value);
  }

  /**
   * Returns the name of a simulation
   *
   * @return - The name of the simulation from within the XML file.
   */
  public String getName() {
	  return this.configuration.getSimulationName();
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
    return this.configuration.getHeight();
  }

  /**
   * The number of columns within the simulation.
   *
   * @return - The number of columns within this simulation.
   */
  public int getNumCols() {
    return this.configuration.getWidth();
  }

  public Map<String, Double> getSimulationParameters() {
    return this.configuration.getSimulationParameters();
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

  public void writeToDisk(File f) throws Exception {
    SimulationWriter w = new SimulationWriter(this.configuration, this.cells);
    w.writeToFile(f);
  }

  public CellShape getCellShape() {
    return this.configuration.getCellShape();
  }
}

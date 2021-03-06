package cellsociety.simulation;

import cellsociety.util.SimulationConfiguration;
import cellsociety.util.SimulationConfiguration.CellShape;
import cellsociety.util.SimulationConfiguration.RandomGridGenerationType;
import cellsociety.util.SimulationWriter;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * This class holds all simulation data and fields.
 *
 * @author Joshua Petitma
 * <p>The simulation class is a general class that holds all the cells used within a simulation
 * and is capable of stepping simulations forward.
 */
public class Simulation {

  private final SimulationConfiguration configuration;
  private CellGrid cellGrid;
  private List<Cell> cells;
  private final int numCells;

  /**
   * Initialize a simulation with the given configuration.
   *
   * @param config - The configuration the simulation will use to initialize its cells and cellgrid
   */
  public Simulation(SimulationConfiguration config) {
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
    this.cellGrid =
        new CellGrid(
            cells,
            configuration.getWidth(),
            configuration.getHeight(),
            configuration.getCellShape(),
            configuration.getEdgeType(),
            configuration.getNeighborhodSize());
    RandomGridGenerationType type = configuration.getRandomGridGenerationType();
    if (type == RandomGridGenerationType.COUNT || type == RandomGridGenerationType.FRACTION) {
      createRandomStates(configuration.getRandomInitialStates(), type);
    } else {
      List<int[]> nonDefaultStates = configuration.getInitialNonDefaultCellStates();
      for (int[] arr : nonDefaultStates) {
        int row = arr[0];
        int col = arr[1];
        Cell cell = cellGrid.getCell(row, col);
        cell.setCellState(arr[2]);
      }
    }
  }

  private void createRandomStates(Map<Integer, Double> freqMap, RandomGridGenerationType type) {
    int rows = configuration.getWidth();
    int cols = configuration.getHeight();
    freqMap.forEach(
        (Integer state, Double freq) -> {
          double target = type == RandomGridGenerationType.COUNT ? freq : numCells / freq;
          for (int i = 0; i < target; i++) {
            int cellValue = 1;
            // keeps going till identifies empty cell
            while (cellValue != 0) {
              int row = (int) (Math.random() * rows);
              int col = (int) (Math.random() * cols);
              Cell cell = cellGrid.getCell(row, col);
              if (cell.getCellState() == 0) {
                cell.setCellState(state);
                break;
              }
            }
          }
        });
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
   * <p>Modifies the current simulation to set its parameter mid-simulation.
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

  /**
   * Returns a mapping of the parameters used within the simulation
   *
   * @return The mapping of the parameters for the simulation.
   */
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

  /**
   * Pokes a specified cell to change its state.
   *
   * @param row    - The row in the grid in which this cell is found
   * @param column - The column in the grid in which this cell is found
   */
  public void pokeCell(int row, int column) {
    cellGrid.pokeCell(row, column);
  }

  /**
   * Saves the current state of the simulation to enable further replay.
   *
   * <p>The saved file will be written in the XML configuration format.
   *
   * @param file - The file in which the XML configuration will be written to.
   */
  public void writeToDisk(File file) throws Exception {
    SimulationWriter writer = new SimulationWriter(this.configuration, this.cells);
    writer.writeToFile(file);
  }

  /**
   * Returns the shape of the cells used within the simulation.
   *
   * @return - The shape of the cells within the simulation.
   */
  public CellShape getCellShape() {
    return this.configuration.getCellShape();
  }
}

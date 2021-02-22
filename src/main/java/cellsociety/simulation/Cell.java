package cellsociety.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A general cell type.
 *
 * @author Joshua Petitma
 *     <p>Cells hold state and examine their neighboring states to determine whether or not they
 *     must change state.
 */
public abstract class Cell {
  private List<Cell> neighbors;
  private int cellState;
  private int nextCellState;
  private int posX;
  private int posY;
  private Map<String, Double> params;

  /**
   * Constructs a cell with the specified state.
   *
   * <p>Cells must contain state and have neighbors, the list of neighbors for this cell is
   * instantiated within this constructor
   *
   * @param cellState - The initial state of the cell.
   */
  protected Cell(int cellState) {
    this.cellState = cellState;
    neighbors = new ArrayList<>();
  }

  /**
   * Construct a cell.
   *
   * @param cellState - The initial integer representation of the cell
   * @param params - The rules the cell uses to detrmine its next state.
   */
  protected Cell(int cellState, Map<String, Double> params) {
    this(cellState);
    this.params = params;
  }
  /**
   * The integer encoding for this cell.
   *
   * <p>The integer encoding corresponds to the state of the cell.
   *
   * @return - An integer representing the cell's state.
   */
  public int getEncoding() {
    return getCellState();
  }

  /**
   * Returns the current state of the cell.
   *
   * @return The current state of the cell.
   */
  protected int getNextCellState() {
    return nextCellState;
  }

  /**
   * Determines the next state of this cell.
   *
   * <p>This cell uses its rules to determine what state it should advance to on the next generation
   * iteration.
   */
  public abstract void computeNextCellState();

  /**
   * Moves a cell to its currently cached state.
   *
   * <p>If the cell has no {@link #nextCellState} then this cell does not advance. Must call {@link
   * #computeNextCellState} before calling.
   */
  protected void updateCellState() {
    cellState = nextCellState;
  }

  /**
   * Advances the current cell state to what is assigned as its next cell state.
   *
   * @param state - The state which the cell will become on the next generation
   */
  protected void setNextCellState(int state) {
    nextCellState = state;
  }

  /**
   * Sets the next state of the current cell.
   *
   * @param state - The cell state to turn this cell into.
   */
  protected void setCellState(int state) {
    this.cellState = state;
  }

  /**
   * Sets the next state of the cell while also passing a list of values it may use to initialize
   * itself.
   *
   * @param state - The state to become
   * @param values - The values the cell can use to do its internal calculations.
   */
  protected void setNextCellState(int state, Map<String, Double> values) {}

  /**
   * Adds a neighbor to the list of neighboring cells.
   *
   * @param neighbor - The cell to be added to the list.
   */
  protected void addNeighbor(Cell neighbor) {
    neighbors.add(neighbor);
  }

  /**
   * Removes a neighbor from the list of neighboring cells.
   *
   * @param neighbor - The cell to be removed from the list.
   */
  protected void removeNeighbor(Cell neighbor) {
    neighbors.remove(neighbor);
  }

  /**
   * The internal Y position of this cell.
   *
   * <p>Returns the Y placement this cell has within the simulation grid.
   */
  public int getY() {
    return posY;
  }

  /**
   * The internal X position of this cell.
   *
   * <p>Returns the X placement this cell has within the simulation grid.
   */
  public int getX() {
    return posX;
  }

  /**
   * Sets the internal X position of this cell.
   *
   * @param posX - The placement X this cell has within a grid.
   */
  public void setX(int posX) {
    this.posX = posX;
  }

  /**
   * Sets the internal Y position of this cell.
   *
   * @param posY - The placement Y this cell has within a grid.
   */
  protected void setY(int posY) {
    this.posY = posY;
  }

  /**
   * Asks the cell to modify its state.
   *
   * <p>When poked a cell will change its current state.
   */
  public void poke() {}

  /**
   * Retrieves the list of current neighbors the cell uses to set its state.
   *
   * @return - A list of cells that this cell uses to calculate its next state
   */
  protected List<Cell> getNeighbors() {
    return this.neighbors;
  }

  /**
   * Returns a specific rule from within this cell.
   *
   * <p>Calling this will retrieve a rule from this cell's corresponding simulation.
   */
  protected double getParam(String key) {
    return params.getOrDefault(key, -1.0);
  }

  /**
   * Set the neighbors of this Cell
   *
   * @param neighbors the new neighbors of this Cell
   */
  public void setNeighbors(List<Cell> neighbors) {
    this.neighbors = neighbors;
  }

  /**
   * Get the current cellState of this Cell
   * @return the current cellState of this Cell
   */
  public int getCellState() {
    return cellState;
  }
}

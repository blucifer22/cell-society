package cellsociety.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * A general cell type.
 *
 * <p>Cells hold state and examine their neighboring states to determine whether or not they must
 * change state.
 */
public abstract class Cell<T extends CellState> {
  protected List<Cell<T>> neighbors;
  protected T state;
  protected T nextState;
  protected int posX;
  protected int posY;

  /**
   * Constructs a cell with the specified state.
   *
   * <p>Cells must contain state and have neighbors, the list of neighbors for this cell is
   * instantiated within this constructor
   *
   * @param state - The initial state of the cell.
   */
  protected Cell(T state) {
    this.state = state;
    neighbors = new ArrayList<>();
  }

  /**
   * Returns the current state of the cell.
   *
   * @return The current state of the cell.
   */
  public T getCurrentState() {
    return state;
  }

  /**
   * The integer encoding for this cell.
   *
   * The integer encoding corresponds to the state
   * of the cell.
   * @return - An integer representing the cell's state.
   */
  public int getEncoding() {
	  return 0;
  }

  /**
   * Returns the current state of the cell.
   *
   * @return The current state of the cell.
   */
  protected T getNextState() {
    return nextState;
  }

  /**
   * Determines the next state of this cell.
   *
   * <p>This cell uses its rules to determine what state it should advance to on the next generation
   * iteration.
   */
  public abstract void computeNextState();

  /**
   * Moves a cell to its currently cached state.
   *
   * <p>If the cell has no {@link #nextState} then this cell does not advance. Must call {@link
   * #computeNextState} before calling.
   */
  protected void updateState() {
    if (nextState != null) {
      state = nextState;
      nextState = null;
    }
  }

  protected void setNextState(T state) {
    if (state != null) {
      nextState = state;
    }
  }

  protected void setState(T state) {
    if (state != null) {
      this.state = state;
    }
  }

  /**
   * Adds a neighbor to the list of neighboring cells.
   *
   * @param neighbor - The cell to be added to the list.
   */
  protected void addNeighbor(Cell<T> neighbor) {
    neighbors.add(neighbor);
  }

  /**
   * Removes a neighbor from the list of neighboring cells.
   *
   * @param neighbor - The cell to be removed from the list.
   */
  protected void removeNeighbor(Cell<T> neighbor) {
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
  protected void setX(int posX) {
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
}

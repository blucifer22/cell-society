package cellsociety.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * A general cell type.
 *
 * Cells hold state and examine their neighboring states to determine whether or not
 * they must change state.
 *
 */
public abstract class Cell<T extends CellState> {
  protected List<Cell<T>> neighbors;
  protected T state;
  protected T nextState;

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
  public void updateState() {
    if (nextState != null) {
      state = nextState;
    }
  }

  /**
   * Adds a neighbor to the list of neighboring cells.
   *
   * @param neighbor - The cell to be added to the list.
   */
  public void addNeighbor(Cell<T> neighbor) {
    neighbors.add(neighbor);
  }

  /**
   * Removes a neighbor from the list of neighboring cells.
   *
   * @param neighbor - The cell to be removed from the list.
   */
  public void removeNeighbor(Cell<T> neighbor) {
    neighbors.remove(neighbor);
  }
}

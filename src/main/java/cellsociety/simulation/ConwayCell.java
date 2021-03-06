package cellsociety.simulation;

import java.util.Map;

/**
 * This class handles the behavior of Cells in Conway's Game of Life, and thus the state transitions
 * therein.
 *
 * @author Marc Chmielewski
 */
public class ConwayCell extends Cell {

  public static final int DEAD = 0;
  public static final int ALIVE = 1;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for ConwayCells is DEAD.
   */
  public ConwayCell(Map<String, Double> params) {
    super(DEAD, params);
  }

  /**
   * Constructs this cell with the specified default state.
   *
   * @param state - The state to use for this cell.
   */
  public ConwayCell(int state) {
    super(state);
  }

  @Override
  public void poke() {
    if (getCellState() == DEAD) {
      setCellState(ALIVE);
      setNextCellState(ALIVE);
    } else {
      setCellState(DEAD);
      setNextCellState(DEAD);
    }
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>Conway's Rules are as follows:
   *
   * <p>Any live cell with two or three live neighbours survives. Any dead cell with three live
   * neighbours becomes a live cell. All other live cells die in the next generation. Similarly, all
   * other dead cells stay dead.
   */
  public void computeNextCellState() {
    int numLiveNeighbors = 0;
    for (Cell cell : getNeighbors()) {
      if (cell.getCellState() == ALIVE) {
        numLiveNeighbors++;
      }
    }
    if (getCellState() == ALIVE
        && numLiveNeighbors >= getParam("AliveNumberMin")
        && numLiveNeighbors <= getParam("AliveNumberMax")) {
      setNextCellState(ALIVE);
    } else if (getCellState() == DEAD
        && numLiveNeighbors >= getParam("SpawnNumberMin")
        && numLiveNeighbors <= getParam("SpawnNumberMax")) {
      setNextCellState(ALIVE);
    } else {
      if (getCellState() != DEAD) {
        setNextCellState(DEAD);
      }
    }
  }
}

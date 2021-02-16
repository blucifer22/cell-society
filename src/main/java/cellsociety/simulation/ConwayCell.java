package cellsociety.simulation;

/**
 * This class handles the behavior of Cells in Conway's Game of Life, and thus the state transitions
 * therein.
 *
 * @author Marc Chmielewski
 */
public class ConwayCell extends Cell {

  public static final int DEAD = 0;
  public static final int ALIVE = 1;
  public static ConwayRule rule;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for ConwayCells is DEAD.
   */
  public ConwayCell() {
    super(DEAD);
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
  protected void poke() {
	  if (cellState == DEAD) {
		  cellState = ALIVE;
	  } else {
		  cellState = DEAD;
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
    for (Cell cell : neighbors) {
      if (cell.getCurrentCellState() == ALIVE) {
        numLiveNeighbors++;
      }
    }
    if (cellState == ALIVE
        && numLiveNeighbors >= rule.getAliveNumberMin()
        && numLiveNeighbors <= rule.getAliveNumberMax()) {
      nextCellState = ALIVE;
    } else if (cellState == DEAD
        && numLiveNeighbors >= rule.getSpawnNumberMin()
        && numLiveNeighbors <= rule.getSpawnNumberMax()) {
      nextCellState = ALIVE;
    } else {
      if (cellState != DEAD) {
        nextCellState = DEAD;
      }
    }
  }
}

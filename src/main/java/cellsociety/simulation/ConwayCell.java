package cellsociety.simulation;

/**
 * This class handles the behavior of Cells in Conway's Game of Life, and thus the state transitions
 * therein.
 *
 * @author Marc Chmielewski
 */
public class ConwayCell extends Cell {

  public static ConwayRule rule;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for ConwayCells is DEAD.
   */
  public ConwayCell() {
    super(new ConwayState(ConwayState.DEAD));
  }

  /**
   * Constructs this cell with the specified default state.
   *
   * @param state - The state to use for this cell.
   */
  public ConwayCell(ConwayState state) {
    super(state);
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
  public void computeNextState() {
    int numLiveNeighbors = 0;
    for (Cell cell : neighbors) {
      if (cell.getCurrentState().getState() == ConwayState.ALIVE) {
        numLiveNeighbors++;
      }
    }
    if (state.getState() == ConwayState.ALIVE
        && numLiveNeighbors >= rule.getAliveNumberMin()
        && numLiveNeighbors <= rule.getAliveNumberMax()) {
      nextState = null; // null states remain the same
    } else if (state.getState() == ConwayState.DEAD
        && numLiveNeighbors >= rule.getSpawnNumberMin()
        && numLiveNeighbors <= rule.getSpawnNumberMax()) {
      nextState = new ConwayState(ConwayState.ALIVE);
    } else {
      if (state.getState() != ConwayState.DEAD) {
        nextState = new ConwayState(ConwayState.DEAD);
      }
    }
  }
}

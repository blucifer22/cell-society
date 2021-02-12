package cellsociety.simulation;

import cellsociety.simulation.ConwayState.STATE;

/**
 * This class handles the behavior of Cells in Conway's Game of Life, and thus the state transitions
 * therein.
 *
 * @author Marc Chmielewski
 */
public class ConwayCell extends Cell<ConwayState> {

  public static ConwayRule rule;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for ConwayCells is DEAD.
   */
  public ConwayCell() {
    super(new ConwayState(STATE.DEAD));
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
   * Conway's Rules are as follows:
   *
   * <p>Any live cell with two or three live neighbours survives.
   * Any dead cell with three live
   * neighbours becomes a live cell.
   * All other live cells die in the next generation.
   * Similarly, all other dead cells stay dead.
   */
  public void computeNextState() {
    int numLiveNeighbors = 0;
    for (Cell cell : neighbors) {
      if (cell.getCurrentState().getState() == STATE.ALIVE) {
        numLiveNeighbors++;
      }
    }
    if(state.getState() == STATE.ALIVE && numLiveNeighbors >= 2 && numLiveNeighbors <= 3) {
      nextState.setState(STATE.ALIVE);
    }
    else if(state.getState() == STATE.DEAD && numLiveNeighbors == 3) {
      nextState.setState(STATE.ALIVE);
    }
    else {
      nextState.setState(STATE.DEAD);
    }
  }
}

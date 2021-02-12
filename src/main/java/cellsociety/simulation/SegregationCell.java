package cellsociety.simulation;

import cellsociety.simulation.SegregationState.STATE;

/**
 * This class handles the behavior of Cells in the Segregation simulation, and thus the state
 * transitions therein.
 *
 * @author Marc Chmielewski
 */
public class SegregationCell extends Cell<SegregationState> {
  public static SegregationRule rule;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for SegregationCells is EMPTY.
   */
  public SegregationCell() {
    super(new SegregationState(SegregationState.STATE.EMPTY));
  }

  /**
   * Constructs this cell with the specified initial state.
   *
   * @param state - The state to use for this cell.
   */
  public SegregationCell(SegregationState state) {
    super(state);
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>Segregation Rules are as follows:
   *
   * <p>If a Cell is EMPTY and it has a neighbor that is FULL, the Cell's next state will be FULL.
   * If the Cell is FULL, it will remain FULL.
   */
  public void computeNextState() {
    double numTypeA = 0;
    double numTypeB = 0;
    for (Cell cell : neighbors) {
      if (cell.getCurrentState().getState() == STATE.TYPE_A) {
        numTypeA++;
      } else if (cell.getCurrentState().getState() == STATE.TYPE_B) {
        numTypeB++;
      }
    }
    switch (this.getCurrentState().getState()) {
      case TYPE_A -> {
        if ((numTypeA / neighbors.size()) >= rule.getCutoffPercentage()) {
          this.nextState = null; // null states remain the same
        } else {
          swapWithEmpty();
        }
      }
      case TYPE_B -> {
        if ((numTypeB / neighbors.size()) >= rule.getCutoffPercentage()) {
          this.nextState = null;
        } else {
          swapWithEmpty();
        }
      }
    }
  }

  private void swapWithEmpty() {
    for (Cell<SegregationState> cell : neighbors) {
      if (cell.getCurrentState().getState() == STATE.EMPTY
          && cell.getNextState().getState() == STATE.EMPTY) {
        nextState = cell.getCurrentState();
		cell.setNextState(this.state);
      }
    }
    if (this.nextState.getState()
        != STATE.EMPTY) { // If there are no possible empty cells to swap into
      this.nextState = null;
    }
  }
}

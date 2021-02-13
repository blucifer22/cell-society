package cellsociety.simulation;

/**
 * This class handles the behavior of Cells in the Segregation simulation, and thus the state
 * transitions therein.
 *
 * @author Marc Chmielewski
 */
public class SegregationCell extends Cell {
  public static SegregationRule rule;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for SegregationCells is EMPTY.
   */
  public SegregationCell() {
    super(new SegregationState(SegregationState.EMPTY));
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
   * <p>If this Cell is surrounded by more than cutoffPercentage other cells of its own type it
   * remains in place. Otherwise, if there is an available, adjacent empty cell it will attempt to
   * swap to that Cell.
   */
  public void computeNextState() {
    double numTypeA = 0;
    double numTypeB = 0;
    for (Cell cell : neighbors) {
      if (cell.getCurrentState().getState() == SegregationState.TYPE_A) {
        numTypeA++;
      } else if (cell.getCurrentState().getState() == SegregationState.TYPE_B) {
        numTypeB++;
      }
    }
    switch (this.getCurrentState().getState()) {
      case SegregationState.TYPE_A -> {
        if ((numTypeA / neighbors.size()) >= rule.getCutoffPercentage()) {
          this.nextState = null; // null states remain the same
        } else {
          swapWithEmpty();
        }
      }
      case SegregationState.TYPE_B -> {
        if ((numTypeB / neighbors.size()) >= rule.getCutoffPercentage()) {
          this.nextState = null;
        } else {
          swapWithEmpty();
        }
      }
    }
  }

  private void swapWithEmpty() {
    for (Cell cell : neighbors) {
      if (cell.getCurrentState().getState() == SegregationState.EMPTY
          && cell.getNextState().getState() == SegregationState.EMPTY) {
        nextState = cell.getCurrentState();
		cell.setNextState(this.state);
      }
    }
    if (this.nextState.getState()
        != SegregationState.EMPTY) { // If there are no possible empty cells to swap into
      this.nextState = null;
    }
  }
}

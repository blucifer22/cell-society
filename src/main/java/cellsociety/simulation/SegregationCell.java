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
  public void computeNextCellState() {
    double numTypeA = 0;
    double numTypeB = 0;
    for (Cell cell : neighbors) {
      if (cell.getCurrentCellState().getState() == SegregationState.TYPE_A) {
        numTypeA++;
      } else if (cell.getCurrentCellState().getState() == SegregationState.TYPE_B) {
        numTypeB++;
      }
    }
    switch (this.getCurrentCellState().getState()) {
      case SegregationState.TYPE_A -> {
        if ((numTypeA / neighbors.size()) >= rule.getCutoffPercentage()) {
          this.nextCellState = null; // null states remain the same
        } else {
          swapWithEmpty();
        }
      }
      case SegregationState.TYPE_B -> {
        if ((numTypeB / neighbors.size()) >= rule.getCutoffPercentage()) {
          this.nextCellState = null;
        } else {
          swapWithEmpty();
        }
      }
    }
  }

  private void swapWithEmpty() {
    for (Cell cell : neighbors) {
      if (cell.getCurrentCellState().getState() == SegregationState.EMPTY
          && cell.getNextCellState().getState() == SegregationState.EMPTY) {
        nextCellState = cell.getCurrentCellState();
		    cell.setNextCellState(this.cellState);
		    break;
      }
    }
    if (this.nextCellState.getState()
        != SegregationState.EMPTY) { // If there are no possible empty cells to swap into
      this.nextCellState = null;
    }
  }
}

package cellsociety.simulation;

import java.util.Collections;
import java.util.Map;

/**
 * This class handles the behavior of Cells in the Segregation simulation, and thus the state
 * transitions therein.
 *
 * @author Marc Chmielewski
 */
public class SegregationCell extends Cell {

  public static final int EMPTY = 0;
  public static final int TYPE_A = 1;
  public static final int TYPE_B = 2;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for SegregationCells is EMPTY.
   */
  public SegregationCell(Map<String, Double> rules) {
    super(EMPTY, rules);
  }

  /**
   * Constructs this cell with the specified initial state.
   *
   * @param state - The state to use for this cell.
   */
  public SegregationCell(int state) {
    super(state);
  }

  @Override
  public void poke() {
    if (++cellState > 2) {
      cellState = 0;
    }
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
    Collections.shuffle(neighbors);
    for (Cell cell : neighbors) {
      if (cell.getCurrentCellState() == TYPE_A) {
        numTypeA++;
      } else if (cell.getCurrentCellState() == TYPE_B) {
        numTypeB++;
      }
    }
    switch (this.getCurrentCellState()) {
      case TYPE_A -> {
        if ((numTypeA / neighbors.size()) >= get("CutoffPercentage")) {
          this.nextCellState = TYPE_A; // remains the same
        } else {
          swapWithEmpty();
        }
      }
      case TYPE_B -> {
        if ((numTypeB / neighbors.size()) >= get("CutoffPercentage")) {
          this.nextCellState = TYPE_B; // remains the same
        } else {
          swapWithEmpty();
        }
      }
    }
  }

  private void swapWithEmpty() {
    boolean swapSuccess = false;
    for (Cell cell : neighbors) {
      if (cell.getCurrentCellState() == EMPTY && cell.getNextCellState() == EMPTY) {
        nextCellState = cell.getCurrentCellState();
        cell.setNextCellState(this.cellState);
        swapSuccess = true;
        break;
      }
    }
    if (!swapSuccess) {
      this.nextCellState = cellState;
    }
  }
}

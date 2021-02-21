package cellsociety.simulation;

import java.util.Map;

/**
 * This class handles the behavior of cells in the fire spreading simulation and thus the state
 * transitions therein.
 *
 * @author Joshua Petitma
 * @author Marc Chmielewski
 */
public class FireCell extends Cell {

  public static final int UNBURNT = 0;
  public static final int BURNING = 1;
  public static final int BURNT = 2;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for FireCells is NORMAL.
   */

  public FireCell(Map<String, Double> params) {
    super(UNBURNT, params);
  }

  /**
   * Constructs this cell with the specified default state.
   *
   * @param state - The state to use for this cell.
   */
  public FireCell(int state) {
    super(state);
  }

  @Override
  public void poke() {
    if (++cellState > BURNT) {
      cellState = UNBURNT;
      nextCellState = UNBURNT;
    }
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>If a neighboring Cell is BURNING, and this Cell is NORMAL then there is a chance that this
   * Cell will catch fire and transition to BURNING. This behavior is established in the FireRule,
   * which is loaded in from the parsed XML.
   */
  public void computeNextCellState() {
    if (cellState == UNBURNT) {
      for (Cell cell : neighbors) {
        if (cell.getCurrentCellState() == BURNING) {
          catchFire();
        }
      }
    } else if (cellState == BURNING) {
      nextCellState = BURNT;
    }
  }

  private void catchFire() {
    if (Math.random() > getParam("Flammability")) {
      nextCellState = BURNING;
    }
  }
}

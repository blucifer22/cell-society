package cellsociety.simulation;

import cellsociety.simulation.FireState.STATE;

/**
 * This class handles the behavior of cells in the fire spreading simulation and thus the state
 * transitions therein.
 *
 * @author Joshua Petitma
 * @author Marc Chmielewski
 */
public class FireCell extends Cell<FireState> {

  /**
   * The rule all firecells follow to change their states.
   *
   * <p>This value is set by the {@link cellsociety.simulation.Simulation}
   */
  public static FireRule rule;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for FireCells is NORMAL.
   */
  public FireCell() {
    super(new FireState());
  }

  /**
   * Constructs this cell with the specified default state.
   *
   * @param state - The state to use for this cell.
   */
  public FireCell(FireState state) {
    super(state);
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>If a neighboring Cell is BURNING, and this Cell is NORMAL then there is a chance that this
   * Cell will catch fire and transition to BURNING. This behavior is established in the FireRule,
   * which is loaded in from the parsed XML.
   */
  public void computeNextState() {
    if (state.getState() == STATE.NORMAL) {
      for (Cell cell : neighbors) {
        if (cell.getCurrentState().getState() == FireState.STATE.BURNING) {
          catchFire();
        }
      }
    } else if (state.getState() == STATE.BURNING) {
      nextState = new FireState(FireState.STATE.BURNT);
    }
  }

  private void catchFire() {
    if (Math.random() > rule.getFlammability()) {
      nextState = new FireState(FireState.STATE.BURNING);
    }
  }
}

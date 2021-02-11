package cellsociety.simulation;

import java.util.List;

public class FireCell extends Cell<FireState> {
  protected List<Cell> neighbors;

  /** The rule all firecells follow to change their states.
   *
   * This value is set by the {@link cellsociety.simulation.Simulation}
   * */
  public static FireRule rule;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for FireCells is NORMAL.
   */
  public FireCell() {
    super(new FireState(FireState.STATE.NORMAL));
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
   * Computes the next state for this cell.
   *
   * <p>Reference its current state and the rules for Fire simulations to se its next state.
   */
  public void computeNextState() {}
}

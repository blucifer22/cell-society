package cellsociety.simulation;


public class FireCell extends Cell<FireState> {

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

  public boolean onFire() {
    return state.getState() == FireState.STATE.BURNING;
  }

  public void computeNextState() {
    for (Cell cell : neighbors) {
      if (cell instanceof FireCell && ((FireCell) cell).onFire()) {
          catchFire();
        }
    }
  }

  private void catchFire() {
    if (Math.random() > rule.getFlammability()) {
      nextState = new FireState(FireState.STATE.BURNING);
    }
  }
}

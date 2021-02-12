package cellsociety.simulation;

public class FireState extends CellState<FireState.STATE> {
  private FireState.STATE state;

  enum STATE {
    NORMAL,
    BURNT,
    BURNING
  }

  /**
   * Constructs State Object with the specified state.
   *
   * @param state - The state which this object is created with.
   */
  public FireState(FireState.STATE state) {
    super(state);
  }

  /**
   * Constructs state object as normal.
   *
   * <p>This constructor creates a state object representing an empty state.
   */
  public FireState() {
    super(FireState.STATE.NORMAL);
  }
}

package cellsociety.simulation;

/**
 * This class represents all the states that a Segregation-type simulation can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class SegregationState extends CellState<SegregationState.STATE> {
  private SegregationState.STATE state;

  /**
   * Constructs a segregation state with the specified state.
   *
   * @param state - The state which this object is created with.
   */
  public SegregationState(SegregationState.STATE state) {
    super(state);
  }

  /**
   * Constructs state object as empty.
   *
   * <p>This constructor creates a state object representing an empty state.
   */
  public SegregationState() {
    super(SegregationState.STATE.EMPTY);
  }

  enum STATE {
    EMPTY,
    TYPE_A,
    TYPE_B
  }
}

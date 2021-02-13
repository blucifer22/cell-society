package cellsociety.simulation;

/**
 * This class represents all the states that a Segregation-type simulation can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class SegregationState extends CellState {
  public static final int EMPTY = 0;
  public static final int TYPE_A = 1;
  public static final int TYPE_B = 2;

  /**
   * Constructs a segregation state with the specified state.
   *
   * @param state - The state which this object is created with.
   */
  public SegregationState(int state) {
    super(state);
  }

  /**
   * Constructs state object as empty.
   *
   * <p>This constructor creates a state object representing an empty state.
   */
  public SegregationState() {
    super(EMPTY);
  }

  /**
   * Implements a toString for FireState
   */
  public String toString() {
    switch (getState()) {
      case EMPTY -> {
        return "EMPTY";
      }
      case TYPE_A -> {
        return "TYPE_A";
      }
      case TYPE_B -> {
        return "TYPE_B";
      }
    }
    return "INVALID STATE";
  }
}

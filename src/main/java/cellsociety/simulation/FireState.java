package cellsociety.simulation;

/**
 * A class that extends CellState for the Fire Spreading simulation
 *
 * @author Marc Chmielewski
 * @author Joshua Petitma
 */
public class FireState extends CellState {
  public static final int UNBURNT = 0;
  public static final int BURNING = 1;
  public static final int BURNT = 2;

  /**
   * Constructs State Object with the specified state.
   *
   * @param state - The state which this object is created with.
   */
  public FireState(int state) {
    super(state);
  }

  /**
   * Constructs state object as normal.
   *
   * <p>This constructor creates a state object representing an empty state.
   */
  public FireState() {
    super(UNBURNT);
  }

  /**
   * Implements a toString for FireState
   */
  public String toString() {
    switch (getState()) {
      case UNBURNT -> {
        return "UNBURNT";
      }
      case BURNING -> {
        return "BURNING";
      }
      case BURNT -> {
        return "BURNT";
      }
    }
    return "INVALID STATE";
  }
}

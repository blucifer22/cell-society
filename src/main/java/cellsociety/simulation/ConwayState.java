package cellsociety.simulation;

/**
 * This class represents all the states that a Cell in Conway's Game of Life can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class ConwayState extends CellState {
  public static final int DEAD = 0;
  public static final int ALIVE = 1;

  /**
   * Constructs a state object with the specified state.
   *
   * @param state - The state which this object is created with.
   */
  public ConwayState(int state) {
    super(state);
  }

  /**
   * Constructs state object with the default state of DEAD.
   *
   * <p>This constructor creates a state object representing an empty state.
   */
  public ConwayState() {
    super(DEAD);
  }

  /**
   * Implements a toString for FireState
   */
  public String toString() {
    switch (getState()) {
      case DEAD-> {
        return "DEAD";
      }
      case ALIVE -> {
        return "ALIVE";
      }
    }
    return "INVALID STATE";
  }
}

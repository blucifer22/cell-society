package cellsociety.simulation;

/**
 * This class represents all the states that a Cell in Conway's Game of Life can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class ConwayState extends CellState<ConwayState.STATE> {
  private ConwayState.STATE state;

  /**
   * Constructs a state object with the specified state.
   *
   * @param state - The state which this object is created with.
   */
  public ConwayState(ConwayState.STATE state) {
    super(state);
  }

  /**
   * Constructs state object with the default state of DEAD.
   *
   * <p>This constructor creates a state object representing an empty state.
   */
  public ConwayState() {
    super(ConwayState.STATE.DEAD);
  }

  enum STATE {
    DEAD,
    ALIVE
  }
}

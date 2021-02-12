package cellsociety.simulation;

/**
 * This class represents all the states that a Wa-Tor World simulation can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class WatorState extends CellState<WatorState.STATE> {
  private WatorState.STATE state;

  /**
   * Constructs a state with the specified state.
   *
   * @param state - The state which this object is created with.
   */
  public WatorState(WatorState.STATE state) {
    super(state);
  }

  /**
   * Constructs state object as water.
   *
   * <p>This constructor creates a state object representing an empty state.
   */
  public WatorState() {
    super(WatorState.STATE.WATER);
  }

  enum STATE {
    WATER,
    FISH,
    SHARK
  }
}

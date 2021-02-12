package cellsociety.simulation;

/**
 * This class represents all the states that a Wa-Tor World simulation can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class WatorState extends CellState<WatorState.STATE> {
  private WatorState.STATE state;

  public WatorState(WatorState.STATE state) {
    super(state);
  }

  enum STATE {
    WATER,
    FISH,
    SHARK
  }
}
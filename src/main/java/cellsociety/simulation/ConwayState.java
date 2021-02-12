package cellsociety.simulation;

/**
 * This class represents all the states that a Cell in Conway's Game of Life can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class ConwayState extends CellState<ConwayState.STATE> {
  private ConwayState.STATE state;

  public ConwayState(ConwayState.STATE state) {
    super(state);
  }

  public ConwayState() {
    super(ConwayState.STATE.DEAD);
  }

  enum STATE {
    DEAD,
    ALIVE
  }
}

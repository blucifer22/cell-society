package cellsociety.simulation;

/**
 * This class represents all the states that a Cell in a Percolation simulation can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class PercolationState extends CellState<PercolationState.STATE> {
  private PercolationState.STATE state;

  public PercolationState(PercolationState.STATE state) {
    super(state);
  }

  enum STATE {
    EMPTY,
    FULL
  }
}
package cellsociety.simulation;

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
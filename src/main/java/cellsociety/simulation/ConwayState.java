package cellsociety.simulation;

public class ConwayState extends CellState<ConwayState.STATE> {
  private ConwayState.STATE state;

  public ConwayState(ConwayState.STATE state) {
    super(state);
  }

  enum STATE {
    DEAD,
    ALIVE
  }
}

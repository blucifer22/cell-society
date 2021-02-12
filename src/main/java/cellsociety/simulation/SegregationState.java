package cellsociety.simulation;

/**
 * This class represents all the states that a Segregation-type simulation can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class SegregationState extends CellState<SegregationState.STATE> {
  private SegregationState.STATE state;

  public SegregationState(SegregationState.STATE state) {
    super(state);
  }

  enum STATE {
    EMPTY,
    TYPE_A,
    TYPE_B
  }
}
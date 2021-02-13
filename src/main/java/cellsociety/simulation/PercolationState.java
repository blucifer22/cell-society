package cellsociety.simulation;

/**
 * This class represents all the states that a Cell in a Percolation simulation can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class PercolationState extends CellState {
  public static final int EMPTY = 0;
  public static final int FULL = 1;

  public PercolationState(int state) {
    super(state);
  }

  public PercolationState() {
    super(EMPTY);
  }

  /**
   * Implements a toString for FireState
   */
  public String toString() {
    switch (getState()) {
      case EMPTY -> {
        return "EMPTY";
      }
      case FULL -> {
        return "FULL";
      }
    }
    return "INVALID STATE";
  }


}

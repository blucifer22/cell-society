package cellsociety.simulation;

import java.util.List;

public abstract class FireCell extends Cell<FireState> {
  protected List<Cell> neighbors;

  // All FireCells need to use the same rule ∴ it's static
  public static FireRule rule;

  public FireCell(FireState state) {
	  super(state);
  }

  public void computeNextState() {

  }
}

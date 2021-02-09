package cellsociety.simulation;

import java.util.List;

public abstract class Cell<T extends CellState> {
  protected List<Cell> neighbors;
  protected T state;
  protected T nextState;

  protected Cell(T state) {
	  this.state = state;
  }

  public T getCurrentState() {
	  return state;
  }

  public abstract void computeNextState();
  public void updateState() {
	  state = nextState;
  }
}

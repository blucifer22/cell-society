package cellsociety.simulation;

import java.util.List;

public abstract class Cell<T extends CellState> {
  protected List<Cell> neighbors;
  protected T state;

  protected Cell(T state) {
	  this.state = state;
  }
}

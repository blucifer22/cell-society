package cellsociety.simulation;

public abstract class CellState<T> {
	protected T state;

	public CellState(T state) {
		this.state = state;
	}

	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}
}

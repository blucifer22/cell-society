package cellsociety.simulation;

public abstract class CellState {

	private int state;

	public CellState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public abstract String toString();
}

package cellsociety.simulation;

public class FireState extends CellState<FireState.STATE> {
	private FireState.STATE state;

	enum STATE {
		NORMAL, BURNT, BURNING
	}

	public FireState(FireState.STATE state) {
		super(state);
	}
}

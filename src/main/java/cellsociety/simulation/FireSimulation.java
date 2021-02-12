package cellsociety.simulation;
import java.util.List;
import java.util.Map;

public class FireSimulation extends Simulation<FireCell> {
	public FireSimulation(
	    Map<String, String> metaData, Map<String, Double> config, List<int[]> nonDefaultStates) {
		super(metaData, config, nonDefaultStates);
	}

	@Override
	public void initialize() {
		for (int i = 0; i < numCells; i++) {
			cells.add(new FireCell());
			System.out.println("IT'S WORKING!");
		}
		cellGrid = new CellGrid(cells, config);
		FireRule rule = new FireRule(config);
	}
}

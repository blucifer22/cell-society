package cellsociety.simulation;
import java.util.List;
import java.util.Map;

public class FireSimulation extends Simulation {
	public FireSimulation(
	    Map<String, String> metaData, Map<String, Double> config, List<int[]> nonDefaultStates) {
		super(metaData, config, nonDefaultStates);
	}

	@Override
	public void initialize() {
		for (int i = 0; i < numCells; i++) {
			cells.add(new FireCell());
		}
		cellGrid = new CellGrid(cells, config);
		FireRule rule = new FireRule(config);
		FireCell.rule = rule;
		for (int[] arr : nonDefaultStates) {
			int state = FireCell.BURNING;
			Cell cell =  cellGrid.getCell(arr[1], arr[0]);
			cell.setCellState(state);
		}
	}
}

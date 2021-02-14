
package cellsociety.simulation;
import java.util.List;
import java.util.Map;

public class ConwaySimulation extends Simulation{
	public ConwaySimulation(
	    Map<String, String> metaData, Map<String, Double> config, List<int[]> nonDefaultStates) {
		super(metaData, config, nonDefaultStates);
	}

	@Override
	public void initialize() {
		for (int i = 0; i < numCells; i++) {
			cells.add(new ConwayCell());
		}
		cellGrid = new CellGrid(cells, config);
		ConwayRule rule = new ConwayRule(config);
		ConwayCell.rule = rule;
		for (int[] arr : nonDefaultStates) {
			int state = ConwayCell.ALIVE;
			Cell cell =  cellGrid.getCell(arr[1], arr[0]);
			if(cell != null) cell.setCellState(state);
		}
	}
}

package cellsociety.simulation;
import java.util.List;
import java.util.Map;

public class ConwaySimulation extends Simulation<FireCell> {
	public FireSimulation(
	    Map<String, String> metaData, Map<String, Double> config, List<int[]> nonDefaultStates) {
		super(metaData, config, nonDefaultStates);
	}

	@Override
	public void initialize() {

	}
}

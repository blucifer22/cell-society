package cellsociety.simulation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import cellsociety.util.XMLParser;
import java.io.File;
import java.util.List;
import cellsociety.simulation.SimulationManager;
import cellsociety.simulation.Simulation;

class TestSimulation {

  @Test
  void testCreation() {
    File file = new File("data/fireSimulation.xml");
	SimulationManager sm = new SimulationManager();
	Simulation sim = sm.createSimulation(file);
	List<Cell> cells = sim.getCells();
	boolean oneBurning = false;
	for (Cell cell : cells) {
		if (cell.getCurrentState().toString().equals("BURNING")) {
			oneBurning = true;
		}
	}
	assertTrue(oneBurning);
  }
}

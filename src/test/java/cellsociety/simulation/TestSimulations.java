package cellsociety.simulation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;

class TestSimulation {

  @Test
  void testCreation() {
		try {
			File file = new File("data/fireSimulation.xml");
			SimulationFactory sm = new SimulationFactory();
			Simulation sim = sm.createSimulation(file);
			List<Cell> cells = sim.getCells();
			boolean oneBurning = false;
			for (Cell cell : cells) {
				if (cell.getCurrentState().toString().equals("BURNING")) {
					oneBurning = true;
				}
			}

			sim.step();
			sim.step();
			sim.step();

			for (Cell cell : cells) {
				System.out.println(cell.getCurrentState());
			}

			assertTrue(oneBurning);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

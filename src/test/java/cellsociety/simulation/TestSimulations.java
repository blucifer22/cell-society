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
			sm.loadSimulationFile(file);
      List<Cell> cells = sm.getSimulation().getCells();
			boolean oneBurning = false;
			for (Cell cell : cells) {
				if (cell.getCurrentCellState().toString().equals("BURNING")) {
					oneBurning = true;
				}
			}

			sm.getSimulation().step();
			sm.getSimulation().step();
			sm.getSimulation().step();

			for (Cell cell : cells) {
				System.out.println(cell.getCurrentCellState());
			}

			assertTrue(oneBurning);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

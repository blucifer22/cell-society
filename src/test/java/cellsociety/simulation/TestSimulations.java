package cellsociety.simulation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;

class TestSimulation {

  @Test
  void testFire() {
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
				System.out.println(cell.getEncoding());
			}

			assertTrue(oneBurning);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	void testConway() {
		try {
			File file = new File("data/conwaySimulation.xml");
			SimulationFactory sm = new SimulationFactory();
			sm.loadSimulationFile(file);
			List<Cell> cells = sm.getSimulation().getCells();
			int counter = 0;
			for (Cell cell : cells) {
				if (cell.getCurrentCellState().toString().equals("ALIVE")) {
					counter++;
				}
			}

			assert(counter == 4);

			sm.getSimulation().step();
			sm.getSimulation().step();
			sm.getSimulation().step();

			for (Cell cell : cells) {
				System.out.println(cell.getCurrentCellState());
				System.out.println(cell.getEncoding());
			}

			assert(counter == 4);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	void testConwayStabilization() {
		try {
			File file = new File("data/conways_test_1.xml");
			SimulationFactory sm = new SimulationFactory();
			sm.loadSimulationFile(file);
			List<Cell> cells = sm.getSimulation().getCells();


			for(int i = 0; i < 25; i++) {
				System.out.print(cells.get(i).getCurrentCellState().getState());
			}
			System.out.println();
			sm.getSimulation().step();
			for(int i = 0; i < 25; i++) {
				System.out.print(cells.get(i).getCurrentCellState().getState());
			}
			System.out.println();
			sm.getSimulation().step();
			for(int i = 0; i < 25; i++) {
				System.out.print(cells.get(i).getCurrentCellState().getState());
			}
			System.out.println();
			sm.getSimulation().step();
			for(int i = 0; i < 25; i++) {
				System.out.print(cells.get(i).getCurrentCellState().getState());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

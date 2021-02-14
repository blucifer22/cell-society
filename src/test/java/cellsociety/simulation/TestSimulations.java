package cellsociety.simulation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

class TestSimulation {

  @Test
  void testFire() {
    try {
      File file = new File("data/fire/fire_test_1.xml");
      SimulationFactory sm = new SimulationFactory();
      sm.loadSimulationFile(file);
      List<Cell> cells = sm.getSimulation().getCells();
      boolean oneBurning = false;
      for (Cell cell : cells) {
        if (cell.getCurrentCellState() == FireCell.BURNING) {
          oneBurning = true;
        }
      }

      sm.getSimulation().step();
      sm.getSimulation().step();
      sm.getSimulation().step();

      assertTrue(oneBurning);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  @Test
  void testConway() {
    try {
      File file = new File("data/conways/conways_test_2.xml");
      SimulationFactory sm = new SimulationFactory();
      sm.loadSimulationFile(file);
      List<Cell> cells = sm.getSimulation().getCells();
      int counter = 0;
      for (Cell cell : cells) {
        if (cell.getCurrentCellState() == ConwayCell.ALIVE) {
          counter++;
        }
      }

	  assertEquals(counter, 4);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  @Test
  void testConwayStabilization() {
    try {

      File file = new File("data/conways/conways_test_1.xml");
      SimulationFactory sm = new SimulationFactory();
      sm.loadSimulationFile(file);
      List<Cell> cells = sm.getSimulation().getCells();
	  Simulation sim = sm.getSimulation();
      for (int i = 0; i < 25; i++) {
        System.out.print(cells.get(i).getCurrentCellState());
      }
      sim.step();
      for (int i = 0; i < 25; i++) {
        System.out.print(cells.get(i).getCurrentCellState());
      }
      sim.step();
      for (int i = 0; i < 25; i++) {
        System.out.print(cells.get(i).getCurrentCellState());
      }
      sim.step();
      for (int i = 0; i < 25; i++) {
        System.out.print(cells.get(i).getCurrentCellState());
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}

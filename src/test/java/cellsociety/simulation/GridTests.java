// The package for the corresponding class you're testing
package cellsociety.simulation;

import static cellsociety.simulation.FireCell.BURNING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Name doesn't really matter
class TestGrids {

  @DisplayName("If the neighborhoods are large then there should be 8 rectangle neighbors")
  @Test
  void testFullNeighborhoods() {
    File file = new File("data/fire/absolute_fire_test.xml");
    SimulationFactory fac = new SimulationFactory();
    try {
      fac.loadSimulationFile(file);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false); // Lol why not just throw a failure? Cuz that makes no sense.
    }
    Simulation sim = fac.getSimulation();
    sim.step();

    List<Cell> cells = sim.getCells();
    long onFire = cells.stream().filter(c -> c.getCellState() == BURNING).count();
    assertEquals(8, onFire);
  }

  @DisplayName("If the neighborhoods are medium then there should be 4 rectangle neighbors")
  @Test
  void testMediumNeighborhoods() {
    File file = new File("data/fire/absolute_medium_fire_test.xml");
    SimulationFactory fac = new SimulationFactory();
    try {
      fac.loadSimulationFile(file);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false); // Lol why not just throw a failure? Cuz that makes no sense.
    }
    Simulation sim = fac.getSimulation();
    sim.step();

    List<Cell> cells = sim.getCells();
    long onFire = cells.stream().filter(c -> c.getCellState() == BURNING).count();
    assertEquals(4, onFire);
  }
}

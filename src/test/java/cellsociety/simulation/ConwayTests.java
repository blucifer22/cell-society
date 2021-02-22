package cellsociety.simulation;

import static cellsociety.simulation.ConwayCell.ALIVE;
import static cellsociety.simulation.ConwayCell.DEAD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * A suite of tests for Conway's Game of Life.
 *
 * @author Marc Chmielewski
 */
public class ConwayTests {

  /**
   * A test for Conway's that makes sure that Cells aren't being erroneously created or destroyed.
   */
  @Test
  public void testPulsar() {
    Simulation pulsar = createSimulation("data/conways/conways_test_5.xml");

    List<Integer> expected = new ArrayList<>();
    expected.add(DEAD, 0);
    expected.add(ALIVE, 1);

    expected.set(DEAD, 577);
    expected.set(ALIVE, 48);

    List<Integer> cellStates48 = getCellStates(pulsar.getCells());
    assertEquals(cellStates48, expected);

    pulsar.step();
    expected.set(DEAD, 569);
    expected.set(ALIVE, 56);

    List<Integer> cellStates56 = getCellStates(pulsar.getCells());
    assertEquals(cellStates56, expected);

    pulsar.step();
    expected.set(DEAD, 553);
    expected.set(ALIVE, 72);

    List<Integer> cellStates72 = getCellStates(pulsar.getCells());
    assertEquals(cellStates72, expected);
  }

  /**
   * Another test for Conway's that makes sure that Cells aren't being erroneously created or
   * destroyed.
   */
  @Test
  public void testGlider() {
    Simulation glider = createSimulation("data/conways/conways_edge_test_1.xml");

    List<Integer> expected = new ArrayList<>();
    expected.add(DEAD, 0);
    expected.add(ALIVE, 1);

    expected.set(DEAD, 620);
    expected.set(ALIVE, 5);

    List<Integer> cellStates5 = getCellStates(glider.getCells());
    assertEquals(cellStates5, expected);

    for(int i = 0; i < 100; i++) {
      glider.step();
      assertEquals(cellStates5, expected);
    }
  }

  private Simulation createSimulation(String filepath) {
    File file = new File(filepath);
    SimulationFactory simulationFactory = new SimulationFactory();

    try {
      simulationFactory.loadSimulationFile(file);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return simulationFactory.getSimulation();
  }

  private List<Integer> getCellStates(List<Cell> cells) {
    ArrayList<Integer> cellStates = new ArrayList<>();
    cellStates.add(DEAD, 0);
    cellStates.add(ALIVE, 0);

    for (Cell curCell : cells) {
      if (curCell.getCellState() == DEAD) {
        cellStates.set(DEAD, (cellStates.get(DEAD) + 1));
      } else if (curCell.getCellState() == ALIVE) {
        cellStates.set(ALIVE, (cellStates.get(ALIVE) + 1));
      }
    }
    return cellStates;
  }
}

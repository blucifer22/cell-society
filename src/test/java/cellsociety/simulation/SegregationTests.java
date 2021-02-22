package cellsociety.simulation;

import static cellsociety.simulation.SegregationCell.EMPTY;
import static cellsociety.simulation.SegregationCell.TYPE_A;
import static cellsociety.simulation.SegregationCell.TYPE_B;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * A suite of tests for the Segregation Simulation.
 *
 * @author Marc Chmielewski
 */
public class SegregationTests {
  @Test
  public void testSimpleSegregation() {
    Simulation simple = createSimulation("data/segregation/segregation_test_1.xml");

    List<Integer> prevRound = new ArrayList<>();
    prevRound.add(EMPTY, 0);
    prevRound.add(TYPE_A, 0);
    prevRound.add(TYPE_B, 0);

    List<Integer> curRound = new ArrayList<>();
    curRound.add(EMPTY, 0);
    curRound.add(TYPE_A, 0);
    curRound.add(TYPE_B, 0);

    prevRound = getCellStates(simple.getCells());

    simple.step();

    curRound = getCellStates(simple.getCells());

    for(int i = 0; i < 500; i++) {
      assertEquals(curRound.get(EMPTY), prevRound.get(EMPTY));
      assertEquals(curRound.get(TYPE_A), prevRound.get(TYPE_A));
      assertEquals(prevRound.get(TYPE_B), curRound.get(TYPE_B));
      prevRound = curRound;
      simple.step();
      curRound = getCellStates(simple.getCells());
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
    cellStates.add(EMPTY, 0);
    cellStates.add(TYPE_A, 0);
    cellStates.add(TYPE_B, 0);

    for (Cell curCell : cells) {
      if (curCell.getCellState() == EMPTY) {
        cellStates.set(EMPTY, (cellStates.get(EMPTY) + 1));
      } else if (curCell.getCellState() == TYPE_A) {
        cellStates.set(TYPE_A, (cellStates.get(TYPE_A) + 1));
      } else if (curCell.getCellState() == TYPE_B) {
        cellStates.set(TYPE_B, (cellStates.get(TYPE_B) + 1));
      }
    }
    return cellStates;
  }
}

package cellsociety.simulation;


import static cellsociety.simulation.FireCell.BURNING;
import static cellsociety.simulation.FireCell.BURNT;
import static cellsociety.simulation.FireCell.UNBURNT;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * A suite of tests for the Fire Simulation.
 *
 * @author Marc Chmielewski
 */
public class FireTests {

  @Test
  public void testSimpleFire() {
    Simulation simple = createSimulation("data/fire/fire_test_1.xml");

    List<Integer> prevRound = new ArrayList<>();
    prevRound.add(UNBURNT, 0);
    prevRound.add(BURNING, 0);
    prevRound.add(BURNT, 0);

    List<Integer> curRound = new ArrayList<>();
    curRound.add(UNBURNT, 0);
    curRound.add(BURNING, 0);
    curRound.add(BURNT, 0);

    prevRound = getCellStates(simple.getCells());

    simple.step();

    curRound = getCellStates(simple.getCells());

    for (int i = 0; i < 500; i++) {
      assertTrue(curRound.get(UNBURNT) <= prevRound.get(UNBURNT));
      assertTrue(curRound.get(BURNT) >= prevRound.get(BURNT));
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
    cellStates.add(UNBURNT, 0);
    cellStates.add(BURNING, 0);
    cellStates.add(BURNT, 0);

    for (Cell curCell : cells) {
      if (curCell.getCellState() == UNBURNT) {
        cellStates.set(UNBURNT, (cellStates.get(UNBURNT) + 1));
      } else if (curCell.getCellState() == BURNING) {
        cellStates.set(BURNING, (cellStates.get(BURNING) + 1));
      } else if (curCell.getCellState() == BURNT) {
        cellStates.set(BURNT, (cellStates.get(BURNT) + 1));
      }
    }
    return cellStates;
  }
}

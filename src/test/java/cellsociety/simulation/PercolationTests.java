package cellsociety.simulation;

import static cellsociety.simulation.FireCell.BURNING;
import static cellsociety.simulation.FireCell.BURNT;
import static cellsociety.simulation.FireCell.UNBURNT;
import static cellsociety.simulation.PercolationCell.BLOCKED;
import static cellsociety.simulation.PercolationCell.EMPTY;
import static cellsociety.simulation.PercolationCell.FULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PercolationTests {
  @Test
  public void testSimplePercolation() {
    Simulation simple = createSimulation("data/percolation/percolation_test_1.xml");

    List<Integer> prevRound = new ArrayList<>();
    prevRound.add(EMPTY, 0);
    prevRound.add(FULL, 0);
    prevRound.add(BLOCKED, 0);

    List<Integer> curRound = new ArrayList<>();
    curRound.add(EMPTY, 0);
    curRound.add(FULL, 0);
    curRound.add(BLOCKED, 0);

    prevRound = getCellStates(simple.getCells());

    simple.step();

    curRound = getCellStates(simple.getCells());

    for(int i = 0; i < 500; i++) {
      assertTrue(curRound.get(EMPTY) <= prevRound.get(EMPTY));
      assertTrue(curRound.get(FULL) >= prevRound.get(FULL));
      assertEquals(prevRound.get(BLOCKED), curRound.get(BLOCKED));
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
    cellStates.add(FULL, 0);
    cellStates.add(BLOCKED, 0);

    for (Cell curCell : cells) {
      if (curCell.getCurrentCellState() == EMPTY) {
        cellStates.set(EMPTY, (cellStates.get(EMPTY) + 1));
      } else if (curCell.getCurrentCellState() == BURNING) {
        cellStates.set(FULL, (cellStates.get(FULL) + 1));
      } else if (curCell.getCurrentCellState() == BLOCKED) {
        cellStates.set(BLOCKED, (cellStates.get(BLOCKED) + 1));
      }
    }
    return cellStates;
  }

}

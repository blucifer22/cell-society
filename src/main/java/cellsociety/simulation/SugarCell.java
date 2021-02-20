package cellsociety.simulation;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * This class handles the behavior of Cells in the SugarScape simulation, and thus the state
 * transitions therein.
 *
 * @author Marc Chmielewski
 */
public class SugarCell extends Cell {
  public static final int PATCH = 0;
  public static final int AGENT = 1;

  private static final String MAX_SUGAR_CAPACITY = "MaxSugarCapacity";
  private static final String SUGAR_REGROWTH_RATE = "SugarRegrowthRate";
  private static final String SUGAR_METABOLISM_RATE = "SugarMetabolismRate";
  private static final String AGENT_SUGAR = "AgentSugar";

  private double agentSugar;
  private double patchSugar;
  private final double sugarRegrowthRate;
  private final double sugarMetabolismRate;
  private Random rng;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for SugarCells is EMPTY
   */
  public SugarCell(Map<String, Double> params) {
    super(PATCH, params);
    patchSugar = getParam(MAX_SUGAR_CAPACITY);
    sugarRegrowthRate = getParam(SUGAR_REGROWTH_RATE);
    sugarMetabolismRate = getParam(SUGAR_METABOLISM_RATE);
    rng = new Random();
  }

  @Override
  protected void setCellState(int state) {
    super.setCellState(state);
    if (state == PATCH) {
      patchSugar = getParam(MAX_SUGAR_CAPACITY);
    } else if (state == AGENT) {
      agentSugar = rng.nextInt((int) getParam(MAX_SUGAR_CAPACITY)); // Start the agents with random sugar
    }
  }

  @Override
  protected void setNextCellState(int state, Map<String, Double> values) {
    super.setNextCellState(state);
    if(state == AGENT) {
      agentSugar = values.getOrDefault(AGENT_SUGAR, getParam(MAX_SUGAR_CAPACITY));
    }
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>SugarScape Rules are as follows:
   *
   */
  public void computeNextCellState() {
    Set<SugarCell> availableNeighbors = findAvailableNeighbors();

    switch(cellState) {
      case PATCH -> regrowSugar();
      case AGENT -> {
        SugarCell cellToMoveTo = findSugar(availableNeighbors);
        if(cellToMoveTo != null) {
          move(cellToMoveTo);
        }
        consumeSugar();
      }
    }
  }

  private void move(SugarCell cellToMoveTo) {
    // TODO: Implement this
  }

  private SugarCell findSugar(Set<SugarCell> availableNeighbors) {
    double maxSugar = 0;
    SugarCell cellToMoveTo = null;
    for(SugarCell cell : availableNeighbors) {
      if(cell.patchSugar > maxSugar) {
        cellToMoveTo = cell;
        maxSugar = cell.patchSugar;
      }
    }
    return cellToMoveTo;
  }

  private void regrowSugar() {
    this.patchSugar = Math.min(this.patchSugar + sugarRegrowthRate, getParam(MAX_SUGAR_CAPACITY));
  }

  private void consumeSugar() {
    this.agentSugar = Math.max(0, agentSugar - sugarMetabolismRate);
    if(this.agentSugar <= 0) {
      this.nextCellState = PATCH;
    }
  }

  private Set<SugarCell> findAvailableNeighbors() {
    Set<SugarCell> availableNeighbors = new HashSet<>();
    for(Cell cell : neighbors) {
      if((cell.getCurrentCellState() != AGENT && cell.getNextCellState() != AGENT)) {
        availableNeighbors.add((SugarCell)cell);
      }
    }
    return availableNeighbors;
  }
}

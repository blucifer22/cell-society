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
  public void poke() {
    super.setCellState(getCellState() + 1);
    if(getCellState() > AGENT) {
      super.setCellState(PATCH);
    }
    if(getCellState() == AGENT) {
      agentSugar = generateAgentSpawnSugar();
    }
    else if(getCellState() == PATCH) {
      agentSugar = 0; // PATCHes don't have agentSugar
      patchSugar = getParam(MAX_SUGAR_CAPACITY); // Re-up the patch sugar to max capacity
    }
  }

  @Override
  protected void setCellState(int state) {
    super.setCellState(state);
    if (state == PATCH) {
      patchSugar = getParam(MAX_SUGAR_CAPACITY);
    } else if (state == AGENT) {
      agentSugar = generateAgentSpawnSugar();
    }
  }

  /**
   * Start the agents with random sugar, with at least enough to avoid starving round 1
   */
  private double generateAgentSpawnSugar() {
    return rng.nextInt((int) getParam(MAX_SUGAR_CAPACITY)) + sugarMetabolismRate + 1;
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
   * PATCHes spawn with maximum sugar capacity, and regrow sugar at a rate governed by
   * sugarRegrowthRate.
   *
   * AGENTs spawn with a random amount of sugar bounded from 0 to the maximum sugar capacity of a
   * PATCH.
   * AGENTs consume sugar at a rate of sugarMetabolismRate, and if their sugar amount ever
   * becomes negative they die.
   * AGENTs greedily search for neighboring PATCHes to take sugar from, and will select
   * those with the most sugar. If there is a tie, they will take the first available one.
   *
   */
  public void computeNextCellState() {
    Set<SugarCell> availableNeighbors = findAvailableNeighbors();

    switch(getCellState()) {
      case PATCH -> regrowSugar();
      case AGENT -> {
        metabolizeSugar();
        SugarCell cellToMoveTo = findSugar(availableNeighbors);
        if(cellToMoveTo != null && this.getCellState() != PATCH) {
          move(cellToMoveTo);
        }
        else if(this.getCellState() != PATCH){ // As long as we're not dead...
          this.setNextCellState(AGENT); // Don't move
        }
      }
    }
  }

  private void move(SugarCell cellToMoveTo) {
    this.agentSugar = this.agentSugar + this.patchSugar;
    this.patchSugar = 0;
    cellToMoveTo.agentSugar = this.agentSugar;
    this.agentSugar = 0.0;
    cellToMoveTo.setNextCellState(AGENT);
    this.setNextCellState(PATCH);
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

  private void metabolizeSugar() {
    this.agentSugar = Math.max(0, agentSugar - sugarMetabolismRate);
    if(this.agentSugar <= 0) { // If the AGENT is going to die
      this.setNextCellState(PATCH);
      super.setCellState(PATCH);
    }
  }

  private Set<SugarCell> findAvailableNeighbors() {
    Set<SugarCell> availableNeighbors = new HashSet<>();
    for(Cell cell : getNeighbors()) {
      if((cell.getCellState() != AGENT && cell.getNextCellState() != AGENT)) {
        availableNeighbors.add((SugarCell)cell);
      }
    }
    return availableNeighbors;
  }
}

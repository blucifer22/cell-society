package cellsociety.simulation;

import java.util.Map;

/**
 * This class handles the behavior of Cells in the Foraging Ants simulation, and thus the state
 * transitions therein.
 *
 * @author Marc Chmielewski
 */
public class AntCell extends Cell{
  public static final int EMPTY = 0;
  public static final int ANT = 1;
  public static final int FOOD = 2;
  public static final int HOME = 3;
  public static final int OBSTACLE = 4;

  private static final String TARGET_PHEROMONE_CONCENTRATION = "TargetPheromoneConcentration";
  private static final String PHEROMONE_EVAPORATION_RATE = "PheromoneEvaporationRate";

  private double homePheromoneLevel;
  private double foodPheromoneLevel;
  private double pheromoneEvaporationRate;
  private double targetPheromoneConcentration;
  private boolean hasFood;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for AntCells is EMPTY
   */
  public AntCell(Map<String, Double> rules) {
    super(EMPTY, rules);
    homePheromoneLevel = 0;
    foodPheromoneLevel = 0;
    pheromoneEvaporationRate = get(PHEROMONE_EVAPORATION_RATE);
    targetPheromoneConcentration = get(TARGET_PHEROMONE_CONCENTRATION);
  }

  @Override
  protected void setCellState(int state) {
    switch(state) {
      case EMPTY -> {
        homePheromoneLevel = 0;
        foodPheromoneLevel = 0;
        pheromoneEvaporationRate = get(PHEROMONE_EVAPORATION_RATE);
        targetPheromoneConcentration = 0;
        hasFood = false;
      }
      case ANT -> {
        homePheromoneLevel = 0;
        foodPheromoneLevel = 0;
        pheromoneEvaporationRate = 0;
        targetPheromoneConcentration = get(TARGET_PHEROMONE_CONCENTRATION);
        hasFood = false;
      }
      default -> {
        homePheromoneLevel = 0;
        foodPheromoneLevel = 0;
        pheromoneEvaporationRate = 0;
        targetPheromoneConcentration = 0;
        hasFood = false;
      }
    }
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>Foraging Ants Rules are as follows:
   *
   */
  public void computeNextCellState() {
    switch(cellState) {
      case EMPTY -> {
        updatePheromoneLevels(this);
      }
      case ANT -> {
        // TODO: Implement ANT cell behavior
      }
    }
  }

  private void updatePheromoneLevels(AntCell cell) {
    if(cell.homePheromoneLevel > 0) {
      cell.homePheromoneLevel = Math.max(0, cell.homePheromoneLevel- cell.pheromoneEvaporationRate);
    }
    if(cell.foodPheromoneLevel > 0) {
      cell.foodPheromoneLevel = Math.max(0, cell.foodPheromoneLevel- cell.pheromoneEvaporationRate);
    }
  }

}

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

  private static final String HOME_PHEROMONE_CONCENTRATION = "HomePheromoneConcentration";
  private static final String FOOD_PHEROMONE_CONCENTRATION = "FoodPheromoneConcentration";
  private static final String TARGET_PHEROMONE_CONCENTRATION = "TargetPheromoneConcentration";
  private static final String PHEROMONE_EVAPORATION_RATE = "PheromoneEvaporationRate";
  private static final String HAS_FOOD = "HasFood";

  private double homePheromoneConcentration;
  private double foodPheromoneConcentration;
  private double pheromoneEvaporationRate;
  private double targetPheromoneConcentration;
  private double hasFood;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for AntCells is EMPTY
   */
  public AntCell(Map<String, Double> rules) {
    super(EMPTY, rules);
    homePheromoneConcentration = 0;
    foodPheromoneConcentration = 0;
    pheromoneEvaporationRate = get(PHEROMONE_EVAPORATION_RATE);
    targetPheromoneConcentration = get(TARGET_PHEROMONE_CONCENTRATION);
  }

  @Override
  protected void setCellState(int state) {
    switch(state) {
      case EMPTY -> {
        homePheromoneConcentration = 0;
        foodPheromoneConcentration = 0;
        pheromoneEvaporationRate = get(PHEROMONE_EVAPORATION_RATE);
        targetPheromoneConcentration = 0;
        hasFood = 0;
      }
      case ANT -> {
        homePheromoneConcentration = 0;
        foodPheromoneConcentration = 0;
        pheromoneEvaporationRate = 0;
        targetPheromoneConcentration = get(TARGET_PHEROMONE_CONCENTRATION);
        hasFood = 0;
      }
      default -> {
        homePheromoneConcentration = 0;
        foodPheromoneConcentration = 0;
        pheromoneEvaporationRate = 0;
        targetPheromoneConcentration = 0;
        hasFood = 0;
      }
    }
  }

  @Override
  protected void setNextCellState(int state, Map<String, Double> values) {
    homePheromoneConcentration = values.getOrDefault(HOME_PHEROMONE_CONCENTRATION, 0.0);
    foodPheromoneConcentration = values.getOrDefault(FOOD_PHEROMONE_CONCENTRATION, 0.0);
    hasFood = values.getOrDefault(HAS_FOOD, 0.0);
    nextCellState = state;
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
    if(cell.homePheromoneConcentration > 0) {
      cell.homePheromoneConcentration = Math.max(0, cell.homePheromoneConcentration - cell.pheromoneEvaporationRate);
    }
    if(cell.foodPheromoneConcentration > 0) {
      cell.foodPheromoneConcentration = Math.max(0, cell.foodPheromoneConcentration - cell.pheromoneEvaporationRate);
    }
  }

}

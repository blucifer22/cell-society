package cellsociety.simulation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    hasFood = 0;
  }

  @Override
  protected void setCellState(int state) {
    hasFood = 0;
    cellState = state;
  }

  @Override
  protected void setNextCellState(int state, Map<String, Double> values) {
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
    Set<Cell> availableNeighbors = findAvailableNeighbors();

    switch(cellState) {
      case EMPTY -> {
        evaporatePheromones();
      }
      case ANT -> {
        if(this.hasFood == 0) { // Ant does not have food
          antDoesNotHaveFood(availableNeighbors);
        }
        else if(this.hasFood == 1) { // Ant has food
          antHasFood(availableNeighbors);
        }
      }
    }
  }

  private void antHasFood(Set<Cell> availableNeighbors) {
    AntCell move = checkHomeMove();
    if(move != null) {
      if(move.cellState == HOME) {
        this.hasFood = 0.0;
      }
      else {
        moveTowardsHome(move);
      }
    }
  }

  private void antDoesNotHaveFood(Set<Cell> availableNeighbors) {
    AntCell move = checkFoodMove();
    if(move != null) {
      if(move.cellState == FOOD) {
        this.hasFood = 1.0;
      }
      else {
        moveTowardsFood(move);
      }
    }
  }

  private Set<Cell> findAvailableNeighbors() {
    Set<Cell> availableNeighbors = new HashSet<>();
    for(Cell cell : neighbors) {
      if(cell.getCurrentCellState() != OBSTACLE && cell.getNextCellState() != OBSTACLE) {
        availableNeighbors.add(cell);
      }
    }
  }

  private void evaporatePheromones() {
    if(this.homePheromoneConcentration > 0) {
      this.homePheromoneConcentration =
          Math.max(0, this.homePheromoneConcentration - this.pheromoneEvaporationRate);
    }
    if(this.foodPheromoneConcentration > 0) {
      this.foodPheromoneConcentration =
          Math.max(0, this.foodPheromoneConcentration - this.pheromoneEvaporationRate);
    }
  }

}

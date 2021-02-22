package cellsociety.simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

  private static final String TARGET_PHEROMONE_CONCENTRATION = "TargetPheromoneConcentration";
  private static final String PHEROMONE_EVAPORATION_RATE = "PheromoneEvaporationRate";
  private static final String HAS_FOOD = "HasFood";

  private final Random rng;
  private double homePheromoneConcentration;
  private double foodPheromoneConcentration;
  private final double pheromoneEvaporationRate;
  private final double targetPheromoneConcentration;
  private double hasFood;
  private List<Cell> previouslyVisitedCells;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for AntCells is EMPTY
   */
  public AntCell(Map<String, Double> params) {
    super(EMPTY, params);
    homePheromoneConcentration = 0;
    foodPheromoneConcentration = 0;
    pheromoneEvaporationRate = getParam(PHEROMONE_EVAPORATION_RATE);
    targetPheromoneConcentration = getParam(TARGET_PHEROMONE_CONCENTRATION);
    hasFood = 0;
    rng = new Random();
    previouslyVisitedCells = new ArrayList<>();
  }

  @Override
  protected void setCellState(int state) {
    super.setCellState(state);
    hasFood = 0;
  }

  @Override
  protected void setNextCellState(int state, Map<String, Double> values) {
    super.setNextCellState(state);
    hasFood = values.getOrDefault(HAS_FOOD, 0.0);
  }

  @Override
  public void poke() {
    if(getCellState() < OBSTACLE)
      super.setCellState(getCellState() + 1);
    else
      super.setCellState(0);
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>Foraging Ants Rules are as follows:
   *
   */
  public void computeNextCellState() {
    Set<AntCell> availableNeighbors = findAvailableNeighbors();

    switch(getCellState()) {
      case EMPTY -> evaporatePheromones();
      case ANT -> {
        if(this.hasFood == 0) { // Ant does not have food
          antDoesNotHaveFood(availableNeighbors);
        }
        else if(this.hasFood == 1) { // Ant has food
          antHasFood(availableNeighbors);
        }
      }
      case FOOD -> setNextCellState(FOOD);
      case HOME -> setNextCellState(HOME);
      case OBSTACLE -> setNextCellState(OBSTACLE);
    }
  }

  private void antHasFood(Set<AntCell> availableNeighbors) {
    AntCell cellToMoveTo = checkHomeMove(availableNeighbors);
    if(cellToMoveTo != null) {
      if(cellToMoveTo.getCellState() == HOME) {
        this.hasFood = 0.0;
        this.setNextCellState(ANT);
        this.previouslyVisitedCells.clear();
      }
      else if(cellToMoveTo.getCellState() == FOOD || cellToMoveTo.getCellState() == ANT) {
        this.setNextCellState(ANT);
      }
      else {
        move(cellToMoveTo);
      }
    }
  }

  private void move(AntCell newCell) {
    if(this.hasFood == 1.0) {
      this.homePheromoneConcentration = targetPheromoneConcentration;
    }
    else if(this.hasFood == 0.0) {
      this.foodPheromoneConcentration = targetPheromoneConcentration;
    }
    this.previouslyVisitedCells.add(this);
    newCell.setNextCellState(ANT);
    newCell.hasFood = this.hasFood;
    newCell.previouslyVisitedCells = new ArrayList<>(this.previouslyVisitedCells);
    this.previouslyVisitedCells.clear();
    this.setNextCellState(EMPTY);
    this.hasFood = 0.0;
  }

  private AntCell checkHomeMove(Set<AntCell> availableNeighbors) {
    double maxPheromoneConcentration = 0.0;
    AntCell moveCell = null;

    for(AntCell cell : availableNeighbors) {
      if(cell.getCellState() == HOME) { // If HOME is in range?
        return cell;
      }
      else if(cell.homePheromoneConcentration > maxPheromoneConcentration
          && !this.previouslyVisitedCells.contains(cell)) { // Most pheromones? (but not just visited)
        moveCell = cell;
        maxPheromoneConcentration = cell.homePheromoneConcentration;
      }
    }

    if(moveCell != null) {
      return moveCell;
    }

    return getRandomCell(availableNeighbors);
  }

  private AntCell getRandomCell(Set<AntCell> availableNeighbors) {
    AntCell moveCell;// If none of the above, settle for random...
    if (availableNeighbors.size() > 0) {
      int index = rng.nextInt(availableNeighbors.size());
      moveCell = getRandomNeighbor(availableNeighbors, index);
      return moveCell;
    }
    return null;
  }

  private AntCell getRandomNeighbor(Set<AntCell> neighbors, int index) {
    int i = 0;
    for(AntCell cell : neighbors) {
      if(i == index) {
        return cell;
      }
      i++;
    }
    return null;
  }

  private void antDoesNotHaveFood(Set<AntCell> availableNeighbors) {
    AntCell move = checkFoodMove(availableNeighbors);
    if(move != null) {
      if(move.getCellState() == FOOD) {
        this.hasFood = 1.0;
        this.setNextCellState(ANT);
        this.previouslyVisitedCells.clear();
      }
      else if(move.getCellState() == HOME || move.getCellState() == ANT) {
        this.setNextCellState(ANT);
      }
      else {
        move(move);
      }
    }
  }

  private AntCell checkFoodMove(Set<AntCell> availableNeighbors) {
    double maxPheromoneConcentration = 0.0;
    AntCell moveCell = null;

    for(AntCell cell : availableNeighbors) {
      if(cell.getCellState() == FOOD) { // If FOOD is in range?
        return cell;
      }
      else if(cell.foodPheromoneConcentration > maxPheromoneConcentration
          && !this.previouslyVisitedCells.contains(cell)) { // Most pheromones? (and not recently visited)
        moveCell = cell;
        maxPheromoneConcentration = cell.foodPheromoneConcentration;
      }
    }

    if(moveCell != null) {
      return moveCell;
    }

    // If none of the above, settle for random...
    return getRandomCell(availableNeighbors);
  }

  private Set<AntCell> findAvailableNeighbors() {
    Set<AntCell> availableNeighbors = new HashSet<>();
    for(Cell cell : getNeighbors()) {
      if((cell.getCellState() != OBSTACLE && cell.getNextCellState() != OBSTACLE &&
          cell.getCellState() != ANT && cell.getNextCellState() != ANT)) {
        availableNeighbors.add((AntCell)cell);
      }
    }
    return availableNeighbors;
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

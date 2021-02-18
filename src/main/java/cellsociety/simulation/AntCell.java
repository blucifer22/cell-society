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

  public static final String HOME_PHEROMONE_LEVEL = "HomePheromoneLevel";
  public static final String FOOD_PHEROMONE_LEVEL = "FoodPheromoneLevel";

  private double homePheromoneLevel = 0;
  private double foodPheromoneLevel = 0;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for WatorCells is WATER.
   */
  public AntCell(Map<String, Double> rules) {
    super(EMPTY, rules);
    homePheromoneLevel = 0;
    foodPheromoneLevel = 0;
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>Foraging Ants Rules are as follows:
   *
   */
  public void computeNextCellState() {

  }

}

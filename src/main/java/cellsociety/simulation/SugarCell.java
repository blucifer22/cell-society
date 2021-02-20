package cellsociety.simulation;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

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
  private static final String SUGAR_GROW_BACK_RATE = "SugarGrowBackRate";
  private static final String SUGAR_METABOLISM_RATE = "SugarMetabolismRate";

  private double agentSugar;
  private double patchSugar;
  private final double sugarGrowBackRate;
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
    sugarGrowBackRate = getParam(SUGAR_GROW_BACK_RATE);
    sugarMetabolismRate = getParam(SUGAR_METABOLISM_RATE);
    rng = new Random();
  }

  @Override
  protected void setCellState(int state) {
    super.setCellState(state);
    if (state == PATCH) {
      patchSugar = getParam(MAX_SUGAR_CAPACITY);
    } else if (state == AGENT) {
      agentSugar = rng.nextInt((int) getParam(MAX_SUGAR_CAPACITY));
    }
  }

  @Override
  protected void setNextCellState(int state, Map<String, Double> values) {
    super.setNextCellState(state);
    if(state == PATCH) {

    }
    else if(state == AGENT) {

    }
  }

  public void computeNextCellState() {

  }

}

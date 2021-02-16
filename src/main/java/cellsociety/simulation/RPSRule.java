package cellsociety.simulation;

import java.util.Map;

/**
 * A POD class that holds the basic rules for the RPS CA. Specifically, it sets the threshold number
 * of winning neighbors required for the Cell to change from one state to another.
 *
 * @author Marc Chmielewski
 */
public class RPSRule extends StateRule {

  private static final double DEFAULT_LOSS_COUNT = 3;

  private final double lossCount;

  /**
   * Default RPSRule constructor that utilizes the "typical" values for RPS. This can be overridden
   * to implement other types of similar simulations.
   */
  public RPSRule() {
    this.lossCount = DEFAULT_LOSS_COUNT;
  }

  /**
   * RPSRule constructor that takes in a Map of parsedXML configuration data, and then sets the
   * properties accordingly.
   *
   * @param parsedXML The Map of parsed XML data that will configure the rules.
   */
  public RPSRule(Map<String, Double> parsedXML) {
    this.lossCount = parsedXML.getOrDefault("LossCount", DEFAULT_LOSS_COUNT);
  }

  /**
   * RPSRule constructor that allows for maximum customization!
   *
   * @param lossCount The number of neighboring Cells that must be FULL for this Cell to transition
   *     to FULL.
   */
  public RPSRule(int lossCount) {
    this.lossCount = lossCount;
  }

  /**
   * Get the number of neighboring Cells that must be defeating this Cell for this Cell to
   * transition.
   *
   * @return the number of neighboring Cells that must be defeating this Cell for this Cell to *
   *     transition.
   */
  public double getLossCount() {
    return lossCount;
  }
}

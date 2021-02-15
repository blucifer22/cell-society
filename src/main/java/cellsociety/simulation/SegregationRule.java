package cellsociety.simulation;

import java.util.Map;

/**
 * A POD class that holds the basic rules for the Segregation Simulation. Specifically, it bounds
 * the percentage of neighbors that must have the same type as this Cell for it to not move to the
 * next available EMPTY cell and transition its current state to EMPTY.
 *
 * @author Marc Chmielewski
 */
public class SegregationRule extends StateRule {

  private static final double DEFAULT_CUTOFF_PERCENTAGE = .70;
  private double cutoffPercentage;

  /**
   * Default SegregationRule constructor that utilizes the "typical" values for Segregation.
   * Specifically, it bounds the minimum percentage of this Cell's neighbors that need to be of the
   * same type for it not to move.
   */
  public SegregationRule() {
    this.cutoffPercentage = DEFAULT_CUTOFF_PERCENTAGE;
  }

  /**
   * SegregationRule constructor that takes in a Map of parsedXML configuration data, and then sets
   * the properties accordingly.
   *
   * @param parsedXML The Map of parsed XML data that will configure the rules.
   */
  public SegregationRule(Map<String, Double> parsedXML) {
    this.cutoffPercentage = parsedXML.getOrDefault("CutoffPercentage", DEFAULT_CUTOFF_PERCENTAGE);
  }

  /**
   * SegrationRule constructor that provides maximum customization!
   *
   * @param cutoffPercentage The minimum percentage of this Cell's neighbors that need to be of the
   *                         same type for it not to move.
   */
  public SegregationRule(double cutoffPercentage) {
    this.cutoffPercentage = cutoffPercentage;
  }

  /**
   * Get the minimum percentage of this Cell's neighbors that need to be of the same type for it not
   * to move.
   *
   * @return The minimum percentage of this Cell's neighbors that need to be of the same type for it
   * not to move.
   */
  public double getCutoffPercentage() {
    return cutoffPercentage;
  }
}

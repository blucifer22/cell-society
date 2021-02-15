package cellsociety.simulation;

import java.util.Map;

/**
 * A POD class that holds the basic rules for the Percolation Simulation. Specifically, it bounds
 * the number of neighbors that must be FULL for a given Cell to transition from EMPTY to FULL.
 *
 * @author Marc Chmielewski
 */
public class PercolationRule extends StateRule {

  private static final double DEFAULT_FILL_NUMBER = 1;

  private double fillNumber;

  /**
   * Default PercolationRule constructor that utilizes the "typical" values for Percolation. This
   * can be overridden to implement other types of similar simulations.
   */
  public PercolationRule() {
    this.fillNumber = DEFAULT_FILL_NUMBER;
  }

  /**
   * PercolationRule constructor that takes in a Map of parsedXML configuration data, and then sets
   * the properties accordingly.
   *
   * @param parsedXML The Map of parsed XML data that will configure the rules.
   */
  public PercolationRule(Map<String, Double> parsedXML) {
    this.fillNumber = parsedXML.getOrDefault("FillNumber", DEFAULT_FILL_NUMBER);
  }

  /**
   * PercolationRule constructor that allows for maximum customization!
   *
   * @param fillNumber The number of neighboring Cells that must be FULL for this Cell to transition
   *                   to FULL.
   */
  public PercolationRule(int fillNumber) {
    this.fillNumber = fillNumber;
  }

  /**
   * Get the number of neighboring Cells that must be FULL for this Cell to transition to FULL.
   *
   * @return The number of neighboring Cells that must be FULL for this Cell to transition * to
   * FULL.
   */
  public double getFillNumber() {
    return fillNumber;
  }
}

package cellsociety.simulation;

import java.util.Map;

/**
 * A POD class that holds the basic rules for the Fire simulation. Specifically, it sets the
 * probability that another Cell will catch fire if its neighbor is on fire.
 *
 * @author Marc Chmielewski
 * @author Joshua Petitma
 */
public class FireRule extends StateRule {
  public static final double DEFAULT_FLAMMABILITY = 0.50;
  private double flammability;

  /**
   * Default FireRule constructor that utilizes the "typical" probability for the Fire simulation.
   * This can be overridden to implement other types of similar simulations.
   */
  public FireRule() {
    this.flammability = DEFAULT_FLAMMABILITY;
  }

  /**
   * FireRule constructor that takes in a Map of parsedXML configuration data, and then sets the
   * properties accordingly.
   *
   * @param parsedXML The Map of parsed XML data that will configure the rules.
   */
  public FireRule(Map<String, Double> parsedXML) {
    this.flammability = parsedXML.getOrDefault("Flammability", DEFAULT_FLAMMABILITY);
  }

  /**
   * FireRule constructor that allows for maximum customization!
   *
   * @param flammability The probability that a neighboring Cell will catch fire if this Cell is on
   *     fire
   */
  public FireRule(double flammability) {
    this.flammability = flammability;
  }

  /**
   * Returns the flammability probability for the simulation.
   *
   * @return The probability that a neighboring cell will catch fire if this Cell is on fire.
   */
  public double getFlammability() {
    return flammability;
  }
}

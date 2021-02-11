package cellsociety.simulation;

import java.util.Map;

/**
 * A POD class that holds the basic rules for Conway's Game of Life. Specifically, it bounds the
 * number of neighbors that must be alive or dead for the stayAlive and spawn conditions
 * respectively.
 *
 * @author Marc Chmielewski
 */
public class ConwayRule extends StateRule {
  private static final double DEFAULT_ALIVE_NUMBER_MIN = 2;
  private static final double DEFAULT_ALIVE_NUMBER_MAX = 3;
  private static final double DEFAULT_SPAWN_NUMBER_MIN = 3;
  private static final double DEFAULT_SPAWN_NUMBER_MAX = 3;
  private final double aliveNumberMin;
  private final double aliveNumberMax;
  private double spawnNumberMin;
  private double spawnNumberMax;

  /**
   * Default ConwayRule constructor that utilizes the "typical" values for Conway's Game of Life.
   * This can be overridden to implement other types of similar simulations.
   */
  public ConwayRule() {
    this.aliveNumberMin = DEFAULT_ALIVE_NUMBER_MIN;
    this.aliveNumberMax = DEFAULT_ALIVE_NUMBER_MAX;
    this.spawnNumberMin = DEFAULT_SPAWN_NUMBER_MIN;
    this.spawnNumberMax = DEFAULT_SPAWN_NUMBER_MAX;
  }

  /**
   * ConwayRule constructor that takes in a Map of parsedXML configuration data, and then sets the
   * properties accordingly.
   *
   * @param parsedXML The Map of parsed XML data that will configure the rules.
   */
  public ConwayRule(Map<String, Double> parsedXML) {
    this.aliveNumberMin = parsedXML.getOrDefault("AliveNumberMin", DEFAULT_ALIVE_NUMBER_MIN);
    this.aliveNumberMax = parsedXML.getOrDefault("AliveNumberMax", DEFAULT_ALIVE_NUMBER_MAX);
    this.spawnNumberMin = parsedXML.getOrDefault("SpawnNumberMin", DEFAULT_SPAWN_NUMBER_MIN);
    this.spawnNumberMax = parsedXML.getOrDefault("SpawnNumberMax", DEFAULT_SPAWN_NUMBER_MAX);
  }

  /**
   * ConwayRule constructor that allows for maximum customization!
   *
   * @param aliveNumberMin The minimum number of neighboring cells that must be alive to have the
   *     current cell stay ALIVE if it is already ALIVE
   * @param aliveNumberMax The maximum number of neighboring cells that must be alive to have the
   *     current cell stay ALIVE if it is already ALIVE
   * @param spawnNumberMin The minimum number of neighboring cells that can be alive to have the
   *     current cell transition from DEAD to ALIVE
   * @param spawnNumberMax The maximum number of neighboring cells that can be alive to have the
   *     current cell transition from DEAD to ALIVE
   */
  public ConwayRule(
      int aliveNumberMin, int aliveNumberMax, int spawnNumberMin, int spawnNumberMax) {
    this.aliveNumberMin = aliveNumberMin;
    this.aliveNumberMax = aliveNumberMax;
    this.spawnNumberMin = spawnNumberMin;
    this.spawnNumberMax = spawnNumberMax;
  }

  /**
   * Get the minimum number of neighboring cells that must be alive to have the current cell stay
   * ALIVE if it is already ALIVE
   *
   * @return The minimum number of neighboring cells that must be alive to have the current cell
   *     stay ALIVE if it is already ALIVE
   */
  public double getAliveNumberMin() {
    return aliveNumberMin;
  }

  /**
   * Get the maximum number of neighboring cells that must be alive to have the current cell stay
   * ALIVE if it is already ALIVE
   *
   * @return The maximum number of neighboring cells that must be alive to have the current cell
   *     stay ALIVE if it is already ALIVE
   */
  public double getAliveNumberMax() {
    return aliveNumberMax;
  }

  /**
   * Get the minimum number of neighboring cells that must be alive to have the current cell
   * transition from DEAD to ALIVE
   *
   * @return The minimum number of neighboring cells that must be alive to have the current cell
   *     transition from DEAD to ALIVE
   */
  public double getSpawnNumberMin() {
    return spawnNumberMin;
  }

  /**
   * Get the maximum number of neighboring cells that must be alive to have the current cell
   * transition from DEAD to ALIVE
   *
   * @return The maximum number of neighboring cells that must be alive to have the current cell
   *     transition from DEAD to ALIVE
   */
  public double getSpawnNumberMax() {
    return spawnNumberMax;
  }
}

package cellsociety.simulation;

import java.util.Map;

/**
 * A POD class that holds the basic rules for the Wa-Tor World Simulation. Specifically, it bounds
 * the breeding time of the fish (the number of simulation steps till the fish breeds), the amount
 * of energy that sharks gain from eating fish, the amount of energy required for the shark to spawn
 * another shark, and how much energy sharks lose per turn if they don't feed.
 *
 * @author Marc Chmielewski
 */
public class WatorRule extends StateRule {

  private static final double DEFAULT_FISH_BREEDING_CYCLE = 2;
  private static final double DEFAULT_FISH_ENERGY_GAIN = 5;
  private static final double DEFAULT_SHARK_SPAWN_ENERGY = 10;
  private static final double DEFAULT_SHARK_ENERGY_LOSS = 1;

  private final double fishBreedingCycle;
  private final double fishEnergyGain;
  private final double sharkSpawnEnergy;
  private final double sharkEnergyLoss;

  /**
   * Default WatorRule constructor that utilizes the "typical" values for Wa-Tor World.
   *
   * <p>Specifically, it: Sets the length of the "fish" breeding cycle to 3 cycles, sets the amount
   * of energy gain that "sharks" get from eating "fish" to 5, sets the amount of energy that a
   * "shark" must expend to spawn another "shark" to 10 (on spawn, energy is split evenly between
   * parent and child), and sets the amount of energy that a "shark" loses per round to 1.
   */
  public WatorRule() {
    this.fishBreedingCycle = DEFAULT_FISH_BREEDING_CYCLE;
    this.fishEnergyGain = DEFAULT_FISH_ENERGY_GAIN;
    this.sharkSpawnEnergy = DEFAULT_SHARK_SPAWN_ENERGY;
    this.sharkEnergyLoss = DEFAULT_SHARK_ENERGY_LOSS;
  }

  /**
   * WatorRule constructor that takes in a Map of parsedXML configuration data, and then sets the
   * properties accordingly.
   *
   * @param parsedXML The Map of parsed XML data that will configure the rules.
   */
  public WatorRule(Map<String, Double> parsedXML) {
    this.fishBreedingCycle =
        parsedXML.getOrDefault("FishBreedingCycle", DEFAULT_FISH_BREEDING_CYCLE);
    this.fishEnergyGain = parsedXML.getOrDefault("FishEnergyGain", DEFAULT_FISH_ENERGY_GAIN);
    this.sharkSpawnEnergy = parsedXML.getOrDefault("SharkSpawnEnergy", DEFAULT_SHARK_SPAWN_ENERGY);
    this.sharkEnergyLoss = parsedXML.getOrDefault("SharkEnergyLoss", DEFAULT_SHARK_ENERGY_LOSS);
  }

  /**
   * WaterRule constructor that provides maximum customization!
   *
   * @param fishBreedingCycle The number of cycles that must pass before a fish spawns another
   *                          fish.
   * @param fishEnergyGain    The amount of energy that a shark gains from eating a fish.
   * @param sharkSpawnEnergy  The amount of energy that a shark needs to have to spawn another
   *                          shark.
   * @param sharkEnergyLoss   The amount of energy that a shark loses per round.
   */
  public WatorRule(
      int fishBreedingCycle, int fishEnergyGain, int sharkSpawnEnergy, int sharkEnergyLoss) {
    this.fishBreedingCycle = fishBreedingCycle;
    this.fishEnergyGain = fishEnergyGain;
    this.sharkSpawnEnergy = sharkSpawnEnergy;
    this.sharkEnergyLoss = sharkEnergyLoss;
  }

  /**
   * Get the number of cycles that must pass before a fish spawns another fish.
   *
   * @return The number of cycles that must pass before a fish spawns another fish.
   */
  public double getFishBreedingCycle() {
    return fishBreedingCycle;
  }

  /**
   * Get the amount of energy that a shark gains from eating a fish.
   *
   * @return The amount of energy that a shark gains from eating a fish.
   */
  public double getFishEnergyGain() {
    return fishEnergyGain;
  }

  /**
   * Get the amount of energy that a shark needs to have to spawn another shark.
   *
   * @return The amount of energy that a shark needs to have to spawn another shark.
   */
  public double getSharkSpawnEnergy() {
    return sharkSpawnEnergy;
  }

  /**
   * Get the amount of energy that a shark loses per round.
   *
   * @return The amount of energy that a shark loses per round.
   */
  public double getSharkEnergyLoss() {
    return sharkEnergyLoss;
  }
}

package cellsociety.simulation;

/**
 * This class represents all the states that a Wa-Tor World simulation can possibly hold.
 *
 * @author Marc Chmielewski
 */
public class WatorState extends CellState {
  public static final int WATER = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;

  private int energyLevel;

  /**
   * Constructs a state with the specified state.
   *
   * @param state - The state which this object is created with.
   */
  public WatorState(int state, int energyLevel) {
    super(state);
    this.energyLevel = energyLevel;
  }

  /**
   * Constructs state object as water.
   *
   * <p>This constructor creates a state object representing an empty state.
   */
  public WatorState() {
    super(WATER);
    energyLevel = 0;
  }

  /**
   * Implements a toString for FireState
   */
  public String toString() {
    switch (getState()) {
      case WATER -> {
        return "WATER";
      }
      case FISH -> {
        return "FISH";
      }
      case SHARK -> {
        return "SHARK";
      }
    }
    return "INVALID STATE";
  }

  /**
   * Get the energyLevel of the current WatorState
   *
   * @return The energyLevel of the current WatorState
   */
  public int getEnergyLevel() {
    return energyLevel;
  }

  /**
   * Set the energyLevel of the current WatorState
   *
   * @param energyLevel The new energyLevel of the current WatorState
   */
  public void setEnergyLevel(int energyLevel) {
    this.energyLevel = energyLevel;
  }
}

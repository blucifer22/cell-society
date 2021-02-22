package cellsociety.simulation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class handles the behavior of Cells in the Wa-Tor World simulation, and thus the state
 * transitions therein.
 *
 * @author Marc Chmielewski
 */
public class WatorCell extends Cell {

  public static final int WATER = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;
  public static final String ENERGY_LEVEL = "EnergyLevel";
  public static final String ROUNDS_TILL_SPAWN = "RoundsTillSpawn";
  private double energyLevel;
  private double roundsTillSpawn;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for WatorCells is WATER.
   */
  public WatorCell(Map<String, Double> params) {
    super(WATER, params);
    energyLevel = 0;
    roundsTillSpawn = 0;
  }

  /**
   * Constructs this cell with the specified initial state.
   *
   * @param state - The state to use for this cell.
   */
  public WatorCell(int state) {
    super(state);
  }

  @Override
  public void poke() {
    super.setCellState(getCellState() + 1);
    if (getCellState() == FISH) {
      energyLevel = 0;
      roundsTillSpawn = getParam("FishBreedingCycle");
    } else if (getCellState() == SHARK) {
      energyLevel = getParam("SharkSpawnEnergy") / 2;
      roundsTillSpawn = 0;
    }
    else {
      super.setCellState(WATER);
      roundsTillSpawn = 0;
      energyLevel = 0;
    }
  }

  @Override
  protected void setCellState(int state) {
    super.setCellState(state);
    if (state == FISH) {
      energyLevel = 0;
      roundsTillSpawn = getParam("FishBreedingCycle");
    } else if (state == SHARK) {
      energyLevel = getParam("SharkSpawnEnergy") / 2;
      roundsTillSpawn = 0;
    } else {
      energyLevel = 0;
      roundsTillSpawn = 0;
    }
  }

  @Override
  protected void setNextCellState(int state, Map<String, Double> values) {
    super.setNextCellState(state);
    if (state == FISH) {
      energyLevel = 0;
      roundsTillSpawn = values.getOrDefault(ROUNDS_TILL_SPAWN, getParam("FishBreedingCycle"));
    } else if (state == SHARK) {
      energyLevel = values.getOrDefault(ENERGY_LEVEL, getParam("SharkSpawnEnergy") / 2);
      roundsTillSpawn = 0;
    } else {
      energyLevel = 0;
      roundsTillSpawn = 0;
    }
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>Wa-Tor World Rules are as follows:
   *
   * <p>If this cell is a fish check to see if it is ready to spawn. If so, and there's an space
   * available, spawn another fish. Else, reset the counter.
   *
   * <p>If this cell is a shark, check to see if it can move into a space that has a fish. If so,
   */
  public void computeNextCellState() {
    Set<Cell> occupiedNeighbors = new HashSet<>();
    findOccupiedNeighbors(occupiedNeighbors);
    HashSet<Cell> unoccupiedNeighbors = new HashSet<>(getNeighbors());
    unoccupiedNeighbors.removeAll(occupiedNeighbors);

    switch (getCellState()) {
      case FISH -> {
        updateFishState(unoccupiedNeighbors);
      }
      case SHARK -> {
        updateSharkState(occupiedNeighbors, unoccupiedNeighbors);
      }
    }
  }

  private void updateSharkState(Set<Cell> occupiedNeighbors, HashSet<Cell> unoccupiedNeighbors) {

    // Decrement SHARK energy level
    energyLevel--;

    // Check SHARK death
    if (energyLevel <= 0) {
      killShark(this);
      return;
    }

    // Check SHARK spawn
    if (energyLevel >= getParam("SharkSpawnEnergy")) {
      boolean success = spawn(SHARK, energyLevel / 2, unoccupiedNeighbors);
      if (success) {
        this.energyLevel /= 2;
      }
    }

    // Attempt to move SHARK and eat a FISH if possible
    Cell sharkMove = checkSharkMove(occupiedNeighbors, unoccupiedNeighbors);
    if (sharkMove != null) {
      move(sharkMove);
    }
  }

  private void updateFishState(HashSet<Cell> unoccupiedNeighbors) {

    // Check FISH spawn
    if (roundsTillSpawn == 0) {
      spawn(FISH, 0, unoccupiedNeighbors);
      roundsTillSpawn = getParam("FishBreedingCycle"); // Reset counter regardless of success
    } else {
      roundsTillSpawn--;
    }

    // Attempt to move FISH
    Cell fishMove = checkFishMove(unoccupiedNeighbors);
    if (fishMove != null) {
      move(fishMove);
    }
  }

  private void killFish(Cell cell) {
    cell.setCellState(WATER);
  }

  private void killShark(Cell cell) {
    Map<String, Double> data = Map.of(ROUNDS_TILL_SPAWN, 0.0, ENERGY_LEVEL, 0.0);
    cell.setCellState(WATER);
    cell.setNextCellState(WATER, data);
  }

  private void move(Cell newCell) {
    this.setNextCellState(WATER);
    if (this.getCellState() == FISH) {
      Map<String, Double> data = Map.of(ROUNDS_TILL_SPAWN, this.roundsTillSpawn, ENERGY_LEVEL, 0.0);
      newCell.setNextCellState(FISH, data);
    } else if (this.getCellState() == SHARK) {
      Map<String, Double> data = Map.of(ROUNDS_TILL_SPAWN, 0.0, ENERGY_LEVEL, this.energyLevel);
      newCell.setNextCellState(SHARK, data);
    }
    this.setCellState(WATER);
  }

  public void setEnergyLevel(double energyLevel) {
    this.energyLevel = energyLevel;
  }

  private Cell checkSharkMove(Set<Cell> occupiedNeighbors, Set<Cell> unoccupiedNeighbors) {
    // Check for fish and eat it if available
    for (Cell neighbor : occupiedNeighbors) {
      if (neighbor.getCellState() == FISH) {
        setEnergyLevel(energyLevel + getParam("FishEnergyGain"));
        killFish(neighbor);
        return neighbor;
      }
    }
    // If not, settle for water
    for (Cell neighbor : unoccupiedNeighbors) {
      if (neighbor.getCellState() == WATER && neighbor.getNextCellState() == WATER) {
        return neighbor;
      }
    }
    return null;
  }

  private Cell checkFishMove(Set<Cell> unoccupiedNeighbors) {
    for (Cell neighbor : unoccupiedNeighbors) {
      if (neighbor.getCellState() == WATER && neighbor.getNextCellState() == WATER) {
        return neighbor;
      }
    }
    return null;
  }

  private void findOccupiedNeighbors(Set<Cell> occupiedNeighbors) {
    for (Cell cell : getNeighbors()) {
      if (cell.getCellState() != WATER || cell.getNextCellState() != WATER) {
        occupiedNeighbors.add(cell);
      }
    }
  }

  private boolean spawn(int cellType, double energyLevel, Set<Cell> unoccupiedNeighbors) {
    for (Cell neighbor : unoccupiedNeighbors) {
      if (neighbor.getCellState() == WATER && (neighbor.getNextCellState() == WATER)) {
        if (cellType == FISH) {
          Map<String, Double> data = Map.of(ROUNDS_TILL_SPAWN, getParam("FishBreedingCycle"));
          neighbor.setNextCellState(FISH, data);
        } else if (cellType == SHARK) {
          Map<String, Double> data = Map.of(ENERGY_LEVEL, energyLevel);
          neighbor.setNextCellState(SHARK, data);
        }
        return true;
      }
    }
    return false;
  }
}

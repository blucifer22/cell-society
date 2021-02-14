package cellsociety.simulation;

import java.util.HashSet;
import java.util.Set;

/**
 * This class handles the behavior of Cells in the Wa-Tor World simulation, and thus the state
 * transitions therein.
 *
 * @author Marc Chmielewski
 */
public class WatorCell extends Cell {
  public static WatorRule rule;
  public static final int WATER = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;

  private double energyLevel;
  private double roundsTillSpawn;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for WatorCells is WATER.
   */
  public WatorCell() {
    this(WATER);
    energyLevel = 15;
    roundsTillSpawn = 3;
  }

  /**
   * Constructs this cell with the specified initial state.
   *
   * @param state - The state to use for this cell.
   */
  public WatorCell(int state) {
    super(state);
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
   *
   *
   *
   */
  public void computeNextCellState() {
    Set<Cell> occupiedNeighbors = new HashSet<>();
    findOccupiedNeighbors(occupiedNeighbors);
    HashSet<Cell> unoccupiedNeighbors = new HashSet<>(neighbors);
    unoccupiedNeighbors.removeAll(occupiedNeighbors);

    switch (cellState) {
      case FISH -> {
        updateFishState(unoccupiedNeighbors, cellState);
      }
      case SHARK -> {
        updateSharkState(occupiedNeighbors, unoccupiedNeighbors, cellState);
      }
    }
  }

  private void updateSharkState(Set<Cell> occupiedNeighbors, HashSet<Cell> unoccupiedNeighbors,
      int currentState) {
    // Check SHARK spawn
    if(currentState >= rule.getSharkSpawnEnergy()) {
      boolean success = spawn(SHARK, energyLevel / 2,
          unoccupiedNeighbors);
      if(success) {
        energyLevel/=2;
      }
    }

    // Check SHARK death
    if(energyLevel <= 0) {
      killShark();
      return;
    }

    // Attempt to move SHARK
    Cell sharkMove = checkSharkMove(occupiedNeighbors, unoccupiedNeighbors, cellState);
    if(sharkMove != null) {
      move(sharkMove);
    }
  }

  private void updateFishState(HashSet<Cell> unoccupiedNeighbors, int currentState) {
    // Check FISH spawn
    if( roundsTillSpawn == 0) {
      spawn(FISH, 0, unoccupiedNeighbors);
      roundsTillSpawn = rule.getFishBreedingCycle();
    }
    else {
      roundsTillSpawn--;
    }

    // Attempt to move FISH
    Cell fishMove = checkFishMove(unoccupiedNeighbors);
    if(fishMove != null) {
      move(fishMove);
    }
  }

  public void setNextCellState(int state, double energyLevel) {
    this.cellState = state;
    this.energyLevel = energyLevel;
  }

  private void killShark() {
    nextCellState = WATER;
  }

  private void move(Cell newCell) {
    setNextCellState(newCell.getCurrentCellState());
    newCell.setNextCellState(getCurrentCellState());
  }

  public void setEnergyLevel(double energyLevel) {
    this.energyLevel = energyLevel;
  }

  private Cell checkSharkMove(Set<Cell> occupiedNeighbors, Set<Cell> unoccupiedNeighbors,
      int currentState) {
    // Check for fish and eat it if available
    for(Cell neighbor : occupiedNeighbors) {
      if(neighbor.getCurrentCellState() == FISH) {
        setEnergyLevel(energyLevel + rule.getFishEnergyGain());
        return neighbor;
      }
    }
    // If not, settle for water
    for(Cell neighbor : unoccupiedNeighbors) {
      if(neighbor.getCurrentCellState() == WATER) {
        return neighbor;
      }
    }
    return null;
  }

  private Cell checkFishMove(Set<Cell> unoccupiedNeighbors) {
    for(Cell neighbor : unoccupiedNeighbors) {
      if(neighbor.getCurrentCellState() == WATER) {
        return neighbor;
      }
    }
    return null;
  }

  private void findOccupiedNeighbors(Set<Cell> occupiedNeighbors) {
    for(Cell cell : neighbors) {
      if(cell.getCurrentCellState() != WATER ||
          cell.getNextCellState() != WATER) {
        occupiedNeighbors.add(cell);
      }
    }
  }

  private boolean spawn(int cellType, double energyLevel, Set<Cell> unoccupiedNeighbors) {
    for(Cell neighbor : unoccupiedNeighbors) {
      if(neighbor.getCurrentCellState() == WATER &&
          (neighbor.getNextCellState() == WATER )) {
        if (neighbor instanceof WatorCell) {
          ((WatorCell) (neighbor)).setNextCellState(cellType, energyLevel);
        }
        return true;
      }
    }
    return false;
  }
}

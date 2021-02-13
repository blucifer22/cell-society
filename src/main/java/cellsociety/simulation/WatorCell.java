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

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for WatorCells is WATER.
   */
  public WatorCell() {
    super(new WatorState(WatorState.WATER, 0));
  }

  /**
   * Constructs this cell with the specified initial state.
   *
   * @param state - The state to use for this cell.
   */
  public WatorCell(WatorState state) {
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
    WatorState currentState = (WatorState) this.getCurrentCellState();

    switch (currentState.getState()) {
      case WatorState.FISH -> {
        updateFishState(unoccupiedNeighbors, currentState);
      }
      case WatorState.SHARK -> {
        updateSharkState(occupiedNeighbors, unoccupiedNeighbors, currentState);
      }
    }
  }

  private void updateSharkState(Set<Cell> occupiedNeighbors, HashSet<Cell> unoccupiedNeighbors,
      WatorState currentState) {
    // Check SHARK spawn
    if(currentState.getEnergyLevel() >= rule.getSharkSpawnEnergy()) {
      boolean success = spawn(WatorState.SHARK, currentState.getEnergyLevel() / 2,
          unoccupiedNeighbors);
      if(success) {
        currentState.setEnergyLevel(currentState.getEnergyLevel() / 2);
      }
    }

    // Check SHARK death
    if(currentState.getEnergyLevel() <= 0) {
      killShark();
      return;
    }

    // Attempt to move SHARK
    Cell sharkMove = checkSharkMove(occupiedNeighbors, unoccupiedNeighbors);
    if(sharkMove != null) {
      move(sharkMove);
    }
  }

  private void updateFishState(HashSet<Cell> unoccupiedNeighbors, WatorState currentState) {
    // Check FISH spawn
    if(currentState.getNumberRoundsTillSpawn() == 0) {
      spawn(WatorState.FISH, 0, unoccupiedNeighbors);
      currentState.setNumberRoundsTillSpawn(rule.getFishBreedingCycle());
    }
    else {
      currentState.setNumberRoundsTillSpawn(currentState.getNumberRoundsTillSpawn() - 1);
    }

    // Attempt to move FISH
    Cell fishMove = checkFishMove(unoccupiedNeighbors);
    if(fishMove != null) {
      move(fishMove);
    }
  }

  private void killShark() {
    nextCellState.setState(WatorState.WATER);
  }

  private void move(Cell newCell) {
    setNextCellState(newCell.getCurrentCellState());
    newCell.setNextCellState(getCurrentCellState());
  }

  private Cell checkSharkMove(Set<Cell> occupiedNeighbors, Set<Cell> unoccupiedNeighbors) {
    for(Cell neighbor : occupiedNeighbors) {
      if(neighbor.getCurrentCellState().getState() == WatorState.FISH) {
        return neighbor;
      }
    }
    for(Cell neighbor : unoccupiedNeighbors) {
      if(neighbor.getCurrentCellState().getState() == WatorState.WATER) {
        return neighbor;
      }
    }
    return null;
  }

  private Cell checkFishMove(Set<Cell> unoccupiedNeighbors) {
    for(Cell neighbor : unoccupiedNeighbors) {
      if(neighbor.getCurrentCellState().getState() == WatorState.WATER) {
        return neighbor;
      }
    }
    return null;
  }

  private void findOccupiedNeighbors(Set<Cell> occupiedNeighbors) {
    for(Cell cell : neighbors) {
      if(cell.getCurrentCellState().getState() != WatorState.WATER ||
          cell.getNextCellState().getState() != WatorState.WATER) {
        occupiedNeighbors.add(cell);
      }
    }
  }

  private boolean spawn(int cellType, double energyLevel, Set<Cell> unoccupiedNeighbors) {
    for(Cell neighbor : unoccupiedNeighbors) {
      if(neighbor.getCurrentCellState().getState() == WatorState.WATER &&
          (neighbor.getNextCellState().getState() == WatorState.WATER ||
              neighbor.getNextCellState() == null)) {
        neighbor.setNextCellState(new WatorState(cellType, energyLevel));
        return true;
      }
    }
    return false;
  }
}

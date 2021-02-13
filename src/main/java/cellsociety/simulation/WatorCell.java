package cellsociety.simulation;

import java.util.ArrayList;
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
    WatorState currentState = (WatorState) this.getCurrentCellState();

    switch (currentState.getState()) {
      case WatorState.FISH -> {

        // Check FISH spawn
        if(currentState.getNumberRoundsTillSpawn() == 0) {
          spawn(WatorState.FISH, 0, occupiedNeighbors);
          currentState.setNumberRoundsTillSpawn(rule.getFishBreedingCycle());
        }
        else {
          currentState.setNumberRoundsTillSpawn(currentState.getNumberRoundsTillSpawn() - 1);
        }

        // Attempt to move FISH
        Cell fishMove = checkFishMove(occupiedNeighbors);
        if(fishMove != null) {
          moveFish();
        }
      }
      case WatorState.SHARK -> {

        // Check SHARK spawn
        if(currentState.getEnergyLevel() >= rule.getSharkSpawnEnergy()) {
          boolean success = spawn(WatorState.SHARK, currentState.getEnergyLevel() / 2, occupiedNeighbors);
          if(success) {
            currentState.setEnergyLevel(currentState.getEnergyLevel() / 2);
          }
        }

        // Check SHARK death
        if(currentState.getEnergyLevel() <= 0) {
          killShark();
          break;
        }

        // Attempt to move SHARK
        Cell sharkMove = checkSharkMove(occupiedNeighbors);
        if(sharkMove != null) {
          moveShark();
        }
      }
    }
  }

  private void killShark() {
    // TODO: Implement this
  }

  private void moveShark() {
    // TODO: Implement this
  }

  private Cell checkSharkMove(Set<Cell> occupiedNeighbors) {
    // TODO: Implement this
    return new WatorCell();
  }

  private void moveFish() {
    // TODO: Implement this
  }

  private Cell checkFishMove(Set<Cell> occupiedNeighbors) {
    // TODO: Implement this
    return new WatorCell();
  }

  private void findOccupiedNeighbors(Set<Cell> occupiedNeighbors) {
    for(Cell cell : neighbors) {
      if(cell.getCurrentCellState().getState() != WatorState.WATER ||
          cell.getNextCellState().getState() != WatorState.WATER) {
        occupiedNeighbors.add(cell);
      }
    }
  }

  private boolean spawn(int cellType, double energyLevel, Set<Cell> occupiedNeighbors) {
    HashSet<Cell> availableNeighbors = new HashSet<>(neighbors);
    availableNeighbors.removeAll(occupiedNeighbors);
    for(Cell neighbor : availableNeighbors) {
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

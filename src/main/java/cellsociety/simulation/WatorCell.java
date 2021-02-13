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
   *
   */
  public void computeNextCellState() {
    Set<Cell> occupiedNeighbors = new HashSet<>();
    for(Cell cell : neighbors) {
      if(cell.getCurrentCellState().getState() != WatorState.WATER) {
        occupiedNeighbors.add(cell);
      }
    }
    WatorState currentState = (WatorState) this.getCurrentCellState();
    switch (currentState.getState()) {
      case WatorState.FISH -> {
        if(currentState.getNumberRoundsTillSpawn() == 0) {
          spawnFish();

        }
      }
      case WatorState.SHARK -> {

      }
    }
  }

  private void spawnFish() {
    // TODO: Implement this!
  }


}

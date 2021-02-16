package cellsociety.simulation;

/**
 * This class handles the behavior of Cells in the Percolation simulation, and thus the state
 * transitions therein.
 *
 * @author Marc Chmielewski
 */
public class PercolationCell extends Cell {

  public static PercolationRule rule;
  public static final int EMPTY = 0;
  public static final int FULL = 1;
  public static final int BLOCKED = 2;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for PercolationCells is EMPTY.
   */
  public PercolationCell() {
    super(EMPTY);
  }

  /**
   * Constructs this cell with the specified default state.
   *
   * @param state - The state to use for this cell.
   */
  public PercolationCell(int state) {
    super(state);
  }

  @Override
  public void poke() {
    if (++cellState > 2) {
      cellState = 0;
    }
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>Percolation Rules are as follows:
   *
   * <p>If a Cell is EMPTY and it has a neighbor that is FULL, the Cell's next state will be FULL.
   * If the Cell is FULL, it will remain FULL.
   */
  public void computeNextCellState() {
    if (cellState == EMPTY) {
      int countFillNeighbors = 0;
      for (Cell cell : neighbors) {
        if (cell.getCurrentCellState() == FULL) {
          countFillNeighbors++;
        }
        if (countFillNeighbors >= rule.getFillNumber()) {
          nextCellState = FULL;
          break;
        }
      }
    } else if (cellState == FULL) {
      nextCellState = FULL;
    } else {
      nextCellState = BLOCKED;
    }
  }
}

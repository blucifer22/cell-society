package cellsociety.simulation;

import java.util.Map;

/**
 * This class handles the behavior of cells in the Rock, Paper, Scissors (RPS) simulation, and thus
 * the state transitions therein.
 *
 * @author Marc Chmielewski
 */
public class RPSCell extends Cell {

  public static final int ROCK = 0;
  public static final int PAPER = 1;
  public static final int SCISSORS = 2;


  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for RPSCells is ROCK.
   */
  public RPSCell(Map<String, Double> params) {
    super(ROCK, params);
  }

  /**
   * Constructs this cell with the specified default state.
   *
   * @param state - The state to use for this cell.
   */
  public RPSCell(int state) {
    super(state);
  }

  @Override
  public void poke() {
    setCellState(getCellState() + 1);
    if (getCellState() > SCISSORS) {
      setCellState(ROCK);
    }
  }

  /**
   * Computes the next state of this Cell by inspecting its neighbors and then determining the
   * transition accordingly.
   *
   * <p>RPS's Rules are as follows:
   *
   * <p>Any cell that is losing to a number of its neighboring cells greater than an assigned
   * threshold value (typically 3) will become the cell that it is "losing to" on the next cycle of
   * the CA.
   */
  public void computeNextCellState() {
    int numRock = 0;
    int numPaper = 0;
    int numScissors = 0;
    for (Cell cell : getNeighbors()) {
      if (cell.getCellState() == ROCK) numRock++;
      else if (cell.getCellState() == PAPER) numPaper++;
      else numScissors++;
    }
    switch (this.getCellState()) {
      case ROCK -> {
        if (numPaper >= getParam("LossCount")) {
          this.setNextCellState(PAPER);
        } else {
          this.setNextCellState(ROCK);
        }
      }
      case PAPER -> {
        if (numScissors >= getParam("LossCount")) {
          this.setNextCellState(SCISSORS);
        } else {
          this.setNextCellState(PAPER);
        }
      }
      case SCISSORS -> {
        if (numRock >= getParam("LossCount")) {
          this.setNextCellState(ROCK);
        } else {
          this.setNextCellState(SCISSORS);
        }
      }
    }
  }
}

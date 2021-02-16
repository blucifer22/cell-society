package cellsociety.simulation;

/**
 * This class handles the behavior of cells in the Rock, Paper, Scissors (RPS) simulation, and thus
 * the state transitions therein.
 *
 * @author Marc Chmielewski
 */
public class RPSCell extends Cell{

  public static final int ROCK = 0;
  public static final int PAPER = 1;
  public static final int SCISSORS = 2;

  public static RPSRule rule;

  /**
   * Construct this cell with its default state.
   *
   * <p>The default state for RPSCells is ROCK.
   */
  public RPSCell() {
    super(ROCK);
  }

  /**
   * Constructs this cell with the specified default state.
   *
   * @param state - The state to use for this cell.
   */
  public RPSCell(int state) {
    super(state);
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
    for(Cell cell : neighbors) {
      if(cell.getCurrentCellState() == ROCK)
        numRock++;
      else if(cell.getCurrentCellState() == PAPER)
        numPaper++;
      else
        numScissors++;
    }
    switch (this.cellState) {
      case ROCK -> {
        if(numPaper >= rule.getLossCount()) {
          this.nextCellState = PAPER;
        }
        else {
          this.nextCellState = ROCK;
        }
      }
      case PAPER -> {
        if(numScissors >= rule.getLossCount()) {
          this.nextCellState = SCISSORS;
        }
        else {
          this.nextCellState = PAPER;
        }
      }
      case SCISSORS -> {
        if(numRock >= rule.getLossCount()) {
          this.nextCellState = ROCK;
        }
        else {
          this.nextCellState = SCISSORS;
        }
      }
    }
  }
}
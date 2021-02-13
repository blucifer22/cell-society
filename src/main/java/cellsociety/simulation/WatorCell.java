package cellsociety.simulation;

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
   * <p>If this cell is a fish
   */
  public void computeNextState() {

  }


}

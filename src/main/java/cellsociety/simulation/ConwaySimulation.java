package cellsociety.simulation;

/**
 * This class is a general implementation of a Conway's Game of Life Simulation.
 *
 * The actual StateRules are implemented at the Cell level; the primary purpose of this class is to
 * synchronize the timing of the Cell state transitions through calls of computeState() and
 * commitState().
 *
 * @author Marc Chmielewski
 */
public class ConwaySimulation extends Simulation{

  public ConwaySimulation( CellGrid cellGrid) {
    super(cellGrid);
  }

  @Override
  public void computeState() {

  }

  @Override
  public void commitState() {

  }


}

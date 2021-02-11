package cellsociety.simulation;

public abstract class Simulation {
  protected CellGrid cellGrid;

  public Simulation(CellGrid cellGrid) {
    this.cellGrid = cellGrid;
  }

  public abstract void computeState();

  public abstract void commitState();
}

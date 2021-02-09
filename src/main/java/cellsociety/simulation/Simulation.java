package cellsociety.simulation;

import java.util.List;

public abstract class Simulation {
  protected List<StateRule> rules;
  protected CellGrid cellGrid;

  public abstract void computeState();
  public abstract void commitState();

}

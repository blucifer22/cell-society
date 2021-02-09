package cellsociety;

import java.util.List;

public abstract class Simulation {
  private List<Rule> rules;
  private CellGrid cellGrid;
  public abstract void computeState();
  public abstract void commitState();

}

package cellsociety;

public abstract class Rule {
  public abstract CellState nextState(CellState curState);
}

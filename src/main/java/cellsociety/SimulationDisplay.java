package cellsociety;

import cellsociety.simulation.Simulation;
import javafx.scene.Group;

public class SimulationDisplay extends Group {
  private Simulation simulation;

  public void step() {}

  public void play() {}

  public void pause() {}

  public void setSimulation(Simulation simulation) {
    this.simulation = simulation;
    // ... more stuff
  }
}

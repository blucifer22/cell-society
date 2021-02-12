package cellsociety;

import cellsociety.simulation.Simulation;
import javafx.scene.Group;

public class SimulationDisplay extends Group {
  private Simulation simulation;

  public void step() {
	  System.out.println("Stepped");
  }

  public void play() {
	  System.out.println("Playing");
  }

  public void pause() {
	  System.out.println("Pausing");
  }

  public void setSimulation(Simulation simulation) {
    this.simulation = simulation;
    // ... more stuff
  }
}

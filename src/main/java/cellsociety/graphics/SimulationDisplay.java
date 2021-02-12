package cellsociety.graphics;

import cellsociety.simulation.Simulation;
import java.util.List;
import java.util.ArrayList;
import cellsociety.simulation.Cell;
import javafx.scene.Group;

public class SimulationDisplay extends Group {
  private Simulation simulation;
  private List<Cell> cells;
  private List<GraphicalCell> graphicalCells;

  public SimulationDisplay() {
	  this.graphicalCells = new ArrayList<>();
	  this.cells = new ArrayList<>();
  }

  /**
   * Steps the current simulation forward once.
   *
   * Calls the necessary methods to display
   * the next spot of the simulation
   */
  public void step() {
	  System.out.println("Stepped");
  }

  /**
   * Allows the simulation to play on its own.
   *
   * This makes the simulation play on its own.
   */
  public void play() {
	  System.out.println("Playing");
  }

  /**
   * Stops the simulation if currently running.
   *
   * If the simulation is currently running this will
   * pause it.
   */
  public void pause() {
	  System.out.println("Pausing");
  }

  /**
   * Sets the current simulation to be played back.
   *
   * @param simulation - Sets the simulation to be
   * displayed.
   */
  public void setSimulation(Simulation simulation) {
    this.simulation = simulation;
	cells.clear();
	graphicalCells.clear();
	cells = simulation.getCells();
	for (Cell cell : cells) {
		graphicalCells.add(new GraphicalCell(cell));
	}
	this.getChildren().addAll(graphicalCells);
  }
}

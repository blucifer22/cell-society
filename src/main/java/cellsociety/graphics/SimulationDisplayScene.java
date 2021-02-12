package cellsociety.graphics;

import cellsociety.simulation.Simulation;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * @author David Coffman
 */

public class SimulationDisplay extends Scene {
  private final Simulation simulation;
  private final List<GraphicalCell> graphicalCells;

  public SimulationDisplay(Simulation simulation, double width, double height) {
    super(new Group(), width, height);
    this.simulation = simulation;
    this.graphicalCells = simulation.getGraphicalCells();
    ObservableList<Node> rootChildren = ((Group) this.getRoot()).getChildren();
    for(GraphicalCell g: graphicalCells) {
      rootChildren.add(g.getNode());
    }
  }

  public void step() {
	  simulation.step();
  }

  public void play() {
	  simulation.start();
  }

  public void pause() {
	  simulation.pause();
  }
}

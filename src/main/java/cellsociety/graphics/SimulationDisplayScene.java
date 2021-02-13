package cellsociety.graphics;

import cellsociety.simulation.SimulationController;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * @author David Coffman
 * @author Marc Chmielewski
 */
public class SimulationDisplayScene extends Scene {

  private final SimulationController simulationController;
  private final Group root;

  public SimulationDisplayScene(SimulationController simulationController, double width,
      double height) {
    super(new Group(), width, height);
    this.root = (Group) this.getRoot();
    this.simulationController = simulationController;
    ObservableList<Node> rootChildren = ((Group) this.getRoot()).getChildren();
//    for (GraphicalCell g : graphicalCells) {
//      rootChildren.add(g.getNode());
//    }
    this.root.getChildren().add(createButtonPane());
  }

  private Pane createButtonPane() {
    HBox row = new HBox(10);
    Button loadButton = new Button("Load New Simulation");
    Button playButton = new Button("Play");
    Button pauseButton = new Button("Pause");
    Button stepButton = new Button("Step");
    row.getChildren().addAll(loadButton, playButton, pauseButton, stepButton);

    loadButton.setOnAction(e -> simulationController.loadSimulation());
    playButton.setOnAction(e -> simulationController.startSimulation());
    pauseButton.setOnAction(e -> simulationController.pauseSimulation());
    stepButton.setOnAction(e -> simulationController.step());
    row.setAlignment(Pos.CENTER);
    return row;
  }
}

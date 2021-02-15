package cellsociety.graphics;

import java.util.ResourceBundle;
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
  private GraphicalCellRectangularGrid graphicalCellGrid;
  private ResourceBundle resources;
  private final double WIDTH;
  private final double HEIGHT;

  public SimulationDisplayScene(
      SimulationController simulationController, double width, double height, ResourceBundle resources) {
    super(new Group(), width, height);
    this.root = (Group) this.getRoot();
    this.simulationController = simulationController;
    this.graphicalCellGrid = simulationController.graphicalCellGridForCurrentSimulation();
	this.resources = resources;
    this.WIDTH = width;
    this.HEIGHT = height;
    buildScene();
  }

  private void buildScene() {
    ObservableList<Node> rootChildren = this.root.getChildren();
    rootChildren.add(createButtonPane());
    rootChildren.add(this.graphicalCellGrid.getNode());
  }

  private Pane createButtonPane() {
    HBox row = new HBox(10);
    Button exitButton = new Button(resources.getString("ExitSimulation"));
    Button playButton = new Button(resources.getString("Play"));
    Button speedUpButton = new Button(resources.getString("SpeedUp"));
    Button slowDownButton = new Button(resources.getString("SlowDown"));
    Button pauseButton = new Button(resources.getString("Pause"));
    Button stepButton = new Button(resources.getString("Step"));

    row.getChildren()
        .addAll(exitButton, playButton, speedUpButton, slowDownButton, pauseButton, stepButton);

    exitButton.setOnAction(e -> simulationController.exitSimulation());
    playButton.setOnAction(e -> simulationController.startSimulation());
    speedUpButton.setOnAction(e -> simulationController.speedUpSimulation());
    slowDownButton.setOnAction(e -> simulationController.slowDownSimulation());
    pauseButton.setOnAction(e -> simulationController.pauseSimulation());
    stepButton.setOnAction(e -> simulationController.step());
    row.setAlignment(Pos.CENTER);
    row.setTranslateY(this.HEIGHT - 60.0);
    row.setPrefWidth(this.WIDTH);
    return row;
  }
}

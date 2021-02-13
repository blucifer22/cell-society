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
  private GraphicalCellRectangularGrid graphicalCellGrid;

  private final double WIDTH;
  private final double HEIGHT;

  public SimulationDisplayScene(SimulationController simulationController, double width,
      double height) {
    super(new Group(), width, height);
    this.root = (Group) this.getRoot();
    this.simulationController = simulationController;
    this.graphicalCellGrid = simulationController.graphicalCellGridForCurrentSimulation();
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
    Button exitButton = new Button("Exit Simulation");
    Button playButton = new Button("Play");
    Button pauseButton = new Button("Pause");
    Button stepButton = new Button("Step");
    row.getChildren().addAll(exitButton, playButton, pauseButton, stepButton);

    exitButton.setOnAction(e -> simulationController.exitSimulation());
    playButton.setOnAction(e -> simulationController.startSimulation());
    pauseButton.setOnAction(e -> simulationController.pauseSimulation());
    stepButton.setOnAction(e -> simulationController.step());
    row.setAlignment(Pos.CENTER);
    row.setTranslateY(this.HEIGHT - 60.0);
    row.setPrefWidth(this.WIDTH);
    return row;
  }
}

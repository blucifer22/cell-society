package cellsociety.graphics;

import cellsociety.graphics.UIController.Theme;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The <code>Scene</code> responsible for rendering the graphical component of a simulation.
 *
 * @author David Coffman
 * @author Marc Chmielewski
 * @author Joshua Petitma
 */
public class SimulationDisplayScene extends Scene {

  private final SimulationController simulationController;
  private final Group root;
  private final double WIDTH;
  private final double HEIGHT;
  private final GraphicalCellGrid graphicalCellGrid;
  private final ResourceBundle resources;

  public SimulationDisplayScene(
      SimulationController simulationController, double width, double height,
      ResourceBundle resources, Theme theme) {
    super(new Group(), width, height);
    this.root = (Group) this.getRoot();
    this.simulationController = simulationController;
    this.graphicalCellGrid = simulationController.graphicalCellGridForCurrentSimulation();
    this.resources = resources;
    this.WIDTH = width;
    this.HEIGHT = height;
    this.getStylesheets().add(getClass().getResource("styles/"+theme+".css").toExternalForm());
    buildScene();
  }

  private HBox firstButtonRow() {
    HBox row = new HBox(10);

    Button playButton = new Button(resources.getString("Play"));
    Button speedUpButton = new Button(resources.getString("SpeedUp"));
    Button slowDownButton = new Button(resources.getString("SlowDown"));
    Button pauseButton = new Button(resources.getString("Pause"));
    Button stepButton = new Button(resources.getString("Step"));
    Button extraSettingsButton = new Button(resources.getString("ExtraSettings"));

    playButton.setOnAction(e -> simulationController.startSimulation());
    speedUpButton.setOnAction(e -> simulationController.speedUpSimulation());
    slowDownButton.setOnAction(e -> simulationController.slowDownSimulation());
    pauseButton.setOnAction(e -> simulationController.pauseSimulation());
    stepButton.setOnAction(e -> simulationController.step());
    extraSettingsButton.setOnAction(e -> simulationController.showParametersPopout());

    row.getChildren().addAll(playButton, speedUpButton, slowDownButton, pauseButton,
        stepButton, extraSettingsButton);
    row.setAlignment(Pos.CENTER);
    return row;
  }

  private HBox secondButtonRow() {
    HBox row = new HBox(10);

    Button exitButton = new Button(resources.getString("ExitSimulation"));
    Button showGraphButton = new Button(resources.getString("ShowGraph"));
    Button saveButton = new Button(resources.getString("SaveSim"));
    Button loadAdditionalButton = new Button(resources.getString("LoadAdditionalSimulation"));

    exitButton.setOnAction(e -> simulationController.exitSimulation());
    showGraphButton.setOnAction(e -> simulationController.showVisualization());
    saveButton.setOnAction(e -> simulationController.saveSimulationToDisk());
    loadAdditionalButton.setOnAction(e -> simulationController.openAdditionalSimulation());

    row.getChildren().addAll(exitButton, showGraphButton, saveButton, loadAdditionalButton);
    row.setAlignment(Pos.CENTER);
    return row;
  }

  private void buildScene() {
    ObservableList<Node> rootChildren = this.root.getChildren();
    rootChildren.add(this.graphicalCellGrid.getNode());

    VBox rows = new VBox(10);
    HBox rowOne = firstButtonRow();
    HBox rowTwo = secondButtonRow();

    rows.getChildren().addAll(rowTwo, rowOne);
    rows.setAlignment(Pos.CENTER);
    rows.setPrefWidth(this.WIDTH);
    rows.setTranslateY(this.HEIGHT - 80.0);

    rootChildren.add(rows);
  }
}

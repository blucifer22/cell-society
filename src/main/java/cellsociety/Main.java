package cellsociety;

import cellsociety.simulation.Simulation;
import cellsociety.simulation.SimulationManager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
  private SimulationManager manager;
  public static final int SCENE_WIDTH = 500;
  public static final int SCENE_HEIGHT = 500;
  private Simulation simulation;
  private SimulationDisplay simDisplay;
  private Scene scene;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    manager = new SimulationManager();
    simDisplay = new SimulationDisplay();
    BorderPane pane = new BorderPane();
    pane.setCenter(simDisplay);
    pane.setBottom(createButtonPane());

    scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private Pane createButtonPane() {
    HBox row = new HBox(10);
    Button loadButton = new Button("load");
    Button playButton = new Button("Play");
    Button pauseButton = new Button("Pause");
    Button stepButton = new Button("Step");
    row.getChildren().addAll(loadButton, playButton, pauseButton, stepButton);

    loadButton.setOnAction(e -> loadSimulation());
    playButton.setOnAction(e -> playSimulation());
    pauseButton.setOnAction(e -> pauseSimulation());
    stepButton.setOnAction(e -> stepSimulation());
    row.setAlignment(Pos.CENTER);
    return row;
  }

  private void loadSimulation() {
  }

  private void playSimulation() {}

  private void pauseSimulation() {}

  private void stepSimulation() {
    System.out.println("Step Simulation");
  }
}

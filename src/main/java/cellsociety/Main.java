package cellsociety;

import cellsociety.simulation.Simulation;
import cellsociety.simulation.SimulationManager;
import java.io.File;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application {
  private SimulationManager simulationManager;
  public static final int SCENE_WIDTH = 500;
  public static final int SCENE_HEIGHT = 500;
  private Simulation simulation;
  private SimulationDisplay simDisplay;
  private Scene scene;
  private Stage primaryStage;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    simulationManager = new SimulationManager();
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

  private void newSimulation(File file) {
    Simulation simulation = simulationManager.createSimulation(file);
    if (simulation != null) {
      simDisplay.setSimulation(simulation);
    } else {
		error("Invalid XML File");
    }
  }

  private void error(String error) {
    Alert a = new Alert(AlertType.ERROR, error);
    a.show();
  }

  private void loadSimulation() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Simulation");
    fileChooser
        .getExtensionFilters()
        .addAll(new ExtensionFilter("XML Files", "*.xml"), new ExtensionFilter("All Files", ".*"));
    File selectedFile = fileChooser.showOpenDialog(primaryStage);
    if (selectedFile != null) {
      newSimulation(selectedFile);
    }
  }

  private void playSimulation() {}

  private void pauseSimulation() {}

  private void stepSimulation() {
    System.out.println("Step Simulation");
  }
}

package cellsociety;

import cellsociety.simulation.SimulationManager;
import cellsociety.simulation.Simulation;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

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

  public Pane createButtonPane() {
	  HBox row = new HBox(10);
	  Button loadButton = new Button("load");
	  Button playButton = new Button("Play");
	  Button pauseButton = new Button("Pause");
	  Button stepButton = new Button("Step");
	  row.getChildren().addAll(loadButton, playButton, pauseButton, stepButton);
	  row.setAlignment(Pos.CENTER);
	  return row;
  }
}

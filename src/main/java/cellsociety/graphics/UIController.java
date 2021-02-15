package cellsociety.graphics;

import cellsociety.simulation.SimulationController;
import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UIController {
  private final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.graphics.English";
  private static final double WINDOW_WIDTH = 600;
  private static final double WINDOW_HEIGHT = 750;
  private ResourceBundle resources;

  private final Stage stage;
  private SimulationController simulationController;

  /**
   * Sole constructor for <code>UIController</code>. Called by <code>Main</code> when doing initial
   * application setup.
   *
   * @param primaryStage the JavaFX application's primary <code>Stage</code>
   */
  public UIController(Stage primaryStage, String locale) {
    this.stage = primaryStage;
    this.simulationController = new SimulationController(this);
    this.resources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
    presentLoadSimScene();
  }

  // Loads the getSimulation loading screen onto the primary stage
  private void presentLoadSimScene() {
    this.stage.setScene(new SimulationSelectionScene(this, WINDOW_WIDTH, WINDOW_HEIGHT, resources));
    this.stage.show();
  }

  /**
   * Allows instantiated <code>Scene</code>s to set the title of the application window depending on
   * the content currently being displayed to the user.
   *
   * @param title the new title text to display
   */
  public void setTitle(String title) {
    stage.setTitle("CASim v0.0" + (title == null ? "" : " > " + title));
  }

  public void exitSimulation() {
    stage.setScene(new SimulationSelectionScene(this, WINDOW_WIDTH, WINDOW_HEIGHT, resources));
  }

  /**
   * Allows instantiated <code>Scene</code>s -- namely <code>SimSelectScene</code>s -- to request to
   * start a getSimulation based on an input file.
   */
  public void loadNewSimulation() {
    simulationController.loadSimulation(600, 600);
  }

  public void showSimulation(SimulationController simulationController) {
    SimulationDisplayScene sds =
        new SimulationDisplayScene(simulationController, WINDOW_WIDTH, WINDOW_HEIGHT, resources);
    this.stage.setScene(sds);
  }

  // Opens a FileChooser window that allows the user to select the appropriate XML file
  public File selectSimulationFile() {
    FileChooser fc = new FileChooser();
    Stage s = new Stage();
    return fc.showOpenDialog(s);
  }

  public void refresh(double elapsedTime) {
    simulationController.update(elapsedTime);
  }

  /**
   * Allows <code>Scene</code>s and other controllers to notify the user about exceptions related to
   * his/her own actions (i.e. selecting a malformed XML file).
   *
   * @param e the <code>Exception</code> to notify the user about
   */
  public void notifyUserOfException(Exception e) {
    Alert a = new Alert(AlertType.ERROR, e.getMessage());
    e.printStackTrace();
    a.show();
  }
}

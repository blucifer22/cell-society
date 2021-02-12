package cellsociety.graphics;

import cellsociety.simulation.SimulationManager;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UIController {
  private static final double WINDOW_WIDTH = 600;
  private static final double WINDOW_HEIGHT = 750;

  private final Stage stage;
  private SimulationManager simulationManager;

  /**
   * Sole constructor for <code>UIController</code>. Called by <code>Main</code> when doing
   * initial application setup.
   *
   * @param primaryStage the JavaFX application's primary <code>Stage</code>
   */
  public UIController(Stage primaryStage) {
    this.stage = primaryStage;
    presentLoadSimScene();
  }

  // Loads the simulation loading screen onto the primary stage
  private void presentLoadSimScene() {
    this.stage.setScene(new SimulationSelectionScene(this, WINDOW_WIDTH, WINDOW_HEIGHT));
  }

  /**
   * Allows instantiated <code>Scene</code>s to set the title of the application window depending
   * on the content currently being displayed to the user.
   *
   * @param title the new title text to display
   */
  public void setTitle(String title) {
    stage.setTitle("CASim v0.0"+(title == null ? "" : " > "+title));
  }

  /**
   * Allows instantiated <code>Scene</code>s -- namely <code>SimSelectScene</code>s -- to request
   * to start a simulation based on an input file.
   */
  public void initializeSimulation() {
    File simulationConfigurationFile = selectSimulationFile();
    if(simulationConfigurationFile == null) {
      return;
    }
    try {
      this.simulationManager = new SimulationManager(simulationConfigurationFile);
      showSimulation(simulationManager);
    } catch (Exception e) {
      error(e);
    }
  }

  private void showSimulation(SimulationManager simulationManager) {
    SimulationDisplayScene sds = new SimulationDisplayScene(this, simulationManager.getGraphicalCells(),
        WINDOW_WIDTH, WINDOW_HEIGHT);
    this.stage.setScene(sds);
  }

  // Opens a FileChooser window that allows the user to select the appropriate XML file
  private File selectSimulationFile() {
    FileChooser fc = new FileChooser();
    Stage s = new Stage();
    return fc.showOpenDialog(s);
  }

  public void refresh(double elapsedTime) {

  }

  /**
   * Allows <code>Scene</code>s and other controllers to notify the user about exceptions related
   * to his/her own actions (i.e. selecting a malformed XML file).
   *
   * @param e the <code>Exception</code> to notify the user about
   */
  public void error(Exception e) {
    Alert a = new Alert(AlertType.ERROR, e.getMessage());
  }
}

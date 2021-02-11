package cellsociety.graphics;

import cellsociety.graphics.scenes.SimSelectScene;
import cellsociety.simulation.SimulationManager;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The main user interface controller class.
 *
 * @author David Coffman
 */
public class UIController {
  private static final double WINDOW_WIDTH = 600;
  private static final double WINDOW_HEIGHT = 750;
  private final Stage primaryStage;

  /**
   * Sole constructor for <code>UIController</code>. Called by <code>Main</code> when doing
   * initial application setup.
   *
   * @param primaryStage the JavaFX application's primary <code>Stage</code>
   */
  public UIController(Stage primaryStage) {
    this.primaryStage = primaryStage;
    primaryStage.setScene(new SimSelectScene(this, WINDOW_WIDTH, WINDOW_HEIGHT));
    primaryStage.show();
  }

  /**
   * Allows instantiated <code>Scene</code>s to set the title of the application window depending
   * on the content currently being displayed to the user.
   *
   * @param title the new title text to display
   */
  public void setTitle(String title) {
    primaryStage.setTitle("CASim v0.0"+(title == null ? "" : " > "+title));
  }

  /**
   * Allows instantiated <code>Scene</code>s -- namely <code>SimSelectScene</code>s -- to request
   * to start a simulation based on an input file.
   */
  public void initializeAndConfigureSimulation() {
    File simulationConfigurationFile = selectSimulationFile();
    if(simulationConfigurationFile == null) {
      return;
    }
    try {
      SimulationManager sm = new SimulationManager(simulationConfigurationFile);
      // kick the XMLParser over to the Simulation controller; start simulation
      /*
      ENTRY POINT FOR SIMULATION!
      NOTE: UIController SHOULD ONLY EVER BE EXPOSED TO THE SIMULATION CONTROLLER (WHATEVER
      WE'RE CALLING IT)
       */
    } catch (Exception e) {
      exceptionAlert(e);
    }
  }

  // Opens a FileChooser window that allows the user to select the appropriate XML file
  private File selectSimulationFile() {
    FileChooser fc = new FileChooser();
    Stage s = new Stage();
    return fc.showOpenDialog(s);
  }

  /**
   * Allows <code>Scene</code>s and other controllers to notify the user about exceptions related
   * to his/her own actions (i.e. selecting a malformed XML file).
   *
   * @param e the <code>Exception</code> to notify the user about
   */
  public void exceptionAlert(Exception e) {
    Alert a = new Alert(AlertType.ERROR, e.getMessage());
    a.show();
  }
}

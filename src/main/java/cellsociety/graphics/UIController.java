package cellsociety.graphics;

import java.io.File;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Application UI controller class.
 *
 * @author David Coffman
 */
public class UIController {

  private static final double WINDOW_WIDTH = 600;
  public static final String RESOURCE_PATH = "cellsociety.graphics.";
  private static final double WINDOW_HEIGHT = 750;
  private final double frameDelay;
  private final String locale;
  private ResourceBundle languageResources;
  private final Stage stage;
  private final SimulationController simulationController;
  private Theme theme;

  /**
   * Sole constructor for <code>UIController</code>. Called by <code>Main</code> when doing initial
   * application setup.
   *
   * @param primaryStage the JavaFX application's primary <code>Stage</code>
   */
  public UIController(Stage primaryStage, double frameDelay, String locale) {
    this.stage = primaryStage;
    this.languageResources = ResourceBundle.getBundle(RESOURCE_PATH + Language.ENGLISH);
    this.simulationController = new SimulationController(this, languageResources);
    this.stage.setResizable(false);
    this.frameDelay = frameDelay;
    this.locale = locale;
    this.theme = Theme.DEFAULT;
    presentLoadSimScene();
    beginUpdates();
  }

  // Sets the application's language setting
  protected void setLanguage(Language lang) {
    this.languageResources = ResourceBundle.getBundle(RESOURCE_PATH + lang);
  }

  // Sets the application's theme setting
  protected void setTheme(Theme theme) {
    this.theme = theme;
  }

  // Begins updates to components instantiated by the UIController
  private void beginUpdates() {
    KeyFrame frame = new KeyFrame(Duration.seconds(frameDelay), e -> refresh(frameDelay));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  // Loads the getSimulation loading screen onto the primary stage
  private void presentLoadSimScene() {
    this.stage.setScene(new SimulationSelectionScene(this, WINDOW_WIDTH, WINDOW_HEIGHT,
        languageResources));
    this.stage.show();
  }

  /**
   * Allows instantiated <code>Scene</code>s to set the title of the application window depending on
   * the content currently being displayed to the user.
   *
   * @param title the new title text to display
   */
  protected void setTitle(String title) {
    stage.setTitle(title);
  }

  /**
   * Exits the current simulation by first pausing the current simulation, then destroying the
   * currently-presented {@link SimulationDisplayScene} and replacing it with a
   * {@link SimulationSelectionScene}.
   */
  protected void exitSimulation() {
    simulationController.pauseSimulation();
    stage.setScene(new SimulationSelectionScene(this, WINDOW_WIDTH, WINDOW_HEIGHT,
        languageResources));
  }

  /**
   * Allows instantiated <code>Scene</code>s -- namely <code>SimSelectScene</code>s -- to request to
   * start a getSimulation based on an input file.
   */
  protected void loadNewSimulation() {
    simulationController.loadSimulation(600, 600);
  }

  /**
   * Shows a simulation to the user by instantiating a new {@link SimulationDisplayScene} and
   * setting it as the primary stage's active scene.
   *
   * @param simulationController
   */
  protected void showSimulation(SimulationController simulationController) {
    SimulationDisplayScene sds =
        new SimulationDisplayScene(this.simulationController, WINDOW_WIDTH, WINDOW_HEIGHT,
            this.languageResources, this.theme);
    this.stage.setScene(sds);
  }

  /**
   * Opens a FileChooser window that allows the user to select the appropriate XML file.
   *
   * @return the {@link File} selected by the user
   */
  protected File fileFromOpenDialog() {
    FileChooser fc = new FileChooser();
    Stage s = new Stage();
    return fc.showOpenDialog(s);
  }

  /**
   * Opens a FileChooser window that allows the user to select the appropriate location for
   * saving a file.
   *
   * @return the {@link File} selected by the user
   */
  protected File fileFromSaveDialog() {
    FileChooser fc = new FileChooser();
    Stage s = new Stage();
    return fc.showSaveDialog(s);
  }

  // Sends a refresh notice to the SimulationController.
  private void refresh(double elapsedTime) {
    simulationController.update(elapsedTime);
  }

  /**
   * Allows <code>Scene</code>s and other controllers to notify the user about exceptions related to
   * his/her own actions (i.e. selecting a malformed XML file).
   *
   * @param e the <code>Exception</code> to notify the user about
   */
  protected void notifyUserOfException(Exception e) {
    Alert a = new Alert(AlertType.ERROR, e.getMessage());
    e.printStackTrace();
    a.show();
  }

  /**
   * Instantiates a new <code>UIController</code>, creates a new stage, and shows that stage.
   * Effectively opens a second instance of the application. Used to show multiple simulations
   * concurrently.
   */
  protected void createNewControlledStage() {
    Stage s = new Stage();
    new UIController(s, frameDelay, locale);
    s.show();
  }

  /**
   * Enumerates all languages usable in the application.
   */
  public enum Language {
    ENGLISH("English"),
    FRENCH("French"),
    POLISH("Polish"),
    SPANISH("Spanish");

    private final String bundleName;

    Language(String s) {
      this.bundleName = s;
    }

    @Override
    public String toString() {
      return this.bundleName;
    }
  }

  /**
   * Enumerates all themes usable in the application.
   */
  public enum Theme {
    DEFAULT("Default"),
    FIRE_SPREAD("Fire"),
    DARK("Dark");

    private final String bundleName;

    Theme(String s) {
      this.bundleName = s;
    }

    @Override
    public String toString() {
      return this.bundleName;
    }
  }
}

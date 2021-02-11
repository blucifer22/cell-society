package cellsociety.graphics;

import cellsociety.graphics.scenes.SimSelectScene;
import cellsociety.util.XMLParser;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UIController {
  private static final double WINDOW_WIDTH = 600;
  private static final double WINDOW_HEIGHT = 750;
  private Stage primaryStage;

  public UIController(Stage primaryStage) {
    this.primaryStage = primaryStage;
    primaryStage.setScene(new SimSelectScene(this, WINDOW_WIDTH, WINDOW_HEIGHT));
    primaryStage.show();
  }

  public void setTitle(String title) {
    primaryStage.setTitle("CASim v0.0"+(title == null ? "" : " > "+title));
  }

  public void selectSimulationFile() {
    final FileChooser fc = new FileChooser();
    Stage s = new Stage();
    File sf = fc.showOpenDialog(s);
    try {
      XMLParser p = new XMLParser(sf);
    } catch (Exception e) {
      exceptionAlert(e);
    }
  }

  public void exceptionAlert(Exception e) {
    Alert a = new Alert(AlertType.ERROR, e.getMessage());
    a.show();
  }
}

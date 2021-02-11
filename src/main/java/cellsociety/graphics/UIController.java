package cellsociety.graphics;

import cellsociety.graphics.scenes.SimSelectScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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

}

package cellsociety.graphics;

import cellsociety.graphics.scenes.SimSelectScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UIMain {
  private static final double WINDOW_WIDTH = 600;
  private static final double WINDOW_HEIGHT = 750;
  private Stage primaryStage;

  public UIMain(Stage primaryStage) {
    this.primaryStage = primaryStage;
    setTitle("Launch");
    primaryStage.setScene(new SimSelectScene(WINDOW_WIDTH, WINDOW_HEIGHT));
    primaryStage.show();
  }

  protected void setTitle(String title) {
    primaryStage.setTitle("CASim v0.0"+(title == null ? "" : " > "+title));
  }

}

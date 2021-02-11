package cellsociety;

import cellsociety.graphics.UIMain;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
  private UIMain mainInterface;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    mainInterface = new UIMain(primaryStage);
  }
}

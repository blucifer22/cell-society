package cellsociety;

import cellsociety.graphics.UIController;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
  private UIController mainInterface;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    mainInterface = new UIController(primaryStage);
  }
}

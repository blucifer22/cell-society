package cellsociety.graphics;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UIMain {
  private Stage primaryStage;

  public UIMain(Stage primaryStage) {
    this.primaryStage = primaryStage;

    setTitle("Launch");

    Button btn = new Button();
    btn.setText("Say 'Hello World'");
    btn.setOnAction(event -> System.out.println("Hello World!"));

    StackPane root = new StackPane();
    root.getChildren().add(btn);
    primaryStage.setScene(new Scene(root, 600, 750));
    primaryStage.show();
  }

  protected void setTitle(String title) {
    primaryStage.setTitle("CASim v0.0"+(title == null ? "" : " > "+title));
  }

}

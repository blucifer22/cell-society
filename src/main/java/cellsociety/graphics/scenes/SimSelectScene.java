package cellsociety.graphics.scenes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class SimSelectScene extends Scene {
  private final Group root;

  public SimSelectScene(double width, double height) {
    super(new Group(), width, height);
    this.root = (Group) this.getRoot();
    configureScene();
  }

  private void configureScene() {
    Button btn = new Button();
    btn.setText("Say 'Hello World'");
    btn.setOnAction(event -> System.out.println("Hello World!"));
    root.getChildren().add(btn);
  }
}

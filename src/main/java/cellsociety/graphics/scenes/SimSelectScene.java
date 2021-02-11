package cellsociety.graphics.scenes;

import cellsociety.graphics.UIController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class SimSelectScene extends Scene {
  private final UIController controller;
  private final Group root;
  private final double width;
  private final double height;

  public SimSelectScene(UIController controller, double width, double height) {
    super(new Group(), width, height);
    this.root = (Group) this.getRoot();
    this.width = width;
    this.height = height;
    this.controller = controller;
    configureScene();
  }

  private void configureScene() {
    controller.setTitle("Launch");
    StackPane sp = new StackPane();

    Button fileLoadButton = new Button();
    fileLoadButton.setText("Load Simulation XML");
    fileLoadButton.setOnAction(event -> controller.selectSimulationFile());
    sp.getChildren().add(fileLoadButton);
    sp.setPrefWidth(width);
    sp.setPrefHeight(height);
    renderNode(sp);
  }

  private void renderNode(Node n) {
    root.getChildren().add(n);
  }
}

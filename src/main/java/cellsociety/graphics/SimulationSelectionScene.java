package cellsociety.graphics;

import cellsociety.graphics.UIController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * <code>SimSelectScene</code> is the initial scene inside the application, instantiated by the
 * <code>UIController</code>.
 *
 * @author David Coffman
 */
public class SimSelectScene extends Scene {
  private final UIController controller;
  private final Group root;
  private final double width;
  private final double height;

  /**
   * Sole constructor for <code>SimSelectScene</code>. Called by <code>UIController</code> to
   * create the first scene in the application (for loading an XML configuration).
   *
   * @param controller the UIController to link the <code>SimSelectScene</code> to
   * @param width the width of the <code>SimSelectScene</code>
   * @param height the height of the <code>SimSelectScene</code>
   */
  public SimSelectScene(UIController controller, double width, double height) {
    super(new Group(), width, height);
    this.root = (Group) this.getRoot();
    this.width = width;
    this.height = height;
    this.controller = controller;
    configureScene();
  }

  // Configures the scene; sets title of window and configures the "load XML" button
  private void configureScene() {
    controller.setTitle("Launch");
    StackPane sp = new StackPane();

    Button fileLoadButton = new Button();
    fileLoadButton.setText("Load Simulation XML");
    fileLoadButton.setOnAction(event -> controller.initializeAndConfigureSimulation());
    sp.getChildren().add(fileLoadButton);
    sp.setPrefWidth(width);
    sp.setPrefHeight(height);
    renderNode(sp);
  }

  // Adds the Node parameter to the root Group of the Scene
  private void renderNode(Node n) {
    root.getChildren().add(n);
  }
}

package cellsociety.graphics;

import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * <code>SimulationSelectScene</code> is the initial scene inside the application, instantiated by
 * the <code>UIController</code>.
 *
 * @author David Coffman
 */
public class SimulationSelectionScene extends Scene {

  private final UIController uiController;
  private final Group root;
  private final double width;
  private final double height;
  private ResourceBundle resources;

  /**
   * Sole constructor for <code>SimulationSelectScene</code>. Called by <code>UIController</code> to
   * create the first scene in the application (for loading an XML configuration).
   *
   * @param uiController the UIController to link the <code>SimulationSelectScene</code> to
   * @param width        the width of the <code>SimulationSelectScene</code>
   * @param height       the height of the <code>SimulationSelectScene</code>
   */
  public SimulationSelectionScene(UIController uiController, double width, double height,
      ResourceBundle resources) {
    super(new Group(), width, height);
    this.root = (Group) this.getRoot();
    this.width = width;
    this.height = height;
    this.resources = resources;
    this.uiController = uiController;
    configureScene();
  }

  // Configures the scene; sets title of window and configures the "load XML" button
  private void configureScene() {
    uiController.setTitle(resources.getString("Launch"));
    StackPane sp = new StackPane();

    Button fileLoadButton = new Button();
    fileLoadButton.setText(resources.getString("LoadSimulationXML"));
    fileLoadButton.setOnAction(event -> uiController.loadNewSimulation());
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

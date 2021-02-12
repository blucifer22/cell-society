package cellsociety.graphics;

import cellsociety.simulation.Simulation;
import java.io.File;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author David Coffman
 */

public class SimulationDisplayScene extends Scene {
  private final UIController controller;

  public SimulationDisplayScene(UIController controller, List<GraphicalCell> graphicalCells,
      double width, double height) {
    super(new Group(), width, height);
    this.controller = controller;
    ObservableList<Node> rootChildren = ((Group) this.getRoot()).getChildren();
    for(GraphicalCell g: graphicalCells) {
      rootChildren.add(g.getNode());
    }
  }

//  private Pane createButtonPane() {
//    HBox row = new HBox(10);
//    Button loadButton = new Button("Load New Simulation");
//    Button playButton = new Button("Play");
//    Button pauseButton = new Button("Pause");
//    Button stepButton = new Button("Step");
//    row.getChildren().addAll(loadButton, playButton, pauseButton, stepButton);
//
//    loadButton.setOnAction(e -> loadSimulation());
//    playButton.setOnAction(e -> playSimulation());
//    pauseButton.setOnAction(e -> pauseSimulation());
//    stepButton.setOnAction(e -> stepSimulation());
//    row.setAlignment(Pos.CENTER);
//    return row;
//  }

//  private void loadSimulation() {
//    FileChooser fileChooser = new FileChooser();
//    fileChooser.setTitle("Select Simulation");
//    fileChooser
//        .getExtensionFilters()
//        .addAll(new ExtensionFilter("XML Files", "*.xml"), new ExtensionFilter("All Files", ".*"));
//    File selectedFile = fileChooser.showOpenDialog(primaryStage);
//    if (selectedFile != null) {
//      newSimulation(selectedFile);
//    }
//  }
}

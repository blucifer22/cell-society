package cellsociety.graphics.scenes;

import cellsociety.graphics.GraphicalCellGrid;
import cellsociety.graphics.UIController;
import cellsociety.simulation.Cell;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;

public class SimDisplayScene extends Scene {
  private final UIController controller;
  private final Group root;
  private final double width;
  private final double height;

  /**
   * Sole constructor for <code>SimDisplayScene</code>. Called by <code>UIController</code> to
   * create the JavaFX elements responsible for rendering the simulation.
   *
   * @param controller the UIController to link the <code>SimDisplayScene</code> to
   * @param width the width of the <code>SimDisplayScene</code>
   * @param height the height of the <code>SimDisplayScene</code>
   */
  public SimDisplayScene(UIController controller, List<List<Cell>> simulationCells,
      Map<Integer, Paint> colorMap, double width, double height) {
    super(new Group(), width, height);
    this.root = (Group) this.getRoot();
    this.width = width;
    this.height = height;
    this.controller = controller;
    buildGraphicalCellGrid(simulationCells, colorMap);
  }

  private void buildGraphicalCellGrid(List<List<Cell>> simulationCells,
      Map<Integer, Paint> colorMap) {
    GraphicalCellGrid gcGrid = new GraphicalCellGrid(simulationCells, Math.min(width, height),
        Math.min(width, height), colorMap);
    renderNode(gcGrid.getNode());
  }

  private void renderNode(Node n) {
    this.root.getChildren().add(n);
  }
}

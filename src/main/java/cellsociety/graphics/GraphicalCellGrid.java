package cellsociety.graphics;

import cellsociety.graphics.cells.GraphicalCell;
import cellsociety.graphics.cells.HexGraphicalCell;
import cellsociety.graphics.cells.RectangularGraphicalCell;
import cellsociety.simulation.Cell;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;

/**
 * A Grid that configures the visual cells into a grid pattern.
 *
 * @author David Coffman
 */
public class GraphicalCellGrid {

  public enum GridStyle {
    RECTANGLE, HEX, TRIANGLE
  }

  private final List<GraphicalCell> graphicalCells;
  private final Group root;

  public GraphicalCellGrid(GridStyle gs, List<Cell> cells, Map<Integer, Paint> paintMap, double width,
      double height, int numRows, int numCols) {
    this.root = new Group();
    this.graphicalCells = new ArrayList<>();

    assert cells.size() == numRows * numCols;

    double cellWidth = width / numCols;
    double cellHeight = height / numRows;

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        Cell c = cells.get(i * numCols + j);
        GraphicalCell gc = null;

        switch(gs) {
          case HEX -> gc = new HexGraphicalCell(c, paintMap, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
          case TRIANGLE -> {assert false;}
          default -> gc = new RectangularGraphicalCell(c, paintMap, j * cellWidth,
              i * cellHeight, cellWidth, cellHeight);
        }

        gc.applyTesselationTransform(j, i);
        this.graphicalCells.add(gc);
        renderNode(gc.getNode());
      }
    }
  }

  public GraphicalCellGrid(List<Cell> cells, Map<Integer, Paint> paintMap,
      double width, double height, int numRows, int numCols) {
    this(GridStyle.RECTANGLE, cells, paintMap, width, height, numRows, numCols);
  }

  public void update() {
    for (GraphicalCell c : graphicalCells) {
      c.update();
    }
  }

  private void renderNode(Node n) {
    root.getChildren().add(n);
  }

  public Node getNode() {
    return this.root;
  }
}

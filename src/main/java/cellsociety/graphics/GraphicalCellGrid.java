package cellsociety.graphics;

import cellsociety.graphics.cells.GraphicalCell;
import cellsociety.graphics.cells.HexGraphicalCell;
import cellsociety.graphics.cells.RectangularGraphicalCell;
import cellsociety.graphics.cells.TriangularGraphicalCell;
import cellsociety.simulation.Cell;
import cellsociety.util.CellShape;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Affine;

/**
 * A Grid that configures the visual cells into a grid pattern.
 *
 * @author David Coffman
 */
public class GraphicalCellGrid {

  private final List<GraphicalCell> graphicalCells;
  private final Group root;

  public GraphicalCellGrid(CellShape cellShape, List<Cell> cells, Map<Integer, Paint> paintMap, double width,
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

        switch(cellShape) {
          case HEX -> gc = new HexGraphicalCell(c, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
          case TRIANGLE -> gc = new TriangularGraphicalCell(c, j*cellWidth, i*cellHeight,
              cellWidth, cellHeight);
          default -> gc = new RectangularGraphicalCell(c, j * cellWidth,
              i * cellHeight, cellWidth, cellHeight);
        }

        gc.applyTesselationTransform(j, i);
        this.graphicalCells.add(gc);
        renderNode(gc.getNode());
      }
    }
    centerAndScaleGrid(width, height);
  }

  private void centerAndScaleGrid(double width, double height) {
    double widthRatio = width/root.getBoundsInParent().getWidth();
    double heightRatio = height/root.getBoundsInParent().getHeight();
    double scaleRatio = Math.min(widthRatio, heightRatio);

    Affine scale = new Affine();
    scale.appendScale(scaleRatio, scaleRatio, 0, 0);
    this.root.getTransforms().addAll(scale);

    double adjWidth = root.getBoundsInParent().getWidth();
    double adjHeight = root.getBoundsInParent().getHeight();
    Affine translate = new Affine();
    translate.appendTranslation((width - adjWidth)/2.0, (height-adjHeight)/2.0);
    this.root.getTransforms().addAll(translate);
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

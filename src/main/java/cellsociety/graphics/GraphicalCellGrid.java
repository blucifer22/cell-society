package cellsociety.graphics;

import cellsociety.graphics.cells.GraphicalCell;
import cellsociety.graphics.cells.HexGraphicalCell;
import cellsociety.graphics.cells.RectangularGraphicalCell;
import cellsociety.graphics.cells.TriangularGraphicalCell;
import cellsociety.simulation.Cell;
import cellsociety.util.SimulationConfiguration.CellShape;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Affine;

/**
 * A class that configures {@link GraphicalCell}s into a grid pattern.
 * <p>
 * Usage (from {@link SimulationController}):
 * <code>
 * this.graphicalCellGrid = new GraphicalCellGrid(simulation.getCellShape(), simulation.getCells(),
 * displayWidth, displayHeight, simulation.getNumRows(), simulation.getNumCols());
 * graphicalCellGrid.update(); uiController.showSimulation(this);
 * </code>
 * <p>
 * To summarize, a <code>GraphicalCellGrid</code> should be instantiated by a {@link
 * SimulationController} using the appropriate geometric parameters and model cell list, then
 * updated (to ensure that each {@link GraphicalCell} has the correct starting color). Rendering
 * typically takes place in a {@link SimulationDisplayScene}, where the
 * <code>GraphicalCellGrid</code>'s rendering node can be inserted into a scene graph, though
 * this could technically be done with any scene graph using {@link GraphicalCellGrid#getNode()}.
 *
 * @author David Coffman
 */
public class GraphicalCellGrid {

  private final List<GraphicalCell> graphicalCells;
  private final Group root;

  /**
   * Sole constructor of <code>GraphicalCellGrid</code>s. Takes geometric parameters in order to lay
   * out any of the {@link GraphicalCell} subclasses into a grid structure.
   *
   * @param cellShape the {@link CellShape} to use when constructing the grid
   * @param cells     the model {@link Cell}s to render
   * @param width     the pixel width of the grid
   * @param height    the pixel height of the grid
   * @param numRows   the cell height of the grid
   * @param numCols   the cell width of the grid
   */
  public GraphicalCellGrid(CellShape cellShape, List<Cell> cells, double width, double height,
      int numRows, int numCols) {
    this.root = new Group();
    this.graphicalCells = new ArrayList<>();

    assert cells.size() == numRows * numCols;

    double cellWidth = width / numCols;
    double cellHeight = height / numRows;

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        Cell c = cells.get(i * numCols + j);
        GraphicalCell gc = null;

        switch (cellShape) {
          case HEXAGON -> gc = new HexGraphicalCell(c, j * cellWidth, i * cellHeight, cellWidth,
              cellHeight);
          case TRIANGLE -> gc = new TriangularGraphicalCell(c, j * cellWidth, i * cellHeight,
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

  // Centers the grid and re-scales it so it fits within the width and height dimensions. Would
  // be critical for an infinite grid implementation, but also useful for GraphicalCell
  // subclasses where dimensions are difficult to calculate and auto-scaling is much simpler.
  private void centerAndScaleGrid(double width, double height) {
    double widthRatio = width / root.getBoundsInParent().getWidth();
    double heightRatio = height / root.getBoundsInParent().getHeight();
    double scaleRatio = Math.min(widthRatio, heightRatio);

    Affine scale = new Affine();
    scale.appendScale(scaleRatio, scaleRatio, 0, 0);
    this.root.getTransforms().addAll(scale);

    double adjWidth = root.getBoundsInParent().getWidth();
    double adjHeight = root.getBoundsInParent().getHeight();
    Affine translate = new Affine();
    translate.appendTranslation((width - adjWidth) / 2.0, (height - adjHeight) / 2.0);
    this.root.getTransforms().addAll(translate);
  }

  /**
   * Updates each {@link GraphicalCell} in the <code>GraphicalCellGrid</code>.
   */
  public void update() {
    for (GraphicalCell c : graphicalCells) {
      c.update();
    }
  }

  // Utility method to make the syntax for adding a Node to the scene graph cleaner
  private void renderNode(Node n) {
    root.getChildren().add(n);
  }

  /**
   * Exposes the <code>GraphicalCellGrid</code>'s rendering node so it can be rendered by a
   * <code>Scene</code>.
   *
   * @return the <code>GraphicalCellGrid</code>'s rendering node
   */
  public Node getNode() {
    return this.root;
  }
}

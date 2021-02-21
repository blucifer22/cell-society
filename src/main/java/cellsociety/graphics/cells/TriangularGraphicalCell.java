package cellsociety.graphics.cells;

import cellsociety.simulation.Cell;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;

public class TriangularGraphicalCell extends GraphicalCell {

  /**
   * Sole constructor for <code>GraphicalCell</code>. Takes a model <code>Cell</code> to render, a
   * <code>Map</code> indicating the appropriate <code>Paint</code> for each state, and the location
   * and size of the <code>GraphicalCell</code>.
   *  @param simulationCell the model <code>Cell</code> to render
   * @param x              the x-position of the <code>GraphicalCell</code>'s top left corner
   * @param y              the y-position of the <code>GraphicalCell</code>'s top left corner
   * @param width          the <code>GraphicalCell</code>'s width
   * @param height         the <code>GraphicalCell</code>'s height
   */
  public TriangularGraphicalCell(Cell simulationCell, double x, double y, double width,
      double height) {
    super(simulationCell, x, y, new Polygon());
    Polygon renderingShape = (Polygon) this.getNode();
    renderingShape.getPoints().addAll(0.0, 0.0, width, 0.0, width/2.0, height);
    renderingShape.setTranslateX(x);
    renderingShape.setTranslateY(y);
  }

  @Override
  public void applyTesselationTransform(int gridX, int gridY) {
    Affine a = new Affine();
    if((gridX + gridY) % 2 == 1) {
      a.appendScale(1, -1);
      a.appendTranslation(0, -this.getNode().getBoundsInParent().getHeight());
    }
    a.appendTranslation(-gridX/2.0*this.getNode().getBoundsInParent().getWidth(), 0);
    this.getNode().getTransforms().addAll(a);
  }
}

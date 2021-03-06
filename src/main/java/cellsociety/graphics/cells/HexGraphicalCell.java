package cellsociety.graphics.cells;

import cellsociety.simulation.Cell;
import javafx.geometry.Bounds;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;

/**
 * A wrapper for model {@link Cell}s that allows for rendering. This implementation uses {@link
 * Polygon}s configured as hexagons for rendering. Used after construction by calling {@link
 * HexGraphicalCell#update()} as appropriate to a given use case.
 *
 * @author David Coffman
 */
public class HexGraphicalCell extends GraphicalCell {

  /**
   * Sole constructor for <code>HexGraphicalCell</code>. Takes a model <code>Cell</code> to render
   * and appropriate positioning and sizing information about the hexagonal rendering shape.
   *
   * @param simulationCell the model <code>Cell</code> to render
   * @param x              the x-position of the <code>GraphicalCell</code>'s top left corner
   * @param y              the y-position of the <code>GraphicalCell</code>'s top left corner
   * @param width          the <code>GraphicalCell</code>'s width
   * @param height         the <code>GraphicalCell</code>'s height
   */
  public HexGraphicalCell(Cell simulationCell, double x, double y, double width, double height) {
    super(simulationCell, new Polygon());
    Polygon renderingShape = (Polygon) this.getNode();
    renderingShape.getPoints()
        .setAll(0.0, height / 4.0, 0.0, 3 * height / 4, width / 2, height, width,
            3 * height / 4, width, height / 4, width / 2, 0.0);
    renderingShape.setTranslateX(x);
    renderingShape.setTranslateY(y);
  }

  @Override
  public void applyTesselationTransform(int gridX, int gridY) {
    Affine transform = new Affine();
    Bounds b = this.getNode().getBoundsInParent();
    if (gridY % 2 == 1) {
      transform.appendTranslation(b.getWidth() * 0.5, 0);
    }
    transform.appendTranslation(0, -gridY * b.getHeight() / 4.0);
    this.getNode().getTransforms().addAll(transform);
  }
}

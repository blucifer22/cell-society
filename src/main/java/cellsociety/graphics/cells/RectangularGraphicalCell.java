package cellsociety.graphics.cells;

import cellsociety.simulation.Cell;
import javafx.scene.shape.Rectangle;


/**
 * A wrapper for model {@link Cell}s that allows for rendering. This implementation uses
 * {@link Rectangle}s for rendering. Used after construction by calling
 * {@link RectangularGraphicalCell#update()} as appropriate to a given use case.
 *
 * @author David Coffman
 */
public class RectangularGraphicalCell extends GraphicalCell {

  /**
   * Sole constructor for <code>RectangularGraphicalCell</code>. Takes a model <code>Cell</code> to
   * render and appropriate positioning and sizing information about the rectangular rendering
   * shape.
   *
   * @param simulationCell the model <code>Cell</code> to render
   * @param x              the x-position of the <code>GraphicalCell</code>'s top left corner
   * @param y              the y-position of the <code>GraphicalCell</code>'s top left corner
   * @param width          the <code>GraphicalCell</code>'s width
   * @param height         the <code>GraphicalCell</code>'s height
   */
  public RectangularGraphicalCell(Cell simulationCell, double x, double y, double width,
      double height) {
    super(simulationCell, new Rectangle());
    Rectangle rect = (Rectangle) super.getNode();
    rect.setWidth(width);
    rect.setHeight(height);
    rect.setX(x);
    rect.setY(y);
  }
}

package cellsociety.graphics.cells;

import cellsociety.simulation.Cell;
import javafx.scene.shape.Rectangle;


/**
 * A wrapper for model <code>Cell</code>s that allows for rendering. Instantiated by a
 * <code>GraphicalCellGrid</code>.
 *
 * @author David Coffman
 */
public class RectangularGraphicalCell extends GraphicalCell {

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
  public RectangularGraphicalCell(Cell simulationCell, double x, double y, double width,
      double height) {
    super(simulationCell, x, y, new Rectangle());
    Rectangle rect = (Rectangle) super.getNode();
    rect.setWidth(width);
    rect.setHeight(height);
    rect.setX(x);
    rect.setY(y);
  }
}

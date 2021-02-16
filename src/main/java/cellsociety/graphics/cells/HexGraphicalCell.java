package cellsociety.graphics.cells;

import cellsociety.simulation.Cell;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;


/**
 * A wrapper for model <code>Cell</code>s that allows for rendering. Instantiated by a
 * <code>GraphicalCellGrid</code>.
 *
 * @author David Coffman
 */
public class HexGraphicalCell extends GraphicalCell {

  private static final double MARGIN = 1.0;

  /**
   * Sole constructor for <code>GraphicalCell</code>. Takes a model <code>Cell</code> to render, a
   * <code>Map</code> indicating the appropriate <code>Paint</code> for each state, and the location
   * and size of the <code>GraphicalCell</code>.
   *
   * @param simulationCell the model <code>Cell</code> to render
   * @param colorMap       the <code>Map</code> indicating the appearances of <code>Cell</code>s in
   *                       different states
   * @param x              the x-position of the <code>GraphicalCell</code>'s top left corner
   * @param y              the y-position of the <code>GraphicalCell</code>'s top left corner
   * @param width          the <code>GraphicalCell</code>'s width
   * @param height         the <code>GraphicalCell</code>'s height
   */
  public HexGraphicalCell(Cell simulationCell, Map<Integer, Paint> colorMap, double x, double y,
      double width, double height) {
    super(simulationCell, colorMap, x, y, new Polygon());
    Polygon renderingShape = (Polygon) this.getNode();
    renderingShape.getPoints().setAll(0.0, height/4.0, 0.0, 3*height/4, width/2, height, width,
        3*height/4, width, height/4, width/2, 0.0);
    renderingShape.setTranslateX(x);
    renderingShape.setTranslateY(y);
  }
}

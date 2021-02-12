package cellsociety.graphics;

import cellsociety.simulation.Cell;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * A wrapper for model <code>Cell</code>s that allows for rendering. Instantiated by a <code>
 * GraphicalCellGrid</code>.
 *
 * @author David Coffman
 */
public class GraphicalCell extends Rectangle {
  private static final double MARGIN = 2.0;
  private final Cell simCell;
  private final Map<Integer, Paint> colorMap;

  /**
   * Sole constructor for <code>GraphicalCell</code>. Takes a model <code>Cell</code> to render, a
   * <code>Map</code> indicating the appropriate <code>Paint</code> for each state, and the location
   * and size of the <code>GraphicalCell</code>.
   *
   * @param simulationCell the model <code>Cell</code> to render
   * @param colorMap the <code>Map</code> indicating the appearances of <code>Cell</code>s in
   *     different states
   * @param x the x-position of the <code>GraphicalCell</code>'s top left corner
   * @param y the y-position of the <code>GraphicalCell</code>'s top left corner
   * @param width the <code>GraphicalCell</code>'s width
   * @param height the <code>GraphicalCell</code>'s height
   */
  public GraphicalCell(
      Cell simulationCell,
      Map<Integer, Paint> colorMap,
      double x,
      double y,
      double width,
      double height) {

    super(x + MARGIN, y + MARGIN, width - 2.0 * MARGIN, height - 2.0 * MARGIN);
    this.simCell = simulationCell;
    this.colorMap = colorMap;
  }

  /**
   * Called by a controller after cells have finished committing their <code>State</code> updates to
   * refresh their graphical appearance.
   */
  public void update() {
    // change once an API for getting the cell's integer-encoded state exists
    this.setFill(colorMap.getOrDefault(0, Color.BLACK));
  }
}

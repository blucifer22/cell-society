package cellsociety;

import cellsociety.simulation.Cell;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A wrapper for model <code>Cell</code>s that allows for rendering. Instantiated by a <code>
 * GraphicalCellGrid</code>.
 *
 * @author David Coffman
 */
public class GraphicalCell extends Rectangle {
  private static final double MARGIN = 2.0;
  public static int CELL_SIZE = 2;
  private final Cell simCell;
  private final Map<Integer, Color> colorMap;

  /**
   * Sole constructor for <code>GraphicalCell</code>. Takes a model <code>Cell</code> to render, a
   * <code>Map</code> indicating the appropriate <code>Paint</code> for each state, and the location
   * and size of the <code>GraphicalCell</code>.
   *
   * @param cell the model <code>Cell</code> to render
   * @param size the <code>GraphicalCell</code>'s width and height
   */
  public GraphicalCell(Cell cell) {
    this(cell, CELL_SIZE);
  }

  public GraphicalCell(Cell cell, double size) {
    super();
    setX(cell.getX() * CELL_SIZE + MARGIN);
    setY(cell.getY() * CELL_SIZE + MARGIN);
    setWidth(size);
    setHeight(size);
    this.simCell = cell;
    this.colorMap = Map.of(0, Color.BLACK, 1, Color.GREEN, 2, Color.BLUE);
	update();
  }

  /**
   * Called by a controller after cells have finished committing their <code>State</code> updates to
   * refresh their graphical appearance.
   */
  public void update() {
    int newColor = simCell.getEncoding();
    this.setFill(colorMap.getOrDefault(newColor, Color.YELLOW));
  }
}

package cellsociety.graphics;

import cellsociety.simulation.Cell;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GraphicalCell {
  private static final double MARGIN = 2.0;

  private final Cell simCell;
  private final Map<Integer, Paint> colorMap;
  private final Shape renderingShape;

  protected GraphicalCell(Cell simulationCell, Map<Integer, Paint> colorMap, double x, double y,
      double width, double height) {
    this.simCell = simulationCell;
    this.colorMap = colorMap;
    this.renderingShape = new Rectangle(x+MARGIN, y+MARGIN, width-2.0*MARGIN, height-2.0*MARGIN);
  }

  protected void update() {
    // change once an API for getting the cell's integer-encoded state exists
    this.renderingShape.setFill(colorMap.get(0));
  }

  protected Node getNode() {
    return this.renderingShape;
  }
}

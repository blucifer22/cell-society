package cellsociety.graphics.cells;

import cellsociety.simulation.Cell;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;


/**
 * A wrapper for model <code>Cell</code>s that allows for rendering. Instantiated by a
 * <code>GraphicalCellGrid</code>.
 *
 * @author David Coffman
 */
public abstract class GraphicalCell {

  private final Cell simCell;
  private final Map<Integer, Paint> colorMap;
  private final Shape renderingShape;

  private class OnClickEventHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent mouseEvent) {
      GraphicalCell.this.simCell.poke();
      GraphicalCell.this.update();
    }
  }

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
   * @param s              the <code>Shape</code> rendered by the the <code>GraphicalCell</code>
   */
  protected GraphicalCell(Cell simulationCell, Map<Integer, Paint> colorMap, double x, double y,
      Shape s) {
    this.simCell = simulationCell;
    this.colorMap = colorMap;
    this.renderingShape = s;
    this.renderingShape.setStroke(Color.BLACK);
    this.renderingShape.setStrokeWidth(1.0);
    this.renderingShape.setStrokeType(StrokeType.INSIDE);
    this.renderingShape.setOnMouseClicked(new OnClickEventHandler());
    update();
  }

  /**
   * Called by a controller after cells have finished committing their <code>State</code> updates to
   * refresh their graphical appearance.
   */
  public void update() {
    //this.renderingShape.setFill(colorMap.getOrDefault(simCell.getEncoding(), Color.WHITE));
    switch(simCell.getEncoding()) {
      case 1 -> this.renderingShape.setFill(Color.RED);
      case 2 -> this.renderingShape.setFill(Color.BLACK);
      case 3 -> this.renderingShape.setFill(Color.GREEN);
      default -> this.renderingShape.setFill(Color.WHITE);
    }
  }

  public void applyTesselationTransform(int gridX, int gridY) {
  }

  /**
   * Exposes the <code>GraphicalCell</code>'s rendering <code>Node</code> for insertion into a scene
   * graph.
   *
   * @return the <code>GraphicalCell</code>'s rendering <code>Node</code>
   */
  public Node getNode() {
    return this.renderingShape;
  }
}

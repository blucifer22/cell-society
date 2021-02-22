package cellsociety.graphics.cells;

import cellsociety.simulation.Cell;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
  private final Shape renderingShape;

  /**
   * Sole constructor for <code>GraphicalCell</code>. Takes a model <code>Cell</code> to render, a
   * <code>Map</code> indicating the appropriate <code>Paint</code> for each state, and the location
   * and size of the <code>GraphicalCell</code>.
   * @param simulationCell the model <code>Cell</code> to render
   * @param x              the x-position of the <code>GraphicalCell</code>'s top left corner
   * @param y              the y-position of the <code>GraphicalCell</code>'s top left corner
   * @param s              the <code>Shape</code> rendered by the the <code>GraphicalCell</code>
   */
  protected GraphicalCell(Cell simulationCell, double x, double y,
      Shape s) {
    this.simCell = simulationCell;
    this.renderingShape = s;
    this.renderingShape.setStroke(Color.BLACK);
    this.renderingShape.setStrokeWidth(1.0);
    this.renderingShape.setStrokeType(StrokeType.INSIDE);
    this.renderingShape.setOnMouseClicked(e -> poke());
    this.renderingShape.getStyleClass().add(stateClassString());
    update();
  }

  private void poke() {
    this.simCell.poke();
    this.update();
  }

  /**
   * Called by a controller after cells have finished committing their <code>State</code> updates to
   * refresh their graphical appearance.
   */
  public void update() {
    List<String> styleClasses = this.renderingShape.getStyleClass();
    styleClasses.remove(styleClasses.size()-1);
    styleClasses.add(stateClassString());
  }

  private String stateClassString() {
    return String.format("state%d", simCell.getEncoding());
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

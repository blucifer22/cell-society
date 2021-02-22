package cellsociety.graphics.cells;

import cellsociety.simulation.Cell;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;


/**
 * A wrapper for model {@link Cell}s that allows for rendering. Abstract and implemented by
 * subclasses which provide specific shapes for rendering. Used by constructing one of its
 * subclasses and calling {@link GraphicalCell#update()} as appropriate to a given use case.
 *
 * @author David Coffman
 */
public abstract class GraphicalCell {

  private final Cell simCell;
  private final Shape renderingShape;

  /**
   * Sole constructor for <code>GraphicalCell</code>. Takes a {@link Cell} to render, and the {@link
   * Shape} to render it with.
   *
   * @param simulationCell the model <code>Cell</code> to render
   * @param s              the <code>Shape</code> rendered by the the <code>GraphicalCell</code>
   */
  protected GraphicalCell(Cell simulationCell, Shape s) {
    this.simCell = simulationCell;
    this.renderingShape = s;
    this.renderingShape.setStroke(Color.BLACK);
    this.renderingShape.setStrokeWidth(1.0);
    this.renderingShape.setStrokeType(StrokeType.INSIDE);
    this.renderingShape.setOnMouseClicked(e -> poke());
    this.renderingShape.getStyleClass().add(stateClassString());
    update();
  }

  // Rotate the state of the cell referenced by this graphical cell.
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
    styleClasses.remove(styleClasses.size() - 1);
    styleClasses.add(stateClassString());
  }

  // Utility method used to construct a String which is then used as a style class (in order to
  // change the cell's appearance based on its statee)
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

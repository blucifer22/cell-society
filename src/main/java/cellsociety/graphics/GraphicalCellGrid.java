package cellsociety.graphics;

import cellsociety.simulation.Cell;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Paint;

/**
 * A container class for <code>GraphicalCell</code>s that allows for rendering. Instantiated by a
 * <code>GraphicalCellGrid</code>. Primarily responsible for the initial layout of the
 * <code>GraphicalCell</code>s.
 */
public class GraphicalCellGrid {
  private final List<List<GraphicalCell>> graphicalCells;
  private final Map<Integer, Paint> colorMap;
  private final double gridWidth;
  private final double gridHeight;

  /**
   * Sole constructor for GraphicalCellGrid.
   * @param cells the cells to render in the <code>GraphicalCellGrid</code>
   * @param gridWidth the target width of the <code>GraphicalCellGrid</code>
   * @param gridHeight the target height of the <code>GraphicalCellGrid</code>
   * @param colorMap the <code>Map</code> indicating the appearances of <code>Cell</code>s in
   *                 different states
   */
  public GraphicalCellGrid(List<List<Cell>> cells, double gridWidth,
      double gridHeight, Map<Integer, Paint> colorMap) {
    this.gridWidth = gridWidth;
    this.gridHeight = gridHeight;
    this.colorMap = colorMap;
    this.graphicalCells = configureGraphicalCells(cells);
  }

  // Configures the GraphicalCells inside the GraphicalCellGrid.
  private List<List<GraphicalCell>> configureGraphicalCells(List<List<Cell>> cells) {
    double cellWidth = gridWidth/cells.get(0).size();
    double cellHeight = gridHeight/cells.size();

    ArrayList<List<GraphicalCell>> ret = new ArrayList<>();
    for(int i = 0; i < cells.size(); i++) {
      ArrayList<GraphicalCell> inner = new ArrayList<>();
      for(int j = 0; j < cells.get(0).size(); j++) {
        Cell thisCell = cells.get(i).get(j);
        inner.add(new GraphicalCell(thisCell, colorMap, j*cellWidth, i*cellHeight, cellWidth,
            cellHeight));
      }
      ret.add(inner);
    }
    return ret;
  }

}

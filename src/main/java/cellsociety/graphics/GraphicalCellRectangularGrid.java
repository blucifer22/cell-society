package cellsociety.graphics;

import cellsociety.simulation.Cell;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;

public class GraphicalCellRectangularGrid {
  private List<GraphicalCell> graphicalCells;
  private Map<Integer, Paint> paintMap;
  private Group root;

  public GraphicalCellRectangularGrid(List<Cell> cells, Map<Integer, Paint> paintMap, double width,
      double height, int numRows, int numCols) {
    this.root = new Group();
    this.graphicalCells = new ArrayList<>();
    this.paintMap = paintMap;

    assert cells.size() == numRows*numCols;

    double cellWidth = width/numCols;
    double cellHeight = height/numRows;

    for(int i = 0; i < numRows; i++) {
      for(int j = 0; j < numCols; j++) {
        Cell c = cells.get(i*numCols+j);
        GraphicalCell gc = new GraphicalCell(c, paintMap, j*width, i*height, cellWidth,
            cellHeight);
        this.graphicalCells.add(gc);
        renderNode(gc.getNode());
      }
    }
  }

  public void update() {
    for(GraphicalCell c: graphicalCells) {
      c.update();
    }
  }

  private void renderNode(Node n) {
    root.getChildren().add(n);
  }

  public Node getNode() {
    return this.root;
  }
}
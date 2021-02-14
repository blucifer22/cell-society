package cellsociety.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A Grid that configures cells in a certain patten and sets the neighbors for for it's held cells.
 *
 * <p>CellGrids use an algorithm to create the neighbors of the the passed in cells.
 */
public class CellGrid {
  public static final double DEFAULT_HEIGHT = 5;
  public static final double DEFAULT_WIDTH = 5;
  protected List<List<Cell>> grid;
  protected Map<String, Double> configuration;
  protected double platformWidth;
  protected double platformHeight;

  /**
   * Constructs a rectangular grid with the specified configuration.
   *
   * <p>Cell grids expect the maps they are passed to contain a "width" and "height" field, if not
   * the height defaults to {@value #DEFAULT_HEIGHT} and the width to {@value #DEFAULT_WIDTH}.
   *
   * @param cells - The list of cells to be configured in a grid pattern.
   * @param configuration - The geometric patterns for this grid.
   */
  public CellGrid(List<Cell> cells, Map<String, Double> configuration) {
    this.grid = new ArrayList<>();
    this.configuration = configuration;
    this.platformWidth = configuration.getOrDefault("Width", DEFAULT_WIDTH);
    this.platformHeight = configuration.getOrDefault("Height", DEFAULT_HEIGHT);
    for (int i = 0, count = 0; i < DEFAULT_HEIGHT && count < cells.size(); i++) {
      grid.add(new ArrayList<>());
      for (int j = 0; j < DEFAULT_WIDTH && count < cells.size(); j++, count++) {
        cells.get(count).setY(i);
        cells.get(count).setX(j);
        grid.get(i).add(cells.get(count));
      }
    }
    createNeighbors();
  }

  private void createNeighbors() {
    List<Cell> row;
    Cell cell;
    for (int i = 0; i < grid.size(); i++) {
      row = grid.get(i);
      for (int j = 0; j < row.size(); j++) {
        cell = row.get(j);
        if (inBounds(i - 1, j)) {
          addCellNeighbor(cell, i - 1, j);
          addCellNeighbor(cell, i - 1, j - 1);
          addCellNeighbor(cell, i - 1, j + 1);
        }

        if (inBounds(i + 1, j)) {
          addCellNeighbor(cell, i + 1, j);
          addCellNeighbor(cell, i + 1, j - 1);
          addCellNeighbor(cell, i + 1, j + 1);
        }

        addCellNeighbor(cell, i, j - 1);
        addCellNeighbor(cell, i, j + 1);
      }
    }
  }

  /**
   * Safely add a cell neighbor to a specified cell.
   *
   * @param cell - The cell that will have a new neighbor
   * @param row - The row in the grid in which this cell is found
   * @param column - The column within the grid this cell is found
   */
  private void addCellNeighbor(Cell cell, int row, int column) {
    if (inBounds(row, column)) {
      cell.addNeighbor(grid.get(row).get(column));
    }
  }

  /**
   * Returns the grid describing the cell configuration.
   *
   * @return A 2D-Array of cells configured with the constructed height and width.
   */
  protected List<List<Cell>> getCells() {
    return this.grid;
  }

  /**
   * Returns a cell in a specific grid position.
   * Returns null if that position is not found
   * within the grid.
   * @param x - The column the cell is found within
   * @param y = The row the cell is found within
   */
  protected Cell getCell(int x, int y) {
	  if (inBounds(y, x)) {
		  return grid.get(y).get(x);
	  } else {
		  return null;
	  }
  }

  /**
   * Verifies that a row and column fall within the grid bounds.
   *
   * @param row - The row in which to check the bounds.
   * @param column - The column of the row in which to check.
   */
  private boolean inBounds(int row, int column) {
    return row > 0 && column > 0 && grid.size() > row && grid.get(row).size() > column;
  }
}

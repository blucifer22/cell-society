package cellsociety.simulation;

import cellsociety.util.CellShape;
import cellsociety.util.SimulationConfiguration.GridType;
import java.util.ArrayList;
import java.util.List;

/**
 * A Grid that configures cells in a certain patten and sets the neighbors for for it's held cells.
 *
 * @author Joshua Petitma
 *     <p>CellGrids use an algorithm to create the neighbors of the the passed in cells.
 */
public class CellGrid {
  protected List<List<Cell>> grid;
  protected double platformWidth;
  protected double platformHeight;

  /**
   * Constructs a rectangular grid with the specified configuration.
   *
   * @param cells - The list of cells to be configured in a grid pattern.
   * @param gridWidth - The number of cells in each row
   * @param gridHeight - The number of cells in each column
   */
  public CellGrid(List<Cell> cells, int gridWidth, int gridHeight, CellShape shape, GridType type) {
    this.grid = new ArrayList<>();
    this.platformWidth = gridWidth;
    this.platformHeight = gridHeight;
    for (int i = 0, count = 0; i < platformHeight && count < cells.size(); i++) {
      grid.add(new ArrayList<>());
      for (int j = 0; j < platformWidth && count < cells.size(); j++, count++) {
        cells.get(count).setY(i);
        cells.get(count).setX(j);
        grid.get(i).add(cells.get(count));
      }
    }
    switch (shape) {
      case TRIANGLE -> createTriNeighbors();
      case HEX -> createHexNeighbors();
      default -> createNeighbors();
    }

    switch (type) {
      case TOROIDAL -> collapseEdges();
    }
  }

  private void collapseEdges() {
    List<Cell> row;
    int width = (int) platformWidth;
    int height = (int) platformHeight;
    for (int rowNum = 0; rowNum < grid.size(); rowNum++) {
      row = grid.get(rowNum);
      addCellNeighbor(row.get(0), rowNum, width - 1);
    }
    row = grid.get(0);
    for (int col = 1; col < width - 1; col++) {
      addCellNeighbor(row.get(col), height - 1, col);
    }
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

  /** Creates triangular neighbors */
  private void createTriNeighbors() {
    List<Cell> row;
    Cell cell;
    boolean other = true;
    for (int i = 0; i < grid.size(); i++) {
      row = grid.get(i);
      for (int j = 0; j < row.size(); j++) {
        cell = row.get(j);
        if (other) {
          addCellNeighbor(cell, i - 1, j);
        } else {
          addCellNeighbor(cell, i + 1, j);
        }

        addCellNeighbor(cell, i, j - 1);
        addCellNeighbor(cell, i, j + 1);
        other = !other;
      }
      other = !other;
    }
  }

  private void createHexNeighbors() {
    List<Cell> row;
    Cell cell;
    boolean other = true;
    for (int i = 0; i < grid.size(); i++) {
      row = grid.get(i);
      for (int j = 0; j < row.size(); j++) {
        cell = row.get(j);

          addCellNeighbor(cell, i - 1, j);
          addCellNeighbor(cell, i + 1, j);
        if (other) {
          addCellNeighbor(cell, i + 1, j - 1);
          addCellNeighbor(cell, i - 1, j - 1);
        }  else {
          addCellNeighbor(cell, i + 1, j+1);
          addCellNeighbor(cell, i - 1, j + 1);
        }

         addCellNeighbor(cell, i, j - 1);
         addCellNeighbor(cell, i, j + 1);
      }
      other = !other;
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
   * Returns a cell in a specific grid position. Returns null if that position is not found within
   * the grid.
   *
   * @param col - The column the cell is found within
   * @param row = The row the cell is found within
   */
  protected Cell getCell(int row, int col) {
    return grid.get(row).get(col);
  }

  /**
   * Verifies that a row and column fall within the grid bounds.
   *
   * @param row - The row in which to check the bounds.
   * @param column - The column of the row in which to check.
   */
  private boolean inBounds(int row, int column) {
    return row >= 0 && column >= 0 && grid.size() > row && grid.get(row).size() > column;
  }

  protected void pokeCell(int row, int column) {
    grid.get(column).get(row).poke();
  }
}

package cellsociety.simulation;

import cellsociety.util.SimulationConfiguration.CellNeighborhoodSize;
import cellsociety.util.SimulationConfiguration.CellShape;
import cellsociety.util.SimulationConfiguration.SimulationEdgeType;
import java.util.ArrayList;
import java.util.List;

/**
 * A Grid that configures cells in a certain patten and sets the neighbors for for it's held cells.
 *
 * @author Joshua Petitma
 *     <p>CellGrids use an algorithm to create the neighbors of the the passed in cells.
 */
public class CellGrid {
  private List<List<Cell>> grid;
  private double platformWidth;
  private double platformHeight;
  private SimulationEdgeType type;
  private int neighborhood;
  /**
   * Constructs a rectangular grid with the specified configuration.
   *
   * @param cells - The list of cells to be configured in a grid pattern.
   * @param gridWidth - The number of cells in each row.
   * @param gridHeight - The number of cells in each column.
   * @param shape - The shape of every cell. Specifies how neighbors will be determined.
   * @param type - The edgetype, specifies what happens at the edges of the grid.
   * @param nSize - The size of neighborhood, determines which cells are considered neighbors.
   */
  public CellGrid(
      List<Cell> cells,
      int gridWidth,
      int gridHeight,
      CellShape shape,
      SimulationEdgeType type,
      CellNeighborhoodSize nSize) {
    this.grid = new ArrayList<>();
    this.platformWidth = gridWidth;
    this.platformHeight = gridHeight;
    this.type = type;
    for (int i = 0, count = 0; i < platformHeight && count < cells.size(); i++) {
      grid.add(new ArrayList<>());
      for (int j = 0; j < platformWidth && count < cells.size(); j++, count++) {
        cells.get(count).setY(i);
        cells.get(count).setX(j);
        grid.get(i).add(cells.get(count));
      }
    }

    switch (nSize) {
      case SMALL -> this.neighborhood = 1;
      case MEDIUM -> this.neighborhood = 2;
      case LARGE -> this.neighborhood = 3;
      default -> this.neighborhood = 3;
    }
    switch (shape) {
      case TRIANGLE -> createTriNeighbors();
      case HEXAGON -> createHexNeighbors();
      default -> createNeighbors();
    }
  }

  private void createNeighbors() {
    List<Cell> row;
    Cell cell;
    for (int i = 0; i < grid.size(); i++) {
      row = grid.get(i);
      for (int j = 0; j < row.size(); j++) {
        cell = row.get(j);
        if (neighborhood > 1) {
          // Periphery neighbors
          addCellNeighbor(cell, i - 1, j - 1);
          addCellNeighbor(cell, i - 1, j + 1);

          addCellNeighbor(cell, i + 1, j - 1);
          addCellNeighbor(cell, i + 1, j + 1);
        }

        addCellNeighbor(cell, i - 1, j);
        addCellNeighbor(cell, i + 1, j);

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

        addCellNeighbor(cell, i + 1, j);
        addCellNeighbor(cell, i - 1, j);
        if (neighborhood > 1) {
          addCellNeighbor(cell, i - 1, j - 1);
          addCellNeighbor(cell, i - 1, j + 1);
          addCellNeighbor(cell, i + 1, j + 1);
          addCellNeighbor(cell, i + 1, j - 1);
        }

        if (neighborhood > 1) {
          if (other) {
            addCellNeighbor(cell, i - 1, j - 2);
            addCellNeighbor(cell, i - 1, j + 2);
          } else {
            addCellNeighbor(cell, i + 1, j - 2);
            addCellNeighbor(cell, i + 1, j + 2);
          }
        }

        if (neighborhood > 2) {
          addCellNeighbor(cell, i, j - 2);
          addCellNeighbor(cell, i, j + 2);
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
        if (neighborhood > 2) {
          if (other) {
            addCellNeighbor(cell, i + 1, j - 1);
            addCellNeighbor(cell, i - 1, j - 1);
          } else {
            addCellNeighbor(cell, i + 1, j + 1);
            addCellNeighbor(cell, i - 1, j + 1);
          }

          if (neighborhood > 2) {
            addCellNeighbor(cell, i, j - 1);
            addCellNeighbor(cell, i, j + 1);
          }
        }
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
    if (this.type == SimulationEdgeType.TOROIDAL) {
      if (row < 0) {
        row = grid.size() + row;
      } else if (row >= grid.size()) {
        row = 0;
      }
      if (column < 0) {
        column = grid.get(0).size() + column;
      } else if (column >= grid.get(0).size()) {
        column = 0;
      }
    }
    if (inBounds(row, column)) {
      cell.addNeighbor(grid.get(row).get(column));
    }
  }

  /**
   * Returns a cell in a specific grid position. Returns null if that position is not found within
   * the grid.
   *
   * @param row = The row the cell is found within
   * @param col - The column the cell is found within
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

  /**
   * Pokes a cell at a specific position.
   *
   * @param row - The row in which the cell is located.
   * @param column - The column in which the cell is located.
   */
  protected void pokeCell(int row, int column) {
    grid.get(column).get(row).poke();
  }
}

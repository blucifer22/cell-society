package cellsociety.simulation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Unit tests for the Grid class
 *
 * @author Joshua Petitma
 */
class TestGrids {

  @Test
  void testCoolStuff() {
	  List<Cell> cells = new ArrayList<>();
	  for (int i = 0; i < 25; i++) {
		  cells.add(new FireCell());
	  }
	  Map<String, Double> config = new HashMap<>();
	  config.put("width", 5.0);
	  config.put("height", 5.0);
	  CellGrid grid = new CellGrid(cells, config);

	  List<List<Cell>> res = grid.getCells();

	  assertEquals(res.size(), 5);
	  for (int i = 0; i < 5; i++) {
		  assertEquals(res.get(i).size(), 5);
	  }

  }
}

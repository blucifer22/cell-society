package cellsociety.util;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.util.SimulationConfiguration.SimulationType;
import java.io.File;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TestXMLParsing {
  @Test
  public void test() {
    try {
      File file = new File("data/conways/conways_edge_test_1.xml");
      XMLParser p = new XMLParser(file);
      Map<String, Double> params = p.getSimulationConfiguration().getSimulationParameters();
      assertNotNull(params.get("AliveNumberMin"));
      assertEquals(params.get("AliveNumberMin"), 2.0);
      assertEquals(params.get("AliveNumberMax"), 3.0);
      assertEquals(params.get("SpawnNumberMin"), 3.0);
      assertEquals(params.get("AliveNumberMax"), 3.0);

      SimulationConfiguration c = p.getSimulationConfiguration();
      assertNotNull(c.getSimulationName());
      assertEquals(c.getSimulationName(), "Conway's Game of Life");
      assertEquals(c.getSimulationType(), SimulationType.CONWAY);
      assertEquals(c.getSimulationAuthor(), "Marc Chmielewski");
      assertEquals(c.getSimulationDescription(), "This is an edge case test for Conway's Game of Life");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

package cellsociety.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TestXMLParsing {
  @Test
  public void test() {
    try {
      File file = new File("data/conways/conways_edge_test_1.xml");
      XMLParser p = new XMLParser(file);
      Map<String, Double> params = p.getSimulationParameters();
      assertNotNull(params.get("AliveNumberMin"));
      assertEquals(params.get("AliveNumberMin"), 2.0);
      assertEquals(params.get("AliveNumberMax"), 3.0);
      assertEquals(params.get("SpawnNumberMin"), 3.0);
      assertEquals(params.get("AliveNumberMax"), 3.0);

      Map<String, String> meta = p.getSimulationMetadata();
      assertNotNull(meta.get("Name"));
      assertEquals(meta.get("Name"), "Conway's Game of Life");
      assertEquals(meta.get("Type"), "Conway");
      assertEquals(meta.get("Author"), "Marc Chmielewski");
      assertEquals(meta.get("Description"), "This is an edge case test for Conway's Game of Life");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

package cellsociety.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Map;

class TestXMLParsing {
  @Test
  public void test() {
    XMLParser p = new XMLParser();

    File file = new File("data/test.xml");
    try {
      p.createConfiguration(file);
      Map<String, Double> params = p.getSimulationParameters();
      assertNotNull(params.get("AliveNumberMin"));
      assertEquals(params.get("AliveNumberMin"), 2.0);
      assertEquals(params.get("AliveNumberMax"), 3.0);
      assertEquals(params.get("SpawnNumberMin"), 3.0);
      assertEquals(params.get("AliveNumberMax"), 3.0);

      Map<String, String> meta = p.getSimulationMetadata();
      assertNotNull(meta.get("Name"));
      assertEquals(meta.get("Name"), "Conway's Game of Life");
      assertEquals(meta.get("Type"), "Cyclic");
      assertEquals(meta.get("Author"), "John D. Sample");
      assertEquals(meta.get("Description"), "This is a cool simulation.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

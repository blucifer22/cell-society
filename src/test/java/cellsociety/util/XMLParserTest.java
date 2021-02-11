package cellsociety.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Test;

class TestXMLParsing {
  @Test
  public void test() {
    try {
      XMLParser p = new XMLParser(new File("data/test.xml"));
      System.out.println(p.getSimulationParameters());
      System.out.println(p.getInitialNonDefaultStates());
      System.out.println(p.getSimulationMetadata());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

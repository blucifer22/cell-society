package cellsociety.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ConwayRule class
 *
 * @author Marc Chmielewski
 */
public class ConwayRuleTests {
  @Test
  public void testConwayRuleDefaultConstructor() {
    ConwayRule rule = new ConwayRule();
    assertEquals(rule.getAliveNumberMin(), 2);
    assertEquals(rule.getAliveNumberMax(), 3);
    assertEquals(rule.getSpawnNumberMin(), 3);
    assertEquals(rule.getSpawnNumberMax(), 3);
  }

  @Test
  public void testConwayRuleCustomConstructor() {
    ConwayRule rule = new ConwayRule(1, 2, 3, 4);
    assertEquals(rule.getAliveNumberMin(), 1);
    assertEquals(rule.getAliveNumberMax(), 2);
    assertEquals(rule.getSpawnNumberMin(), 3);
    assertEquals(rule.getSpawnNumberMax(), 4);
  }

  @Test
  public void testConwayRuleXMLMapConstructor() {
    Map<String, Double> ruleMap = new HashMap<>();
    ruleMap.put("AliveNumberMin", 2.0);
    ruleMap.put("AliveNumberMax", 3.0);
    ruleMap.put("SpawnNumberMin", 3.0);
    ruleMap.put("SpawnNumberMax", 3.0);

    ConwayRule rule = new ConwayRule(ruleMap);

    assertEquals(rule.getAliveNumberMin(), 2);
    assertEquals(rule.getAliveNumberMax(), 3);
    assertEquals(rule.getSpawnNumberMin(), 3);
    assertEquals(rule.getSpawnNumberMax(), 3);
  }

  @Test
  public void testConwayRuleXMLMapConstructorWithDefaults() {
    Map<String, Double> ruleMap = new HashMap<>();
    ruleMap.put("AliveNumberMin", 1.0);
    ruleMap.put("SpawnNumberMin", 3.0);
    ruleMap.put("SpawnNumberMax", 4.0);

    ConwayRule rule = new ConwayRule(ruleMap);

    assertEquals(rule.getAliveNumberMin(), 1);
    assertEquals(rule.getAliveNumberMax(), 3);
    assertEquals(rule.getSpawnNumberMin(), 3);
    assertEquals(rule.getSpawnNumberMax(), 4);
  }
}

package cellsociety.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the PercolationRule class
 *
 * @author Marc Chmielewski
 */
public class PercolationRuleTests {
  @Test
  public void testPercolationRuleDefaultConstructor() {
    PercolationRule rule = new PercolationRule();
    assertEquals(rule.getFillNumber(), 1);
  }

  @Test
  public void testPercolationRuleCustomConstructor() {
    PercolationRule rule = new PercolationRule(2);
    assertEquals(rule.getFillNumber(), 2);
  }

  @Test
  public void testPercolationRuleXMLMapConstructor() {
    Map<String, Double> ruleMap = new HashMap<>();
    ruleMap.put("FillNumber", 3.0);

    PercolationRule rule = new PercolationRule(ruleMap);
    assertEquals(rule.getFillNumber(), 3);
  }

  @Test
  public void testPercolationRuleXMLMapConstructorWithDefaults() {
    Map<String, Double> ruleMap = new HashMap<>();

    PercolationRule rule = new PercolationRule(ruleMap);

    assertEquals(rule.getFillNumber(), 1);
  }
}

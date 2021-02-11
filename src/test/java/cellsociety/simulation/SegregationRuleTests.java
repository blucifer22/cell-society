package cellsociety.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the SegregationRule class
 *
 * @author Marc Chmielewski
 */
public class SegregationRuleTests {
  @Test
  public void testSegregationRuleDefaultConstructor() {
    SegregationRule rule = new SegregationRule();
    assertEquals(rule.getCutoffPercentage(), .7);
  }

  @Test
  public void testSegregationRuleCustomConstructor() {
    SegregationRule rule = new SegregationRule(.8);
    assertEquals(rule.getCutoffPercentage(), .8);
  }

  @Test
  public void testSegregationRuleXMLMapConstructor() {
    Map<String, Double> ruleMap = new HashMap<>();
    ruleMap.put("CutoffPercentage", .4);

    SegregationRule rule = new SegregationRule(.4);
    assertEquals(rule.getCutoffPercentage(), .4);
  }

  @Test
  public void testSegregationRuleXMLMapConstructorWithDefaults() {
    Map<String, Double> ruleMap = new HashMap<>();

    SegregationRule rule = new SegregationRule(ruleMap);

    assertEquals(rule.getCutoffPercentage(), .7);
  }
}

package cellsociety.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the FireRule class
 *
 * @author Marc Chmielewski
 */
public class FireRuleTests {
  @Test
  public void testFireRuleDefaultConstructor() {
    FireRule rule = new FireRule();
    assertEquals(rule.getFlammability(), .5);
  }

  @Test
  public void testFireRuleCustomConstructor() {
    FireRule rule = new FireRule(.7);
    assertEquals(rule.getFlammability(), .7);
  }

  @Test
  public void testFireRuleXMLMapConstructor() {
    Map<String, Double> ruleMap = new HashMap<>();
    ruleMap.put("Flammability", .2);

    FireRule rule = new FireRule(ruleMap);
    assertEquals(rule.getFlammability(), .2);
  }

  @Test
  public void testFireRuleXMLMapConstructorWithDefaults() {
    Map<String, Double> ruleMap = new HashMap<>();

    FireRule rule = new FireRule(ruleMap);

    assertEquals(rule.getFlammability(), .5);
  }
}

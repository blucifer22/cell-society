package cellsociety.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the WatorRule class
 *
 * @author Marc Chmielewski
 */
public class WatorRuleTests {
  @Test
  public void testWatorRuleDefaultConstructor() {
    WatorRule rule = new WatorRule();
    assertEquals(rule.getFishBreedingCycle(), 3);
    assertEquals(rule.getFishEnergyGain(), 5);
    assertEquals(rule.getSharkSpawnEnergy(), 10);
    assertEquals(rule.getSharkEnergyLoss(), 1);
  }

  @Test
  public void testWatorRuleCustomConstructor() {
    WatorRule rule = new WatorRule(1, 2, 3, 4);
    assertEquals(rule.getFishBreedingCycle(), 1);
    assertEquals(rule.getFishEnergyGain(), 2);
    assertEquals(rule.getSharkSpawnEnergy(), 3);
    assertEquals(rule.getSharkEnergyLoss(), 4);
  }

  @Test
  public void testWatorRuleXMLMapConstructor() {
    Map<String, Double> ruleMap = new HashMap<>();
    ruleMap.put("FishBreedingCycle", 5.0);
    ruleMap.put("FishEnergyGain", 6.0);
    ruleMap.put("SharkSpawnEnergy", 7.0);
    ruleMap.put("SharkEnergyLoss", 8.0);

    WatorRule rule = new WatorRule(ruleMap);

    assertEquals(rule.getFishBreedingCycle(), 5);
    assertEquals(rule.getFishEnergyGain(), 6);
    assertEquals(rule.getSharkSpawnEnergy(), 7);
    assertEquals(rule.getSharkEnergyLoss(), 8);
  }

  @Test
  public void testWatorRuleXMLMapConstructorWithDefaults() {
    Map<String, Double> ruleMap = new HashMap<>();
    ruleMap.put("FishBreedingCycle", 1.0);
    ruleMap.put("SharkEnergyLoss", 4.0);

    WatorRule rule = new WatorRule(ruleMap);

    assertEquals(rule.getFishBreedingCycle(), 1);
    assertEquals(rule.getFishEnergyGain(), 5);
    assertEquals(rule.getSharkSpawnEnergy(), 10);
    assertEquals(rule.getSharkEnergyLoss(), 4);
  }
}

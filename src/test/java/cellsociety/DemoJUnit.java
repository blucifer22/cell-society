// The package for the corresponding class you're testing
package cellsociety;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

// Name doesn't really matter
class TestDemo {

  @Test
  void testCoolStuff() {
    String test = new String("Hello Dudes!");
    assertEquals(test.charAt(0), 'H');
    assertNotEquals(test, "Bye Dudes!");
    assertTrue(test.contains("o D"));
  }
}

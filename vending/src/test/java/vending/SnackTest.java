package vending;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for class Snack
 */
class SnackTest {
    @Test()
    void testSnackHappy() {
        Integer amount = 10;
        Double price = 10.0;
        String name = "oreos";

        Snack snack = new Snack(amount, price, name);
        assertEquals(amount, snack.getAmount());
        assertEquals(price, snack.getPrice());
        assertEquals(name, snack.getName());
    }
}

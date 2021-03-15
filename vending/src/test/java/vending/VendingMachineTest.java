package vending;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for class Vending Machine.
 */
class VendingMachineTest {
    /**
     * Tries to get add to an invalid spot
     */
    @Test
    void testAddInvalidSpot(){
        Integer amount = 10;
        Double price = 10.0;
        String name = "oreos";
        Snack snack = new Snack(amount, price, name);
        ArrayList<String> spots = new ArrayList<>();
        spots.add("A1");
        spots.add("A2");
        VendingMachine machine = new VendingMachine(spots);
        Throwable exception = assertThrows(Exception.class, () -> {
            machine.addSnack("B1", snack);
        });
        assertEquals("That spot does not exist in the vending machine.", exception.getMessage());
    }

    /**
     * Tries to get add to an spot that already has a snack
     */
    @Test
    void testAddFilledSpot(){
        Integer amount = 10;
        Double price = 10.0;
        String name = "oreos";
        Snack snack = new Snack(amount, price, name);
        Snack snack2 = new Snack(amount, price, name);
        ArrayList<String> spots = new ArrayList<>();
        spots.add("A1");
        spots.add("A2");
        VendingMachine machine = new VendingMachine(spots);

        Throwable exception = assertThrows(Exception.class, () -> {
            machine.addSnack("A1", snack);
            machine.addSnack("A1", snack2);
        });
        assertEquals("There is another snack in that spot.", exception.getMessage());
    }
    
    /**
     * Tries to remove an invalid spot
     */
    @Test
    void testRemoveInvalidSpot() {
        ArrayList<String> spots = new ArrayList<>();
        spots.add("A1");
        spots.add("A2");
        VendingMachine machine = new VendingMachine(spots);
        Throwable exception = assertThrows(Exception.class, () -> {
            machine.removeSnack("B1");
        });
        assertEquals("That spot does not exist in the vending machine.", exception.getMessage());
    }
    
    /**
     * Tries to remove an an empty spot
     */
    @Test
    void testRemoveEmptySpot() {
        ArrayList<String> spots = new ArrayList<>();
        spots.add("A1");
        spots.add("A2");
        VendingMachine machine = new VendingMachine(spots);
        Throwable exception = assertThrows(Exception.class, () -> {
            machine.removeSnack("A1");
        });
        assertEquals("There is nothing to remove.", exception.getMessage());
    }
    
    /**
     * Tries to vend an invalid spot
     */
    @Test
    void testVendInvalidSpot() {
        ArrayList<String> spots = new ArrayList<>();
        spots.add("A1");
        spots.add("A2");
        VendingMachine machine = new VendingMachine(spots);
        Throwable exception = assertThrows(Exception.class, () -> {
            machine.vendSnack("B1");
        });
        assertEquals("That spot does not exist in the vending machine.", exception.getMessage());
    }

    /**
     * Tries to vend an empty spot
     */
    @Test
    void testVendEmptySpot() {
        ArrayList<String> spots = new ArrayList<>();
        spots.add("A1");
        spots.add("A2");
        VendingMachine machine = new VendingMachine(spots);
        Throwable exception = assertThrows(Exception.class, () -> {
            machine.vendSnack("A1");
        });
        assertEquals("There is nothing to vend.", exception.getMessage());
    }

    /**
     * test to see if a snack is removed once amount reaches 0
     */
    @Test
    void testVendRemoveSnack() {
        Integer amount = 1;
        Double price = 10.0;
        String name = "oreos";
        Snack snack = new Snack(amount, price, name);
        ArrayList<String> spots = new ArrayList<>();
        spots.add("A1");
        spots.add("A2");
        VendingMachine machine = new VendingMachine(spots);
        Throwable exception = assertThrows(Exception.class, () -> {
            machine.addSnack("A1", snack);
            machine.vendSnack("A1");
            machine.vendSnack("A1");
        });
        assertEquals("There is nothing to vend.", exception.getMessage());
    }
}

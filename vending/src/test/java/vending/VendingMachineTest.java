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
     * test adding to an invalid spot
     */
    @Test
    void testAddInvalidSpot(){
        Snack snack = new Snack(2, 5.0, "chips");
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
     * test adding to an spot that already has a snack
     */
    @Test
    void testAddFilledSpot(){
        Snack snack1 = new Snack(2, 5.0, "chips");
        Snack snack2 = new Snack(1, 5.0, "oreos");
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
     * test removing an invalid spot
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
     * test removing an an empty spot
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
     * test vending an invalid spot
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
     * test vending an empty spot
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
        Snack snack = new Snack(1, 5.0, "oreos");
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

    /**
     * test to see what happens when we try to add too many snacks
     */
    @Test
    void testAddSnacksTooMany() {
        ArrayList<Snack> snacks = new ArrayList<>();
        Snack snack1 = new Snack(1, 5.0, "oreos");
        Snack snack2 = new Snack(2, 3.9, "chips");
        Snack snack3 = new Snack(1, 2.9, "candy");
        snacks.add(snack1);
        snacks.add(snack2);
        snacks.add(snack3);

        ArrayList<String> spots = new ArrayList<>();
        spots.add("A1");
        spots.add("A2");
        VendingMachine machine = new VendingMachine(spots);
        Throwable exception = assertThrows(Exception.class, () -> {
            machine.addSnacks(snacks);
        });
        assertEquals("Input has too many snacks.", exception.getMessage());
    }
}

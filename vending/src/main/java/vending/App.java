package vending;

import java.util.ArrayList;

/**
 * This is our main class.
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        try{
            // read json
            ArrayList<String> spots = Util.getSpots(2, 2);
            VendingMachine machine = new VendingMachine(spots);

            //add snacks
            machine.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

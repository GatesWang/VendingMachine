package vending;

import java.util.HashMap;
import java.util.ArrayList;


/**
 * This represents a vending machine.
 */
public class VendingMachine {
    private HashMap<String, Snack> snacks; 
    private ArrayList<String> spots;

    public VendingMachine(ArrayList<String> spots){
        this.spots = spots;
        snacks = new HashMap<>();
    }

    public void addSnack(String spot, Snack snack) throws Exception{
        if(!spots.contains(spot)){
            throw new Exception("That spot does not exist in the vending machine.");
        }
        if(snacks.get(spot) != null){
            throw new Exception("There is another snack in that spot.");
        }
        snacks.put(spot, snack);
    }

    public Snack removeSnack(String spot) throws Exception{
        if(!spots.contains(spot)){
            throw new Exception("That spot does not exist in the vending machine.");
        }
        if(snacks.get(spot) == null){
            throw new Exception("There is nothing to remove.");
        }
        return snacks.remove(spot);
    }

    public void vendSnack(String spot) throws Exception{
        if(!spots.contains(spot)){
            throw new Exception("That spot does not exist in the vending machine.");
        }
        if(snacks.get(spot) == null){
            throw new Exception("There is nothing to vend.");
        }
        
        Snack snack = snacks.get(spot);
        int amount = snack.getAmount();
        amount--;
        if(amount > 0){
            snack.setAmount(amount);
        }
        else{
            snacks.remove(spot);
        }
    }

    public void show(){
        System.out.println(snacks);
    }
    
}



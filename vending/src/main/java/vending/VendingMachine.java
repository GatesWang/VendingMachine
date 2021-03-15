package vending;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.stream.Collectors;

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

    public void addSnacks(ArrayList<Snack> items) throws Exception{
        Iterator<Snack> snackItr = items.iterator();
        for(String spot : spots){
            if(snacks.get(spot) == null && snackItr.hasNext()){
                snacks.put(spot, snackItr.next());
            }
        }

        if(snackItr.hasNext()){
            throw new Exception("Input has too many snacks.");
        }
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
        spots.stream()
        .sorted()
        .collect(Collectors.groupingBy(it -> it.charAt(0)))
        .values()
        .forEach(row -> {
            String format = "%-12.12s ";
            String label = "";
            String name = "";
            String price = "";

            for(String spot : row){
                Snack snack = snacks.get(spot);
                label += String.format(format, snack==null? spot : spot + "(" + snack.getAmount() + ")");
                name += String.format(format, snack==null? "" : snack.getName());
                price += String.format(format, snack==null? "" : snack.getPrice());
            }
            System.out.println(label + "\n" + name + "\n" + price + "\n");
        });
    }
    
}



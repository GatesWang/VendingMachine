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
    private ArrayList<String> spots; //contains all available spots
    private HashMap<String, Snack> options; //maps from spot to snack
    private HashMap<String, Snack> cart; //maps from spot to snack
    
    public VendingMachine(ArrayList<String> spots){
        this.spots = spots;
        options = new HashMap<>();
        cart = new HashMap<>();
    }

    public void addOptions(ArrayList<Snack> items) throws Exception{
        if(cart.size()>0){
            throw new Exception("Can't edit options when the cart is not empty.");
        }
        List<String> emptySpots = spots.stream()
        .filter(spot -> options.get(spot)==null)
        .collect(Collectors.toList());

        if(items.size() > emptySpots.size()){
            throw new Exception("Trying to add too many options.");
        }
        
        Iterator<Snack> snackItr = items.iterator();
        emptySpots.stream().forEach(spot -> {
            if(snackItr.hasNext()){
                options.put(spot, snackItr.next());
            }
        });
    }

    public void addOption(String spot, Snack snack) throws Exception{
        if(cart.size()>0){
            throw new Exception("Can't edit options when the cart is not empty.");
        }
        if(!spots.contains(spot)){
            throw new Exception("That spot does not exist in the vending machine.");
        }
        if(options.get(spot) != null){
            throw new Exception("There is another snack in that spot.");
        }
        options.put(spot, snack);
    }

    public Snack removeOption(String spot) throws Exception{
        if(cart.size()>0){
            throw new Exception("Can't edit options when the cart is not empty.");
        }
        if(!spots.contains(spot)){
            throw new Exception("That spot does not exist in the vending machine.");
        }
        if(options.get(spot) == null){
            throw new Exception("There is nothing to remove.");
        }
        return options.remove(spot);
    }
    
    public Snack vendSnack(String spot) throws Exception{
        if(!spots.contains(spot)){
            throw new Exception("That spot does not exist in the vending machine.");
        }
        if(options.get(spot) == null){
            throw new Exception("There is nothing to vend.");
        }
        
        Snack snack = options.get(spot);
        String name = snack.getName();
        Integer amount = snack.getAmount();
        Double price = snack.getPrice();

        //update the machine
        amount--;
        if(amount > 0){
            snack.setAmount(amount);
        }
        else{
            options.remove(spot);
        }

        //update the cart
        Snack snackInCart = cart.get(spot);
        if(snackInCart!=null){
            snackInCart.setAmount(snackInCart.getAmount()+1);
        }
        else{
            snackInCart = new Snack(1, price, name);
            cart.put(spot, snackInCart);
        }
        return snackInCart; 
    }
    
    public Snack unVendSnack(String spot) throws Exception{
        if(!spots.contains(spot)){
            throw new Exception("That spot does not exist in the vending machine.");
        }
        if(cart.get(spot) == null){
            throw new Exception("That snack is not in the cart.");
        }

        Snack snackInCart = cart.get(spot);
        String name = snackInCart.getName();
        Integer amount = snackInCart.getAmount();
        Double price = snackInCart.getPrice();

        //update the cart
        amount--;
        if(amount > 0){
            snackInCart.setAmount(amount);
        }
        else{
            cart.remove(spot);
        }

        //update the machine
        Snack snackInMachine = options.get(spot);
        if(snackInMachine!=null){
            snackInMachine.setAmount(snackInMachine.getAmount()+1);
        }
        else{
            snackInMachine = new Snack(1, price, name);
            options.put(spot, snackInMachine);
        }
        return snackInMachine; 
    }

    public double checkOut() throws Exception{
        if(cart.size() == 0){
            throw new Exception("Can't checkout with no items.");
        }
        double total = cart.keySet()
        .stream()
        .map(spot -> cart.get(spot))
        .mapToDouble(snack -> snack.getAmount() * snack.getPrice())
        .reduce(0, (a,b) -> a + b); 

        cart = new HashMap<>();
        return total;
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
                Snack snack = options.get(spot);
                label += String.format(format, snack==null? spot : spot + "(" + snack.getAmount() + ")");
                name += String.format(format, snack==null? "" : snack.getName());
                price += String.format(format, snack==null? "" : snack.getPrice());
            }
            System.out.println(label + "\n" + name + "\n" + price + "\n");
        });
    }
    
    public void showCart(){
        if(cart.size() == 0){
            System.out.println("Empty cart");
        }
        for(String spot : cart.keySet()){
            Snack snack = cart.get(spot);
            String name = snack.getName();
            int amount = snack.getAmount();
            double total = snack.getPrice() * amount;
            System.out.println(String.format("%s %d %s %f\n", spot, amount, name , total));
        }
    }
}



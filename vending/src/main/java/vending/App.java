package vending;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import jdk.jfr.events.ExceptionThrownEvent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * This is our main class.
 */
public final class App {
    private App() {
    }

    private static VendingMachine createMachine(String path) throws Exception{
        String content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(content);
        JSONObject config = obj.getJSONObject("config");
        JSONArray items = obj.getJSONArray("items");

        int rows = config.getInt("rows");
        int cols = Integer.parseInt(config.getString("columns"));
        ArrayList<String> spots = Util.getSpots(rows, cols);
        VendingMachine machine = new VendingMachine(spots);
        addOptions(items, machine);
        return machine;
    }

    private static void addOptions(JSONArray items, VendingMachine machine) throws Exception{
        ArrayList<Snack> options = new ArrayList<>();
        for (int i=0; i<items.length(); i++) {
            String name = items.getJSONObject(i).getString("name");
            Integer amount = items.getJSONObject(i).getInt("amount");
            String price = items.getJSONObject(i).getString("price");
            NumberFormat format = NumberFormat.getCurrencyInstance();
            Number number = format.parse(price);

            Snack snack = new Snack(amount, number.doubleValue(), name);
            options.add(snack);
        }
        machine.addOptions(options);
    }

    private static void addOption(Scanner scanner, VendingMachine machine) throws Exception{
        Integer amount = null;
        Double price = null;
        String name = null;
        while(amount==null){
            try{
                System.out.println("Enter quantity of snacks: ");
                amount = Integer.parseInt(scanner.nextLine());
            }
            catch(Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        while(price==null){
            try{
                System.out.println("Enter price of snacks: ");
                price = Double.parseDouble(scanner.nextLine());
            }
            catch(Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        while(name==null){
            try{
                System.out.println("Enter name of snacks: ");
                name = scanner.nextLine();
            }
            catch(Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        Snack snack = new Snack(amount, price, name);
        System.out.println("Enter label of the snack: ");
        String spot = scanner.nextLine();
        machine.addOption(spot, snack);
    }

    private static void getAction(Scanner scanner, VendingMachine machine) throws Exception{
        String input = scanner.nextLine();
        switch(input){
            case "a":
                System.out.println("Enter label for snack (letter)(number):");
                String spot = scanner.nextLine();
                Snack snack = machine.vendSnack(spot);
                System.out.println(String.format("you bought %s!\n", snack.getName())); 
                break;
            case "r":
                System.out.println("Enter label for removal (letter)(number):");
                spot = scanner.nextLine();
                snack = machine.unVendSnack(spot);
                System.out.println(String.format("you returned %s!\n", snack.getName())); 
                break;
            case "c":
                machine.showCart();
                break;
            case "p":
                machine.showCart();
                double cost = machine.checkOut();
                System.out.println(String.format("pay: %f! (y/n)\n", cost)); 
                input = scanner.nextLine(); 
                if(input.equals("y")){
                    System.out.println("paid for!\n");
                }
                else{
                    System.out.println("payment did not go through!\n");
                }
                break;     
            case "e":
                System.out.println("(a)dd option or (r)emove option\n"); 
                input = scanner.nextLine(); 
                if(input.equals("a")){ 
                    addOption(scanner, machine);
                }
                else if(input.equals("r")){
                    System.out.println("Enter label of the snack: ");
                    spot = scanner.nextLine();
                    machine.removeOption(spot);
                }
                else{
                    System.out.println("Invalid option");
                }
                break;  
            default:
                System.out.println("Invalid command");
        } 
    }
    
    private static void loop(VendingMachine machine){
        Scanner scanner = new Scanner(System.in);
        String prompt = "Enter command: (a)dd, (r)emove, (c)art, p(ay), e(dit)";
        machine.show();
        System.out.println(prompt);
        do{
            try{
                getAction(scanner, machine);
            }
            catch(Exception e){
                System.out.println(e.getMessage() + "\n");
            }
            machine.show();
            System.out.println(prompt);
        }while(scanner.hasNext());
        scanner.close(); 
    }
    
    public static void main(String[] args) {
        try{
            String path = "../input.json";
            VendingMachine machine = createMachine(path);
            loop(machine);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

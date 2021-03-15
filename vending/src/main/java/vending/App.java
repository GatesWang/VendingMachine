package vending;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.text.NumberFormat;

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
        addSnacks(items, machine);
        return machine;
    }

    private static void addSnacks(JSONArray items, VendingMachine machine) throws Exception{
        ArrayList<Snack> snacks = new ArrayList<>();
        for (int i=0; i<items.length(); i++) {
            String name = items.getJSONObject(i).getString("name");
            Integer amount = items.getJSONObject(i).getInt("amount");
            String price = items.getJSONObject(i).getString("price");
            NumberFormat format = NumberFormat.getCurrencyInstance();
            Number number = format.parse(price);

            Snack snack = new Snack(amount, number.doubleValue(), name);
            snacks.add(snack);
        }
        machine.addSnacks(snacks);
    }

    public static void main(String[] args) {
        try{
            String path = "../input.json";
            VendingMachine machine = createMachine(path);
            machine.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

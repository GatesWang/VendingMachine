package vending;

import java.util.ArrayList;

public class Util{
    /*
        This simulates clearing screen. It works in IDE's too. 
    */
    public static void clearScreen(){
        for(int i=0; i<5; i++){
            System.out.println();
        }
    }
    /*
        Converts int to respective character
        0 -> a
        1 -> b
        etc
    */
    public static String getCharForNumber(int i) throws Exception{
        if(i>0 && i<27){
            return String.valueOf((char)(i + 64));
        }
        else{
            throw new Exception("That number can't be mapped to a letter");
        }
    }
    /*
        Given the number of rows and columns
        generate the potential spots in a vending machine e.g.
        getSpots(2,2) 
        => 
        A1 A2
        B1 B2
    */
    public static ArrayList<String> getSpots(int rows, int cols) throws Exception{
        ArrayList<String> spots = new ArrayList<>();
        for(int i=1; i<=rows; i++){
            for(int j=1; j<=cols; j++){
                spots.add(Util.getCharForNumber(i) + j);
            }
        }
        return spots;
    }
}
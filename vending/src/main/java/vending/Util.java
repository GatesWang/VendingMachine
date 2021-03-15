package vending;

import java.util.ArrayList;

public class Util{
    public static String getCharForNumber(int i) throws Exception{
        if(i>0 && i<27){
            return String.valueOf((char)(i + 64));
        }
        else{
            throw new Exception("That number can't be mapped to a letter");
        }
    }

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
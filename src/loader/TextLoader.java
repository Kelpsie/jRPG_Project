package loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextLoader {

    private static File file = new File("assets/mapData.txt");
    public static Scanner scanner = null;

    public static String readText(){

        String string = "";
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(scanner.hasNextLine()){
            string = scanner.nextLine();
        }
        return string;
    }

    public static int[][] convertTo2D(String string, int[][] mapData){
        String[] unformattedMapData = string.split(",");
        for(int y = 0; y < 32; y++){
            for(int x = 0; x < 32; x++){
                mapData[y][x] = Integer.parseInt(unformattedMapData[((y*32)+x)]);
            }
        }

        return mapData;
    }

}

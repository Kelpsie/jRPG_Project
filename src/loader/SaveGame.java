package loader;

import models.Player;
import skills.RangedAttack;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SaveGame {

    int skillPoints;
    int level;
    int RangedeAttackLvl;
    int health;
    int xp;

    static ArrayList<Integer> SavedData = new ArrayList<>();
    static ArrayList<Integer> ReadData = new ArrayList<>();
    static ArrayList<Integer> DefaultData = new ArrayList<>();

    public static void createFile() throws IOException {
        DefaultData.add(100);
        DefaultData.add(0);
        DefaultData.add(0);
        DefaultData.add(0);
        DefaultData.add(15);
        DefaultData.add(15);
        DefaultData.add(1);
        File file = new File("assets/000.dat");
        if(file.createNewFile()){
            System.out.println("save file does not currently exist | making save file");
            PrintWriter write2 = new PrintWriter(new FileOutputStream("assets/000.dat", false));
            for (int i : DefaultData){
                write2.println(i);
            }
            write2.close();
        } else {

        }
    }

    public static void writeData() {
        SavedData.add(Player.hp);
        SavedData.add(Player.level);
        SavedData.add(Player.skillPoints);
        SavedData.add(Player.xp);
        SavedData.add(Player.posX);
        SavedData.add(Player.posY);
        SavedData.add(RangedAttack.level);

        try (PrintWriter writer = new PrintWriter(new FileWriter("assets/000.dat", false))) {

            for (int i : SavedData){
                writer.println(i);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        SavedData.clear();


    }

    public static void readData() throws FileNotFoundException {
        File saveGame = new File("assets/000.dat");
        Scanner read = new Scanner(saveGame);
        while (read.hasNextLine()){
            ReadData.add(Integer.parseInt(read.nextLine()));
        }
        read.close();
        System.out.println(ReadData);
        Player.hp = ReadData.get(0);
        Player.level = ReadData.get(1);
        Player.skillPoints = ReadData.get(2);
        Player.xp = ReadData.get(3);
        Player.posX = ReadData.get(4);
        Player.posY = ReadData.get(5);
        RangedAttack.level = ReadData.get(6);
    }

}


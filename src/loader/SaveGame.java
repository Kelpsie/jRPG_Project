package loader;

import HUD.MainHUD;
import main.Game;
import models.Player;
import scenes.MapScene;
import skills.Blink;
import skills.Heal;
import skills.MeleeAttack;
import skills.RangedAttack;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static scenes.MapScene.*;

public class SaveGame {

    public static boolean toLoad;
    static boolean flag = false;
    public static String pathName = "assets/data/000.dat";

    static ArrayList<Integer> SavedData = new ArrayList<>();
    static ArrayList<Integer> ReadData = new ArrayList<>();
    static ArrayList<Integer> DefaultData = new ArrayList<>();


    static void positionPlayer(){
        int[] mapPos = mapToScreen(Player.posX, Player.posY);
        MapScene.mapX = ((Game.WIDTH / 2)/map.tileSize)*map.tileSize - mapPos[0];
        MapScene.mapY = ((Game.HEIGHT / 2)/map.tileSize)*map.tileSize - mapPos[1];
    }

    public static void createFile() throws IOException {
        DefaultData.add(200);
        DefaultData.add(200);
        DefaultData.add(0);
        DefaultData.add(0);
        DefaultData.add(0);
        DefaultData.add(4);
        DefaultData.add(33);
        DefaultData.add(1);
        DefaultData.add(1);
        DefaultData.add(1);
        DefaultData.add(1);
        File file = new File(pathName);
        if(file.createNewFile()){
            System.out.println("save file does not currently exist | making save file");
            PrintWriter write2 = new PrintWriter(new FileOutputStream(pathName, false));
            for (int i : DefaultData){
                write2.println(i);
            }
            write2.close();
        } else {
            flag = true;
            readData();
        }
    }

    public static void writeData() {
        SavedData.add(Player.hp);
        SavedData.add(Player.maxHP);
        SavedData.add(Player.level);
        SavedData.add(Player.skillPoints);
        SavedData.add(Player.xp);
        SavedData.add(Player.posX);
        SavedData.add(Player.posY);
        SavedData.add(MapScene.skills.get("Ranged Attack").level);
        SavedData.add(MapScene.skills.get("Melee Attack").level);
        SavedData.add(MapScene.skills.get("Heal").level);
        SavedData.add(MapScene.skills.get("Blink").level);

        try (PrintWriter writer = new PrintWriter(new FileWriter(pathName, false))) {

            for (int i : SavedData){
                writer.println(i);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        SavedData.clear(); //flush data to be written

    }

    public static void readData() throws FileNotFoundException {

        if(toLoad){
            try{
                File saveGame = new File(pathName);
                Scanner read = new Scanner(saveGame);
                while (read.hasNextLine()){
                    ReadData.add(Integer.parseInt(read.nextLine()));
                }
                read.close();
                System.out.println("Reading data...\n" + ReadData);

                Player.hp = ReadData.get(0);
                Player.maxHP = ReadData.get(1);
                Player.level = ReadData.get(2);
                Player.skillPoints = ReadData.get(3);
                Player.xp = ReadData.get(4);
                Player.posX = ReadData.get(5);
                Player.posY = ReadData.get(6);
                MapScene.skills.get("Ranged Attack").level = ReadData.get(7);
                MapScene.skills.get("Melee Attack").level = ReadData.get(8);
                MapScene.skills.get("Heal").level = ReadData.get(9);
                MapScene.skills.get("Blink").level = ReadData.get(10);

                //place the player on the map AFTER loading in the player position data
                MainHUD.level.setText("Level:" + Player.level);
                positionPlayer();
                ReadData.clear(); //clear data once loaded
            } catch(Exception e ) {
                Player.hp = 200;
                Player.maxHP = 200;
                Player.level = 1;
                Player.skillPoints = 0;
                Player.xp = 0;
                Player.posX = 4;
                Player.posY = 33;
                MapScene.skills.get("Ranged Attack").level = 1;
                MapScene.skills.get("Melee Attack").level = 1;
                MapScene.skills.get("Heal").level = 1;
                MapScene.skills.get("Blink").level = 1;
                notificationQueue.add("Error Loading Save File!");
                positionPlayer();
                ReadData.clear(); //clear data once loaded
            }

        } else {
            //if not read save data, default to starting location using default values in Player
            positionPlayer();
            ReadData.clear();
        }


    }

}


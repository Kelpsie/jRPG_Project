package models;

import HUD.MainHUD;
import audio.AudioHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import loader.ImageLoader;
import loader.SaveGame;
import scenes.MapScene;

import java.util.ArrayList;

public class Player {


    private int id;
    public static int posX = 4, posY = 33;
    private GraphicsContext graphicsContext;
    private static ArrayList<Image> animations = new ArrayList<>();
    public static int animationCounter = 0;
    public static int charFrameNumber = 0;
    public static int maxHP = 200;
    public static int hp = 200;
    public static float xpToNextLevel = 448;
    public static int xp = 0;
    public static int skillPoints = 0;
    public static int level = 1;


    public Player(GraphicsContext graphicsContext, int id){
        try {
            SaveGame.readData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.id = id;
        this.graphicsContext = graphicsContext;
        xpToNextLevel =  Math.round((384 * (level / 2.0)));
        MainHUD.xpBar.setWidth((int)((xp / xpToNextLevel) * 384));
        ImageLoader.readTileMap("assets/MainChar.png", animations, 32);

    }
    public static void restoreHealth(int restoreValue){
        if (hp < maxHP){
            hp += restoreValue;
            if ( hp > maxHP){
                hp = maxHP;
            }
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setXP(int xpValue){
        xp += xpValue;
        if (xp > xpToNextLevel){
            AudioHandler.playAudio("levelup.wav");
            level += 1;
            skillPoints += 1;
            xp = 1;
            xpToNextLevel += Math.round((xpToNextLevel * (level / 2.0)));
            maxHP += Math.round((maxHP / 8.0) * level);
            hp = maxHP;
            MainHUD.xp.setText("XP:" + Player.xp + "/" + (int) Player.xpToNextLevel);
            MainHUD.xpBar.setWidth(0);
        }   MainHUD.level.setText("Level:" + Player.level);
        if (xp <= xpToNextLevel) {
            MainHUD.xpBar.setWidth((int)((xp / xpToNextLevel) * 448));
        }

    }


    public void draw(int x, int y){

        if (charFrameNumber > 1){
            charFrameNumber = 0;
            animationCounter++;
            if (animationCounter >= animations.size()){
                animationCounter = 0;
            }
        }
        graphicsContext.drawImage(animations.get(animationCounter), x, y);

    }
}

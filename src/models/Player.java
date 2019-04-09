package models;

import HUD.MainHUD;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import loader.ImageLoader;
import java.util.ArrayList;

public class Player {


    private int id;
    public int posX, posY;
    private GraphicsContext graphicsContext;
    private static ArrayList<Image> animations = new ArrayList<>();
    public static int animationCounter = 0;
    public static int charFrameNumber = 0;
    public static int hp = 100;
    public static int regenValue = 2;
    public static int xp = 0;
    public static int skillPoints = 0;
    public static int level = 1;


    public Player(GraphicsContext graphicsContext, int id, int x, int y){
        this.id = id;
        this.graphicsContext = graphicsContext;
        this.posX = x; this.posY = y;
        ImageLoader.readTileMap("assets/MainChar.png", animations, 32);
    }

    public static void regenHealth(){
        if (hp < 100){
            hp += regenValue;
            if ( hp > 100){
                hp = 100;
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
        if (xp >= 384){
            level += 1;
            skillPoints += 1;
            System.out.println(level);
            xp = 0;
            MainHUD.xpBar.setWidth(xp);
        }
        if (xp <= 384) {
            MainHUD.xpBar.setWidth(xp);
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

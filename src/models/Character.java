package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import loader.ImageLoader;
import java.util.ArrayList;

public class Character {


    private int id;
    private int posX;
    private int posY;
    private GraphicsContext graphicsContext;
    private static ArrayList<Image> animations = new ArrayList<>();
    public static int animationCounter = 0;
    public static int charFrameNumber = 0;


    public Character(GraphicsContext graphicsContext, int id){
        this.id = id;
        this.graphicsContext = graphicsContext;
        ImageLoader.readTileMap("assets/MainChar.png", animations, 32);
    }

    public int getId() {
        return id;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }


    public void drawPlayer(int x, int y){

        if (charFrameNumber > 10){
            charFrameNumber = 0;
            animationCounter++;
            if (animationCounter >= animations.size()){
                animationCounter = 0;
            }
        }
        graphicsContext.drawImage(animations.get(animationCounter), x, y);

    }
}

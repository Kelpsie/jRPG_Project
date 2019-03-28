package models;

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
    public int hp = 100;


    public Player(GraphicsContext graphicsContext, int id, int x, int y){
        this.id = id;
        this.graphicsContext = graphicsContext;
        this.posX = x; this.posY = y;
        ImageLoader.readTileMap("assets/MainChar.png", animations, 32);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void draw(int x, int y){

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

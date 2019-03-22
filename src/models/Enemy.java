package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import loader.ImageLoader;

import java.util.ArrayList;

public class Enemy {
    public int posX;
    public int posY;
    private GraphicsContext graphicsContext;
    private ArrayList<Image> animations = new ArrayList<>();
    private int framesToSkip;
    private int framesSkipped = 0;
    private int currentFrame = 0;


    public Enemy(GraphicsContext graphicsContext, String file, int frameSize, int framesToSkip, int x, int y) {
        this.graphicsContext = graphicsContext;
        ImageLoader.readTileMap(file, animations, frameSize);
        this.framesToSkip = framesToSkip + 1;
        posX = x; posY = y;
    }

    public void draw(int x, int y) {
        framesSkipped = (framesSkipped + 1) % framesToSkip;
        if (framesSkipped == 0)
            currentFrame = (currentFrame + 1) % animations.size();
        graphicsContext.drawImage(animations.get(currentFrame), x, y);
    }

}

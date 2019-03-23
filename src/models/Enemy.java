package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import loader.ImageLoader;
import scenes.MapScene;

import java.util.ArrayList;

public class Enemy {
    public int posX, posY;
    private int drawX, drawY;
    private GraphicsContext graphicsContext;
    private ArrayList<Image> animations = new ArrayList<>();
    private int framesToSkip;
    private int framesSkipped = 0;
    private int currentFrame = 0;
    private int direction = -1;


    public Enemy(GraphicsContext graphicsContext, String file, int frameSize, int framesToSkip, int x, int y) {
        this.graphicsContext = graphicsContext;
        ImageLoader.readTileMap(file, animations, frameSize);
        this.framesToSkip = framesToSkip + 1;
        posX = x; posY = y;


        drawX = posX * MapScene.map.tileSize;
        drawY = posY * MapScene.map.tileSize;
    }

    //TODO: Move should be private. Currently public for testing purposes
    public void move(int dir) {
        int newX = 0;
        int newY = 0;
        switch(dir) {
            case 0: newY = -1; break;
            case 1: newX = 1 ; break;
            case 2: newY = 1 ; break;
            case 3: newX = -1; break;
        }
        if (!MapScene.map.collidable(posX + newX, posY + newY)) {
            direction = dir;
            posX += newX;
            posY += newY;
        }
    }

    public void draw() {
        framesSkipped = (framesSkipped + 1) % framesToSkip;
        if (framesSkipped == 0)
            currentFrame = (currentFrame + 1) % animations.size();
        graphicsContext.drawImage(animations.get(currentFrame), drawX+MapScene.mapX, drawY+MapScene.mapY);
    }

    // Returns true if Enemy needs to do more work next frame
    // such as when between tiles during movement
    public boolean update() {
        if (direction != -1) {
            switch (direction) {
                case 0: drawY -= 1; break;
                case 1: drawX += 1; break;
                case 2: drawY += 1; break;
                case 3: drawX -= 1; break;
            }
            if (drawX % MapScene.map.tileSize == 0 && drawY % MapScene.map.tileSize == 0) {
                direction = -1;
                return false;
            }
            return true;
        }



        return false;
    }

}

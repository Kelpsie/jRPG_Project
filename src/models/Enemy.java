package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import loader.ImageLoader;
import scenes.MapScene;

import java.util.ArrayList;
import java.util.Random;

public abstract class Enemy {
    public int posX, posY;
    private int drawX, drawY;
    private int targetX, targetY;
    private GraphicsContext graphicsContext;
    private ArrayList<Image> animations = new ArrayList<>();
    private int framesToSkip;
    private int framesSkipped = 0;
    private int currentFrame = 0;
    public boolean canAct = true;
    public int hp = 100;
    public int damage = 5;
    public int xpWorth;


    public Enemy(String file, int frameSize, int framesToSkip, int x, int y, int xp) {
        ImageLoader.readTileMap(file, animations, frameSize);
        this.framesToSkip = framesToSkip + 1;
        posX = x; posY = y;


        drawX = posX * MapScene.map.tileSize;
        drawY = posY * MapScene.map.tileSize;
        targetX = posX; targetY = posY;
        this.xpWorth = xp;
    }

    public void takeDamage(int damage) {
        hp -= damage;
    }

    //TODO: Move should be private. Currently public for testing purposes
    public void move(int newX, int newY) {

        if (MapScene.walkable(newX, newY)) {
            targetX = newX;
            targetY = newY;
        }
    }

    public abstract void act();


    // Returns true if Enemy needs to do more work next frame
    // such as when between tiles during movement
    public boolean update() {
        if (targetX != posX || targetY != posY) {
            if (targetX < posX) drawX -= 2;
            if (targetX > posX) drawX += 2;
            if (targetY < posY) drawY -= 2;
            if (targetY > posY) drawY += 2;
            int[] targetDrawPos = MapScene.mapToScreen(targetX, targetY);

            if (drawX+MapScene.mapX == targetDrawPos[0])
                posX = targetX;
            if (drawY+MapScene.mapY == targetDrawPos[1])
                posY = targetY;
            if (targetX == posX && targetY == posY) return false;

            return true;
        }

        return false;
    }


    public void draw(GraphicsContext graphicsContext) {
        framesSkipped = (framesSkipped + 1) % framesToSkip;
        if (framesSkipped == 0)
            currentFrame = (currentFrame + 1) % animations.size();
        graphicsContext.drawImage(animations.get(currentFrame), drawX+MapScene.mapX, drawY+MapScene.mapY);
    }

}

package scenes;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Game;
import main.GameLoop;
import models.GameScene;

import java.util.ArrayList;


public class MapScene extends GameScene {
    //TODO: Add map switching

    Canvas canvas;
    GraphicsContext graphics;
    int tileAnimationCounter;
    int tilePosX;
    int tilePosY;

    /*
     * RENDER STACK
     *
     * Main Map Layer -> static image
     * Animated Tiles Layer -> tile map
     * Sprites -> tile map
     * Foreground Layer -> static image? (maybe tile map as well, not too sure though, will discuss)
     * Lighting Layer -> layer with alpha
     *
     * TODO :
     * need to calculate fixed x y position of player sprite (need to know what resolution we are going to be using)
     * code cleanup
     */

    private void baseLayer(GraphicsContext graphicsContext, Image baseLayer, int x, int y){
        graphicsContext.drawImage(baseLayer, x, y);
    }

    private void animatedLayer(GraphicsContext graphicsContext, ArrayList<Image> tiles, int tilesize, int mapSize, int[][] mapData, int x, int y){
        if(GameLoop.frameNumber > 30) {
            GameLoop.frameNumber = 0;
            tileAnimationCounter++;
            if (tileAnimationCounter > 2) {
                tileAnimationCounter = 0;
            }
        }

        // reset position per call

        tilePosY = 0;
        tilePosX = 0;
        for(int mapX=0; mapX<mapData.length; mapX++) {

            for(int mapY=0; mapY<mapData[x].length; mapY++) {
                if(mapData[mapX][mapY] == 21){
                    int tile = (mapData[mapX][mapY]-1) + tileAnimationCounter;
                    graphicsContext.drawImage(tiles.get(tile), (tilePosX+x), (tilePosY+y));

                }

                tilePosX += tilesize;
            }

            tilePosY += tilesize;
            tilePosX = 0;
            if(tilePosY > mapSize){
                tilePosY = 0;
            }

        }

    }

    private void sprites(GraphicsContext graphicsContext, Image sprite, int x, int y){
        graphicsContext.drawImage(sprite, x, y);
    }

    private void foregroundLayer(GraphicsContext graphicsContext, ArrayList<Image> tileMaps, int[][] mapData, int x, int y){

    }

    private void lightingLayer(GraphicsContext graphicsContext, Image lighting, int[][] mapData, int x, int y){
        graphicsContext.drawImage(lighting, x, y);
    }


    public MapScene() {
        Pane root = new Pane();
        scene = new Scene(root, Game.WIDTH, Game.HEIGHT);

        canvas = new Canvas(Game.WIDTH, Game.HEIGHT);
        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();

        //TODO: Load assets (sprites, world map)
    }

    @Override
    public void draw() {
        /*graphics.setFill(new Color(.1, .1, .11, 1));
        graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        graphics.setFill(new Color(.5, .3, .34, 1));
        graphics.fillRect(Game.WIDTH / 2f, 50, 20, 20);*/

        //TODO: Display sprites and current map
    }
}
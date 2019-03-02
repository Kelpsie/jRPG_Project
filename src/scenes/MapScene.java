package scenes;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import loader.ImageLoader;
import loader.TextLoader;
import main.Game;
import main.GameLoop;
import models.GameMap;
import models.GameScene;

import java.util.ArrayList;


public class MapScene extends GameScene {
    //TODO: Add map switching

    Canvas canvas;
    GraphicsContext graphics;
    int tileAnimationCounter;
    int tilePosX;
    int tilePosY;
    ArrayList<Image> tiles = new ArrayList<>();
    Image mapImage;
    int mapData[][] = new int[32][32];

    GameMap map;
    int playerX = 0, playerY = 0;

    /*int mapData[][] = {{1,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,2,},
            {16,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,16,},
            {16,4,10,10,10,4,10,10,10,4,10,10,10,4,10,10,10,4,4,4,21,21,21,21,4,4,4,4,4,4,4,16,},
            {16,4,4,10,4,4,10,4,4,4,10,4,4,4,4,10,4,4,4,4,21,21,21,21,4,4,4,4,4,4,4,16,},
            {16,4,4,10,21,21,10,10,4,4,10,10,10,4,4,10,4,4,4,4,21,21,21,21,4,4,4,4,4,4,4,16,},
            {16,4,4,10,21,21,10,4,4,4,4,4,10,4,4,10,4,4,4,4,21,21,21,21,4,4,4,4,4,4,4,16,},
            {16,4,4,10,4,4,10,10,10,4,10,10,10,4,4,10,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,16,},
            {16,4,10,10,10,4,10,10,4,4,4,4,4,4,4,4,4,4,4,4,4,4,21,21,4,4,4,4,4,4,4,16,},
            {16,4,10,4,10,4,4,10,4,4,4,4,4,4,4,4,4,4,4,4,4,4,21,21,4,4,4,4,4,4,4,16,},
            {16,4,10,4,10,4,4,10,4,4,4,4,21,21,21,21,4,4,4,4,4,4,4,4,4,4,4,21,21,21,4,16,},
            {16,4,10,4,10,4,4,10,4,4,4,4,21,4,4,21,4,4,4,4,4,4,4,4,4,4,4,21,21,21,4,16,},
            {16,4,10,10,10,4,10,10,10,4,4,4,21,4,4,21,4,4,4,4,4,4,4,4,4,4,4,21,21,21,4,16,},
            {16,4,4,4,4,4,4,4,4,4,4,4,21,21,21,21,4,4,4,4,5,5,4,4,4,4,4,4,4,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,6,6,4,4,4,12,4,12,4,12,4,16,},
            {16,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,10,10,4,4,4,4,12,4,12,4,12,16,},
            {16,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,11,11,4,4,4,12,4,12,4,12,4,16,},
            {16,4,4,4,4,4,4,4,4,21,4,4,21,4,4,4,5,4,4,4,4,4,4,4,4,5,12,4,12,4,12,16,},
            {16,4,4,4,4,4,5,4,4,4,21,21,4,4,4,4,6,4,4,4,4,4,4,4,4,6,4,12,4,12,4,16,},
            {16,4,4,4,4,4,6,4,4,4,21,21,4,4,4,4,10,4,4,4,4,4,4,4,4,10,12,4,12,4,12,16,},
            {16,4,4,4,4,4,10,4,4,21,4,4,21,4,4,4,11,4,4,4,5,5,4,4,4,11,4,4,4,4,4,16,},
            {16,4,4,4,4,4,11,4,4,4,4,4,4,4,4,4,4,4,4,4,6,6,4,4,4,4,4,4,4,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,10,10,4,4,4,4,4,17,17,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,11,11,4,4,4,4,4,18,18,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,5,4,4,4,4,4,4,4,4,5,4,19,19,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,6,4,8,8,4,4,4,4,4,6,4,20,20,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,5,4,4,4,4,4,10,4,8,8,4,4,4,4,4,10,4,4,4,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,6,4,4,4,4,4,11,4,4,4,8,8,4,4,4,11,4,4,4,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,10,4,4,4,4,4,4,4,4,4,8,8,4,4,4,4,4,4,4,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,11,4,4,4,4,4,4,4,4,4,4,4,8,8,4,4,4,4,4,4,4,16,},
            {16,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,8,8,4,4,4,4,4,4,4,16,},
            {9,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,3}};*/


    //public static Image baseMap = ImageLoader.loadImage("file:assets/testmap.png");
    /*
     * RENDER STACK
     *
     * Main GameMap Layer -> static image
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

    private void baseLayer(GraphicsContext graphicsContext, ArrayList<Image> tiles, int tilesize, int mapSize, int[][] mapData, int x, int y){

        tilePosX = 0;
        tilePosY = 0;
        for(int mapY=0; mapY<mapData.length; mapY++) {
            for(int mapX=0; mapX<mapData.length; mapX++) {
                graphicsContext.drawImage(tiles.get((mapData[mapY][mapX])-1), (tilePosX+x), (tilePosY+y));
                tilePosX += tilesize;
            }
            tilePosY += tilesize;
            tilePosX = 0;
            if(tilePosY > mapSize){
                tilePosY = 0;
            }

        }

    }


    // this should be re written to work with single dimension array

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
        for(int mapY=0; mapY<mapData.length; mapY++) {
            for(int mapX=0; mapX<mapData.length; mapX++) {
                if(mapData[mapY][mapX] == 21){
                    int tile = (mapData[mapY][mapX]-1) + tileAnimationCounter;
                    graphicsContext.drawImage(tiles.get(tile), ((mapX * tilesize)+x), ((mapY * tilesize)+y));

                }

                tilePosX += tilesize;
            }

            tilePosY += tilesize;
            tilePosX = 0;
            if(tilePosY > mapSize){
                tilePosY = 0;
                System.out.println("What?");
            }

        }

    }

    private void sprites(GraphicsContext graphicsContext, Image sprite, int x, int y){
        graphicsContext.drawImage(sprite, x, y);
    }

    private void foregroundLayer(GraphicsContext graphicsContext, Image foreground, int[][] mapData, int x, int y){
        graphicsContext.drawImage(foreground, x, y);
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
        ImageLoader.readTileMap("assets/maptilestest.png", tiles, 32);
        mapImage = ImageLoader.loadImage("file:assets/testmap.png");
        TextLoader.convertTo2D(TextLoader.readText(), mapData);

        try {
            map = new GameMap("testmap.tmx");
        } catch (Exception e) { e.printStackTrace();}

    }

    @Override
    public void draw() {
        baseLayer(graphics, mapImage, 0, 0);
        //baseLayer(graphics, tiles, 32, 1024, mapData, 0,0);
        animatedLayer(graphics, tiles, 32, 1024, mapData, 0,0);
        GameLoop.frameNumber++;
    }
}
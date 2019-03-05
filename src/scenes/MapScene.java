package scenes;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import loader.ImageLoader;
import loader.TextLoader;
import main.Game;
import main.GameLoop;
import models.Character;
import models.GameMap;
import models.GameScene;

import java.util.ArrayList;


public class MapScene extends GameScene {

    ArrayList<KeyCode> keys = new ArrayList<>();

    Character player;

    Canvas canvas;
    GraphicsContext graphics;
    //Color background = Color.rgb(33, 33, 33, 1);

    int tileAnimationCounter;
    ArrayList<Image> tiles = new ArrayList<>();
    Image mapImage;
    int mapData[][] = new int[32][32];

    GameMap map;
    int mapX = 0, mapY = 0;
    final int PLAYERSTARTX = 20, PLAYERSTARTY = 12;
    int playerX = PLAYERSTARTX, playerY = PLAYERSTARTY;
    int mapDirX = 0, mapDirY = 0;


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

    // this should be re written to work with single dimension array

    private void animatedLayer(GraphicsContext graphicsContext, ArrayList<Image> tiles, int tilesize, int mapSize, int x, int y){
        if(GameLoop.frameNumber > 15) {
            GameLoop.frameNumber = 0;
            tileAnimationCounter++;
            if (tileAnimationCounter > 2) {
                tileAnimationCounter = 0;
            }
        }

        // reset position per call

        for(int mapY=0; mapY<mapData.length; mapY++) {
            for(int mapX=0; mapX<mapData.length; mapX++) {
                int tile = map.tileAt("Main Map", mapX, mapY);
                if (tile == 21) {
                    //Why is this -1 necessary?
                    int toDraw = (tile - 1) + tileAnimationCounter;
                    graphicsContext.drawImage(tiles.get(toDraw), ((mapX * tilesize)+x), ((mapY * tilesize)+y));

                }
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


    /**
     * CONSTRUCTOR
     */

    public MapScene() {
        Pane root = new Pane();
        scene = new Scene(root, Game.WIDTH, Game.HEIGHT);

        canvas = new Canvas(Game.WIDTH, Game.HEIGHT);
        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();
        player = new Character(graphics, 0);
        ImageLoader.readTileMap("assets/maptilestest.png", tiles, 32);
        mapImage = ImageLoader.loadImage("file:assets/testmap.png");


        try {
            map = new GameMap("testmap.tmx");
        } catch (Exception e) { e.printStackTrace();}

        scene.setOnKeyPressed(event -> {
            if (!keys.contains(event.getCode()))
                keys.add(event.getCode());
        });
        scene.setOnKeyReleased(event -> {
            if (keys.contains(event.getCode()))
                keys.remove(event.getCode());
        });
    }

    @Override
    public void update(double delta) {


        if (mapDirX != 0 || mapDirY != 0) {
            mapX += (mapDirX);
            mapY += (mapDirY);


            // If we've moved the width/height of one tile, stop moving
            if (mapX % map.tileSize == 0 && mapY % map.tileSize == 0) {
                mapDirX = 0; mapDirY = 0;
            }
        }
        if (mapDirX == 0 && mapDirY == 0) {
            if (keys.contains(KeyCode.UP))          mapDirY = 1;
            else if (keys.contains(KeyCode.DOWN))   mapDirY = -1;
            else if (keys.contains(KeyCode.LEFT))   mapDirX = 1;
            else if (keys.contains(KeyCode.RIGHT))  mapDirX = -1;



            // If player would collide with the map, stop moving
            // Subtract here instead of add because the map moves left as the player moves right, etc
            if (map.collidable(playerX - mapDirX, playerY - mapDirY)) {
                mapDirX = 0; mapDirY = 0;
            } else {
                playerX -= mapDirX; playerY -= mapDirY;

            }
        }
    }

    @Override
    public void draw() {
        // Clear screen
        //graphics.setFill(background);
        graphics.fillRect(0, 0, scene.getWidth(), scene.getHeight());


        baseLayer(graphics, mapImage, mapX, mapY);
        //baseLayer(graphics, tiles, 32, 1024, mapData, 0,0);
        animatedLayer(graphics, tiles, 32, 1024, mapX,mapY);

        // Simple player sprite
        graphics.setFill(Color.PAPAYAWHIP);
        player.drawPlayer((PLAYERSTARTX * map.tileSize+2), (PLAYERSTARTY * map.tileSize+2));
        //graphics.fillRect(PLAYERSTARTX * map.tileSize+2, PLAYERSTARTY * map.tileSize+2, 28, 28 );


        GameLoop.frameNumber++;
        if(Math.abs(mapDirX) == 1 || Math.abs(mapDirY) == 1){
            Character.charFrameNumber++;
        } else {
            if (Character.animationCounter > 0){
                Character.animationCounter = 0;
            }
        }
        //System.out.println(Character.charFrameNumber);
    }
}
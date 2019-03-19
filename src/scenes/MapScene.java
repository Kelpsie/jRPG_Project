package scenes;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import loader.ImageLoader;
import loader.TextLoader;
import main.Game;
import main.GameLoop;
import models.Character;
import models.GameMap;
import models.GameScene;

import java.util.ArrayList;
import java.util.LinkedList;


public class MapScene extends GameScene {

    ArrayList<KeyCode> keys = new ArrayList<>();

    Character player;

    StackPane root;
    Canvas canvas;
    GraphicsContext graphics;
    //Color background = Color.rgb(33, 33, 33, 1);

    int tileAnimationCounter;
    ArrayList<Image> tiles = new ArrayList<>();
    Image mapImage;

    GameMap map;
    int mapX = 0, mapY = 0;
    final int PLAYERSTARTX = 19, PLAYERSTARTY = 12;
    int playerX = PLAYERSTARTX, playerY = PLAYERSTARTY;
    int mapDirX = 0, mapDirY = 0;

    LinkedList<Label> notificationLabels;
    LinkedList<String> notificationQueue;


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
     * code cleanup
     */



    private void baseLayer(GraphicsContext graphicsContext, Image baseLayer, int x, int y){
        graphicsContext.drawImage(baseLayer, x, y);
    }

    private void animatedLayer(GraphicsContext graphicsContext, ArrayList<Image> tiles, int tilesize, int mapSize, int x, int y){
        if(GameLoop.frameNumber > 15) {
            GameLoop.frameNumber = 0;
            tileAnimationCounter++;
            if (tileAnimationCounter > 2) {
                tileAnimationCounter = 0;
            }
        }

        // reset position per call

        for(int mapY=0; mapY<map.height; mapY++) {
            for(int mapX=0; mapX<map.width; mapX++) {
                int tile = map.tileAt("Main Map", mapX, mapY); // <- change this to use a diff layer
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

    private void addNotification(String s) {
        Label nLabel = new Label(s);
        notificationLabels.add(nLabel);
        nLabel.getStyleClass().add("notification");
        nLabel.setTranslateY(24 + 8);
        root.getChildren().add(nLabel);
        root.setAlignment(nLabel, Pos.BOTTOM_RIGHT);

        for (Label note: notificationLabels) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(250), note);
            tt.setByY(-(24+8));
            tt.play();
        }

        FadeTransition fadeIn = new FadeTransition(Duration.millis(250), nLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), nLabel);
        fadeOut.setToValue(0);

        SequentialTransition sq = new SequentialTransition(
                fadeIn,
                new PauseTransition(Duration.seconds(5)),
                fadeOut
        );
        sq.setOnFinished(e -> root.getChildren().remove(notificationLabels.pop()));
        sq.play();
    }


    /**
     * CONSTRUCTOR
     */

    public MapScene() {
        root = new StackPane();
        scene = new Scene(root, Game.WIDTH, Game.HEIGHT);
        scene.getStylesheets().add("styles/map.css");

        canvas = new Canvas(Game.WIDTH, Game.HEIGHT);
        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();
        player = new Character(graphics, 0);

        ImageLoader.readTileMap("assets/maptilestest.png", tiles, 32);
        mapImage = ImageLoader.loadImage("file:assets/testmap.png");

        try {
            map = new GameMap("testmap.tmx");
        } catch (Exception e) { e.printStackTrace();}


        notificationQueue = new LinkedList<>();
        notificationLabels = new LinkedList<>();
        Timeline doNotes = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            if (notificationQueue.size() > 0)
                addNotification(notificationQueue.pop());
        }));
        doNotes.setCycleCount(-1);
        doNotes.play();

        scene.setOnKeyPressed(event -> {
            if (!keys.contains(event.getCode()))
                keys.add(event.getCode());
            //TODO: Remove temporary notification test code
            if (event.getCode() == KeyCode.PERIOD)
                notificationQueue.push("Player X: " + playerX);
        });
        scene.setOnKeyReleased(event -> {
            if (keys.contains(event.getCode()))
                keys.remove(event.getCode());
        });
    }

    @Override
    public void update(double delta) {


        if (mapDirX != 0 || mapDirY != 0) {
            mapX += (mapDirX * 2);
            mapY += (mapDirY * 2);


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
        graphics.fillRect(0, 0, scene.getWidth(), scene.getHeight());

        baseLayer(graphics, mapImage, mapX, mapY);
        animatedLayer(graphics, tiles, 32, 1024, mapX,mapY);

        // Simple player sprite
        player.drawPlayer((PLAYERSTARTX * map.tileSize), (PLAYERSTARTY * map.tileSize));


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
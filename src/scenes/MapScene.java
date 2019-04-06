package scenes;

import enemies.SquareEnemy;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import loader.ImageLoader;
import main.Game;
import main.GameLoop;
import models.*;
import skills.RangedAttack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;


public class MapScene extends GameScene {

    ArrayList<KeyCode> keys = new ArrayList<>();
    ArrayList<Image> tiles = new ArrayList<>();
    LinkedList<Label> notificationLabels;
    public static LinkedList<String> notificationQueue;

    StackPane root;
    Canvas canvas;
    GraphicsContext graphics;

    public static Player player;
    public static GameMap map;
    Image mapImage;

    int tileAnimationCounter;
    public static int mapX, mapY;
    final int PLAYERSTARTX = 20, PLAYERSTARTY = 10;
    int mapDirX = 0, mapDirY = 0;
    boolean playerTurn = false;


    static ArrayList<Enemy> enemies;
    static HashMap<String, Skill> skills;
    static String currentSkill = "Ranged Attack";

    public static Random random = new Random();


    /*
     * RENDER STACK
     *
     * Main GameMap Layer -> static image
     * Animated Tiles Layer -> tile map
     * Sprites -> tile map
     * Foreground Layer -> static image
     * Lighting Layer -> layer with alpha
     *
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
                    int toDraw = (tile - 1) + tileAnimationCounter;
                    graphicsContext.drawImage(tiles.get(toDraw), ((mapX * tilesize)+x), ((mapY * tilesize)+y));

                }
            }
        }
    }

    public static int distance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static int[] screenToMap(int x, int y) {
        return new int[]{(x - mapX)/map.tileSize, (y - mapY)/map.tileSize};
    }
    public static int[] mapToScreen(int x, int y) {
        return new int[]{(x*map.tileSize + mapX), (y*map.tileSize + mapY)};
    }

    public static Enemy enemyAt(int x, int y) {
        for (Enemy e: enemies) {
            if (e.posX == x && e.posY == y)
                return e;
        }
        return null;
    }

    public static boolean walkable(int x, int y) {

        return (!map.collidable(x, y) &&
                enemyAt(x, y) == null &&
                (player.posX != x || player.posY != y));
    }

    private static void pruneEnemies() {
        ArrayList<Enemy> toKill = new ArrayList<>();
        for (Enemy e: enemies) {
            if (e.hp <= 0)
                toKill.add(e);
        }
        enemies.removeAll(toKill);
    }

    void endPlayerTurn() {
        playerTurn = false;
        for (Enemy e : enemies) e.act();
        for (Skill s : skills.values()) s.turnsSinceUsed += 1;
    }

    private void addNotification(String s) {
        Label nLabel = new Label(s);
        notificationLabels.add(nLabel);
        nLabel.getStyleClass().add("notification");
        nLabel.setTranslateY(27);
        root.getChildren().add(nLabel);
        root.setAlignment(nLabel, Pos.BOTTOM_RIGHT);

        for (Label note: notificationLabels) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(125), note);
            tt.setByY(-(27));
            tt.play();
        }

        FadeTransition fadeIn = new FadeTransition(Duration.millis(125), nLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        FadeTransition fadeOut = new FadeTransition(Duration.millis(125), nLabel);
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
        player = new Player(graphics, 0, PLAYERSTARTX, PLAYERSTARTY);

        ImageLoader.readTileMap("assets/maptilestest.png", tiles, 32);
        mapImage = ImageLoader.loadImage("file:assets/testmap.png");

        try {
            map = new GameMap("testmap.tmx");
        } catch (Exception e) { e.printStackTrace();}

        int[] mapPos = mapToScreen(PLAYERSTARTX, PLAYERSTARTY);
        mapX = ((Game.WIDTH / 2)/map.tileSize)*map.tileSize - mapPos[0];
        mapY = ((Game.HEIGHT / 2)/map.tileSize)*map.tileSize - mapPos[1];


        notificationQueue = new LinkedList<>();
        notificationLabels = new LinkedList<>();
        Timeline doNotes = new Timeline(new KeyFrame(Duration.millis(250), e -> {
            if (notificationQueue.size() > 0)
                addNotification(notificationQueue.pop());
        }));
        doNotes.setCycleCount(-1);
        doNotes.play();

        scene.setOnKeyPressed(event -> {
            if (!keys.contains(event.getCode()))
                keys.add(event.getCode());
        });
        scene.setOnKeyReleased(event -> {
            if (keys.contains(event.getCode()))
                keys.remove(event.getCode());
        });

        //TODO: Click code is all test stuff
        canvas.setOnMouseClicked(event -> {
            int[] pos = screenToMap((int)event.getSceneX(), (int)event.getSceneY());
            notificationQueue.add("X: " + pos[0] + " Y: " + pos[1]);
            //for (Enemy e: enemies) if (e.posX == pos[0] && e.posY == pos[1]) e.move(new Random().nextInt(4));
            if (playerTurn) {
                if (skills.get(currentSkill).canUse(pos[0], pos[1])) {
                    skills.get(currentSkill).use(pos[0], pos[1]);
                    notificationQueue.add("Used " + currentSkill );
                    endPlayerTurn();
                } else {
                    notificationQueue.add("Invalid position for " + currentSkill);
                }
            }
        });


        //TODO: Remove enemy test code
        enemies = new ArrayList<>();
        enemies.add(new SquareEnemy(graphics, "assets/Enemy_1.png", 32, 10, 22, 6, 0));
        enemies.add(new SquareEnemy(graphics, "assets/Enemy_1.png", 32, 10, 20, 9, 0));
        enemies.add(new SquareEnemy(graphics, "assets/Enemy_1.png", 32, 10, 18, 15, 0));
        enemies.add(new SquareEnemy(graphics, "assets/Enemy_1.png", 32, 10, 20, 11, 0));
        enemies.add(new SquareEnemy(graphics, "assets/Enemy_1.png", 32, 10, 16, 5, 0));
        enemies.add(new SquareEnemy(graphics, "assets/Enemy_1.png", 32, 10, 14, 9, 0));
        enemies.add(new SquareEnemy(graphics, "assets/Enemy_1.png", 32, 10, 13, 20, 0));
        enemies.add(new SquareEnemy(graphics, "assets/Enemy_1.png", 32, 10, 26, 17, 0));

        skills = new HashMap<>();
        skills.put("Ranged Attack", new RangedAttack());

    }




    @Override
    public void update(double delta) {

        //TODO: Split this monster function up a bit. Jesus.

        // playerTurn is set to false when the player has done an action
        // playerTurn is false if any Enemy has work to do next frame
        if (!playerTurn) {
            pruneEnemies();
            for (int i = 0; i < enemies.size(); i++) {
                Enemy curEnemy = enemies.get(i);
                if (curEnemy.update()) {
                    playerTurn = false;
                    break;
                } else {
                    curEnemy.canAct = false;
                }
                playerTurn = true;
            }
        } else {
            if (mapDirX != 0 || mapDirY != 0) {
                mapX += (mapDirX * 2);
                mapY += (mapDirY * 2);


                // If we've moved the width/height of one tile, stop moving
                if (mapX % map.tileSize == 0 && mapY % map.tileSize == 0) {
                    player.posX -= mapDirX; player.posY -= mapDirY;
                    mapDirX = 0; mapDirY = 0;
                    endPlayerTurn();
                }
            }
            if (mapDirX == 0 && mapDirY == 0 && playerTurn) {
                if (keys.contains(KeyCode.UP))          mapDirY = 1;
                else if (keys.contains(KeyCode.DOWN))   mapDirY = -1;
                else if (keys.contains(KeyCode.LEFT))   mapDirX = 1;
                else if (keys.contains(KeyCode.RIGHT))  mapDirX = -1;



                // If player would collide with the map or an enemy, don't move
                // Subtract here instead of add because the map moves left as the player moves right, etc
                if (!walkable(player.posX - mapDirX, player.posY - mapDirY)) {
                    mapDirX = 0; mapDirY = 0;
                }
            }
        }
    }

    @Override
    public void draw() {
        // Clear screen
        graphics.fillRect(0, 0, scene.getWidth(), scene.getHeight());

        baseLayer(graphics, mapImage, mapX, mapY);
        animatedLayer(graphics, tiles, 32, 1024, mapX,mapY);
        player.draw(((Game.WIDTH / 2)/map.tileSize)*map.tileSize, ((Game.HEIGHT / 2)/map.tileSize)*map.tileSize);

        GameLoop.frameNumber++;
        if(Math.abs(mapDirX) == 1 || Math.abs(mapDirY) == 1){
            Player.charFrameNumber++;
        } else {
            if (Player.animationCounter > 0){
                Player.animationCounter = 0;
            }
        }

        for (Enemy enemy : enemies) {
            enemy.draw();
        }
    }
}
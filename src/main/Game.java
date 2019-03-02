package main;

import javafx.application.Application;
import javafx.stage.Stage;
import models.GameScene;
import scenes.BattleScene;
import scenes.MapScene;
import scenes.OpeningScene;

import java.util.Hashtable;

public class Game extends Application {

    public static Stage stage;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    //store scenes and current scene information
    public static Hashtable<String, GameScene> scenes;
    public static String currentSceneName;
    public static GameScene currentScene;
    static OpeningScene openingScene;

    GameLoop gameLoop;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("The Name Of Our Game, I Guess");

        gameLoop = new GameLoop();

        openingScene = new OpeningScene();

        // Make a new instance of every scene in the game
        scenes = new Hashtable<>();
        scenes.put("Map", new MapScene());
        scenes.put("Battle", new BattleScene());

        // Go to Opening scene
        restart();

        stage.show();
        gameLoop.start();
    }

    public static void restart() {
        currentScene = null;
        currentSceneName = null;
        openingScene.start();
    }

    public static void setScene(String name) {
        GameScene s = scenes.get(name);
        if (s != null) {
            currentSceneName = name;
            currentScene = s;
            stage.setScene(s.getScene());
        }
    }




    public static void main(String[] args) { launch(args); }
}

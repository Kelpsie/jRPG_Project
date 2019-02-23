package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.GameScene;
import scenes.BattleScene;
import scenes.MapScene;
import scenes.OpeningScene;

import java.util.Hashtable;
import java.util.Map;

public class Game extends Application {

    public static Stage stage;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    public static Hashtable<String, GameScene> scenes;
    public static String currentSceneName;
    public static GameScene currentScene;

    GameLoop gameLoop;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("The Name Of Our Game, I Guess");

        gameLoop = new GameLoop();

        // Make a new instance of every scene in the game
        scenes = new Hashtable<>();
        scenes.put("Opening", new OpeningScene());
        scenes.put("Map", new MapScene());
        scenes.put("Battle", new BattleScene());

        // Game starts in the "Opening" scene.
        setScene("Opening");

        stage.show();
        gameLoop.start();
    }

    public static void setScene(String name) {
        GameScene s = scenes.get(name);
        if (s != null) {
            System.out.println("Changing scene to " + name);
            currentSceneName = name;
            currentScene = s;
            stage.setScene(s.getScene());
        }
    }




    public static void main(String[] args) { launch(args); }
}

package main;

import audio.AudioHandler;
import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import loader.SaveGame;
import models.GameScene;
import scenes.BattleScene;
import scenes.MapScene;
import scenes.OpeningScene;

import java.io.IOException;
import java.util.Hashtable;

public class Game extends Application {

    public static Stage stage;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    //store scenes and current scene information
    public static MediaPlayer bgAudio;
    public static Hashtable<String, GameScene> scenes;
    public static String currentSceneName;
    public static GameScene currentScene;
    static OpeningScene openingScene;

    GameLoop gameLoop;

    @Override
    public void start(Stage primaryStage) {
        try {
            SaveGame.createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioHandler.initAudiohandler();
        Font.loadFont(getClass().getResourceAsStream("/styles/VT220-mod.ttf"), 16);

        stage = primaryStage;
        stage.setTitle("The Name Of Our Game, I Guess");

        gameLoop = new GameLoop();

        openingScene = new OpeningScene();

        // Make a new instance of every scene in the game
        scenes = new Hashtable<>();
        scenes.put("GameMap", new MapScene());
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

package models;

import javafx.scene.Scene;

import java.util.HashSet;

public abstract class GameScene {
    public Scene scene;
    public HashSet<String> keys;

    public GameScene() { }

    public Scene getScene() { return scene; }
    public abstract void draw();

    public abstract void update(double delta);
}

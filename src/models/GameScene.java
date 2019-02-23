package models;

import javafx.scene.Scene;

public abstract class GameScene {
    public Scene scene;
    public Scene getScene() { return scene; }
    public abstract void draw();
}

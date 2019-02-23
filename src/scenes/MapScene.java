package scenes;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Game;
import models.GameScene;


public class MapScene extends GameScene {

    Canvas canvas;
    GraphicsContext graphics;

    public MapScene() {
        Pane root = new Pane();
        canvas = new Canvas(Game.WIDTH, Game.HEIGHT);
        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();
        scene = new Scene(root, Game.WIDTH, Game.HEIGHT);

        //TODO: Load sprites and world map
    }

    @Override
    public void draw() {
        graphics.setFill(new Color(.3, .3, .4, 1));
        graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        //TODO: Display sprites and world map
    }
}
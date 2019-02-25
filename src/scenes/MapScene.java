package scenes;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Game;
import models.GameScene;


public class MapScene extends GameScene {
    //TODO: Add map switching

    Canvas canvas;
    GraphicsContext graphics;

    public MapScene() {
        Pane root = new Pane();
        scene = new Scene(root, Game.WIDTH, Game.HEIGHT);

        canvas = new Canvas(Game.WIDTH, Game.HEIGHT);
        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();

        //TODO: Load assets (sprites, world map)
    }

    @Override
    public void draw() {
        graphics.setFill(new Color(.1, .1, .11, 1));
        graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        graphics.setFill(new Color(.5, .3, .34, 1));
        graphics.fillRect(Game.WIDTH / 2f, 50, 20, 20);

        //TODO: Display sprites and current map
    }
}
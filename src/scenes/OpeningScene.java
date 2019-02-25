package scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import main.Game;
import models.GameScene;

public class OpeningScene {

    public Scene scene;

    public OpeningScene() {
        BorderPane root = new BorderPane();

        //TODO: Make a proper loading screen, current is a placeholder
        Button test = new Button("Enter Game");
        test.setOnMouseClicked(event -> {
            Game.setScene("Map");
        });
        root.setCenter(test);

        scene = new Scene(root, Game.WIDTH, Game.HEIGHT);
    }

    public void start() {
        Game.stage.setScene(scene);
    }
}

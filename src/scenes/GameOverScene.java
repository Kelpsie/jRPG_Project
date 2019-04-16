package scenes;

import audio.AudioHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import loader.SaveGame;
import main.Game;
import models.Player;


public class GameOverScene {

    public Scene scene;
    VBox vb = new VBox();
    BorderPane root;
    static Label GOText = new Label();
    Button quit;
    public static boolean isBossKill = false;

    public static void setText(){
        if(!isBossKill){
            GOText.setText("YOU DIED");
        } else {
            GOText.getStyleClass().add("END");
            GOText.setText("YOU WIN!");
            Player.posX = 4; //default values
            Player.posY = 33;
            SaveGame.writeData(); //save game if win
        }
    }

    public GameOverScene(){
        root = new BorderPane();
        root.setId("root");


        GOText.getStyleClass().add("GameOver");
        quit = new Button("QUIT");
        quit.setOnMouseEntered(e -> AudioHandler.playAudio("menuhit.wav"));
        quit.setOnMouseClicked(event ->{
            AudioHandler.playAudio("menuhit.wav");
            System.exit(0);
        });

        vb.getChildren().addAll(GOText, quit);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(50);
        root.setCenter(vb);
        scene = new Scene(root, Game.WIDTH, Game.HEIGHT);
        scene.getStylesheets().add("styles/opening.css");
    }


}

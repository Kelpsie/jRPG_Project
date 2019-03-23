package scenes;

import audio.AudioHandler;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import loader.GetFilePath;
import main.Game;
import models.GameScene;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class OpeningScene {

    public Scene scene;
    BorderPane root;
    Button test;
    Group player;
    double volume = 1;

    final int MAX_SHAPES = 80;
    int shapes = 1;
    Random rand = new Random();
    MediaPlayer mp0;
    MediaPlayer mp1;

    public OpeningScene() {


        root = new BorderPane();
        root.setId("root");

        root.setEffect(new Bloom(0.1));

        Label title = new Label("Untitled RPG");
        title.getStyleClass().add("text");
        root.setTop(title);

        Label members = new Label("Andrew McNeill\nThomas Racz");
        members.getStyleClass().add("text");
        root.setBottom(members);
        root.setAlignment(members, Pos.BOTTOM_RIGHT);


        player = new Group();
        root.getChildren().add(player);
        player.setTranslateX(Game.WIDTH / 2 - 200);
        player.setTranslateY(Game.HEIGHT / 2);

        Rectangle rect_1 = new Rectangle();
        rect_1.setId("rect_1");
        player.getChildren().add(rect_1);
        rect_1.setWidth(20);
        rect_1.setHeight(20);

        Rectangle rect_2 = new Rectangle();
        rect_2.setId("rect_2");
        player.getChildren().add(rect_2);
        rect_2.setWidth(6);
        rect_2.setHeight(6);
        rect_2.setX(10 - 3);
        rect_2.setY(10 - 3);


        RotateTransition playerRT = new RotateTransition();
        playerRT.setNode(player);
        playerRT.setFromAngle(45);
        playerRT.setToAngle(45 + 90);
        playerRT.setDuration(Duration.millis(500));

        SequentialTransition playerRST = new SequentialTransition(
                new PauseTransition(Duration.millis(250)),
                playerRT
        );
        playerRST.setCycleCount(Timeline.INDEFINITE);
        playerRST.play();




        VBox buttons = new VBox();
        VBox settingsBox = new VBox();
        buttons.setId("buttons");
        root.setCenter(buttons);
        buttons.setMaxWidth(Game.WIDTH / 4);
        settingsBox.setMaxWidth(Game.WIDTH / 4);

        Label volLabel = new Label("Volume 100%");


        Button volPlus = new Button("+");
        volPlus.setOnMouseClicked(volUp -> {
            volume += 0.1;
            volLabel.setText("Volume " + (Math.round(volume*100)) + "%");
            AudioHandler.setVolume(volume);
            AudioHandler.playAudio(1);
        });

        Button volMinus = new Button("-");
        volMinus.setOnMouseClicked(volDown -> {
            volume -= 0.1;
            volLabel.setText("Volume " + (Math.round(volume*100)) + "%");
            AudioHandler.setVolume(volume);
            AudioHandler.playAudio(1);
        });

        Button back = new Button("back");
        back.setOnMouseClicked(backEvent -> {
            buttons.setVisible(true);
            title.setVisible(true);
            members.setVisible(true);
            settingsBox.setVisible(false);
            root.setCenter(buttons);
        });



        test = new Button("Enter Game");
        test.setOnMouseClicked(event -> {
            AudioHandler.stopAudio(0);
            Game.setScene("GameMap");
        });
        test.setOnMouseEntered(new ButtonHover(test));

        Button b2 = new Button("Load Game");
        b2.setOnMouseEntered(new ButtonHover(b2));

        Button b3 = new Button("Settings");
        b3.setOnMouseEntered(new ButtonHover(b3));
        b3.setOnMouseClicked(settings ->{
            AudioHandler.playAudio(1);
            buttons.setVisible(false);
            title.setVisible(false);
            members.setVisible(false);
            settingsBox.setVisible(true);
            volLabel.setVisible(true);
            volMinus.setVisible(true);
            volPlus.setVisible(true);
            root.setCenter(settingsBox);
        });





        settingsBox.getChildren().addAll(volLabel, volPlus, volMinus, back);
        buttons.getChildren().addAll(test, b2, b3);

        scene = new Scene(root, Game.WIDTH, Game.HEIGHT);
        scene.getStylesheets().add("styles/opening.css");
        newShape();
        newShape();
        newShape();
        newShape();
        newShape();


    }
    class ButtonHover implements EventHandler<javafx.scene.input.MouseEvent> {
        Button button;
        ButtonHover(Button button) {this.button = button;}
        @Override
        public void handle(javafx.scene.input.MouseEvent event) {
            Bounds boundsInScene = button.localToScene(button.getBoundsInLocal());
            double x = boundsInScene.getMinX() - 40;
            double y = boundsInScene.getMinY() + (boundsInScene.getHeight() / 2);

            // I have no idea why already being at the location you're
            // pathing to breaks this so hard, but here's a workaround
            if ((Math.abs(player.getTranslateX() - x) < 12) &&
                (Math.abs(player.getTranslateY() - y) < 12))
                return;

            PathTransition movePlayer = new PathTransition();
            movePlayer.setNode(player);
            movePlayer.setDuration(Duration.millis(200));
            Path movePath = new Path();
            movePath.getElements().add(new MoveTo(player.getTranslateX()+10, player.getTranslateY()+10));
            movePath.getElements().add(new LineTo(x, y));
            movePlayer.setPath(movePath);
            movePlayer.play();
            AudioHandler.playAudio(1);


        }
    }

    public void newShape() {
        int duration = rand.nextInt(5000) + 5000;

        Shape shape;
        switch (rand.nextInt(4)) {
            case 0:
                Rectangle rect = new Rectangle();
                rect.setHeight(18);
                rect.setWidth(18);
                shape = rect;
                break;
            case 1:
                Circle circle = new Circle();
                circle.setRadius(10);
                shape = circle;
                break;
            case 2:
                Polygon pentagon = new Polygon();
                pentagon.getPoints().addAll(
                        20.0,0.0,
                        13.0,-10.0,
                        2.0,-6.0,
                        2.0,6.0,
                        13.0,10.0
                ); // Coordinates from https://www.mathopenref.com/coordpolycalc.html
                shape = pentagon;
                break;
            default:
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(
                        0.0,0.0,
                        20.0,0.0,
                        10.0,17.3205 );
                shape = triangle;
        }

        shape.setFill(Color.rgb(0, 0, 0, 0));
        shape.setStroke(Color.hsb(rand.nextInt(12)*30, 0.9, .9));
        shape.setStrokeWidth(3);
        root.getChildren().add(shape);
        shape.toBack();

        Path path = new Path();
        switch (rand.nextInt(4)) {
            case 0: // Top to Bottom
                path.getElements().add(new MoveTo(rand.nextDouble() * Game.WIDTH, -25));
                path.getElements().add(new LineTo(rand.nextDouble() * Game.WIDTH, Game.HEIGHT + 25));
                break;
            case 1: // Right to Left
                path.getElements().add(new MoveTo(Game.WIDTH + 25, rand.nextDouble() * Game.HEIGHT));
                path.getElements().add(new LineTo(-25, rand.nextDouble() * Game.HEIGHT));
                break;
            case 2: // Bottom to Top
                path.getElements().add(new MoveTo(rand.nextDouble() * Game.WIDTH, Game.HEIGHT + 25));
                path.getElements().add(new LineTo(rand.nextDouble() * Game.WIDTH, -25));
                break;
            case 3: // Left to Right
                path.getElements().add(new MoveTo(-25, rand.nextDouble() * Game.HEIGHT));
                path.getElements().add(new LineTo(Game.WIDTH + 25, rand.nextDouble() * Game.HEIGHT));
                break;
        }
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(duration));
        pathTransition.setNode(shape);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);


        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(shape);
        rotateTransition.setByAngle(rand.nextInt(1000));
        rotateTransition.setDuration(Duration.millis(duration));

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(shape);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setDuration(Duration.millis(rand.nextInt(500) + 1500));

        SequentialTransition fadeSequence = new SequentialTransition(
                new PauseTransition(Duration.millis(duration / 5)),
                fadeTransition
        );

        ParallelTransition parallelTransition = new ParallelTransition(
                pathTransition, rotateTransition, fadeSequence
        );

        parallelTransition.setOnFinished(e -> {
            root.getChildren().remove(pathTransition.getNode());
            newShape();
            if (shapes < MAX_SHAPES) {
                newShape();
                shapes++;
            }
        });

        parallelTransition.play();



    }

    public void start() {
        AudioHandler.playAudio(0);
        Game.stage.setScene(scene);


    }
}

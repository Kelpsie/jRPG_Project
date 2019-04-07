package HUD;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import models.Player;
import scenes.MapScene;

import static scenes.MapScene.notificationQueue;

public class MainHUD extends BorderPane {

    /**
     * TODO: Add skill selection, cleanup some stuff related to HUD in MapScene, somehow integrate cooldown timer animations?
     */

    private static final int buttonSize = 64;
    public static HBox hb = new HBox();
    private static BorderPane skillupograde = new BorderPane();

    public static Label health = new Label(Integer.toString(Player.hp));

    public static Button skill1 = new Button();
    public static Button skill2 = new Button();
    public static Button skill3 = new Button();
    public static Button tree = new Button();

    public static void initHUD(){
        health.getStyleClass().add("health");

        skill1.setMinHeight(buttonSize);
        skill1.setMinWidth(buttonSize);
        skill1.getStyleClass().add("skill1");

        skill2.getStyleClass().add("skill2");
        skill2.setMinHeight(buttonSize);
        skill2.setMinWidth(buttonSize);

        skill3.getStyleClass().add("skill3");
        skill3.setMinHeight(buttonSize);
        skill3.setMinWidth(buttonSize);

        tree.getStyleClass().add("tree");
        tree.setMinHeight(buttonSize);
        tree.setMinWidth(buttonSize);

        skill1.setOnMouseClicked(event -> {
            MapScene.currentSkill = "Ranged Attack";
            notificationQueue.add("Selected: " + MapScene.currentSkill);
        });
        skill2.setOnMouseClicked(event -> {
            MapScene.currentSkill = "Ranged Attack";
            notificationQueue.add("Selected: " + MapScene.currentSkill);
        });
        skill3.setOnMouseClicked(event -> {
            MapScene.currentSkill = "Ranged Attack";
            notificationQueue.add("Selected: " + MapScene.currentSkill);
        });

        hb.getChildren().addAll(health, skill1, skill2, skill3, tree);
        hb.setAlignment(Pos.BOTTOM_CENTER);

        //can make button pressed / release using node.arm() / node.disarm(), can use for cool down timers in some way.

    }

}

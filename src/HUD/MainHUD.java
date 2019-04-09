package HUD;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import models.Player;
import scenes.MapScene;
import skills.RangedAttack;

import static scenes.MapScene.notificationQueue;

public class MainHUD extends BorderPane {

    /**
     * TODO: Add skill selection, cleanup some stuff related to HUD in MapScene, somehow integrate cooldown timer animations?
     */

    private static final int buttonSize = 64;

    //bottom hud

    public static HBox hb = new HBox();
    private static HBox hbLower = new HBox();
    public static VBox BottomHUDVBox = new VBox();

    public static Label health = new Label(Integer.toString(Player.hp));

    private static Button skill1 = new Button();
    private static Button skill2 = new Button();
    private static Button skill3 = new Button();
    private static Button tree = new Button();

    public static Rectangle xpBar = new Rectangle();

    //skill upgrade overlay

    public static BorderPane skillUpgrade = new BorderPane();
    private static VBox vSkillBox = new VBox();
    private static HBox s1hbox = new HBox();
    private static HBox s2hbox = new HBox();
    private static HBox s3hbox = new HBox();

    private static Button back = new Button();
    private static Label s1Label = new Label("Ranged Attack: lvl " + RangedAttack.level);
    private static Label s2Label = new Label("Ranged Attack: lvl " + RangedAttack.level);
    private static Label s3Label = new Label("Ranged Attack: lvl " + RangedAttack.level);
    private static Label skillPoints = new Label("Skill Points Avail: " + Player.skillPoints);



    private static Button s1AddPoint = new Button();
    private static Button s2AddPoint = new Button();
    private static Button s3AddPoint = new Button();


    public static void initHUD(){

        health.setMinWidth(128);
        xpBar.setWidth(0);
        xpBar.setHeight(24);

        s1Label.getStyleClass().add("Skill1Label");
        s2Label.getStyleClass().add("Skill2Label");
        s3Label.getStyleClass().add("Skill3Label");

        s1Label.setMinWidth(384);
        s2Label.setMinWidth(384);
        s3Label.setMinWidth(384);

        s1Label.setPadding(new Insets(20,0,0,0));
        s2Label.setPadding(new Insets(20,0,0,0));
        s3Label.setPadding(new Insets(20,0,0,0));

        s1hbox.getChildren().addAll(s1Label, s1AddPoint);
        s2hbox.getChildren().addAll(s2Label, s2AddPoint);
        s3hbox.getChildren().addAll(s3Label, s3AddPoint);

        s1AddPoint.setMinHeight(buttonSize);
        s1AddPoint.setMinWidth(buttonSize);
        s1AddPoint.getStyleClass().add("add");

        s2AddPoint.setMinHeight(buttonSize);
        s2AddPoint.setMinWidth(buttonSize);
        s2AddPoint.getStyleClass().add("add");

        s3AddPoint.setMinHeight(buttonSize);
        s3AddPoint.setMinWidth(buttonSize);
        s3AddPoint.getStyleClass().add("add");

        s1AddPoint.setOnMouseClicked(event -> {
            if(Player.skillPoints > 0) {
                RangedAttack.level += 1;
                Player.skillPoints -= 1;
                skillPoints.setText("Skill Points Avail: " + Player.skillPoints);
                s1Label.setText("Ranged Attack: lvl " + RangedAttack.level);
            }
            System.out.println(RangedAttack.level);
            System.out.println(Player.skillPoints);
        });



        vSkillBox.getChildren().addAll(back, skillPoints, s1hbox, s2hbox, s3hbox);
        vSkillBox.setPadding(new Insets(32));
        vSkillBox.setSpacing(16);


        skillUpgrade.setCenter(vSkillBox);
        skillUpgrade.setVisible(false);
        skillUpgrade.getStyleClass().add("skills");
        skillPoints.getStyleClass().add("skillPoints");


        /*
            Bottom HUD
         */

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

        back.getStyleClass().add("back");
        back.setMinHeight(buttonSize);
        back.setMinWidth(buttonSize);



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

        tree.setOnMouseClicked(event -> {
            skillPoints.setText("Skill Points Avail: " + Player.skillPoints);
            s1Label.setText("Ranged Attack: lvl " + RangedAttack.level);
            s2Label.setText("Ranged Attack: lvl " + RangedAttack.level);
            s3Label.setText("Ranged Attack: lvl " + RangedAttack.level);
            skillUpgrade.setVisible(true);
        });

        back.setOnMouseClicked(event -> skillUpgrade.setVisible(false));

        hb.getChildren().addAll(health, skill1, skill2, skill3, tree);
        hbLower.getChildren().add(xpBar);
        BottomHUDVBox.getChildren().addAll(hb, hbLower);
        BottomHUDVBox.setAlignment(Pos.BOTTOM_CENTER);

        //can make button pressed / release using node.arm() / node.disarm(), can use for cool down timers in some way.

    }

}

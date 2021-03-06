package HUD;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import loader.SaveGame;
import models.Player;
import scenes.MapScene;
import skills.Blink;
import skills.Heal;
import skills.MeleeAttack;
import skills.RangedAttack;

import java.util.ArrayList;

import static scenes.MapScene.notificationQueue;
import static scenes.MapScene.root;

public class MainHUD extends BorderPane {

    /**
     * TODO: Add skill selection, cleanup some stuff related to HUD in MapScene, somehow integrate cooldown timer animations?
     */

    private static final int buttonSize = 64;

    //bottom hud

    public static HBox hb = new HBox();
    private static HBox hbLower = new HBox();
    public static VBox BottomHUDVBox = new VBox();

    public static Label health = new Label("HP:" + Player.hp);
    public static Label xp = new Label("XP:" + Player.xp + "/" + (int) Player.xpToNextLevel);
    public static Label level = new Label("Level:" + Player.level);
    static Label saveLabel = new Label("SAVE GAME");

    public static ToggleButton skill1 = new ToggleButton();
    public static ToggleButton skill2 = new ToggleButton();
    public static Button skill3 = new Button();
    public static ToggleButton skill4 = new ToggleButton();
    public static Button tree = new Button();

    public static Rectangle xpBar = new Rectangle();

    //skill upgrade overlay

    public static BorderPane skillUpgrade = new BorderPane();
    private static VBox vSkillBox = new VBox();
    private static VBox bottomLabelVBox = new VBox();
    private static VBox saveVBox = new VBox();
    private static HBox saveHBox = new HBox();
    private static HBox s1hbox = new HBox();
    private static VBox s1vbox = new VBox();
    private static VBox s2vbox = new VBox();
    private static HBox menuBar = new HBox();

    private static Button back = new Button();
    private static Button saveGame = new Button();
    private static Label s1Label = new Label("Ranged Attack: lvl 1");
    private static Label s2Label = new Label("Melee Attack lvl 1");
    private static Label s3Label = new Label("Restore Health: lvl 1");
    private static Label s4Label = new Label("Blink: lvl 1");
    private static Label skillPoints = new Label("Skill Points Avail: " + Player.skillPoints);



    private static Button s1AddPoint = new Button();
    private static Button s2AddPoint = new Button();
    private static Button s3AddPoint = new Button();
    private static Button s4AddPoint = new Button();

    private static final ToggleGroup tg = new ToggleGroup();


    public static void initHUD(){

        health.setPrefSize(192, 21);
        xp.setPrefSize(192, 21);
        level.setPrefSize(192, 21);
        xp.getStyleClass().add("HUDlabel");
        level.getStyleClass().add("HUDlabel");
        health.getStyleClass().add("HUDlabel");

        xpBar.setFill(Color.YELLOW);
        xpBar.setWidth(0);
        xpBar.setHeight(16);

        s1Label.getStyleClass().add("Skill1Label");
        s2Label.getStyleClass().add("Skill2Label");
        s3Label.getStyleClass().add("Skill3Label");
        s4Label.getStyleClass().add("Skill3Label");

        s1Label.setMinWidth(384);
        s2Label.setMinWidth(384);
        s3Label.setMinWidth(384);
        s4Label.setMinWidth(384);

        s1Label.setPadding(new Insets(20,0,0,0));
        s2Label.setPadding(new Insets(20,0,0,0));
        s3Label.setPadding(new Insets(20,0,0,0));
        s4Label.setPadding(new Insets(20,0,0,0));

        s1vbox.getChildren().addAll(s1Label, s2Label, s3Label, s4Label);
        s1vbox.setSpacing(22);
        s2vbox.getChildren().addAll(s1AddPoint, s2AddPoint, s3AddPoint, s4AddPoint);
        s1hbox.getChildren().addAll(s1vbox, s2vbox);
        s1hbox.setSpacing(20);

        s1AddPoint.setMinHeight(buttonSize);
        s1AddPoint.setMinWidth(buttonSize);
        s1AddPoint.getStyleClass().add("add");

        s2AddPoint.setMinHeight(buttonSize);
        s2AddPoint.setMinWidth(buttonSize);
        s2AddPoint.getStyleClass().add("add");

        s3AddPoint.setMinHeight(buttonSize);
        s3AddPoint.setMinWidth(buttonSize);
        s3AddPoint.getStyleClass().add("add");

        s4AddPoint.setMinHeight(buttonSize);
        s4AddPoint.setMinWidth(buttonSize);
        s4AddPoint.getStyleClass().add("add");

        saveGame.setMinHeight(buttonSize);
        saveGame.setMinWidth(buttonSize);
        saveGame.getStyleClass().add("saveButton");


        s1AddPoint.setOnMouseClicked(event -> {
            if(Player.skillPoints > 0) {
                MapScene.skills.get("Ranged Attack").level += 1;
                Player.skillPoints -= 1;
                skillPoints.setText("Skill Points Avail: " + Player.skillPoints);
                s1Label.setText("Ranged Attack: lvl " + MapScene.skills.get("Ranged Attack").level);
            }
        });
        s2AddPoint.setOnMouseClicked(event -> {
            if(Player.skillPoints > 0) {
                MapScene.skills.get("Melee Attack").level += 1;
                Player.skillPoints -= 1;
                skillPoints.setText("Skill Points Avail: " + Player.skillPoints);
                s2Label.setText("Melee Attack: lvl " + MapScene.skills.get("Melee Attack").level);
            }
        });
        s3AddPoint.setOnMouseClicked(event -> {
            if(Player.skillPoints > 0) {
                MapScene.skills.get("Heal").level += 1;
                Player.skillPoints -= 1;
                skillPoints.setText("Skill Points Avail: " + Player.skillPoints);
                s3Label.setText("Restore Health: lvl " + MapScene.skills.get("Heal").level);
            }
        });
        s4AddPoint.setOnMouseClicked(event -> {
            if(Player.skillPoints > 0) {
                MapScene.skills.get("Blink").level += 1;
                Player.skillPoints -= 1;
                skillPoints.setText("Skill Points Avail: " + Player.skillPoints);
                s4Label.setText("Blink lvl: " + MapScene.skills.get("Blink").level);
            }
        });

        Button game1 = new Button("GAME 1");
        Button game2 = new Button("GAME 2");
        Button game3 = new Button("GAME 3");
        game1.getStyleClass().add("saveGameButton");
        game2.getStyleClass().add("saveGameButton");
        game3.getStyleClass().add("saveGameButton");

        saveLabel.getStyleClass().add("saveTitle");

        saveGame.setOnMouseClicked(saveEvent ->{
            vSkillBox.setVisible(false);
            saveVBox.setVisible(true);
            saveHBox.setVisible(true);
            skillUpgrade.setCenter(saveVBox);

        });

        game1.setOnMouseClicked(saveGame1Event ->{
            if(MapScene.enemies.size() > 0){
                saveVBox.setVisible(false);
                saveHBox.setVisible(false);
                skillUpgrade.setVisible(false);
                notificationQueue.add("You can't save while there are enemies!");
            } else{
                SaveGame.pathName = "assets/data/000.dat";
                SaveGame.writeData();
                saveVBox.setVisible(false);
                saveHBox.setVisible(false);
                skillUpgrade.setVisible(false);
                notificationQueue.add("Saved Game!");
            }

        });

        /*
            this is like a wild forest where you cant find anything unless you have
            a map, or in this case ctrl + f :joy::joy:
         */

        game2.setOnMouseClicked(saveGame2Event ->{
            if(MapScene.enemies.size() > 0){
                saveVBox.setVisible(false);
                saveHBox.setVisible(false);
                skillUpgrade.setVisible(false);
                notificationQueue.add("You can't save while there are enemies!");
            } else{
                SaveGame.pathName = "assets/data/001.dat";
                SaveGame.writeData();
                saveVBox.setVisible(false);
                skillUpgrade.setVisible(false);
                notificationQueue.add("Saved Game!");
            }
        });

        game3.setOnMouseClicked(saveGame3Event ->{
            if(MapScene.enemies.size() > 0){
                saveVBox.setVisible(false);
                skillUpgrade.setVisible(false);
                notificationQueue.add("You can't save while there are enemies!");
            } else{
                SaveGame.pathName = "assets/data/002.dat";
                SaveGame.writeData();
                saveVBox.setVisible(false);
                skillUpgrade.setVisible(false);
                notificationQueue.add("Saved Game!");
            }
        });


        menuBar.getChildren().addAll(back, saveGame);
        menuBar.setSpacing(50);
        menuBar.setPadding(new Insets(150, 0,0,0));
        saveHBox.getChildren().addAll(game1, game2, game3);
        saveHBox.setPadding(new Insets(100,0,0,0));
        saveHBox.setSpacing(50);
        saveHBox.setAlignment(Pos.CENTER);
        saveVBox.getChildren().addAll(saveLabel, saveHBox);
        saveVBox.setAlignment(Pos.CENTER);
        saveVBox.setPadding(new Insets(0,0,300,0));
        vSkillBox.getChildren().addAll(skillPoints, s1hbox, menuBar);
        vSkillBox.setPadding(new Insets(32));
        vSkillBox.setSpacing(16);


        skillUpgrade.setCenter(vSkillBox);
        skillUpgrade.setVisible(false);
        skillUpgrade.getStyleClass().add("skills");
        skillPoints.getStyleClass().add("skillPoints");


        /*
            Bottom HUD
         */



        skill1.setMinHeight(buttonSize);
        skill1.setMinWidth(buttonSize);
        skill1.getStyleClass().add("skill1");

        skill2.getStyleClass().add("skill2");
        skill2.setMinHeight(buttonSize);
        skill2.setMinWidth(buttonSize);

        skill3.getStyleClass().add("skill3");
        skill3.setMinHeight(buttonSize);
        skill3.setMinWidth(buttonSize);

        skill4.getStyleClass().add("skill4");
        skill4.setMinHeight(buttonSize);
        skill4.setMinWidth(buttonSize);

        tree.getStyleClass().add("tree");
        tree.setMinHeight(buttonSize);
        tree.setMinWidth(buttonSize);

        back.getStyleClass().add("back");
        back.setMinHeight(buttonSize);
        back.setMinWidth(buttonSize);

        skill1.setToggleGroup(tg);
        skill2.setToggleGroup(tg);
        skill4.setToggleGroup(tg);

        skill1.setOnAction(event -> {
            if(skill1.isSelected()){
                MapScene.currentSkill = "Ranged Attack";
                notificationQueue.add("Selected: " + MapScene.currentSkill);
                root.requestFocus();
            } else {
                MapScene.currentSkill = null;
                notificationQueue.add("Selected: " + MapScene.currentSkill);
                root.requestFocus();
            }

        });
        skill2.setOnAction(event -> {
            if(skill2.isSelected()) {
                MapScene.currentSkill = "Melee Attack";
                notificationQueue.add("Selected: " + MapScene.currentSkill);
                root.requestFocus();
            } else {
                MapScene.currentSkill = null;
                notificationQueue.add("Selected: " + MapScene.currentSkill);
                root.requestFocus();
            }
        });
        skill3.setOnAction(event -> {
            Heal.use();
            root.requestFocus();
        });
        skill4.setOnAction(event -> {
            if(skill4.isSelected()){
                MapScene.currentSkill = "Blink";
                notificationQueue.add("Selected: " + MapScene.currentSkill);
                root.requestFocus();
            } else {
                MapScene.currentSkill = null;
                notificationQueue.add("Selected: " + MapScene.currentSkill);
                root.requestFocus();
            }

        });

        tree.setOnAction(event -> {
            if(!MainHUD.skillUpgrade.isVisible()){
                skillPoints.setText("Skill Points Avail: " + Player.skillPoints);
                s1Label.setText("Ranged Attack: lvl " + MapScene.skills.get("Ranged Attack").level);
                s2Label.setText("Melee Attack: lvl " + MapScene.skills.get("Melee Attack").level);
                s3Label.setText("Restore Health: lvl " + MapScene.skills.get("Heal").level);
                s4Label.setText("Blink lvl: " + MapScene.skills.get("Blink").level);
                skillUpgrade.setVisible(true);
                vSkillBox.setVisible(true);
                skillUpgrade.setCenter(vSkillBox);
            } else {
                skillUpgrade.setVisible(false);
                root.requestFocus();;
            }

        });

        back.setOnMouseClicked(event -> skillUpgrade.setVisible(false));

        bottomLabelVBox.getChildren().addAll(health, level, xp);

        hb.getChildren().addAll(bottomLabelVBox, skill1, skill2, skill3, skill4, tree);
        hbLower.getChildren().add(xpBar);
        BottomHUDVBox.getChildren().addAll(hb, hbLower);
        BottomHUDVBox.setAlignment(Pos.BOTTOM_CENTER);


    }

}

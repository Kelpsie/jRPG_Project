package main;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {


    @Override
    public void handle(long now) {
        if (Game.currentScene == null || Game.currentSceneName == "Opening")
            return;

        Game.currentScene.draw();
    }
}

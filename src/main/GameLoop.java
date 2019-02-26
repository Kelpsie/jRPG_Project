package main;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

    public static int frameNumber;

    // main loop

    @Override
    public void handle(long now) {
        if (Game.currentScene == null)
            return;

        Game.currentScene.draw();
    }
}


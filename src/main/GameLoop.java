package main;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

    public static int frameNumber;
    int drawCall = 0;
    // main loop

    @Override
    public void handle(long now) {
        if (Game.currentScene == null)
            return;

        if((drawCall++) %100 == 0) {
            System.out.println(System.nanoTime() - now);
        }
        Game.currentScene.draw();
    }
}


package skills;

import audio.AudioHandler;
import models.Player;
import models.Skill;
import scenes.MapScene;

public class Blink extends Skill {

    public Blink() {
        cooldown = 4;
        turnsSinceUsed = 9999;
    }

    int range() {
        return 2 + level;
    }

    @Override
    public boolean canUse(int x, int y) {
        System.out.println(turnsSinceUsed + " " + cooldown);
        if (turnsSinceUsed < cooldown) {
            MapScene.notificationQueue.add("Blink on cooldown");
            return false;
        }
        if (!MapScene.walkable(x, y)) {
            MapScene.notificationQueue.add("Location obstructed");
            return false;
        }
        if (MapScene.distance(Player.posX, Player.posY, x, y) > range()) {
            MapScene.notificationQueue.add("Out of range (" + range() + ")");
            return false;
        }

        return true;
    }

    @Override
    public void use(int x, int y) {
        MapScene.movePlayer(x, y);
        turnsSinceUsed = 0;
        AudioHandler.playAudio("blink.wav");
    }
}

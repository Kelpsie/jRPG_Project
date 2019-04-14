package skills;

import audio.AudioHandler;
import models.Enemy;
import models.Player;
import models.Skill;
import scenes.MapScene;

public class RangedAttack extends Skill {

    public RangedAttack() {
        cooldown = 3;
        turnsSinceUsed = 4;
    }

    int damage() {
        return 5 + (int)Math.pow(level, 1.5)*2;
    }

    int range() {
        return 3 + level;
    }

    @Override
    public boolean canUse(int x, int y) {
        if (turnsSinceUsed < cooldown) {
            MapScene.notificationQueue.add("Ranged Attack on cooldown");
            return false;
        }
        Enemy e = MapScene.enemyAt(x, y);
        if (e == null) {
            MapScene.notificationQueue.add("No enemy targeted");
            return false;
        }

        if (MapScene.distance(Player.posX, Player.posY, x, y) <= range()) {
            return true;
        }
        System.out.println(MapScene.distance(Player.posX, Player.posY, x, y));

        MapScene.notificationQueue.add("Enemy out of range (" + range() + ")");
        return false;
    }

    @Override
    public void use(int x, int y) {
        Enemy e = MapScene.enemyAt(x, y);
        e.takeDamage(damage());
        MapScene.notificationQueue.add("Dealt " + damage() + " damage." );

        turnsSinceUsed = 0;
        AudioHandler.playAudio("ranged.wav");
    }
}

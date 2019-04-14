package skills;

import audio.AudioHandler;
import models.Enemy;
import models.Player;
import models.Skill;
import scenes.MapScene;

public class MeleeAttack extends Skill {

    public MeleeAttack() {
        cooldown = 0;
        turnsSinceUsed = 4;
    }

    int damage() {
        return 6 + (int)Math.pow(level, 2)*2;
    }

    @Override
    public boolean canUse(int x, int y) {
        Enemy e = MapScene.enemyAt(x, y);
        if (e == null) {
            MapScene.notificationQueue.add("No enemy targeted");
            return false;
        }

        if (MapScene.distance(Player.posX, Player.posY, x, y) == 1) {
            return true;
        }

        MapScene.notificationQueue.add("Enemy out of melee range");
        return false;
    }

    @Override
    public void use(int x, int y) {
        Enemy e = MapScene.enemyAt(x, y);

        e.takeDamage(damage());
        MapScene.notificationQueue.add("Dealt " + damage() + " damage." );

        turnsSinceUsed = 0;
        AudioHandler.playAudio("melee.wav");
    }
}

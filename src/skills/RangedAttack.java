package skills;

import models.Enemy;
import models.Skill;
import scenes.MapScene;

public class RangedAttack extends Skill {

    public RangedAttack() {
        cooldown = 0;
        turnsSinceUsed = 4;
    }

    int damage() {
        return 5 + level * level;
    }

    int range() {
        return 2 + level * 2;
    }

    @Override
    public boolean canUse(int x, int y) {
        if (turnsSinceUsed < cooldown) {
            System.out.println("Can't use yet: " + turnsSinceUsed + "/" + cooldown);
            return false;
        }
        Enemy e = MapScene.enemyAt(x, y);
        if (e == null) return false;

        if (MapScene.distance(MapScene.player.posX, MapScene.player.posY, x, y) <= range()) {
            return true;
        }
        return false;
    }

    @Override
    public void use(int x, int y) {
        Enemy e = MapScene.enemyAt(x, y);

        e.takeDamage(damage());
        MapScene.notificationQueue.add(e.hp + "");

        System.out.println("..");
        turnsSinceUsed = 0;
    }
}

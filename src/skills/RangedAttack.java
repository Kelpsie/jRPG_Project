package skills;

import models.Enemy;
import models.Skill;
import scenes.MapScene;

public class RangedAttack extends Skill {

    int damage() {
        return 5 + level * level;
    }

    int range() {
        return 3 + level * 2;
    }

    @Override
    public boolean use(int x, int y) {
        Enemy e = MapScene.enemyAt(x, y);
        if (e == null) return false;

        if (MapScene.distance(MapScene.player.posX, MapScene.player.posY, x, y) <= range()) {
            e.takeDamage(damage());
            MapScene.notificationQueue.add(e.hp + "");
            return true;
        }
        return false;
    }
}

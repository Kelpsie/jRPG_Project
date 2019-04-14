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
        return 5 + (int)(level * (level/2.0+1));
    }

    int range() {
        return 3 + level;
    }

    @Override
    public boolean canUse(int x, int y) {
        if (turnsSinceUsed < cooldown) {
            System.out.println("Can't use yet: " + turnsSinceUsed + "/" + cooldown);
            MapScene.notificationQueue.add("Ranged Attack on cooldown");
            return false;
        }
        Enemy e = MapScene.enemyAt(x, y);
        if (e == null) {
            MapScene.notificationQueue.add("No enemy targeted");
            return false;
        }

        if (MapScene.distance(MapScene.player.posX, MapScene.player.posY, x, y) <= range()) {
            return true;
        }


        MapScene.notificationQueue.add("Enemy out of range (" + range() + ")");
        return false;
    }

    @Override
    public void use(int x, int y) {
        Enemy e = MapScene.enemyAt(x, y);

        e.takeDamage(damage());
        MapScene.notificationQueue.add("Dealt " + damage() + " damage." );


        turnsSinceUsed = 0;
    }
}

package skills;

import models.Player;
import models.Skill;
import scenes.MapScene;

public class Heal extends Skill {

    public static int level = 1;

    public Heal(){
        cooldown = 0;
    }

    @Override
    public boolean canUse(int x, int y) {
        return true;
    }

    @Override
    public void use(int x, int y) {
        int restoreValue = (int) Math.round(Math.abs(Player.hp) / ((level / 2.6) * 20));
        Player.restoreHealth(restoreValue);
        MapScene.notificationQueue.add("Healed " + restoreValue + " health");
    }
}

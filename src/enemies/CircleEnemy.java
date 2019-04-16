package enemies;

import HUD.MainHUD;
import javafx.scene.canvas.GraphicsContext;
import models.Enemy;
import models.Player;
import scenes.MapScene;

public class CircleEnemy extends Enemy {
    static String[] files = {
            "Enemy_4.png",
            "Enemy_4._hue2.png",
            "Enemy_4._hue3.png",
            "Enemy_4._hue4.png"
    };
    static int[] xp = { 20, 50, 100, 300 };
    public CircleEnemy(int x, int y, int type, int xp_multi) {
        super("assets/" + files[type], 32, 10, x, y, xp[type]*xp_multi);
        switch (type) {
            case 0: hp = 10; damage = 3; break;
            case 1: hp = 20; damage = 9; break;
            case 2: hp = 50; damage = 15; break;
            case 3: hp = 150; damage = 43; break;
        }
    }

    @Override
    public void act() {
        boolean healed = false;
        for (Enemy e : MapScene.enemies) {
            if (e == this) continue;
            if ((MapScene.distance(posX, posY, MapScene.player.posX, MapScene.player.posY) <= 3)) {
                e.hp += damage;
                healed = true;
            }
        }
        if (healed)
            MapScene.notificationQueue.add("Circle healed nearby enemies!");
    }

    @Override
    public boolean update() {
        // If performing basic behaviour, no need to check for specific behaviour
        if (super.update()) return true;

        return false;
    }
}

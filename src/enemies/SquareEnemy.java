package enemies;

import HUD.MainHUD;
import javafx.scene.canvas.GraphicsContext;
import models.Enemy;
import models.Player;
import scenes.MapScene;

public class SquareEnemy extends Enemy {
    static String[] files = {
            "Enemy_1.png",
            "Enemy_1_hue2.png",
            "Enemy_1_hue3.png",
            "Enemy_1_hue4.png"
    };
    static int[] xp = { 20, 50, 100, 300 };
    public SquareEnemy(int x, int y, int type, int xp_multi) {
        super("assets/" + files[type], 32, 10, x, y, xp[type]*xp_multi);
        switch (type) {
            case 0: hp = 15; damage = 2; break;
            case 1: hp = 30; damage = 10; break;
            case 2: hp = 70; damage = 30; break;
            case 3: hp = 150; damage = 60; break;
        }
    }

    @Override
    public void act() {
        if (MapScene.distance(posX, posY, MapScene.player.posX, MapScene.player.posY) > 1) {
            // Only move 50% of the time
            if (MapScene.random.nextBoolean()) {
                if (posX < MapScene.player.posX) move(posX + 1, posY);
                else if (posX > MapScene.player.posX) move(posX - 1, posY);
                else if (posY < MapScene.player.posY) move(posX, posY + 1);
                else if (posY > MapScene.player.posY) move(posX, posY - 1);
            }
        } else {
            MapScene.player.hp -= damage;
            MapScene.notificationQueue.add("Took "+damage+" damage from Square");
        }
    }

    @Override
    public boolean update() {
        // If performing basic behaviour, no need to check for specific behaviour
        if (super.update()) return true;

        return false;
    }
}

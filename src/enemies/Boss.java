package enemies;

import HUD.MainHUD;
import javafx.scene.canvas.GraphicsContext;
import models.Enemy;
import models.Player;
import scenes.MapScene;

public class Boss extends Enemy {

    public Boss(int x, int y) {
        super("assets/Boss_1.png", 32, 10, x, y, 10000);
        hp = 100;
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        for (Enemy e : MapScene.enemies) {
            if (e instanceof Boss && e != this)
                e.hp = hp;
        }
    }

    @Override
    public void act() {
        // There are 9. 9 bosses acting at once would be craziness.
        if (MapScene.random.nextInt(5) == 0)
            return;

        MapScene.notificationQueue.add("Boss did a thing! " + posX + " " + posY);
    }

    @Override
    public boolean update() {
        // If performing basic behaviour, no need to check for specific behaviour
        if (super.update()) return true;

        return false;
    }

    @Override
    public void draw(GraphicsContext g) {}
}

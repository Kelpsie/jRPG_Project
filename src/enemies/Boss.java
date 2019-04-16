package enemies;

import HUD.MainHUD;
import audio.AudioHandler;
import javafx.scene.canvas.GraphicsContext;
import main.Game;
import models.Enemy;
import models.Player;
import scenes.GameOverScene;
import scenes.MapScene;

public class Boss extends Enemy {


    public Boss(int x, int y) {
        super("assets/Boss_1.png", 32, 10, x, y, 10000);
        hp = 100;
        damage = 60;
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        for (Enemy e : MapScene.enemies) {
            if (e instanceof Boss && e != this)
                e.hp = hp;

        }
        if(hp <= 0 ){
            GameOverScene.isBossKill = true;
            GameOverScene.setText();
            Game.stage.setScene(MapScene.s.scene);
            AudioHandler.stopBackgroundAudio();
        }

    }

    void spawnPentagon(int type) {

        int tries = 0;
        boolean spawned = false;
        while (tries < 100 && !spawned) {
            tries++;
            int eX = MapScene.random.nextInt(6) + (posX - 3);
            int eY = MapScene.random.nextInt(6) + (posY - 3);
            if (!MapScene.walkable(eX, eY))
                continue;
            MapScene.enemiesToSpawn.add(new PentagonEnemy(eX, eY, type, 0));
            spawned = true;
        }
    }

    @Override
    public void act() {
        // There are 9. 9 bosses acting at once would be craziness.
        if (MapScene.random.nextInt(12) != 0)
            return;

        switch (MapScene.random.nextInt(10)) {
            case 8: spawnPentagon(2);
            case 7: spawnPentagon(3); MapScene.notificationQueue.add("Boss spawned in some trouble!"); break;
            case 6:
            case 5: MapScene.player.hp -= damage*2; MapScene.notificationQueue.add("Boss chunked you for " + damage*2 + " damage!"); break;
            case 4:
            case 3:
            case 2:
            case 1:
            case 0: MapScene.player.hp -= damage; MapScene.notificationQueue.add("Boss hit you for " + damage + " damage!"); break;

        }
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

package enemies;

import HUD.MainHUD;
import javafx.scene.canvas.GraphicsContext;
import models.Enemy;
import models.Player;
import models.Spawner;
import scenes.MapScene;

public class PentagonEnemy extends Enemy {
    int type;
    static String[] files = {
            "Enemy_3.png",
            "Enemy_3_hue2.png",
            "Enemy_3_hue3.png",
            "Enemy_3_hue4.png"
    };
    static int[] xp = { 50, 120, 250, 500 };
    public PentagonEnemy(int x, int y, int type, int xp_multi) {
        super("assets/" + files[type], 32, 10, x, y, xp[type]*xp_multi);
        switch (type) {
            case 0: hp = 10; break;
            case 1: hp = 20; break;
            case 2: hp = 55; break;
            case 3: hp = 150; break;
        }
        this.type = type;
    }

    @Override
    public void act() {

        if (MapScene.random.nextInt(3) == 0) {
            int tries = 0;
            boolean spawned = false;
            while (tries < 100 && !spawned) {
                tries++;
                int eX = MapScene.random.nextInt(6) + (posX - 3);
                int eY = MapScene.random.nextInt(6) + (posY - 3);
                if (!MapScene.walkable(eX, eY))
                    continue;
                // Random number from 0 to 3, more likely to produce low numbers
                int spawnLevel = MapScene.random.nextInt((type+1))*MapScene.random.nextInt((type+1)) / 3;
                // 0-3 = square, 4-7 = triangle
                int spawnType = MapScene.random.nextInt(2) == 0 ? 0 : 4;
                switch (spawnLevel+spawnType) {
                    case 0: MapScene.enemiesToSpawn.add(new SquareEnemy(eX, eY, 0, 0)); break;
                    case 1: MapScene.enemiesToSpawn.add(new SquareEnemy(eX, eY, 1, 0)); break;
                    case 2: MapScene.enemiesToSpawn.add(new SquareEnemy(eX, eY, 2, 0)); break;
                    case 3: MapScene.enemiesToSpawn.add(new SquareEnemy(eX, eY, 3, 0)); break;
                    case 4: MapScene.enemiesToSpawn.add(new TriangleEnemy(eX, eY, 0, 0)); break;
                    case 5: MapScene.enemiesToSpawn.add(new TriangleEnemy(eX, eY, 1, 0)); break;
                    case 6: MapScene.enemiesToSpawn.add(new TriangleEnemy(eX, eY, 2, 0)); break;
                    case 7: MapScene.enemiesToSpawn.add(new TriangleEnemy(eX, eY, 3, 0)); break;
                }
                spawned = true;
                MapScene.notificationQueue.add("Pentagon spawned an enemy");
            }
        }

    }

    @Override
    public boolean update() {
        // If performing basic behaviour, no need to check for specific behaviour
        if (super.update()) return true;

        return false;
    }
}

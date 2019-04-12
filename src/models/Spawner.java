package models;

import enemies.SquareEnemy;
import scenes.MapScene;

public class Spawner {
    public int x, y;
    int enterRadius, exitRadius, spawnRadius;
    String[] enemies;
    boolean inside = false;

    public Spawner(int x, int y, int enterRadius, int exitRadius, int spawnRadius, String enemies) {
        this.x = x; this.y = y;
        this.enterRadius = enterRadius;
        this.exitRadius = exitRadius;
        this.spawnRadius = spawnRadius;
        this.enemies = enemies.split("");
    }

    public void spawn(int x, int y, String eString) {
        switch (eString) {
            case "0":
                MapScene.spawnEnemy(new SquareEnemy("assets/Enemy_1.png", 32, 10, x, y, 0));
                break;
        }
    }

    public void update() {
        int distance = MapScene.distance(x, y, MapScene.player.posX, MapScene.player.posY);

        // If the player just exited, reset for future spawning
        if (inside && distance >= exitRadius) {
            inside = false;
            return;
        }

        // If the player just entered, spawn enemies
        if (!inside && distance <= enterRadius) {
            inside = true;
            for (String eString : enemies) {
                int tries = 0;
                boolean spawned = false;
                // Try a bunch of times, since we want the enemies to always spawn
                while (tries < 100 && !spawned) {
                    tries++;
                    int eX = MapScene.random.nextInt(spawnRadius*2) + (x - spawnRadius);
                    int eY = MapScene.random.nextInt(spawnRadius*2) + (y - spawnRadius);
                    if (!MapScene.walkable(eX, eY))
                        continue;
                    spawn(eX, eY, eString);
                    spawned = true;
                }
            }
        }

    }
}

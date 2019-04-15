package models;

import enemies.*;
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

    public static void spawn(int x, int y, String eString, int xp_multi) {
        switch (eString) {
            case "0": MapScene.spawnEnemy(new SquareEnemy(x, y, 0, xp_multi)); break;
            case "1":  MapScene.spawnEnemy(new SquareEnemy(x, y, 1, xp_multi)); break;
            case "2": MapScene.spawnEnemy(new SquareEnemy(x, y, 2, xp_multi)); break;
            case "3": MapScene.spawnEnemy(new SquareEnemy(x, y, 3, xp_multi)); break;
            case "4": MapScene.spawnEnemy(new TriangleEnemy(x, y, 0, xp_multi)); break;
            case "5": MapScene.spawnEnemy(new TriangleEnemy(x, y, 1, xp_multi)); break;
            case "6": MapScene.spawnEnemy(new TriangleEnemy(x, y, 2, xp_multi)); break;
            case "7": MapScene.spawnEnemy(new TriangleEnemy(x, y, 3, xp_multi)); break;
            case "8": MapScene.spawnEnemy(new PentagonEnemy(x, y, 0, xp_multi)); break;
            case "9": MapScene.spawnEnemy(new PentagonEnemy(x, y, 1, xp_multi)); break;
            case "A": MapScene.spawnEnemy(new PentagonEnemy(x, y, 2, xp_multi)); break;
            case "B": MapScene.spawnEnemy(new PentagonEnemy(x, y, 3, xp_multi)); break;
            case "C": MapScene.spawnEnemy(new CircleEnemy(x, y, 0, xp_multi)); break;
            case "D": MapScene.spawnEnemy(new CircleEnemy(x, y, 1, xp_multi)); break;
            case "E": MapScene.spawnEnemy(new CircleEnemy(x, y, 2, xp_multi)); break;
            case "F": MapScene.spawnEnemy(new CircleEnemy(x, y, 3, xp_multi)); break;
            case "Z": MapScene.spawnEnemy(new Boss(x, y)); break;
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
                    int eX = x, eY = y;
                    if (spawnRadius > 0) {
                        eX = MapScene.random.nextInt(spawnRadius * 2) + (x - spawnRadius);
                        eY = MapScene.random.nextInt(spawnRadius * 2) + (y - spawnRadius);
                    }
                    if (!MapScene.walkable(eX, eY))
                        continue;
                    spawn(eX, eY, eString, 1);
                    spawned = true;
                }
            }
        }

    }
}

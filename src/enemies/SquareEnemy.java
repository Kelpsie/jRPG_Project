package enemies;

import javafx.scene.canvas.GraphicsContext;
import models.Enemy;
import scenes.MapScene;

public class SquareEnemy extends Enemy {
    public int hp;

    public SquareEnemy(GraphicsContext graphicsContext, String file, int frameSize, int framesToSkip, int x, int y, int type) {
        super(graphicsContext, file, frameSize, framesToSkip, x, y);
        switch (type) {
            case 0: hp = 30; break;
        }
    }

    @Override
    public boolean update() {
        // If performing basic behaviour, no need to check for specific behaviour
        if (super.update()) return true;
        //System.out.print(posX + " " + posY + " ");
        System.out.println((posX - MapScene.player.posX) + (posY - MapScene.player.posY));
        if (MapScene.distance(posX, posY, MapScene.player.posX, MapScene.player.posY) == 1) {
            MapScene.player.hp -= 5;
            MapScene.notificationQueue.add("Dealt 5 damage to player");
            MapScene.notificationQueue.add("Player HP: " + MapScene.player.hp);
        }

        return false;
    }
}

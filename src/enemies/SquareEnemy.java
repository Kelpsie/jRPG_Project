package enemies;

import HUD.MainHUD;
import javafx.scene.canvas.GraphicsContext;
import models.Enemy;
import models.Player;
import scenes.MapScene;

public class SquareEnemy extends Enemy {

    public SquareEnemy(GraphicsContext graphicsContext, String file, int frameSize, int framesToSkip, int x, int y, int type) {
        super(graphicsContext, file, frameSize, framesToSkip, x, y, 76);
        switch (type) {
            case 0: hp = 5; break;
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
            MainHUD.health.setText(Integer.toString(Player.hp));
            MapScene.player.hp -= 5;
            MapScene.notificationQueue.add("Dealt 5 damage to player");
            MapScene.notificationQueue.add("Player HP: " + MapScene.player.hp);
        }
    }

    @Override
    public boolean update() {
        // If performing basic behaviour, no need to check for specific behaviour
        if (super.update()) return true;

        return false;
    }
}

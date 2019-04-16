package skills;

import audio.AudioHandler;
import models.Player;
import models.Skill;
import scenes.MapScene;

public class Heal extends Skill {

    public static int cooldown = 1;
    public static int turnsSinceUsed = 2;

    public Heal(){

    }

    public static void use(){
        if(turnsSinceUsed <= cooldown){
            MapScene.notificationQueue.add("Heal on cooldown");
        } else {
            if(turnsSinceUsed > cooldown){
                int restoreValue = Math.round(Math.abs(Player.maxHP/ 20) * MapScene.skills.get("Heal").level);
                Player.restoreHealth(restoreValue);
                MapScene.notificationQueue.add("Healed " + restoreValue + " health");
                AudioHandler.playAudio("heal.wav");
                turnsSinceUsed = 0;
            }

        }

    }

    @Override
    public boolean canUse(int x, int y) {
        return true;
    }

    @Override
    public void use(int x, int y) {

    }
}

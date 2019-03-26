package audio;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import loader.GetFilePath;
import main.Game;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class AudioHandler {

    static ArrayList<String> audioFiles = new ArrayList<>();
    public static ArrayList<String> audioNames = new ArrayList<>();
    public static ArrayList<AudioClip> audioPlayer = new ArrayList<>();
    public static HashMap<String, AudioClip> audioList = new HashMap();


    public static void playAudio(String name){
        try {
            audioList.get(name).play();
        } catch (Exception e){
            System.out.println("Failed to play the audio requested because the name " + name +" is" +
                    " most likely not a name for any of the audio files that are present!");
        }

    }

    public static void playBackgroundAudio(String name){
        Game.bgAudio = new MediaPlayer(new Media(new File("assets/audio/" + name).toURI().toString()));
        Game.bgAudio.play();
    }

    public static void stopAudio(String name){
        try {
            audioList.get(name).stop();
        } catch (Exception e){
            System.out.println("Failed to play the audio requested because the name " + name +" is" +
                    " most likely not a name for any of the audio files that are present!");
        }
    }

    public static void stopBackgroundAudio() {
        Game.bgAudio.stop();
    }


    public static void setVolume(double level){
        for(AudioClip ac : audioList.values()){
            ac.setVolume(level);
        }
        Game.bgAudio.setVolume(level);

    }


    /**
     * https://docs.oracle.com/javase/7/docs/api/java/nio/file/Paths.html
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/media/AudioClip.html
     */

    public static void initAudiohandler() {
        GetFilePath.getFilePaths("assets/audio/", audioFiles, audioNames);
        for(int id = 0; id < audioFiles.size(); id++){
            Path audioPath = Paths.get(audioFiles.get(id));
            AudioClip ac = new AudioClip(audioPath.toUri().toString());
            audioPlayer.add(ac);
            audioList.put(audioNames.get(id), ac);
        }
    }


}

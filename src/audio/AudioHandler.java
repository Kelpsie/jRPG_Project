package audio;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import loader.GetFilePath;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class AudioHandler {

    static ArrayList<String> audioFiles = new ArrayList<>();
    public static ArrayList<AudioClip> audioPlayer = new ArrayList<>();

    public static void playAudio(int id) {
        audioPlayer.get(id).play();
    }

    public static void stopAudio(int id){
        audioPlayer.get(id).stop();
    }

    public static void setVolume(double level) {
        for(AudioClip mp : audioPlayer){
            System.out.println(mp.toString());
            mp.setVolume(level);
            if(mp.isPlaying()){
                mp.stop();
                mp.play();
            }
        }
    }

    /**
     * https://docs.oracle.com/javase/7/docs/api/java/nio/file/Paths.html
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/media/AudioClip.html
     */

    public static void initAudiohandler() {
        GetFilePath.getFilePaths("assets/audio/", audioFiles);
        for(int id = 0; id < audioFiles.size(); id++){
            Path audioPath = Paths.get(audioFiles.get(id));
            AudioClip mp = new AudioClip(audioPath.toUri().toString());
            audioPlayer.add(mp);
        }
    }


}

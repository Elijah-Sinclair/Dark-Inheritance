import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;

public class SoundManager {				// a Singleton class
    HashMap<String, Clip> clips;

    private static SoundManager instance = null;	// keeps track of Singleton instance

    private float volume;

    private SoundManager() {
        clips = new HashMap<String, Clip>();

        Clip clip = loadClip("/first_stage.wav");
        clips.put("Stage1BGM", clip);
        clip = loadClip("/boss_stage.wav");
        clips.put("StageBossBGM", clip);
        clip = loadClip("/hurt.wav");
        clips.put("Hurt", clip);
        clip = loadClip("/victory.wav");
        clips.put("Win", clip);
        clip = loadClip("/game_over.wav");
        clips.put("Lose", clip);

        //volume = 0.75f;
    }


    public static SoundManager getInstance() {	// class method to retrieve instance of Singleton
        if (instance == null)
            instance = new SoundManager();

        return instance;
    }


    public Clip loadClip (String fileName) {    // gets clip from the specified file
        Clip clip = null;

        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                    SoundManager.class.getResource(fileName)
            );

            clip = AudioSystem.getClip();
            clip.open(audioIn);

        } catch (Exception e) {
            System.out.println("Error opening sound file: " + fileName);
            e.printStackTrace();
        }

        return clip;
    }


    public Clip getClip (String title) {

        return clips.get(title);
    }


    public void playClip(String title, boolean looping) {
        Clip clip = getClip(title);

        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }

            clip.setFramePosition(0);

            if (looping)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clip.start();
        }
    }


    public void stopClip(String title) {
        Clip clip = getClip(title);
        if (clip != null) {
            clip.stop();
        }
    }

}
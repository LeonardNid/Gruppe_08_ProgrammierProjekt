package virusvoid.logic.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.net.URL;

/**
 * Manages the loading and playback of audio clips.
 */
public class SoundManager {

    /**
     * Loads an audio clip from the specified file path.
     *
     * @param path The file path of the audio file.
     * @return     A {@code Clip} object representing the loaded audio clip.
     */
    public static Clip loadSound(String path) {
        Clip clip;
        try {
            URL url = SoundManager.class.getClassLoader().getResource(path);
            if (url != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                return clip;
            } else {
                System.out.println("Audio-Datei nicht gefunden: " + path);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets the volume level for the specified audio clip.
     *
     * @param volume The new volume level (0.0 to 1.0).
     * @param clip   The {@code Clip} object for which the volume is to be set.
     */
    protected static void setVolume(float volume, Clip clip) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }
}
package IT_fighter.layers.app.Sound;

import javax.sound.sampled.Clip;

public class SoundManager {
    private static boolean mute = false;
    public void play(Clip clip) {
        clip.start();
    }
    public void loop(Clip clip) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void stop(Clip clip) {
        clip.stop();
    }
    public static void volumeUp()
    {}
}

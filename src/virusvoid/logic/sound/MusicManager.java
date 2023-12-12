package virusvoid.logic.sound;

import virusvoid.help.WaitHelper;

import javax.sound.sampled.Clip;

/**
 * Manages the playing of background music in the game.
 */
public class MusicManager {

    private static float volume = 0.3f;
    private static Clip currentClip;

    /**
     * Plays the specified music in a separate thread, continuously looping it.
     *
     * @param music The file path of the music to be played.
     */
    public static void playMusic(String music) {
        currentClip = SoundManager.loadSound(music);

        new Thread(() -> {
            WaitHelper.waitFor(100);

            if (currentClip != null) {
                SoundManager.setVolume(volume, currentClip);
                currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }).start();
    }

    /**
     * Stops the currently playing music in a separate thread (with a short delay before stopping).
     */
    public static void stopCurrentMusic() {
        Clip clipToStop = currentClip;

        new Thread(() -> {
            WaitHelper.waitFor(100);

            if (clipToStop != null && clipToStop.isRunning()) {
                clipToStop.stop();
            }
        }).start();
    }

    /**
     * Sets the volume level for the currently playing music.
     *
     * @param newVolume The new volume level (0.0 to 1.0).
     */
    public static void setVolume(float newVolume) {
        volume = newVolume;
        SoundManager.setVolume(volume, currentClip);
    }

    /**
     * Gets the volume level of the currently playing music.
     *
     * @return The volume level (0.0 to 1.0).
     */
    public static float getMusicVolume() {
        return volume;
    }
}
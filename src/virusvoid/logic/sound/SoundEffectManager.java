package virusvoid.logic.sound;

import javax.sound.sampled.Clip;

/**
 * Manages the playback of sound effects in the game.
 */
public class SoundEffectManager {

    private static float volume = 0.3f;

    /**
     * Plays and loads the specified sound effect.
     *
     * @param soundEffect The file path of the sound effect to be played.
     */
    public static void playAndLoadClip(String soundEffect) {
        playClip(SoundManager.loadSound(soundEffect));
    }

    /**
     * Plays the provided sound clip in a separate thread.
     *
     * @param clip The sound clip to be played.
     */
    public static void playClip(Clip clip) {
        if (clip != null) {
            new Thread(() -> {
                SoundManager.setVolume(volume, clip);
                clip.start();
            }).start();
        }
    }

    /**
     * Sets the volume level for sound effects.
     *
     * @param newVolume The new volume level (0.0 to 1.0).
     */
    public static void setVolume(float newVolume) {
        volume = newVolume;
    }

    /**
     * Gets the volume level for sound effects.
     *
     * @return The volume level (0.0 to 1.0).
     */
    public static float getSoundEffectsVolume() {
        return volume;
    }
}
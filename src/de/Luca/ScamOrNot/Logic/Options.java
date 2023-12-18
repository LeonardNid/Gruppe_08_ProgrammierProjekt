package de.Luca.ScamOrNot.Logic;

public class Options {
    private static int volume = 15;

    private static boolean mute = false;
    private static String difficulty = "Easy";

    public static String getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(String diff) {
        difficulty = diff;
    }

    public static boolean getMute() {
        return mute;
    }

    public static int getVolume() {
        return volume;
    }

    public static void setVolume(int vol) {
        volume = vol;
    }

    public static void setMute(boolean val) {
        mute = val;
    }

    public static void reloadOptions() {
        if(mute) {
            MusicPlayer.setVolume(MusicPlayer.getClip(), 0);
        }
        else {
            MusicPlayer.setVolume(MusicPlayer.getClip(), ((float) volume /100));
        }
    }
}

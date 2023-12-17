package IT_fighter.layers.app.Sound;

import IT_fighter.utilities.LoadAndSaveData;

import javax.sound.sampled.Clip;

public class GameMusic {
    private final Clip gameMusic = LoadAndSaveData.getSoundFile("ITF_game_music.wav");

    public void playGameMusic() {
        gameMusic.start();
    }
    public void loopGameMusic() {
        gameMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stopGameMusic() {
        gameMusic.stop();
    }





}

package IT_fighter.layers.app.Sound;

public class SoundManager {
    public static final int GAMEMUSIC = 0;
    public static final int GAMESOUND = 1;
    public static final boolean DOWN = true;
    public static final boolean UP = false;
    private static Sound gameMusic = new Sound("ITF_game_music.wav");
    private static Sound killSound = new Sound("ITF_erro.wav");


    public static void setGameMusicVolume(boolean down) {
        if(down) {
            gameMusic.volumeDown();
        } else {
            gameMusic.volumeUp();
        }
    }
    public static void setGameSoundVolume(boolean down) {
        if(down) {
            killSound.volumeDown();
        } else {
            killSound.volumeUp();
        }
    }
    public static void playGameMusic() {
        gameMusic.loop();
    }
    public static void stopGameMusic() {
        gameMusic.stop();
    }

    public static void playKillSound() {
        //Sound killSound = new Sound("ITF_erro.wav");
        killSound.start();
    }
    public static void stopKillSound() {
        killSound.stop();
    }

}

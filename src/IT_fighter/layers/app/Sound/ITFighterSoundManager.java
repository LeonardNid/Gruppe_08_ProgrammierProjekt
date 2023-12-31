package IT_fighter.layers.app.Sound;

public class ITFighterSoundManager {
    public static final int GAMEMUSIC = 0;
    public static final int GAMESOUND = 1;
    public static final boolean DOWN = true;
    public static final boolean UP = false;
    private static ITFighterSound gameMusic = new ITFighterSound("ITF_game_music.wav");
    private static ITFighterSound killSound = new ITFighterSound("ITF_erro.wav");
    private static ITFighterSound tiktokSound = new ITFighterSound("ITF_tiktok_Sound.wav");


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
            tiktokSound.volumeDown();
        } else {
            killSound.volumeUp();
            tiktokSound.volumeUp();
        }
    }
    public static void playTiktokSound() {
        tiktokSound.loop(true);
    }
    public static void stopTiktokSound() {
        tiktokSound.stop();
    }
    public static void playGameMusic() {
        gameMusic.loop(false);
    }
    public static void stopGameMusic() {
        gameMusic.stop();
    }
    public static void playKillSound() {
        killSound.start();
    }
    public static void stopKillSound() {
        killSound.stop();
    }

}

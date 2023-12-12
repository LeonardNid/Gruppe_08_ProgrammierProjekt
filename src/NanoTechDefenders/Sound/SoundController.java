package NanoTechDefenders.Sound;

import java.util.Arrays;

/**
 * Die Klasse SoundController verwaltet die verschiedenen Sounds im Spiel.
 */
public class SoundController {
    private static Audio[] audioNormal = new Audio[]{new Audio("NTD_SoldierSound.wav"), new Audio("NTD_SoldierSound.wav")};
    private static Audio[] audioPiercer = new Audio[]{new Audio("NTD_PiercerSound.wav"), new Audio("NTD_PiercerSound.wav")};
    private static Audio[] audioSniper = new Audio[]{new Audio("NTD_RocketShotSound.wav"), new Audio("NTD_RocketShotSound.wav")};
    private static Audio audioFlameThrower = new Audio("NTD_FlameThrowerSound.wav");
    private static Audio[] audioBeam = new Audio[]{new Audio("NTD_BeamSound.wav"), new Audio("NTD_BeamSound.wav")};

    private static Audio audioLifelost = new Audio("NTD_LifeLostSound.wav");
    private static Audio audioEnemyDeath = new Audio("NTD_EnemyDeathSound.wav");
    private static Audio audioGameOver = new Audio("NTD_GameOverSound.wav");

    /**
     * Konstruktor für die SoundController-Klasse.
     * Hier werden die Einstellungen für die verschiedenen Sounds vorgenommen.
     */
    public SoundController() {
        for (Audio audio : Arrays.stream(audioNormal).toList()) {
            audio.setVolume(0.1f);
        }
        for (Audio audio : Arrays.stream(audioPiercer).toList()) {
            audio.setSoundEnd(10000);
        }
        for (Audio audio : Arrays.stream(audioSniper).toList()) {
            audio.setVolume(0.04f);
            audio.setSoundEnd(20000);
        }
        audioFlameThrower.setVolume(0.1f);
        audioFlameThrower.setSoundEnd(50000);
        for (Audio audio : Arrays.stream(audioBeam).toList()) {
            audio.setSoundEnd(50000);
        }
        audioLifelost.setSoundEnd(-1);
        audioEnemyDeath.setVolume(0.03f);
    }

    /**
     * Spielt einen NanoTechDefenders.Sound aus dem gegebenen Audio-Array ab.
     *
     * @param audioArray Das Array von Audio-Objekten.
     */
    private static void playSound(Audio[] audioArray) {
        new Thread(() -> {
            if (!audioArray[0].isRunning()) {
                audioArray[0].playSound();
            } else {
                audioArray[1].playSound();
            }
        }).start();
    }

    /**
     * Spielt einen NanoTechDefenders.Sound aus dem gegebenen Audio-Objekt ab.
     *
     * @param audioArray Das Audio-Objekt.
     */
    private static void playSound(Audio audioArray) {
        new Thread(audioArray::playSound).start();
    }

    /**
     * Spielt den normalen NanoTechDefenders.Sound ab.
     */
    public static void playNormal() {
        playSound(audioNormal);
    }

    /**
     * Spielt den Piercer-NanoTechDefenders.Sound ab.
     */
    public static void playPiercer() {
        playSound(audioPiercer);
    }

    /**
     * Spielt den Sniper-NanoTechDefenders.Sound ab.
     */
    public static void playSniper() {
        playSound(audioSniper);
    }

    /**
     * Spielt den FlameThrower-NanoTechDefenders.Sound ab.
     */
    public static void playFlameThrower() {
        playSound(audioFlameThrower);
    }

    /**
     * Spielt den Beam-NanoTechDefenders.Sound ab.
     */
    public static void playBeam() {
        playSound(audioBeam);
    }

    /**
     * Spielt den Lifelost-NanoTechDefenders.Sound ab.
     */
    public static void playAudioLifelost() {
        playSound(audioLifelost);
    }

    /**
     * Spielt den EnemyDeath-NanoTechDefenders.Sound ab.
     */
    public static void playEnemyDeath() {
        playSound(audioEnemyDeath);
    }

    /**
     * Spielt den GameOver-NanoTechDefenders.Sound ab.
     */
    public static void playGameOver() {
        playSound(audioGameOver);
    }
}

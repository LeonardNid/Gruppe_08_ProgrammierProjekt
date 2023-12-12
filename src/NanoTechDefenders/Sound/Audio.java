package NanoTechDefenders.Sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Die Klasse Audio ermöglicht das Laden und Abspielen von Audiodateien.
 */
public class Audio {
    private AudioInputStream audioInputStream;
    private Clip clip;
    private FloatControl gainControl;
    private int soundEnd = 3000;

    /**
     * Konstruktor für die Audio-Klasse.
     *
     * @param fileName Der Dateiname der Audiodatei.
     */
    public Audio(String fileName) {
        try (InputStream inputStream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(fileName))) {
            if (inputStream != null) {
                audioInputStream = AudioSystem.getAudioInputStream(inputStream);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                // Get the FloatControl for controlling the volume
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(0.05F);
            } else {
                System.out.println("Audiodatei nicht gefunden: " + fileName);
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Setzt die Lautstärke (Gewinn) des Audioclips.
     *
     * @param volume Der Lautstärkewert, normalerweise im Bereich von 0.0 bis 1.0.
     */
    public void setVolume(float volume) {
        // Überprüfen, ob der Lautstärkewert im gültigen Bereich liegt
        if (volume < 0.0f || volume > 1.0f) {
            throw new IllegalArgumentException("Lautstärkewert muss im Bereich von 0.0 bis 1.0 liegen");
        }

        // Den Gewinnwert auf Grundlage einer logarithmischen Skala berechnen
        float gain = (float) (Math.log10(volume) * 20.0);

        // Den Gewinnwert setzen
        gainControl.setValue(gain);
    }

    /**
     * Setzt die Endposition des Sounds im Clip.
     *
     * @param soundEnd Die Endposition des Sounds im Clip.
     */
    public void setSoundEnd(int soundEnd) {
        this.soundEnd = soundEnd;
    }

    /**
     * Überprüft, ob der Audioclip gerade abgespielt wird.
     *
     * @return True, wenn der Clip abgespielt wird, sonst false.
     */
    public boolean isRunning() {
        return clip.isRunning();
    }

    /**
     * Spielt den NanoTechDefenders.Sound ab, wenn der Clip nicht bereits läuft oder die Endposition erreicht wurde.
     */
    public void playSound() {
        if (!clip.isRunning() || clip.getFramePosition() > soundEnd) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}

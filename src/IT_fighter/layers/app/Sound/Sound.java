package IT_fighter.layers.app.Sound;

import IT_fighter.utilities.LoadAndSaveData;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {
    private String soundName;
    private Clip sound;
    float currentVolume = -10f;
    FloatControl floatControl;
    public Sound(String soundName) {
        this.soundName = soundName;
        sound = LoadAndSaveData.getSoundFile(soundName);
        floatControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
        floatControl.setValue(currentVolume);
    }
    public void loop() {
        sound.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void start() {
        sound = LoadAndSaveData.getSoundFile(soundName);
        floatControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
        floatControl.setValue(currentVolume);
        sound.start();
    }
    public void stop() {
        sound.stop();
        sound.close();
    }

    public void volumeUp() {
        currentVolume += 2.0f;
        if(currentVolume > 6.0f) {
            currentVolume = 6.0f;
        }
        floatControl.setValue(currentVolume);

    }

    public void volumeDown() {
        currentVolume -= 2.0f;
        if (currentVolume < -80.0f) {
            currentVolume -= -80.0f;
        }
        floatControl.setValue(currentVolume);

    }
    public static Clip getSoundFile(String soundFileName) {
        Clip clip;
        String errorMessage = "Fehler bei SoundFile einlesen";
        try {
            String path = "ITfighter_resources/"+soundFileName;
            System.out.println(path);
            InputStream musicStream = LoadAndSaveData.class.getClassLoader().getResourceAsStream(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicStream);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            System.out.println(errorMessage);
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            System.out.println(errorMessage+ "falsche Url");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println(errorMessage);
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            System.out.println(errorMessage);
            throw new RuntimeException(e);

        }
        return clip;
    }

}

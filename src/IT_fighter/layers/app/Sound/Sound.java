package IT_fighter.layers.app.Sound;

import IT_fighter.utilities.LoadAndSaveData;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {
    private String soundName;
    private boolean restart = false;
    private Clip sound;
    float currentVolume = -10f;
    FloatControl floatControl;
    public Sound(String soundName) {
        this.soundName = soundName;
        setUpSound();

    }
    public void setUpSound() {
        sound = LoadAndSaveData.getSoundFile(soundName);
        floatControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
        floatControl.setValue(currentVolume);
    }
    public void loop(boolean restart) {
        if(restart) {
            setUpSound();
            this.restart = true;
        }
        sound.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void start() {
        setUpSound();
        sound.start();
    }
    public void stop() {
        sound.stop();
        if(restart) {
            sound.close();
        }

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

}

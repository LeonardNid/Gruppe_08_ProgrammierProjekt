package IT_fighter.layers.app.Sound;

import IT_fighter.utilities.LoadAndSaveData;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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
        sound.start();
    }
    public void stop() {
        sound.stop();
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

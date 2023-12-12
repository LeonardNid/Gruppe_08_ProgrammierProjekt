package de.Luca.ScamOrNot.Logic;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MusicPlayer {
    private final List<File> tracks;
    private Clip clip;
    private int currentIndex = -1;

    private Clip before;

    public MusicPlayer(List<File> tracks) {
        this.tracks = new ArrayList<>(tracks);
        Collections.shuffle(this.tracks);
    }

    public void play() {
        if(before == clip && before != null) {
            return;
        }

        if (clip != null && clip.isRunning()) {
            return;
        }

        if (tracks.isEmpty()) {
            System.out.println("Keine Tracks vorhanden.");
            return;
        }

        int newIndex;
        do {
            newIndex = new Random().nextInt(tracks.size());
        } while (newIndex == currentIndex);

        currentIndex = newIndex;

        try {
            File selectedTrack = tracks.get(currentIndex);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(selectedTrack);
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            setVolume(clip, 0.15f);

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("An error was occured: " + e.getMessage());
        }

        System.out.println("neuer track" + clip.toString());

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                play();
            }
        }, clip.getMicrosecondLength());

    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    public static Clip loadClip(String soundFilePath) {
        try {
            ClassLoader classLoader = main.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(soundFilePath);

            if (inputStream != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(inputStream);

                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                audioIn.close();
                inputStream.close();

                return clip;
            } else {
                System.err.println("Sound file not found!");
                return null;
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("An error was occured: " + e.getMessage());
            return null;
        }
    }

    public static void playSound(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public static void setVolume(Clip clip, float volume) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);

            gainControl.setValue(dB);
        }
    }
}

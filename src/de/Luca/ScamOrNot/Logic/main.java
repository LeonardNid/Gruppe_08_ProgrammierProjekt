package de.Luca.ScamOrNot.Logic;

import de.Luca.ScamOrNot.UI.MainFrame;
import de.Luca.ScamOrNot.UI.MainMenueUI;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {

    /*
        CHOICES:
        - 0 -> Normal
        - 1 -> Phishing
        - 2 -> Scam
     */

    //TODO: anzeigen der erklärung nach email wahl
    //TODO?: email frame bisschen schöner stylen
    //TODO?: End screen ui schön

    public static List<File> loaded_tracks = new ArrayList<>();

    public static MusicPlayer player;

    public static Font pixelFont;

    public static void main() {
        Email.init();
        initMusic();
        initFonts();

        player = new MusicPlayer(loaded_tracks);
        player.play();

        MainFrame.init();
        MainMenueUI.init();
    }

    private static void initFonts() {
        try {
            InputStream fontStream = main.class.getResourceAsStream("/ScamOrNot/Fonts/Minecraft.ttf");
            if (fontStream != null) {
                pixelFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(pixelFont);
            } else {
                System.err.println("Font file not found!");
            }
        } catch (Exception e) {
            System.err.println("An error was occurred: " + e.getMessage());
        }
    }

    private static void initMusic() {
        try {
            ClassLoader classLoader = main.class.getClassLoader();
            URL musicFolder = classLoader.getResource("ScamOrNot/MusicTracks");

            if (musicFolder != null) {
                File[] files = new File(musicFolder.toURI()).listFiles();
                if (files != null) {
                    loaded_tracks.addAll(Arrays.asList(files));
                }
            } else {
                System.err.println("MusicTracks folder not found!");
            }
        } catch (Exception e) {
            System.err.println("An error was occurred: " + e.getMessage());
        }
    }
}

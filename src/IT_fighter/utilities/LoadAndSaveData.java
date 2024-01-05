package IT_fighter.utilities;

import virusvoid.logic.sound.SoundManager;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Dient zum Laden von Dateien in Java-Klassen
 */
public class LoadAndSaveData {

    //##################################################################################################################
    private static final String sourceOfResources = "ITfighter_resources/";
    public static final String LEVEL_ATLAS = "ITF_outside_sprites.jpg";
    public static final String fontName = "ITF_BACKTO1982.TTF";
    //##################################################################################################################

    /**
     * liest ein Bild ein und gibt dieses als Buffered Image zurück.
     * @param pictureName Name des Bildes, das eingelesen werden soll.
     * @return wird keine Datei gefunden zum Einlesen des Bilds wird null zurückgegeben.
     */
    public static BufferedImage getImage(String pictureName) {
        BufferedImage image;
        InputStream inputStream = LoadAndSaveData.class.getClassLoader().getResourceAsStream(sourceOfResources + pictureName);
        try {
            image = ImageIO.read(inputStream);
            inputStream.close();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Liest die Standard-Schrift für die Anwendung ein.
     * @param size Gibt die Größe an, die die Schrift haben soll.
     * @return Gibt die Schrift in der gewünschten Größe zurück.
     */
    public static Font getFont(int size) {
        try (InputStream inputStream = LoadAndSaveData.class.getClassLoader().
                getResourceAsStream(sourceOfResources+fontName)){
            if (inputStream != null) {
                return Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(Font.BOLD, size);
            } else {
                System.out.println("Schriftart-Datei nicht gefunden: "+ sourceOfResources+fontName);
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
    //##################################################################################################################

    /**
     * Liest ein Bild, in dem ein Pixel für einen Block des Spiels steht ein und überträgt diese Daten in ein
     * zweidimensionales int-Array.
     * @param level name des Bildes, welches die Daten für das Level beinhaltet.
     * @return gibt int-Array mit Level-Daten zurück.
     */
    public static int[][] getLevelData(String level) {
        BufferedImage levelOneData = getImage(level);
        int[][] levelData = new int[levelOneData.getHeight()][levelOneData.getWidth()];

        for (int j = 0; j < levelOneData.getHeight(); j++)
            for (int i = 0; i < levelOneData.getWidth(); i++) {
                Color color = new Color(levelOneData.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                levelData[j][i] = value;
            }
        return levelData;

    }

    /**
     * liest ein Soundfile ein und gibt diese als Clip zurück.
     */
    public static Clip getSoundFile(String soundFileName) {
        Clip clip;
        try {
            String path = sourceOfResources+soundFileName;
            URL url = LoadAndSaveData.class.getClassLoader().getResource(path);
            if(url != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                return clip;
            } else {
                System.out.println("Fehler bei SoundFile einlesen"+path);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

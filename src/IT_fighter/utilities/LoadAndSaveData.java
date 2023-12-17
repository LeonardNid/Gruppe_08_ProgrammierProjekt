package IT_fighter.utilities;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadAndSaveData {

    //##################################################################################################################
    //TODO hier richtige Quellen an geben
    private static final String sourceOfResources = "/ITfighter_resources/";
    public static final String LEVEL_ATLAS = "ITF_outside_sprites.jpg";


    public static final String fontName = "ITF_BACKTO1982.TTF";
    //##################################################################################################################
    public static BufferedImage getImage(String pictureName) {
        BufferedImage image;
        System.out.println(sourceOfResources+pictureName);
        InputStream inputStream = LoadAndSaveData.class.getResourceAsStream(sourceOfResources + pictureName);
        try {
            image = ImageIO.read(inputStream);
            inputStream.close();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Font getFont(int size) {
        try {
            String filepath = LoadAndSaveData.class.getResource(sourceOfResources+fontName).getPath();
            File fontfile = new File(filepath);
            return Font.createFont(Font.TRUETYPE_FONT, fontfile).deriveFont(Font.BOLD, size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
    //##################################################################################################################
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
    public static Clip getSoundFile(String soundFileName) {
        Clip clip;
        String errorMessage = "Fehler bei SoundFile einlesen";
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new URL(sourceOfResources + soundFileName));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            System.out.println(errorMessage);
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            System.out.println(errorMessage);
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

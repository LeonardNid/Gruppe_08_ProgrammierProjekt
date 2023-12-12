package NanoTechDefenders.Help;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Die Klasse MyImage bietet Methoden zum Laden, Skalieren und Rotieren von Bildern.
 */
public class MyImage {
    private static HashMap<String, ImageIcon> imageMap = new HashMap<>();

    /**
     * Erstellt eine skalierte ImageIcon-Instanz aus der angegebenen Datei mit den angegebenen Abmessungen.
     *
     * @param file   Der Dateipfad des Bildes.
     * @param width  Die Breite des skalierten Bildes.
     * @param height Die Höhe des skalierten Bildes.
     * @return Eine ImageIcon-Instanz des skalierten Bildes.
     */
    public static ImageIcon createScaledImageIcon(String file, int width, int height) {
        if (imageMap.containsKey(file + width + height)) {
            return imageMap.get(file + width + height);
        }
        try (InputStream inputStream = MyImage.class.getClassLoader().getResourceAsStream(file)) {
            if (inputStream != null) {
                // Bild für den Hintergrund laden
                Image originalImage = new ImageIcon(ImageIO.read(inputStream)).getImage();
                // Bildgröße anpassen
                Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);

                ImageIcon imageIcon = new ImageIcon(scaledImage);
                imageMap.put(file + width + height, imageIcon);
                return imageIcon;
            } else {
                System.out.println("Fehler beim Lesen des Bildes: " + file);
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen des Bildes: " + file);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Rotiert die gegebene ImageIcon-Instanz um den angegebenen Winkel.
     *
     * @param icon  Die ImageIcon-Instanz, die rotiert werden soll.
     * @param angle Der Rotationswinkel in Grad.
     * @return Eine ImageIcon-Instanz des rotierten Bildes.
     */
    public static ImageIcon rotateImageIcon(ImageIcon icon, double angle) {
        // Rotiere das Bild um den angegebenen Winkel
        Image rotatedImage = ((Image) icon.getImage());
        BufferedImage bufferedRotatedImage = new BufferedImage(rotatedImage.getWidth(null), rotatedImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedRotatedImage.createGraphics();

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), rotatedImage.getWidth(null) / 2, rotatedImage.getHeight(null) / 2);
        g2d.setTransform(transform);
        g2d.drawImage(rotatedImage, 0, 0, null);
        g2d.dispose();

        return new ImageIcon(bufferedRotatedImage);
    }
}

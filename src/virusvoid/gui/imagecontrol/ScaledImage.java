package virusvoid.gui.imagecontrol;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The {@code ScaledImage} class provides utility methods for creating and drawing scaled image icons.
 * It includes a method to create a scaled ImageIcon from a given image file and dimensions, and another
 * method to draw an ImageIcon on a specified Graphics object at a given location.
 */
public class ScaledImage {

    /**
     * Creates a scaled ImageIcon from the given image file, width, and height.
     *
     * @param pictureFile The name of the image file.
     * @param width       The desired width of the scaled image.
     * @param height      The desired height of the scaled image.
     * @return A scaled ImageIcon, or {@code null} if an error occurs.
     */
    public static ImageIcon createScaledImageIcon(String pictureFile, int width, int height) {
        try (InputStream inputStream = ScaledImage.class.getClassLoader().getResourceAsStream(pictureFile)) {
            if (inputStream != null) {
                return new ImageIcon(new ImageIcon(ImageIO.read(inputStream)).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
            } else {
                System.out.println("Fehler beim Lesen des Bildes: " + pictureFile);
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen des Bildes: " + pictureFile);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Draws the specified ImageIcon on the provided Graphics object at the specified location.
     *
     * @param g          The Graphics object on which to draw the image.
     * @param imageIcon  The ImageIcon to be drawn.
     * @param x          The x-coordinate of the top-left corner of the image.
     * @param y          The y-coordinate of the top-left corner of the image.
     * @param j          The JPanel on which the image is drawn.
     */
    public static void drawImage(Graphics g, ImageIcon imageIcon, int x, int y, JPanel j) {
        g.drawImage(imageIcon.getImage(), x, y, j);
    }
}
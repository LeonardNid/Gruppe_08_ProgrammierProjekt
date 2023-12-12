package de.Luca.ScamOrNot.UI.CustomElements;

import de.Luca.ScamOrNot.Logic.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            ClassLoader classLoader = main.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(imagePath);

            if (inputStream != null) {
                backgroundImage = ImageIO.read(inputStream);
                inputStream.close();
            } else {
                System.err.println("Image file not found!");
            }
        } catch (IOException e) {
            System.err.println("An error was occured: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

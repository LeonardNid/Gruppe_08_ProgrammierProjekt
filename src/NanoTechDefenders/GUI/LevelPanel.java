package NanoTechDefenders.GUI;

import NanoTechDefenders.Help.MyImage;

import javax.swing.*;
import java.awt.*;

/**
 * Das LevelPanel ist ein JPanel, das als Hintergrundbild ein Level anzeigt.
 */
public class LevelPanel extends JPanel {
    private final int frameWidth;
    private final int frameHeight;
    private final ImageIcon backgroundImage;

    /**
     * Konstruktor für das LevelPanel.
     *
     * @param frameWidth  Die Breite des übergeordneten Frames.
     * @param frameHeight Die Höhe des übergeordneten Frames.
     */
    public LevelPanel(int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        backgroundImage = MyImage.createScaledImageIcon("NTD_background_Level.png", frameWidth, frameHeight);
    }

    /**
     * Zeichnet das Hintergrundbild auf das Panel.
     *
     * @param g Das Graphics-Objekt zum Zeichnen.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Zeichnet das Hintergrundbild.
        g.drawImage(backgroundImage.getImage(), 0, 0, frameWidth, frameHeight, this);
    }
}

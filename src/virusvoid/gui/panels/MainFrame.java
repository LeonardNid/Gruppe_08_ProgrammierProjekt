package virusvoid.gui.panels;
import virusvoid.gui.imagecontrol.PictureManager;
import virusvoid.gui.imagecontrol.ScaledImage;

import java.awt.*;

import javax.swing.*;

/**
 * The main frame for the "Virus-Void" game, providing a background panel with a scaled image.
 */
public class MainFrame extends JFrame {

    private static JPanel backgroundPanel;

    /**
     * Constructs a new MainFrame for the "Virus-Void" game.
     */
    public MainFrame() {
        this.setTitle("Virus-Void");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH); // size of the background picture which is used for scaling, is 960 x 540 pixels in size
        this.setUndecorated(true);

        backgroundPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (PictureManager.getBackgroundImageIcon() == null) {
                    PictureManager.createBackgroundImageIcon(this.getWidth(), this.getHeight());
                }
                ScaledImage.drawImage(g,PictureManager.getBackgroundImageIcon(), 0, 0, this);
            }
        };

        this.add(backgroundPanel);
        this.setVisible(true);
    }

    /**
     * Gets the background panel of the main frame.
     *
     * @return The background JPanel.
     */
    public static JPanel getBackgroundPanel() {
        return backgroundPanel;
    }
}
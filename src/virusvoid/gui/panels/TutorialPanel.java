package virusvoid.gui.panels;

import virusvoid.gui.imagecontrol.ScaledImage;
import virusvoid.gui.labels.PlanetHPLabel;
import virusvoid.gui.other.GuiController;
import virusvoid.gui.other.PlanetHPLabelManager;
import virusvoid.gui.other.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Panel for displaying the tutorial interface of the "Virus-Void" game.
 */
public class TutorialPanel extends GameTutorialSuperPanel {

    private static PlanetHPLabel planetHpLabel;
    private final JLabel skipStoryLabel;

    /**
     * Constructs a new TutorialPanel.
     */
    public TutorialPanel() {
        super(1);

        planetHpLabel = PlanetHPLabelManager.getPlanetHPLabel();
        planetHpLabel.setVisible(false);
        this.add(planetHpLabel);

        skipStoryLabel = createLabel("Press Space to skip the Story.", LIGHT_BLUE, pixelArtFont(30));
        skipStoryLabel.setBounds(GuiController.getMainFrameWidth() / 2 - 275, 50, 550, 30);
        this.add(skipStoryLabel);
    }

    /**
     * Overrides the paintComponent method to customize the rendering of the tutorial panel.
     *
     * @param g The Graphics object to paint on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (GuiController.isKiVoiceOver()) {
            drawKIVoiceOverText(g);
        } else if (GuiController.isDrawMovement()) {
            if (skipStoryLabel.isVisible()) {
                skipStoryLabel.setVisible(false);
            }
            drawMovement(g);
        } else if (GuiController.isDrawPauseAndQuit()) {
            drawPauseAndQuit(g);
        } else if (GuiController.isDrawEnemyInstructions()) {
            drawEnemyInstructions(g);
        } else {
            planetHpLabel.setVisible(true);
        }
    }

    /**
     * Draws a black screen on the panel.
     *
     * @param g The Graphics object to paint on.
     */
    private void drawBlackScreen(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    /**
     * Draws the movement instructions on the panel.
     *
     * @param g The Graphics object to paint on.
     */
    private void drawMovement(Graphics g) {
        drawBlackScreen(g);

        int imageWidth = 128;
        int imageHeight = 116;
        int spacing = 50;
        int imageY = (this.getHeight() - imageHeight) / 2;

        int image1X = (this.getWidth() - (2 * imageWidth + spacing)) / 2;
        int image2X = (this.getWidth() + spacing) / 2;

        ImageIcon wasd = ScaledImage.createScaledImageIcon("VirusVoid_WASD.png", imageWidth, imageHeight);
        ImageIcon arrowKeys = ScaledImage.createScaledImageIcon("VirusVoid_Pfeiltasten.png", imageWidth, imageHeight);

        ScaledImage.drawImage(g, wasd, image1X, imageY, this);
        ScaledImage.drawImage(g, arrowKeys, image2X, imageY, this);

        g.setColor(Color.WHITE);
        g.setFont(pixelArtFont(FIFTY));
        FontMetrics fm = g.getFontMetrics();
        String text = "Movement:";
        g.drawString(text, (this.getWidth() - fm.stringWidth(text)) / 2, imageY - fm.getHeight() * 2);
    }

    /**
     * Draws the movement instructions on the panel.
     *
     * @param g The Graphics object to paint on.
     */
    private void drawPauseAndQuit(Graphics g) {
        drawBlackScreen(g);

        int imageWidth = 64;
        int imageHeight = 54;
        int spacing = 200;

        int imageY = (this.getHeight() - imageHeight) / 2;

        int image1X = (this.getWidth() - (2 * imageWidth + spacing)) / 2;
        int image2X = (this.getWidth() + spacing) / 2;

        ImageIcon pKey = ScaledImage.createScaledImageIcon("VirusVoid_P-Key.png", imageWidth, imageHeight);
        ImageIcon escKey = ScaledImage.createScaledImageIcon("VirusVoid_Esc-Key.png", imageWidth, imageHeight);

        ScaledImage.drawImage(g, pKey, image1X, imageY, this);
        ScaledImage.drawImage(g, escKey, image2X, imageY, this);

        g.setColor(Color.WHITE);
        g.setFont(pixelArtFont(40));
        FontMetrics fm = g.getFontMetrics();
        String text = "Pause:";
        int textHeight = fm.getHeight();
        g.drawString(text, image1X + imageWidth / 2 - fm.stringWidth(text) / 2, imageY - textHeight);

        text = "Quit:";
        g.drawString(text, image2X + imageWidth / 2 - fm.stringWidth(text) / 2, imageY - textHeight);
    }

    /**
     * Draws enemy instructions on the panel.
     *
     * @param g The Graphics object to paint on.
     */
    private void drawEnemyInstructions(Graphics g) {
        drawBlackScreen(g);

        g.setColor(Color.WHITE);
        g.setFont(pixelArtFont(25));
        FontMetrics fm = g.getFontMetrics();
        String text = "All enemies inflict damage upon contact with your spaceship.";
        g.drawString(text, this.getWidth() / 2 - fm.stringWidth(text) / 2, this.getHeight() / 2 - fm.getHeight() * 2);

        text = "Your planet is damaged when green enemies exit the bottom of the screen.";
        g.drawString(text, this.getWidth() / 2 - fm.stringWidth(text) / 2, this.getHeight() / 2);

        text = "Avoid contact with red projectiles.";
        g.drawString(text, this.getWidth() / 2 - fm.stringWidth(text) / 2, this.getHeight() / 2 + fm.getHeight() * 2);
    }

    /**
     * Draws the story text for the voice-over.
     *
     * @param g The Graphics object to paint on.
     */
    private void drawKIVoiceOverText(Graphics g) {
        drawBlackScreen(g);

        g.setColor(Color.WHITE);
        g.setFont(pixelArtFont(25));
        FontMetrics fm = g.getFontMetrics();

        String[] introText = {
                "In the gloomy Twenty-third century, the AI virus \"Virus-X\" mercilessly",
                "consumes the stars, threatening ships and robots that were once our servants.",
                "But you, brave hero, dare to jump into the Virus-Void. In this digital",
                "battlefield, you must confront the infected machines, where your decisions",
                "will determine victory or defeat.",
                "",
                "Armed with humanity and technology, you traverse the dark corridors of",
                "the Virus-Void. Every step is a dance with the unknown, every choice",
                "determines your fate. You are the Guardian of the Stars, unleashing the",
                "power of technology to defeat Virus-X and free the fallen machines.",
                "",
                "In this epic battle that holds the fate of humanity, you become the",
                "light in the Virus-Void. Your actions echo through the vastness of space,",
                "a triumphant call against the darkness. May your resistance ignite the",
                "light in the Virus-Void and save humanity from impending doom."
        };

        int startY = this.getHeight() / 2 - (introText.length * fm.getHeight()) / 2;

        for (int i = 0; i < introText.length; i++) {
            String line = introText[i];
            g.drawString(line, this.getWidth() / 2 - fm.stringWidth(line) / 2, startY + i * fm.getHeight());
        }
    }
}
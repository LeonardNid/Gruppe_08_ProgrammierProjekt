package virusvoid.gui.panels;

import virusvoid.gui.labels.PlanetHPLabel;
import virusvoid.gui.other.GuiController;
import virusvoid.gui.other.PlanetHPLabelManager;
import virusvoid.help.WaitHelper;
import virusvoid.gui.other.*;

import java.awt.*;

/**
 * Panel for displaying the game interface, including the planet's HP label, boss HP bar, and level announcement.
 */
public class GamePanel extends GameTutorialSuperPanel {

    private static boolean showLevelOrWaveAnnouncement;
    private static PlanetHPLabel planetHpLabel;
    private static int currentLevelOrWave;

    /**
     * Constructs a new GamePanel.
     */
    public GamePanel() {
        super(0);
        planetHpLabel = PlanetHPLabelManager.getPlanetHPLabel();
        this.add(planetHpLabel);
    }

    /**
     * Paints the game panel, including the boss HP bar and level announcement if applicable.
     *
     * @param g The Graphics context for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (GuiController.isBossSpawned()) {
            drawBossHp(g);
        }
        if (showLevelOrWaveAnnouncement) {
            drawLevelAnnouncement(g);
        }
    }

    /**
     * Draws the boss HP bar on the game panel.
     *
     * @param g The Graphics context for drawing.
     */
    private void drawBossHp(Graphics g) {
        int bossBarWidth = GuiController.scaleX(400);
        int bossBarHeight = 25;
        int bossBarX = this.getWidth() / 2 - bossBarWidth / 2;

        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(bossBarX, 0, bossBarWidth + 3, bossBarHeight + 3);

        // Draw current HP
        g.setColor(Color.RED);
        g.fillRect(bossBarX + 3, 3, GuiController.getBossHPLabelWidth() - 3, bossBarHeight - 3);
    }

    /**
     * Draws the level/wave announcement on the game panel.
     *
     * @param g The Graphics context for drawing.
     */
    private void drawLevelAnnouncement(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Draw level announcement in the center
        g.setColor(LIGHT_BLUE);
        g.setFont(pixelArtFont(FIFTY));

        String text;
        if (GuiController.isInfiniteGameRunning()) {
            text = "Wave ";
        } else {
            text = "Level ";
        }
        text += currentLevelOrWave;
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (this.getWidth() - textWidth) / 2;
        int y = (this.getHeight() - textHeight) / 2 + fm.getAscent();
        g.drawString(text, x, y);
    }

    /**
     * Displays the level/wave announcement for a specific level/wave and a specific Time.
     *
     * @param levelOrWaveNumber The level/wave to be announced.
     */
    public static void showLevelAnnouncementOrWave(int levelOrWaveNumber) {
        planetHpLabel.setVisible(false);

        currentLevelOrWave = levelOrWaveNumber;
        showLevelOrWaveAnnouncement = true;

        new Thread(() -> {
            while (GuiController.isLevelOrWaveAnnouncement()) {
                WaitHelper.waitFor(20);
            }
            showLevelOrWaveAnnouncement = false;
            planetHpLabel.setVisible(true);
        }).start();
    }
}
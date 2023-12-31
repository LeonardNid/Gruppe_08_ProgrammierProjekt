package virusvoid.gui.panels;

import virusvoid.gui.imagecontrol.PictureManager;
import virusvoid.gui.labels.PauseLabel;
import virusvoid.gui.labels.ScaledLabel;
import virusvoid.gui.labels.SpaceshipLabel;
import virusvoid.gui.other.GuiBasic;
import virusvoid.gui.other.GuiController;
import virusvoid.gui.other.SpaceshipKeyListener;
import virusvoid.gui.other.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * The base panel for the game/tutorial, providing components for spaceship, projectiles, and animations.
 */
public class GameTutorialSuperPanel extends GuiBasic {

    private static CopyOnWriteArrayList<ScaledLabel> explosionAnimationLabels;
    private static ScaledLabel rightPlayerProjectileAnimationLabel;
    private static ScaledLabel leftPlayerProjectileAnimationLabel;
    private static SpaceshipKeyListener spaceshipKeyListener;
    private static SpaceshipLabel spaceshipLabel;
    private static OrangePanel orangePanel;
    private static JLabel pauseLabel;

    /**
     * Constructs a GameTutorialSuperPanel with the specified keyListenerType (important for managing things in the keylistener).
     *
     * @param keyListenerType The type of key listener.
     */
    public GameTutorialSuperPanel(int keyListenerType) {
        explosionAnimationLabels = new CopyOnWriteArrayList<>();

        int size = GuiController.scaleY(50);

        spaceshipLabel = new SpaceshipLabel(PictureManager.getSpaceshipImageIcon(), new Point(GuiController.scaleX(460), GuiController.scaleY(420)), 0) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int xBuffer = GuiController.scaleX(5);
                int yBuffer = GuiController.scaleY(8);
                g.setColor(Color.RED);
                g.drawLine(xBuffer, size + yBuffer, size - xBuffer, size + yBuffer); // Hintergrund, damit man sehen kann, wie viel HP schon abgezogen wurde
                g.setColor(Color.GREEN);
                g.drawLine(xBuffer, size + yBuffer, xBuffer + GuiController.getPlayerHPLabelWidth(), size + yBuffer); // wirkliche HP anzeige
            }
        };

        this.add(spaceshipLabel);

        this.setPreferredSize(new Dimension(GuiController.scaleX(960), GuiController.scaleY(540)));
        this.setLayout(null);
        this.setOpaque(false);
        spaceshipKeyListener = new SpaceshipKeyListener(spaceshipLabel, keyListenerType);
        this.addKeyListener(spaceshipKeyListener);

        pauseLabel = PauseLabel.createLabel();
        this.add(pauseLabel);

        orangePanel = new OrangePanel();
        this.add(orangePanel);
    }

    /**
     * Changes the visibility of the orange panel.
     */
    public void changeOrangePanelVisibility() {
        orangePanel.setVisible(!orangePanel.isVisible());
    }

    /**
     * Changes the visibility of the pause label.
     */
    public void changePauseLabelVisibility() {
        pauseLabel.setVisible(!pauseLabel.isVisible());
    }

    /**
     * Gets the SpaceshipKeyListener associated with this panel.
     *
     * @return The SpaceshipKeyListener.
     */
    public SpaceshipKeyListener getSpaceshipKeylistener() {
        return spaceshipKeyListener;
    }

    /**
     * Gets the SpaceshipLabel associated with this panel.
     *
     * @return The SpaceshipLabel.
     */
    public static SpaceshipLabel getSpaceshipLabel() {
        return spaceshipLabel;
    }

    /**
     * Removes the player's projectile animation labels from the panel.
     */
    public void removePlayeProjectileAnimationLabels() {
        SwingUtilities.invokeLater(() -> {
            if (rightPlayerProjectileAnimationLabel != null && leftPlayerProjectileAnimationLabel != null) {
                this.remove(rightPlayerProjectileAnimationLabel);
                this.remove(leftPlayerProjectileAnimationLabel);
            }
        });
    }

    /**
     * Displays the animation frame for the player's projectiles.
     *
     * @param blueShotAnimatonFrame   The ImageIcon for the projectile animation frame.
     * @param xLeftProjectile         The X coordinate of the left projectile.
     * @param xRightProjectile        The X coordinate of the right projectile.
     * @param projectileY             The Y coordinate of the projectiles.
     */
    public void showPlayerProjectileAnimationFrame(ImageIcon blueShotAnimatonFrame, int xLeftProjectile, int xRightProjectile, int projectileY) {
        SwingUtilities.invokeLater(() -> {
            rightPlayerProjectileAnimationLabel = new ScaledLabel(blueShotAnimatonFrame, new Point(xRightProjectile, projectileY), 0);
            leftPlayerProjectileAnimationLabel = new ScaledLabel(blueShotAnimatonFrame, new Point(xLeftProjectile, projectileY), 0);

            this.add(rightPlayerProjectileAnimationLabel);
            this.add(leftPlayerProjectileAnimationLabel);
        });
    }

    /**
     * Removes the oldest explosion animation label from the panel.
     */
    public void removeExplosionAnimationLabel() {
        SwingUtilities.invokeLater(() -> {
            if (!explosionAnimationLabels.isEmpty()) {
                this.remove(explosionAnimationLabels.get(0));
                explosionAnimationLabels.remove(explosionAnimationLabels.get(0));
            }
        });
    }

    /**
     * Clears all explosion animation labels from the panel.
     */
    public void clearExplosionAnimationLabels() {
        SwingUtilities.invokeLater(() -> {
            for (ScaledLabel scaledLabel : explosionAnimationLabels) {
                this.remove(scaledLabel);
                explosionAnimationLabels.remove(scaledLabel);
            }
        });
    }

    /**
     * Displays the animation frame for the explosion.
     *
     * @param enemyExplosionAnimatonFrame  The ImageIcon for the explosion animation frame.
     * @param x                            The X coordinate of the explosion.
     * @param y                            The Y coordinate of the explosion.
     */
    public void showExplosionAnimationFrame(ImageIcon enemyExplosionAnimatonFrame, int x, int y) {
        SwingUtilities.invokeLater(() -> {
            ScaledLabel tempLabel = new ScaledLabel(enemyExplosionAnimatonFrame, new Point(x, y), 0);

            explosionAnimationLabels.add(tempLabel);
            this.add(tempLabel);
        });
    }
}
package virusvoid.gui.imagecontrol;

import virusvoid.gui.other.GuiController;

import javax.swing.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code PictureManager} class manages ImageIcons for various elements in the game.
 * It provides methods for retrieving images for spaceships, enemies, projectiles, and animations.
 */

public class PictureManager {

    private static final CopyOnWriteArrayList<ImageIcon> spaceshipExplosionAnimationFrames = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<ImageIcon> enemyExplosionAnimationFrames = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<ImageIcon> bossExplosionAnimationFrames = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<ImageIcon> blueShotAnimatonFrames = new CopyOnWriteArrayList<>();
    private static ImageIcon orbitalBossProjectileImageIcon;
    private static ImageIcon homingBossProjectileImageIcon;
    private static ImageIcon backgroundImageIcon = null;
    private static ImageIcon enemyProjectileImageIcon;
    private static ImageIcon blueProjectileImageIcon;
    private static ImageIcon shootingEnemyImageIcon;
    private static ImageIcon defaultEnemyImageIcon;
    private static ImageIcon spaceshipImageIcon;
    private static ImageIcon bossImageIcon;

    /**
     * Creates image icons for various game elements.
     */
    public static void createImageIcons() {
        int size = GuiController.scaleY(50);
        spaceshipImageIcon = ScaledImage.createScaledImageIcon("VirusVoid_Spaceship.png", size, size);

        size = GuiController.scaleX(125);
        bossImageIcon = ScaledImage.createScaledImageIcon("VirusVoid_Boss.png", size, size);

        size = GuiController.scaleX(25);
        defaultEnemyImageIcon = ScaledImage.createScaledImageIcon("VirusVoid_Enemy.png", size, size);

        shootingEnemyImageIcon = ScaledImage.createScaledImageIcon("VirusVoid_Enemy_2.png", size, size);

        int width = GuiController.scaleX(6);
        int height = GuiController.scaleY(25);
        blueProjectileImageIcon = ScaledImage.createScaledImageIcon("VirusVoid_BlueProjectile.png", width, height);

        orbitalBossProjectileImageIcon = ScaledImage.createScaledImageIcon("VirusVoid_RedProjectile.png", width, height);

        width = GuiController.scaleX(8);
        height = GuiController.scaleY(11);
        enemyProjectileImageIcon = ScaledImage.createScaledImageIcon("VirusVoid_RedProjectile.png", width, height);

        width = GuiController.scaleX(12);
        height = GuiController.scaleY(16);
        homingBossProjectileImageIcon = ScaledImage.createScaledImageIcon("VirusVoid_RedProjectile.png", width, height);

        addFramesToFrameList(5, blueShotAnimatonFrames, "VirusVoid_BlueShotAnimatonFrame.png", GuiController.scaleX(20));

        addFramesToFrameList(13, bossExplosionAnimationFrames, "VirusVoid_ExplosionFrame.png", GuiController.scaleX(300));

        addFramesToFrameList(13, enemyExplosionAnimationFrames, "VirusVoid_ExplosionFrame.png", GuiController.scaleX(35));

        addFramesToFrameList(13, spaceshipExplosionAnimationFrames, "VirusVoid_ExplosionFrame.png", GuiController.scaleX(100));
    }

    /**
     * Adds animation frames to the specified frame list.
     *
     * @param elementCount The number of frames to add.
     * @param list         The frame list to which frames will be added.
     * @param fileName     The base file name of the frames.
     * @param size         The size to which the frames will be scaled.
     */
    private static void addFramesToFrameList(int elementCount, CopyOnWriteArrayList<ImageIcon> list, String fileName, int size) {
        for (int i = 0; i < elementCount; ++i) {
            list.add(ScaledImage.createScaledImageIcon(getFrameFile(i, fileName), size, size));
        }
    }

    /**
     * Generates the file name for a specific frame.
     *
     * @param number   The frame number.
     * @param fileName The base file name of the frames.
     * @return The complete file name for the specified frame.
     */
    private static String getFrameFile(int number, String fileName) {
        int dotIndex = fileName.lastIndexOf(".");

        String prefix = fileName.substring(0, dotIndex);
        String suffix = fileName.substring(dotIndex);
        return prefix + number + suffix;
    }

    /**
     * Creates the background image icon.
     *
     * @param width  The width of the background image.
     * @param height The height of the background image.
     */
    public static void createBackgroundImageIcon(int width, int height) {
        backgroundImageIcon = ScaledImage.createScaledImageIcon("VirusVoid_Background.png", width, height);
    }

    /**
     * Gets the list with the frames for the spaceship explosion animation.
     *
     * @return The list with the frames for the spaceship explosion animation.
     */
    public static CopyOnWriteArrayList<ImageIcon> getSpaceshipExplosionAnimationFrames() {
        return spaceshipExplosionAnimationFrames;
    }

    /**
     * Gets the list with the frames for the enemy explosion animation.
     *
     * @return The list with the frames for the enemy explosion animation.
     */
    public static CopyOnWriteArrayList<ImageIcon> getEnemyExplosionAnimationFrames() {
        return enemyExplosionAnimationFrames;
    }

    /**
     * Gets the list with the frames for the boss explosion animation.
     *
     * @return The list with the frames for the boss explosion animation.
     */
    public static CopyOnWriteArrayList<ImageIcon> getBossExplosionAnimationFrames() {
        return bossExplosionAnimationFrames;
    }

    /**
     * Gets the list with the frames for the blue shooting animation.
     *
     * @return The list with the frames for the blue shooting animation.
     */
    public static CopyOnWriteArrayList<ImageIcon> getBlueShotAnimatonFrames() {
        return blueShotAnimatonFrames;
    }

    /**
     * Gets the orbital boss projectile image icon.
     *
     * @return The orbital boss projectile image icon.
     */
    public static ImageIcon getOrbitalBossProjectileImageIcon() {
        return orbitalBossProjectileImageIcon;
    }

    /**
     * Gets the homing boss projectile image icon.
     *
     * @return The homing boss projectile image icon.
     */
    public static ImageIcon getHomingBossProjectileImageIcon() {
        return homingBossProjectileImageIcon;
    }

    /**
     * Gets the enemy projectile image icon.
     *
     * @return The enemy projectile image icon.
     */
    public static ImageIcon getEnemyProjectileImageIcon() {
        return enemyProjectileImageIcon;
    }

    /**
     * Gets the blue projectile image icon.
     *
     * @return The blue projectile image icon.
     */
    public static ImageIcon getBlueProjectileImageIcon() {
        return blueProjectileImageIcon;
    }

    /**
     * Gets the shooting enemy image icon.
     *
     * @return The shooting enemy image icon.
     */
    public static ImageIcon getShootingEnemyImageIcon() {
        return shootingEnemyImageIcon;
    }

    /**
     * Gets the default enemy image icon.
     *
     * @return The default enemy image icon.
     */
    public static ImageIcon getDefaultEnemyImageIcon() {
        return defaultEnemyImageIcon;
    }

    /**
     * Gets the background image icon.
     *
     * @return The background image icon.
     */
    public static ImageIcon getBackgroundImageIcon() {
        return backgroundImageIcon;
    }

    /**
     * Gets the spaceship image icon.
     *
     * @return The spaceship image icon.
     */
    public static ImageIcon getSpaceshipImageIcon() {
        return spaceshipImageIcon;
    }

    /**
     * Gets the boss image icon.
     *
     * @return The boss image icon.
     */
    public static ImageIcon getBossImageIcon() {
        return bossImageIcon;
    }
}
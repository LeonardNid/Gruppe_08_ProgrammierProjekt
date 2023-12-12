package virusvoid.gui.other;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

import Main.Main;
import virusvoid.gui.imagecontrol.PictureManager;
import virusvoid.gui.labels.ScaledLabel;
import virusvoid.gui.panels.*;
import virusvoid.help.WaitHelper;
import virusvoid.logic.controller.LogicController;

import static Main.Main.frame;

/**
 * The {@code GuiController} class manages the graphical user interface (GUI) components and handles
 * the transition between different panels in the game.
 * The communication of the logic and GUI layer also runs via this class
 */
public class GuiController {

    private static final CopyOnWriteArrayList<ScaledLabel> defaultEnemyList = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<ScaledLabel> shootingEnemyList = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<ScaledLabel> playerProjectileList = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<ScaledLabel> enemyProjectileList = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<ScaledLabel> bossHomingProjectileList = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<ScaledLabel> bossProjectileList = new CopyOnWriteArrayList<>();
    private static SpaceshipKeyListener spaceshipKeyListener;
    private static JPanel backgroundPanel;
    private static boolean infiniteGame;
    private static MainFrame mainFrame;
    private static int mainFrameHeight;
    private static int mainFrameWidth;
    private static JPanel activePanel;
    private static ScaledLabel boss;

    /**
     * Starts the GUI by initializing the main frame, creating image icons, starting some manager classes and setting the initial panel.
     */
    public static void startGui() {
		SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame();

            WaitHelper.waitFor(50);

            mainFrameHeight = mainFrame.getHeight();
            mainFrameWidth = mainFrame.getWidth();

            PictureManager.createImageIcons();

            backgroundPanel = MainFrame.getBackgroundPanel();
            switchPanel(new MainMenuPanel());

            PlanetHPLabelManager.startPlanetHPLabelManager();
            infiniteGame = false;
        });
	}

    /**
     * Scales a value based on the width of the main frame and the ratio to the width of the background image. (Scaling in X-Direction)
     *
     * @param defaultValue The default value to be scaled.
     * @return The scaled value for the default value.
     */
    public static int scaleX(double defaultValue) {
		return (int) (defaultValue * (mainFrameWidth / 960.0));
	}

    /**
     * Scales a value based on the height of the main frame and the ratio to the height of the background image. (Scaling in Y-Direction)
     *
     * @param defaultValue The default value to be scaled.
     * @return The scaled value for the default value.
     */
    public static int scaleY(double defaultValue) {
        return (int) (defaultValue * (mainFrameHeight / 540.0));
    }

    /**
     * Exits the application.
     */
    public static void quitApplication() {
        LogicController.stopMusic();
        mainFrame.dispose();
        Main.createMainFrame();
    }

    /**
     * Exits the game or tutorial respectively tells the logic to stop the game- or tutorial-logic
     *
     * @param quitType The type of exit (0 for game, 1 for tutorial).
     */
    protected static void quit(int quitType) {
        if (quitType == 0) {
            if (isInfiniteGameRunning()) {
                LogicController.stopInfiniteGame();
            } else {
                LogicController.stopGame();
            }
        } else {
            LogicController.stopTutorial();
        }
    }

    /**
     * Pauses or unpauses the game or tutorial based on the pause type respectively tells the logic to pause or unpause the game- or tutorial-logic
     *
     * @param pauseType The type of pause (0 for game, 1 for tutorial).
     */
    protected static void pause(int pauseType) {
        if (pauseType == 0) {
            if (isInfiniteGameRunning()) {
                LogicController.pauseUnpauseInfiniteGame();
            } else {
                LogicController.pauseUnpauseGame();
            }
        } else {
            LogicController.pauseUnpauseTutorial();
        }
    }

    /**
     * Switches the current panel to the specified panel.
     * In addition, the game or tutorial logic is started when switching to a game or tutorial panel and a few other things are executed that should be executed when switching to certain panels.
     *
     * @param panelToSwitchTo The panel to switch to.
     */
    public static void switchPanel(JPanel panelToSwitchTo) {
        if (activePanel instanceof GameTutorialEndPanel && panelToSwitchTo instanceof GameTutorialPanel || activePanel instanceof GamePanel && panelToSwitchTo instanceof GameTutorialPanel || activePanel instanceof TutorialPanel && panelToSwitchTo instanceof GameTutorialPanel) {
            LogicController.startMenuMusic();
        }

        activePanel = panelToSwitchTo;
        backgroundPanel.removeAll();
        backgroundPanel.add(activePanel);
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
        activePanel.requestFocusInWindow();

        if (activePanel instanceof GamePanel) {
            spaceshipKeyListener = ((GamePanel) activePanel).getSpaceshipKeylistener();
            if (infiniteGame) {
                LogicController.startInfiniteGame();
                infiniteGame = false;
            } else {
                LogicController.startGame();
            }
        } else if (activePanel instanceof TutorialPanel) {
            spaceshipKeyListener = ((TutorialPanel) activePanel).getSpaceshipKeylistener();
            LogicController.startTutorial();
        }
    }

    /**
     * Toggles when the infinite game should be started (to differentiate between normal and infinite game)
     */
    public static void toggleInfinteGame() {
        infiniteGame = true;
    }

    /**
     * Refreshes the active panel 120 times per second to ensure that the game/tutorial display looks clean.
     *
     * @param repaintType The type of repaint (0 for game, 1 for tutorial).
     */
    public static void repaintPanel(int repaintType) {
        final int targetRepaints = 120;
        final long optimalTime = 1_000_000_000 / targetRepaints;

        new Thread(() -> {
            long lastRepaintTime = 0;
            int repaints = 0;

            while (true) {
                if (!(activePanel instanceof GamePanel) && repaintType == 0) {
                    break;
                } else if (!(activePanel instanceof TutorialPanel) && repaintType == 1) {
                    break;
                }

                long lastLoopTime = System.nanoTime();
                activePanel.repaint();

                // Berechnung der FPS
                long thisRepaintTime = System.nanoTime();
                if (thisRepaintTime - lastRepaintTime >= 1_000_000_000) {
                    System.out.println("Repaints: " + repaints);
                    repaints = 0;
                    lastRepaintTime = thisRepaintTime;
                }
                ++repaints;

                // Kontrolle der Framerate
                long sleepTime = (lastLoopTime - System.nanoTime() + optimalTime) / 1_000_000;
                if (sleepTime > 0) {
                    WaitHelper.waitFor((int) sleepTime);
                }
            }
        }).start();
    }

    /**
     * Creates and adds a new enemy label to the current panel based on the specified parameters.
     *
     * @param location  The location where the enemy label should be placed.
     * @param enemyId   The identifier for the enemy label.
     * @param enemyType The type of the enemy label (0 for green, 1 for red).
     */
    public static void createEnemyLabel(Point location, int enemyId, int enemyType) {
        ScaledLabel enemy;
        if (enemyType == 0) { // grüne Gegner
            enemy = new ScaledLabel(PictureManager.getDefaultEnemyImageIcon(), location, enemyId);
            defaultEnemyList.add(enemy);
        } else { // rote Gegner
            enemy = new ScaledLabel(PictureManager.getShootingEnemyImageIcon(), location, enemyId);
            shootingEnemyList.add(enemy);
        }

        activePanel.add(enemy);
    }

    /**
     * Creates and adds a new boss label to the current panel at the specified location.
     *
     * @param location The location where the boss label should be placed.
     */
    public static void createBossLabel(Point location) {
        boss = new ScaledLabel(PictureManager.getBossImageIcon(), location, 0);
        activePanel.add(boss);
    }

    /**
     * Creates and adds a new projectile label to the current panel based on the specified parameters.
     *
     * @param location      The location where the projectile label should be placed.
     * @param projectileId  The identifier for the projectile label.
     * @param type          The type of the projectile label (0 for player, 1 for enemy, 2 for homing boss, 3 for normal boss).
     */
    public static void createProjectileLabel(Point location, int projectileId, int type) {
        ScaledLabel projectile;
        if (type == 0) { // player projectile

            projectile = new ScaledLabel(PictureManager.getBlueProjectileImageIcon(), location, projectileId);
            playerProjectileList.add(projectile);
        } else if (type == 1) { // enemy projectile

            projectile = new ScaledLabel(PictureManager.getEnemyProjectileImageIcon(), location, projectileId);
            enemyProjectileList.add(projectile);
        } else if (type == 2) { // homing boss projectile

            projectile = new ScaledLabel(PictureManager.getHomingBossProjectileImageIcon(), location, projectileId);
            bossHomingProjectileList.add(projectile);
        } else { // normal boss projectile

            projectile = new ScaledLabel(PictureManager.getOrbitalBossProjectileImageIcon(), location, projectileId);
            bossProjectileList.add(projectile);
        }

        activePanel.add(projectile);
    }

    /**
     * Updates the location of a label identified by its labelId.
     *
     * @param location  The new location for the label.
     * @param labelId   The identifier of the label to be updated.
     * @param innerType The type of the label (0 for enemy, 1 for projectile).
     * @param labelType The category of the label (0 for enemy labels, 1 for projectile labels).
     */
    public static void updateLabel(Point location, int labelId, int innerType, int labelType) {        
        if (labelType == 0) {
            if (innerType == 0) { // grüner Gegner
                updateLabelInList(defaultEnemyList, labelId, location);
            } else { // roter Gegner
                updateLabelInList(shootingEnemyList, labelId, location);
            }
        } else {
            if (innerType == 0) { // Spieler-Projektil
                updateLabelInList(playerProjectileList, labelId, location);
            } else if (innerType == 1) { // Gegner-Projektil
                updateLabelInList(enemyProjectileList, labelId, location);
            } else if (innerType == 2) { // homing Boss-Projektil
                updateLabelInList(bossHomingProjectileList, labelId, location);
            } else { // normales Boss-Projektil
                updateLabelInList(bossProjectileList, labelId, location);
            }
        }
    }

    /**
     * Updates the location of a label in the specified list identified by its labelId.
     *
     * @param list    The list containing the label to be updated.
     * @param labelId The identifier of the label to be updated.
     * @param location The new location for the label.
     */
    protected static void updateLabelInList(CopyOnWriteArrayList<ScaledLabel> list, int labelId, Point location) {
        for (ScaledLabel label : list) {
            if (label.getId() == labelId) {
                label.setLocation(location);
                break;
            }
        }
    }

    /**
     * Updates the location of the boss label.
     *
     * @param location The new location for the boss label.
     */
    public static void updateBoss(Point location) {
        boss.setLocation(location);
    }

    /**
     * Removes a label from the current panel based on its identifier and type.
     *
     * @param labelId   The identifier of the label to be removed.
     * @param innerType The type of the label (0 for enemy, 1 for projectile).
     * @param labelType The category of the label (0 for enemy labels, 1 for projectile labels).
     */
    public static void removeLabel(int labelId, int innerType, int labelType) {
        if (labelType == 0) { // enemy Labels
            if (innerType == 0) {
                removeFromListAndPanel(defaultEnemyList, labelId);
            } else {
                removeFromListAndPanel(shootingEnemyList, labelId);
            }
        } else { // projectile Labels
            if (innerType == 0) {
                removeFromListAndPanel(playerProjectileList, labelId);
            } else if (innerType == 1) {
                removeFromListAndPanel(enemyProjectileList, labelId);
            } else if (innerType == 2) {
                removeFromListAndPanel(bossHomingProjectileList, labelId);
            } else {
                removeFromListAndPanel(bossProjectileList, labelId);
            }
        }
    }

    /**
     * Removes a label from the specified list and the current panel based on its identifier.
     *
     * @param list    The list containing the label to be removed.
     * @param labelId The identifier of the label to be removed.
     */
    protected static void removeFromListAndPanel(CopyOnWriteArrayList<ScaledLabel> list, int labelId) {
        for (ScaledLabel label : list) {
            if (label.getId() == labelId) {
                activePanel.remove(label);
                list.remove(label);
                break;
            }
        }
    }

    /**
     * Updates the direction of the spaceship in the logic based on the specified keycode and whether the key is pressed or released.
     *
     * @param keycode           The keycode indicating the pressed or released key.
     * @param pressedOrReleased A boolean indicating whether the key is pressed (true) or released (false).
     */
    protected static void updateDirection(int keycode, boolean pressedOrReleased) {
        LogicController.updateDirection(keycode, pressedOrReleased);
    }

    /**
     * Updates the spaceship label location and returns the updated location.
     *
     * @return The updated spaceship location as a Point.
     */
    protected static Point updateSpaceshipLocation() {
        return LogicController.updateSpaceshipLocation();
    }

    /**
     * Retrieves the current width of the main game frame.
     *
     * @return The width of the main game frame.
     */
    public static int getMainFrameWidth() {
        return mainFrameWidth;
    }

    /**
     * Retrieves the current height of the main game frame.
     *
     * @return The height of the main game frame.
     */
    public static int getMainFrameHeight() {
        return mainFrameHeight;
    }

    /**
     * Gets the width of the player health points label based on the current player health.
     *
     * @return The width of the player health points label.
     */
    public static int getPlayerHPLabelWidth() {
        return Math.round(((GameTutorialSuperPanel.getSpaceshipLabel().getWidth() - scaleX(5) * 2) *
            (LogicController.getPlayerHp() / LogicController.getMaxPlayerHp())));
    }

    /**
     * Gets the current music volume from the logic.
     *
     * @return The current music volume.
     */
    protected static float getMusicVolume() {
        return LogicController.getMusicVolume();
    }

    /**
     * Gets the current sound effects volume from the logic.
     *
     * @return The current sound effects volume.
     */
    protected static float getSoundEffectsVolume() {
        return LogicController.getSoundEffectsVolume();
    }

    /**
     * Sets the music volume to the specified value.
     *
     * @param volume The desired music volume.
     */
    protected static void setMusicVolume(float volume) {
        LogicController.setMusicVolume(volume);
    }

    /**
     * Sets the sound effects volume to the specified value.
     *
     * @param volume The desired sound effects volume.
     */
    protected static void setSoundEffectsVolume(float volume) {
        LogicController.setSoundEffectsVolume(volume);
    }

    /**
     * Gets the health points of the planet and updates the corresponding label.
     *
     * @param planetHp The new health points of the planet.
     */
    public static void setPlanetHp(int planetHp) {
        PlanetHPLabelManager.getPlanetHPLabel().setPlanetHP(planetHp);
    }

    /**
     * Gets the width of the boss health points label based on the current boss health.
     *
     * @return The width of the boss health points label.
     */
    public static int getBossHPLabelWidth() {
        return Math.round((scaleX(400) * (LogicController.getBossHp() / LogicController.getMaxBossHp())));
    }

    /**
     * Checks if the boss is currently spawned.
     *
     * @return {@code true} if the boss is spawned; otherwise, {@code false}.
     */
    public static boolean isBossSpawned() {
        return LogicController.isBossSpawned();
    }

    /**
     * Shuts down the key listener executor.
     */
    public static void shutKeyListenerDown() {
        spaceshipKeyListener.shutExecutorDown();
    }

    /**
     * Resumes the key listener executor.
     */
    public static void resumeKeyListener() {
        spaceshipKeyListener.resumeExecutor();
    }

    /**
     * Pauses the key listener executor.
     */
    public static void pauseKeyListener() {
        spaceshipKeyListener.pauseExecutor();
    }

    /**
     * Changes the visibility of the orange panel (showed when Tutorial/Game is paused)
     */
    public static void changeOrangePanelVisibility() {
        ((GameTutorialSuperPanel) activePanel).changeOrangePanelVisibility();
    }

    /**
     * Changes the visibility of the pause label in the tutorial.
     */
    public static void changePauseLabelVisibility() {
        ((GameTutorialSuperPanel) activePanel).changePauseLabelVisibility();
    }

    /**
     * Displays the game over screen with the specified end message, message color, and retry option.
     *
     * @param endMessage    The message to be displayed on the game over screen.
     * @param messageColor  The color of the message.
     * @param againOrRetry  The option for restarting the game.
     * @param infiniteOrNot True if the infinite game should be started when pressing the retry button
     */
    public static void gameOverScreen(String endMessage, Color messageColor, String againOrRetry, boolean infiniteOrNot) {
        switchPanel(new GameTutorialEndPanel(endMessage, messageColor, againOrRetry, 'G', infiniteOrNot)); // G == das beim Drücken vom "again" Button GamePanel erzeugt wird
    }

    /**
     * Displays the tutorial end screen with the specified end message and message color.
     *
     * @param endMessage   The message to be displayed on the tutorial completion screen.
     * @param messageColor The color of the message.
     */
    public static void tutorialEndScreen(String endMessage, Color messageColor) {
        switchPanel(new GameTutorialEndPanel(endMessage, messageColor, "Again", 'T', false)); // T == das beim Drücken vom "again" Button TutorialPanel erzeugt wird
    }

    /**
     * Displays the level/wave announcement for the specified level.
     *
     * @param levelOrWaveNumber The level/wave to be announced.
     */
    public static void showLevelOrWave(int levelOrWaveNumber) {
        GamePanel.showLevelAnnouncementOrWave(levelOrWaveNumber);
    }

    /**
     * Checks if a level announcement is currently being displayed.
     *
     * @return {@code true} if a level announcement is being displayed; otherwise, {@code false}.
     */
    public static boolean isLevelOrWaveAnnouncement() {
        return LogicController.isLevelOrWaveAnnouncement();
    }

    /**
     * Indicates whether enemy instructions should be displayed in the tutorial.
     *
     * @return {@code true} if enemy instructions should be displayed; otherwise, {@code false}.
     */
    public static boolean isDrawEnemyInstructions() {
        return LogicController.isDrawEnemyInstructions();
    }

    /**
     * Indicates whether pause and quit instructions should be displayed in the tutorial.
     *
     * @return {@code true} if pause and quit instructions should be displayed; otherwise, {@code false}.
     */
    public static boolean isDrawPauseAndQuit() {
        return LogicController.isDrawPauseAndQuit();
    }

    /**
     * Indicates whether movement instructions should be displayed in the tutorial.
     *
     * @return {@code true} if movement instructions should be displayed; otherwise, {@code false}.
     */
    public static boolean isDrawMovement() {
        return LogicController.isDrawMovement();
    }

    /**
     * Indicates whether KIVoiceOver is active.
     *
     * @return {@code true} if KIVoiceOver is active; otherwise, {@code false}.
     */
    public static boolean isKiVoiceOver() {
        return LogicController.isKiVoiceOver();
    }

    /**
     * "Tells" the logic that the story should bes skipped (activation through keylistener/user)
     */
    public static void skipStory() {
        LogicController.skipStory();
    }

    /**
     * Calls a Methode which displays the animation frame of the player's projectile.
     *
     * @param frameIndex       The index of the animation frame.
     * @param xLeftProjectile  The x-coordinate of the left projectile.
     * @param xRightProjectile The x-coordinate of the right projectile.
     * @param projectileY      The y-coordinate of the projectile.
     */
    public static void showPlayerProjectileAnimationFrame(int frameIndex, int xLeftProjectile, int xRightProjectile, int projectileY) {
        if (activePanel instanceof GameTutorialSuperPanel) {
            ((GameTutorialSuperPanel) activePanel).showPlayerProjectileAnimationFrame(PictureManager.getBlueShotAnimatonFrames().get(frameIndex), xLeftProjectile, xRightProjectile, projectileY);
        }
    }

    /**
     * Calls a Methode which removes the animation labels for player projectiles.
     */
    public static void removePlayeProjectileAnimationLabels() {
        if (activePanel instanceof GameTutorialSuperPanel) {
            ((GameTutorialSuperPanel) activePanel).removePlayeProjectileAnimationLabels();
        }
    }

    /**
     * Calls a Methode which displays the animation frame of an enemy explosion.
     *
     * @param frameIndex        The index of the animation frame.
     * @param x                 The x-coordinate of the explosion.
     * @param y                 The y-coordinate of the explosion.
     * @param explosionFrameType The type of explosion frame (E for enemy, B for boss, S for spaceship).
     */
    public static void showEnemyExplosionAnimationFrame(int frameIndex, int x, int y, char explosionFrameType) {
        if (activePanel instanceof GameTutorialSuperPanel) {
            ImageIcon frame = switch (explosionFrameType) {
                case ('E') -> PictureManager.getEnemyExplosionAnimationFrames().get(frameIndex);
                case ('B') -> PictureManager.getBossExplosionAnimationFrames().get(frameIndex);
                case ('S') -> PictureManager.getSpaceshipExplosionAnimationFrames().get(frameIndex);
                default -> null;
            };

            if (frame != null) {
                ((GameTutorialSuperPanel) activePanel).showExplosionAnimationFrame(frame, x, y);
            }
        }
    }

    /**
     * Calls a Methode which removes a label displaying an explosion animation.
     */
    public static void removeExplosionAnimationLabel() {
        if (activePanel instanceof GameTutorialSuperPanel) {
            ((GameTutorialSuperPanel) activePanel).removeExplosionAnimationLabel();
        }
    }

    /**
     * Calls a Methode which clears the list of all explosion animation labels.
     */
    public static void clearExplosionAnimationLabels() {
        if (activePanel instanceof GameTutorialSuperPanel) {
            ((GameTutorialSuperPanel) activePanel).clearExplosionAnimationLabels();
        }
    }

    /**
     * Removes the label displaying the boss character.
     */
    public static void removeBossLabel() {
        if (activePanel instanceof GamePanel && boss != null) {
            activePanel.remove(boss);
        }
    }

    /**
     * Removes the label displaying the spaceship.
     */
    public static void removeSpaceshipLabel() {
        if (activePanel instanceof GameTutorialSuperPanel && GameTutorialSuperPanel.getSpaceshipLabel() != null) {
            activePanel.remove(GameTutorialSuperPanel.getSpaceshipLabel());
        }
    }

    /**
     * Returns whether the infinte game is currently running.
     *
     * @return true if the infinite game is running, false otherwise.
     */
    public static boolean isInfiniteGameRunning() {
        return LogicController.isInfiniteGameRunning();
    }
}
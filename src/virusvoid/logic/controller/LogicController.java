package virusvoid.logic.controller;

import java.awt.*;
import java.util.Random;

import virusvoid.logic.enemymanager.BossManager;
import virusvoid.logic.objects.GameObject;
import virusvoid.logic.other.MoveSpaceship;
import virusvoid.logic.other.manager.PlanetManager;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.sound.MusicManager;
import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.gui.other.GuiController;
import virusvoid.logic.other.*;

/**
 * The LogicController class manages the whole game logic.
 */
public class LogicController {

    /**
     * Starts the application by initiating the GUI (and starts menu music).
     */
    public static void startApplication() {
        GuiController.startGui();
        startMenuMusic();
    }

    /**
     * Starts the menu music.
     */
    public static void startMenuMusic() {
        MusicManager.playMusic("VirusVoid_MenuMusic.wav");
    }

    /**
     * Starts the game music.
     */
    public static void startGameMusic() {
        MusicManager.playMusic("VirusVoid_GameMusic.wav");
    }

    /**
     * Stops the currently playing music.
     */
    public static void stopMusic() {
        MusicManager.stopCurrentMusic();
    }

    /**
     * Gets the current music volume.
     *
     * @return The current music volume.
     */
    public static float getMusicVolume() {
        return MusicManager.getMusicVolume();
    }

    /**
     * Gets the current sound effects volume.
     *
     * @return The current sound effects volume.
     */
    public static float getSoundEffectsVolume() {
        return SoundEffectManager.getSoundEffectsVolume();
    }

    /**
     * Sets the music volume.
     *
     * @param volume The desired volume.
     */
    public static void setMusicVolume(float volume) {
        MusicManager.setVolume(volume);
    }

    /**
     * Sets the sound effects volume.
     *
     * @param volume The desired volume.
     */
    public static void setSoundEffectsVolume(float volume) {
        SoundEffectManager.setVolume(volume);
    }

    /**
     * Stops the menu music and starts the game/tutorial music
     */
    private static void musicTransition() {
        stopMusic();
        startGameMusic();
    }

    /**
     * Starts the game by initializing necessary variables and starting enemy and projectile movement and collision detection.
     */
    public static void startGame() {
        musicTransition();
        GameController.startGameController();
        GuiController.repaintPanel(0);
    }

    /**
     * Starts the infinite game by initializing necessary variables and starting enemy and projectile movement and collision detection.
     */
    public static void startInfiniteGame() {
        musicTransition();
        InfiniteGameController.startInifinteGame();
        GuiController.repaintPanel(0);
    }

    /**
     * Starts the tutorial by initializing necessary variables and starting essential processes.
     */
    public static void startTutorial() {
        musicTransition();
        TutorialController.startTutorialController();
        GuiController.repaintPanel(1);
    }

    /**
     * Stops the infinte game.
     */
    public static void stopInfiniteGame() {
        InfiniteGameController.setGameOver();
    }

    /**
     * Stops the game.
     */
    public static void stopGame() {
        GameController.setGameOver();
    }

    /**
     * Pauses or unpauses the game.
     */
    public static void pauseUnpauseGame() {
        GameController.setPaused();
    }

    /**
     * Pauses or unpauses the infinite game.
     */
    public static void pauseUnpauseInfiniteGame() {
        InfiniteGameController.setPaused();
    }

    /**
     * Stops the tutorial.
     */
    public static void stopTutorial() {
        TutorialController.stopTutorial();
    }

    /**
     * Returns whether the infinte game is currently running.
     *
     * @return true if the infinite game is running, false otherwise.
     */
    public static boolean isInfiniteGameRunning() {
        return InfiniteGameController.isInfiniteGameRunning();
    }

    /**
     * Pauses or unpauses the tutorial.
     */
    public static void pauseUnpauseTutorial() {
        TutorialController.setPaused();
    }

    /**
     * Calls a GUI method to provide visual feedback on whether the game or tutorial is paused.
     * Displays an orange screen partially if applicable.
     */
    public static void changeOrangePanelVisibility() {
        GuiController.changeOrangePanelVisibility();
    }

    /**
     * Calls a GUI method to provide visual feedback on whether the game or tutorial is paused.
     * Always displays the feedback.
     */
    public static void changePauseLabelVisibility() {
        GuiController.changePauseLabelVisibility();
    }

    /**
     * Passes whether EnemyInstructions should be displayed in the tutorial.
     *
     * @return True if EnemyInstructions should be displayed, false otherwise.
     */
    public static boolean isDrawEnemyInstructions() {
        return TutorialController.isDrawEnemyInstructions();
    }

    /**
     * Passes whether PauseAndQuitInstructions should be displayed in the tutorial.
     *
     * @return True if PauseAndQuitInstructions should be displayed, false otherwise.
     */
    public static boolean isDrawPauseAndQuit() {
        return TutorialController.isDrawPauseAndQuit();
    }

    /**
     * Passes whether MovementInstructions should be displayed in the tutorial.
     *
     * @return True if MovementInstructions should be displayed, false otherwise.
     */
    public static boolean isDrawMovement() {
        return TutorialController.isDrawMovement();
    }

    /**
     * Passes whether KIVoiceOver is active.
     *
     * @return True if KIVoiceOver is active, false otherwise.
     */
    public static boolean isKiVoiceOver() {
        return TutorialController.isKiVoiceOver();
    }

    /**
     * Ends the KeyListener in the GUI.
     */
    protected static void shutKeyListenerDown() {
        GuiController.shutKeyListenerDown();
    }

    /**
     * Resumes the KeyListener in the GUI.
     */
    public static void resumeKeyListener() {
        GuiController.resumeKeyListener();
    }

    /**
     * Pauses the KeyListener in the GUI.
     */

    public static void pauseKeyListener() {
        GuiController.pauseKeyListener();
    }

    /**
     * Calls a Method in the GUI which displays the Game Over screen after the game ends.
     *
     * @param endMessage      The message displayed on the EndScreen.
     * @param messageColor    The color of the message text.
     * @param againOrRetry    String that is either "again" or "retry," depending on why the screen is displayed.
     * @param infiniteOrNot   True if the infinite game should be started when pressing the retry button
     */
    public static void gameOverScreen(String endMessage, Color messageColor, String againOrRetry, boolean infiniteOrNot) {
        GuiController.gameOverScreen(endMessage, messageColor, againOrRetry, infiniteOrNot);
    }

    /**
     * Calls a Method in the GUI which displays the Tutorial Over screen after the tutorial ends.
     *
     * @param endMessage      The message displayed on the EndScreen.
     * @param messageColor    The color of the message text.
     */
    public static void tutorialEndScreen(String endMessage, Color messageColor) {
        GuiController.tutorialEndScreen(endMessage, messageColor);
    }

    /**
     * Calls a method in TutorialController to skip the story in the tutorial.
     */
    public static void skipStory() {
        TutorialController.skipKiVoiceOver();
    }

    /**
     * Passes the current level/wave to the GUI to display.
     *
     * @param levelOrWaveNumber The current level/wave.
     */
    protected static void showLevelOrWave(int levelOrWaveNumber) {
        GuiController.showLevelOrWave(levelOrWaveNumber);
    }

    /**
     * Passes whether the level announcement is currently being displayed.
     *
     * @return True if the level announcement is active, false otherwise.
     */
    public static boolean isLevelOrWaveAnnouncement() {
        if (GameController.isGameRunning()) {
            return GameController.isLevelAnnouncement();
        } else {
            return InfiniteGameController.isWaveAnnouncement();
        }
    }

    /**
     * Calls a method in GuiController to create a label for a new enemy with the specified image and coordinates.
     *
     * @param location  The coordinates of the enemy.
     * @param enemyId   An id that uniquely identifies the enemy within its type.
     * @param enemyType The type of the enemy.
     */
    public static void createEnemyLabel(Point location, int enemyId, int enemyType) {
        GuiController.createEnemyLabel(location, enemyId, enemyType);
    }

    /**
     * Calls a method in GuiController to create a label for the Boss with the specified coordinates.
     *
     * @param location The position of the Boss.
     */
    public static void createBossLabel(Point location) {
        GuiController.createBossLabel(location);
    }

    /**
     * Calls a method in GuiController to create a new label for a projectile at the specified coordinates.
     *
     * @param location      The coordinates of the projectile.
     * @param projectileId  An id that uniquely identifies the projectile within its type.
     * @param type          The type of the projectile.
     */
    public static void createProjectileLabel(Point location, int projectileId, int type) {
        GuiController.createProjectileLabel(location, projectileId, type);
    }

    /**
     * Calls a method in GuiController to update the position of an existing label.
     *
     * @param location  The coordinates for the projectile.
     * @param labelId   An id that identifies the label to be updated, combined with labelType and innerType.
     * @param innerType The type that identifies the label to be updated, combined with labelId.
     * @param labelType The type of the label, whether projectile or enemy.
     */
    public static void updateLabel(Point location, int labelId, int innerType, int labelType) {
        GuiController.updateLabel(location, labelId, innerType, labelType);
    }

    /**
     * Calls a method in GuiController to remove an enemy label.
     *
     * @param labelId   An id that partially identifies the label to be removed.
     * @param innerType The type that partially identifies the label to be removed.
     * @param labelType The type of the label, whether projectile or enemy.
     */
    public static void removeLabel(int labelId, int innerType, int labelType) {
        GuiController.removeLabel(labelId, innerType, labelType);
    }

    /**
     * Calls a method in GuiController to update the Boss label.
     *
     * @param location The new position of the Boss.
     */
    public static void updateBoss(Point location) {
        GuiController.updateBoss(location);
    }

    /**
     * Calls scaleX of GuiController.
     *
     * @param defaultValue The default value to be scaled.
     * @return The value scaled by scaleX from GuiController.
     */
    public static int scaleX(double defaultValue) {
        return GuiController.scaleX(defaultValue);
    }

    /**
     * Calls scaleY of GuiController.
     *
     * @param defaultValue The default value to be scaled.
     * @return The value scaled by scaleY from GuiController.
     */
    public static int scaleY(double defaultValue) {
        return GuiController.scaleY(defaultValue);
    }

    /**
     * Passes the direction in which the spaceship should move.
     *
     * @param keycode            The keycode representing the direction.
     * @param pressedOrReleased  Whether the key is pressed or released.
     */
    public static void updateDirection(int keycode, boolean pressedOrReleased) {
        MoveSpaceship.updateDirection(keycode, pressedOrReleased);
    }

    /**
     * Returns the updated spaceship location.
     *
     * @return The updated spaceship location.
     */
    public static Point updateSpaceshipLocation() {
        return MoveSpaceship.updateSpaceshipLocation();
    }

    /**
     * Returns whether the Boss has spawned.
     *
     * @return True if the Boss has spawned, false otherwise.
     */
    public static boolean isBossSpawned() {
        if (GameController.isGameRunning()) {
            return GameController.isBossSpawned();
        } else {
            return InfiniteGameController.isBossSpawned();
        }
    }

    /**
     * Returns a random x-coordinate within a constrained range.
     *
     * @return The random x-coordinate.
     */
    public static int getRandomWidthX() {
        Random random = new Random();
        return random.nextInt(getMainFrameWidth() - 2 * scaleY(50) - scaleX(20) + 1)
                + SpaceshipManager.getSpaceship().getWidth();
    }

    /**
     * Returns the width of the main frame.
     *
     * @return The width of the main frame.
     */
    public static int getMainFrameWidth() {
        return GuiController.getMainFrameWidth();
    }

    /**
     * Returns the height of the main frame.
     *
     * @return The height of the main frame.
     */
    public static int getMainFrameHeight() {
        return GuiController.getMainFrameHeight();
    }

    /**
     * Returns the current HP of the player.
     *
     * @return The current HP of the player.
     */
    public static float getPlayerHp() {
        if (SpaceshipManager.getSpaceship() == null) {
            return 20.0f;
        }
        return (float) SpaceshipManager.getSpaceship().getHp();
    }

    /**
     * Returns the maximum HP of the player.
     *
     * @return The maximum HP of the player.
     */
    public static float getMaxPlayerHp() {
        if (SpaceshipManager.getSpaceship() == null) {
            return 20.0f;
        }
        return (float) SpaceshipManager.getSpaceship().getMaxHp();
    }

    /**
     * Updates the label displaying the planet's HP.
     */
    public static void updatePlanetHp() {
        GuiController.setPlanetHp(PlanetManager.getPlanet().getHp());
    }

    /**
     * Returns the current HP of the Boss.
     *
     * @return The current HP of the Boss.
     */
    public static float getBossHp() {
        if (BossManager.getBoss() == null) {
            return 100.0f;
        }
        return (float) BossManager.getBoss().getHp();
    }

    /**
     * Returns the maximum HP of the Boss.
     *
     * @return The maximum HP of the Boss.
     */
    public static float getMaxBossHp() {
        if (BossManager.getBoss() == null) {
            return 100.0f;
        }
        return (float) BossManager.getBoss().getMaxHp();
    }

    /**
     * Checks if two GameObjects (rectangles) collide.
     *
     * @param box1 The first logical box.
     * @param box2 The second logical box.
     * @return True if the rectangles collide, false otherwise.
     */
    public static boolean collision(GameObject box1, GameObject box2) {
        return (box1.getX() < box2.getX() + box2.getWidth()) &&
               (box1.getX() + box1.getWidth() > box2.getX()) &&
               (box1.getY() < box2.getY() + box2.getHeight()) &&
               (box1.getY() + box1.getHeight() > box2.getY());
    }

    /**
     * Calls a method in GuiController to display a specific frame for the player's projectile animation.
     *
     * @param frameIndex       The index of the animation frame.
     * @param xLeftProjectile  The x-coordinate of the left projectile.
     * @param xRightProjectile The x-coordinate of the right projectile.
     * @param projectileY      The y-coordinate of the projectile.
     */
    public static void showPlayerProjectileAnimationFrame(int frameIndex, int xLeftProjectile, int xRightProjectile, int projectileY) {
        GuiController.showPlayerProjectileAnimationFrame(frameIndex, xLeftProjectile, xRightProjectile, projectileY);
    }

    /**
     * Calls a method in GuiController to remove labels associated with the player's projectile animation.
     */
    public static void removePlayeProjectileAnimationLabels() {
        GuiController.removePlayeProjectileAnimationLabels();
    }

    /**
     * Calls a method in GuiController to display a specific frame of an explosion animation.
     *
     * @param frameIndex         The index of the animation frame.
     * @param x                  The x-coordinate of the explosion.
     * @param y                  The y-coordinate of the explosion.
     * @param explosionFrameType The type of explosion frame.
     */
    public static void showExplosionAnimationFrame(int frameIndex, int x, int y, char explosionFrameType) {
        GuiController.showEnemyExplosionAnimationFrame(frameIndex, x, y, explosionFrameType);
    }

    /**
     * Calls a method in GuiController to remove a label associated with an explosion animation.
     */
    public static void removeExplosionAnimationLabel() {
        GuiController.removeExplosionAnimationLabel();
    }

    /**
     * Calls a method in GuiController to clear all labels associated with explosion animations.
     */
    public static void clearExplosionAnimationLabels() {
        GuiController.clearExplosionAnimationLabels();
    }

    /**
     * Calls a method in GuiController to remove the Boss label.
     */
    public static void removeBossLabel() {
        GuiController.removeBossLabel();
    }

    /**
     * Calls a method in GuiController to remove the Spaceship label.
     */
    public static void removeSpaceshipLabel() {
        GuiController.removeSpaceshipLabel();
    }
}
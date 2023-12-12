package virusvoid.logic.objects;

import virusvoid.logic.controller.TutorialController;
import virusvoid.logic.other.TutorialAndGameHelper;
import virusvoid.logic.projectilemanager.PlayerProjectileManager;
import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.logic.controller.GameController;
import virusvoid.logic.controller.InfiniteGameController;
import virusvoid.logic.controller.LogicController;

import java.awt.*;

/**
 * Represents the player's spaceship in the game.
 */
public class Spaceship extends GameObject {

    private final Polygon spaceshipHitbox;
    private final int maxHp = 20; // 20
    private boolean exploding;
    private int hp;

    /**
     * Initializes a new Spaceship with specified attributes.
     * Also initializes the Hitbox of the Spacship.
     *
     * @param x The x-coordinate of the Spaceship.
     * @param y The y-coordinate of the Spaceship.
     */
    public Spaceship(int x, int y) {
        super(x, y, LogicController.scaleX(50), LogicController.scaleX(50));
        exploding = false;
        hp = maxHp;

        int[] xPoints = {24, 33, 49, 49, 37, 12, 0, 0, 16};
        int[] yPoints = {0, 22, 16, 36, 50, 50, 36, 16, 22};
        spaceshipHitbox = new Polygon(scaleAndRepositionCoordinates(xPoints, 'X', x), scaleAndRepositionCoordinates(yPoints, 'Y', y), xPoints.length);
    }

    /**
     * Scales and repositions the coordinates based on the specified scaling direction and offset.
     *
     * @param points           The original array of coordinates to scale and reposition.
     * @param scalingDirection The direction in which to scale the coordinates ('X' or 'Y').
     * @param offset           The offset to apply to the coordinates.
     * @return The scaled and repositioned coordinates.
     */
    private int[] scaleAndRepositionCoordinates(int[] points, char scalingDirection, int offset) {
        int pointsLength = points.length;
        int[] scaledPoints = new int[pointsLength];

        for (int i = 0; i < pointsLength; ++i) {
            int valueToScale = points[i];
            int scaledValue = (scalingDirection == 'X') ? LogicController.scaleX(valueToScale) : LogicController.scaleY(valueToScale);
            scaledPoints[i] = scaledValue + offset;
        }

        return scaledPoints;
    }

    /**
     * Updates the coordinates of the Spaceship's hitbox based on the specified direction.
     *
     * @param xDirection The direction in which to update the x-coordinates.
     * @param yDirection The direction in which to update the y-coordinates.
     */
    public void updateSpaceshipHitbox(int xDirection, int yDirection) {
        for (int i = 0; i < spaceshipHitbox.npoints; ++i) {
            spaceshipHitbox.xpoints[i] = spaceshipHitbox.xpoints[i] + xDirection;
            spaceshipHitbox.ypoints[i] = spaceshipHitbox.ypoints[i] + yDirection;
        }
    }

    /**
     * Inflicts damage to the Spaceship and handles the consequences, such as animation and game over.
     *
     * @param damage The amount of damage to be inflicted.
     */
    public void damage(int damage) {
        if (hp > 0) {
            hp -= damage;
            System.out.println("SpaceshipHP: " + hp);
        }

        if (hp <= 0 && !exploding) {
            exploding = true;
            new Thread(() -> {
                TutorialAndGameHelper.animateExplosion(this, 'S', 60, 2, 20);
                SoundEffectManager.playAndLoadClip("VirusVoid_LargeExplosion.wav");
                PlayerProjectileManager.removePlayerProjectiles();
                LogicController.removeSpaceshipLabel();

                if (GameController.isGameRunning()) {
                    TutorialAndGameHelper.waitAndCheckForPause(2500, 10, 20, GameController::isGameRunning, GameController::isPaused);
                } else if (InfiniteGameController.isInfiniteGameRunning()) {
                    TutorialAndGameHelper.waitAndCheckForPause(2500, 10, 20, InfiniteGameController::isInfiniteGameRunning, InfiniteGameController::isPaused);
                } else {
                    TutorialAndGameHelper.waitAndCheckForPause(2500, 10, 20, TutorialController::isTutorialRunning, TutorialController::isPaused);
                }

                if (GameController.isGameRunning() || InfiniteGameController.isInfiniteGameRunning()) {
                    LogicController.gameOverScreen("The Virus-Void got to your Planet", Color.RED, "Retry", InfiniteGameController.isInfiniteGameRunning());
                }

                if (GameController.isGameRunning()) {
                    GameController.setGameOver();
                } else if (TutorialController.isTutorialRunning()) {
                    TutorialController.stopTutorial();
                    LogicController.tutorialEndScreen("You didn't finish the TUTORIAL.", Color.RED);
                } else if (InfiniteGameController.isInfiniteGameRunning()) {
                    InfiniteGameController.setGameOver();
                }
            }).start();
        }
    }

    /**
     * Gets the current health points of the Spaceship.
     *
     * @return The current health points of the Spaceship.
     */
    public int getHp() {
        return Math.max(hp, 0);
    }

    /**
     * Gets the maximum health points of the Spaceship.
     *
     * @return The maximum health points of the Spaceship.
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Gets the hitbox polygon representing the Spaceship.
     *
     * @return The hitbox polygon of the Spaceship.
     */
    public Polygon getSpaceshipHitbox() {
        return spaceshipHitbox;
    }

    /**
     * Checks if the Spaceship is currently exploding.
     *
     * @return True if the Spaceship is exploding; otherwise, false.
     */
    public boolean isExploding() {
        return exploding;
    }
}
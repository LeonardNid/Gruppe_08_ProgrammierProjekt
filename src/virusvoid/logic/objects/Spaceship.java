package virusvoid.logic.objects;

import virusvoid.logic.controller.TutorialController;
import virusvoid.logic.other.TutorialAndGameHelper;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.projectilemanager.PlayerProjectileManager;
import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.logic.controller.GameController;
import virusvoid.logic.controller.InfiniteGameController;
import virusvoid.logic.controller.LogicController;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Represents the player's spaceship in the game.
 */
public class Spaceship extends GameObject {

    private final int speed = (LogicController.scaleX(4.5) + LogicController.scaleY(4.5)) / 2;
    private final int scaled910 = LogicController.scaleX(910);
    private final int scaled480 = LogicController.scaleY(480);
    private static boolean moveRight = false;
    private static boolean moveDown = false;
    private static boolean moveLeft = false;
    private static boolean moveUp = false;
    private final Polygon spaceshipHitbox;
    private final int maxHp = 20; // 20
    private boolean exploding;
    private final int height;
    private final int width;
    private int hp;

    /**
     * Initializes a new Spaceship with specified attributes.
     * Also initializes the Hitbox of the Spacship.
     *
     * @param x The x-coordinate of the Spaceship.
     * @param y The y-coordinate of the Spaceship.
     */
    public Spaceship(int x, int y) {
        super(x, y);
        this.height = LogicController.scaleX(50);
        this.width = LogicController.scaleX(50);
        exploding = false;
        hp = maxHp;

        int[] xPoints = {24, 33, 49, 49, 37, 12, 0, 0, 16};
        int[] yPoints = {0, 22, 16, 36, 50, 50, 36, 16, 22};
        spaceshipHitbox = new Polygon(scaleAndRepositionCoordinates(xPoints, 'X', x), scaleAndRepositionCoordinates(yPoints, 'Y', y), xPoints.length);
    }

    /**
     * Updates the location of the spaceship based on user input.
     * (Hitbox of spacship is also updated in here)
     *
     * @return The updated location of the spaceship.
     */
    public Point updateSpaceshipLocation() {
        if (!exploding) {
            int spaceshipSize = this.getHeight();
            int horizontalSpeed = 0;
            int locationX = this.x;
            int locationY = this.y;
            int verticalSpeed = 0;

            if (moveLeft && locationX > spaceshipSize / 5) {
                horizontalSpeed -= speed;
            }
            if (moveUp && locationY > spaceshipSize / 5) {
                verticalSpeed -= speed;
            }
            if (moveRight && locationX < scaled910 - spaceshipSize / 5) {
                horizontalSpeed += speed;
            }
            if (moveDown && locationY < scaled480 - spaceshipSize / 5) {
                verticalSpeed += speed;
            }

            // When moving both horizontally and vertically, the speed is halved
            if (horizontalSpeed != 0 && verticalSpeed != 0) {
                horizontalSpeed = (int) (horizontalSpeed / Math.sqrt(2));
                verticalSpeed = (int) (verticalSpeed / Math.sqrt(2));
            }

            locationX = Math.max(Math.min(locationX + horizontalSpeed, scaled910 - spaceshipSize / 5), spaceshipSize / 5);
            locationY = Math.max(Math.min(locationY + verticalSpeed, scaled480 - spaceshipSize / 5), spaceshipSize / 5);

            this.updateSpaceshipHitbox(locationX - this.x, locationY - this.y);

            this.x = locationX;
            this.y = locationY;

            return this.getLocation();
        }

        return new Point(LogicController.scaleX(460), LogicController.scaleY(420));
    }

    /**
     * Updates the movement direction based on keyboard input.
     *
     * @param keycode            The key code corresponding to the pressed or released key.
     * @param pressedOrReleased  {@code true} if the key is pressed, {@code false} if released.
     */
    public void updateDirection(int keycode, boolean pressedOrReleased) {
        if (keycode == KeyEvent.VK_A || keycode == KeyEvent.VK_LEFT) {
            moveLeft = pressedOrReleased;
        } else if (keycode == KeyEvent.VK_W || keycode == KeyEvent.VK_UP) {
            moveUp = pressedOrReleased;
        } else if (keycode == KeyEvent.VK_D || keycode == KeyEvent.VK_RIGHT) {
            moveRight = pressedOrReleased;
        } else if (keycode == KeyEvent.VK_S || keycode == KeyEvent.VK_DOWN) {
            moveDown = pressedOrReleased;
        }
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

    /**
     * Gets the height of the spaceship.
     *
     * @return The height of the spaceship.
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the spaceship.
     *
     * @return The width of the spaceship.
     */
    @Override
    public int getWidth() {
        return width;
    }
}
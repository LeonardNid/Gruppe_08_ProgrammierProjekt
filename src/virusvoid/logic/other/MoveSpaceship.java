package virusvoid.logic.other;

import virusvoid.logic.controller.LogicController;
import virusvoid.logic.objects.Spaceship;
import virusvoid.logic.other.manager.SpaceshipManager;

import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 * Manages the movement of the spaceship based on user input.
 */
public class MoveSpaceship {

    private static boolean moveRight;
    private static boolean moveDown;
    private static boolean moveLeft;
    private static boolean moveUp;
    private static int scaled910;
    private static int scaled480;
    private static int speed;

    /**
     * Initializes the spaceship movement manager with default values.
     */
    public static void startMoveSpaceship() {
        speed = (LogicController.scaleX(4.5) + LogicController.scaleY(4.5)) / 2;
        scaled910 = LogicController.scaleX(910);
        scaled480 = LogicController.scaleY(480);
        moveRight = false;
        moveDown = false;
        moveLeft = false;
        moveUp = false;
    }

    /**
     * Updates the location of the spaceship based on user input.
     * (Hitbox of spacship is also updated in here)
     *
     * @return The updated location of the spaceship.
     */
    public static Point updateSpaceshipLocation() {
        Spaceship spaceship = SpaceshipManager.getSpaceship();
        if (spaceship != null && !spaceship.isExploding()) {
            int spaceshipSize = spaceship.getHeight();
            int locationX = spaceship.x;
            int locationY = spaceship.y;
            int horizontalSpeed = 0;
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

            spaceship.updateSpaceshipHitbox(locationX - spaceship.x, locationY - spaceship.y);

            spaceship.x = locationX;
            spaceship.y = locationY;

            return spaceship.getLocation();
        }

        return new Point(LogicController.scaleX(460), LogicController.scaleY(420));
    }

    /**
     * Updates the movement direction based on keyboard input.
     *
     * @param keycode            The key code corresponding to the pressed or released key.
     * @param pressedOrReleased  {@code true} if the key is pressed, {@code false} if released.
     */
    public static void updateDirection(int keycode, boolean pressedOrReleased) {
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
}
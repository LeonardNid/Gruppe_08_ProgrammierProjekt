package virusvoid.logic.other.manager;

import virusvoid.logic.controller.LogicController;
import virusvoid.logic.objects.Spaceship;

/**
 * Manages the {@link Spaceship} instance in the game.
 */
public class SpaceshipManager {

    private static Spaceship spaceship;

    /**
     * Initializes the spaceship manager by creating a new {@link Spaceship} instance.
     */
    public static void startSpaceshipManager() {
        spaceship = new Spaceship(LogicController.scaleX(460), LogicController.scaleY(420));
    }

    /**
     * Gets the spaceship instance managed by this manager.
     *
     * @return The {@link Spaceship} instance.
     */
    public static Spaceship getSpaceship() {
        return spaceship;
    }

    /**
     * Checks if the spaceship is currently exploding.
     *
     * @return {@code true} if the spaceship is exploding, {@code false} otherwise.
     */
    public static boolean isSpaceshipExploding() {
        if (spaceship != null) {
            return spaceship.isExploding();
        }
        return false;
    }
}
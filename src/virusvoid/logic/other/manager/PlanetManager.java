package virusvoid.logic.other.manager;

import virusvoid.logic.objects.Planet;

/**
 * Manages the {@link Planet} instance in the game.
 */
public class PlanetManager {

    private static Planet planet;

    /**
     * Initializes the planet manager by creating a new {@link Planet} instance.
     */
    public static void startPlanetManager() {
        planet = new Planet();
    }

    /**
     * Gets the planet instance managed by this manager.
     *
     * @return The {@link Planet} instance.
     */
    public static Planet getPlanet() {
        return planet;
    }
}
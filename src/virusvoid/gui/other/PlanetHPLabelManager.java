package virusvoid.gui.other;

import virusvoid.gui.labels.PlanetHPLabel;

/**
 * The {@code PlanetHPLabelManager} class manages the display of the planet's health points (HP) label.
 * It provides methods to initialize and retrieve the planet's HP label.
 */
public class PlanetHPLabelManager {

    private static PlanetHPLabel planetHPLabel;

    /**
     * Initializes the PlanetHPLabelManager by creating a new instance of the PlanetHPLabel.
     * This method should be called once to set up the manager.
     */
    public static void startPlanetHPLabelManager() {
        planetHPLabel = new PlanetHPLabel();
    }

    /**
     * Retrieves the instance of the PlanetHPLabel managed by this manager.
     *
     * @return The PlanetHPLabel instance.
     */
    public static PlanetHPLabel getPlanetHPLabel () {
        return planetHPLabel;
    }
}
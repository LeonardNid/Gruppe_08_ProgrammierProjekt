package virusvoid.logic.other.manager;

import virusvoid.logic.controller.TutorialController;

/**
 * Time manager specific to the tutorial, extending the abstract {@code TimeManager} class.
 * Manages tutorial time and provides functionality to check if the game is paused.
 */
public class TutorialTimeManager extends TimeManager {

    /**
     * Constructs a {@code TutorialTimeManager} with the specified execution interval in milliseconds.
     *
     * @param executeIntervalMillis The execution interval in milliseconds.
     */
    public TutorialTimeManager(long executeIntervalMillis) {
        super(executeIntervalMillis);
    }

    /**
     * Checks if the tutorial is currently paused.
     *
     * @return {@code true} if the tutorial is paused, {@code false} otherwise.
     */
    @Override
    public boolean isPaused() {
        return TutorialController.isPaused();
    }
}
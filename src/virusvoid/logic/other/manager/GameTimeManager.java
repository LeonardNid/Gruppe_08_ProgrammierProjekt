package virusvoid.logic.other.manager;

import virusvoid.logic.controller.GameController;

/**
 * Manages game time and provides functionality to check if the game is paused.
 */
public class GameTimeManager extends TimeManager {

    /**
     * Initializes a new GameTimeManager with the specified execution interval in milliseconds.
     *
     * @param executeIntervalMillis The interval at which the manager's actions are executed.
     */
    public GameTimeManager(long executeIntervalMillis) {
        super(executeIntervalMillis);
    }

    /**
     * Checks if the game is currently paused.
     *
     * @return True if the game is paused, {@code false} otherwise.
     */
    @Override
    public boolean isPaused() {
        return GameController.isPaused();
    }
}
package virusvoid.logic.other.manager;

import virusvoid.logic.controller.InfiniteGameController;

/**
 * Manages game time (infinite mode) and provides functionality to check if the game is paused.
 */
public class InfiniteGameTimeManager extends TimeManager {

    /**
     * Initializes a new GameTimeManager with the specified execution interval in milliseconds.
     *
     * @param executeIntervalMillis The interval at which the manager's actions are executed.
     */
    public InfiniteGameTimeManager(long executeIntervalMillis) {
        super(executeIntervalMillis);
    }

    /**
     * Checks if the game (infinite mode) is currently paused.
     *
     * @return True if the game is paused, {@code false} otherwise.
     */
    @Override
    public boolean isPaused() {
        return InfiniteGameController.isPaused();
    }
}

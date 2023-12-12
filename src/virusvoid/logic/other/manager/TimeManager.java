package virusvoid.logic.other.manager;

/**
 * Abstract class for managing the execution interval of events based on elapsed time.
 */
public abstract class TimeManager {

    private long executeInterval;
    private long elapsedTime;
    private long lastTime;

    /**
     * Constructs a {@code TimeManager} with the specified execution interval in milliseconds.
     *
     * @param executeIntervalMillis The execution interval in milliseconds.
     */
    public TimeManager(long executeIntervalMillis) {
        setExecuteInterval(executeIntervalMillis);
        this.lastTime = 0;
    }

    /**
     * Updates the elapsed time based on the system's current time.
     */
    public void updateElapsedTime() {
        long currentTime = System.nanoTime();

        if (isPaused()) {
            lastTime = currentTime - elapsedTime;
        } else {
            elapsedTime = currentTime - lastTime;
        }
    }

    /**
     * Checks if the interval has elapsed and resets the timer if it has.
     *
     * @return {@code true} if the interval has elapsed, {@code false} otherwise (also false if isPaused() returns true).
     */
    public boolean shouldExecute() {
        boolean shouldExecute = (elapsedTime >= executeInterval);

        if (shouldExecute) {
            lastTime = System.nanoTime();
        }

        if (isPaused()) {
            return false;
        }
        return shouldExecute;
    }

    /**
     * Sets the execution interval for events.
     *
     * @param executeIntervalMillis The execution interval in milliseconds.
     */
    public void setExecuteInterval(long executeIntervalMillis) {
        this.executeInterval = executeIntervalMillis * 1_000_000;
    }

    /**
     * Checks whether the event (to be specified in the implementation) is currently paused.
     *
     * @return {@code true} whether the event is paused, {@code false} otherwise.
     */
    abstract public boolean isPaused();
}
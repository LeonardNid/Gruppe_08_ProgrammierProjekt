package virusvoid.help;

/**
 * Utility class providing a method for waiting/sleeping in the application.
 */
public class WaitHelper {

    /**
     * Causes the currently executing thread to sleep (temporarily cease execution) for the specified number of milliseconds.
     *
     * @param waitTime The time to sleep in milliseconds.
     */
    public static void waitFor(int waitTime) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package virusvoid.gui.other;

import virusvoid.gui.panels.GameTutorialPanel;
import virusvoid.gui.labels.SpaceshipLabel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingUtilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The {@code SpaceshipKeyListener} class implements a KeyListener for spaceship movement in the game.
 * It listens for keyboard inputs, updates the spaceship's location, and provides methods to control
 * the execution of the associated ScheduledExecutorService.
 */
public class SpaceshipKeyListener implements KeyListener {

    private final ScheduledExecutorService executor;
    private final SpaceshipLabel spaceship;
    private final int keyListenerType;
    private boolean pause;

    /**
     * Constructs a SpaceshipKeyListener with the specified SpaceshipLabel and key listener type.
     *
     * @param spaceship       The SpaceshipLabel associated with this key listener.
     * @param keyListenerType The type of the key listener (e.g., game or tutorial).
     */
    public SpaceshipKeyListener(SpaceshipLabel spaceship, int keyListenerType) {
        this.keyListenerType = keyListenerType;
        this.spaceship = spaceship;
        this.pause = false;

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (!pause) {
                updateSpaceshipLocation();
            }
        }, 0, 20, TimeUnit.MILLISECONDS);
    }

    /**
     * Handles the key pressed event. The spaceship can be moved cleanly using the arrow keys or the wasd.
     * Pausing and ending the game/tutorial is possible via this method.
     *
     * @param keyEvent The KeyEvent associated with the key pressed event.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keycode = keyEvent.getKeyCode();
        GuiController.updateDirection(keycode, true);
        if (keycode == KeyEvent.VK_ESCAPE) {
            GuiController.quit(keyListenerType);
            GuiController.switchPanel(new GameTutorialPanel());
        }
        if (keycode == KeyEvent.VK_P) {
            GuiController.pause(keyListenerType);
        }
        if (keyListenerType == 1 && GuiController.isKiVoiceOver() && keycode == KeyEvent.VK_SPACE) {
            GuiController.skipStory();
        }
    }

    /**
     * Handles the key released event. The spaceship can be moved cleanly using the arrow keys or the wasd.
     *
     * @param keyEvent The KeyEvent associated with the key released event.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        GuiController.updateDirection(keyEvent.getKeyCode(), false);
    }

    /**
     * Handles the key typed event (not implemented).
     *
     * @param e The KeyEvent associated with the key typed event.
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Updates the spaceship's label location on the Swing event dispatch thread.
     */
    private void updateSpaceshipLocation() {
        SwingUtilities.invokeLater(() -> spaceship.setLocation(GuiController.updateSpaceshipLocation()));
    }

    /**
     * Shuts down the executor service associated with this key listener.
     */
    protected void shutExecutorDown() {
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
    }

    /**
     * Pauses the executor service associated with this key listener.
     */
    public void pauseExecutor() {
        pause = true;
    }

    /**
     * Resumes the executor service associated with this key listener.
     */
    public void resumeExecutor() {
        pause = false;
    }
}
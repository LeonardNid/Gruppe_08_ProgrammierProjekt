package virusvoid.logic.other;

import virusvoid.logic.controller.TutorialController;
import virusvoid.logic.projectilemanager.EnemyProjectileManager;
import virusvoid.logic.projectilemanager.PlayerProjectileManager;
import virusvoid.logic.projectilemanager.ProjectileManagerHelper;
import virusvoid.help.WaitHelper;
import virusvoid.logic.controller.GameController;
import virusvoid.logic.controller.InfiniteGameController;
import virusvoid.logic.controller.LogicController;
import virusvoid.logic.enemymanager.DefaultEnemyManager;
import virusvoid.logic.enemymanager.EnemyManagerHelper;
import virusvoid.logic.enemymanager.ShootingEnemyManager;
import virusvoid.logic.objects.GameObject;
import virusvoid.logic.other.manager.GameTutorialEventManager;
import virusvoid.logic.other.manager.PlanetManager;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.other.manager.TimeManager;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;

/**
 * Helper class for managing various aspects of the game and tutorial.
 */
public class TutorialAndGameHelper {

    /**
     * Starts the necessary managers for the game and tutorial.
     */
    public static void startManagers() {
        SpaceshipManager.startSpaceshipManager();

        PlanetManager.startPlanetManager();
        LogicController.updatePlanetHp();

        ProjectileManagerHelper.startProjectileManagerHelper();
        PlayerProjectileManager.startPlayerProjectileManager();

        EnemyManagerHelper.startEnemyManager();
        DefaultEnemyManager.startDefaultEnemyManager();
        ShootingEnemyManager.startShootingEnemyManager();

        EnemyProjectileManager.startEnemyProjectileManager();
    }

    /**
     * Moves and spawns player projectiles based on the given time managers.
     *
     * @param playerProjectileSpawnTimeManager The time manager for spawning player projectiles.
     * @param playerProjectileMoveTimeManager  The time manager for moving player projectiles.
     */
    public static void moveAndSpawnPlayerProjectiles(TimeManager playerProjectileSpawnTimeManager, TimeManager playerProjectileMoveTimeManager) {
        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_PLAYER_PROJECTILES, playerProjectileSpawnTimeManager);
        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_PLAYER_PROJECTILES, playerProjectileMoveTimeManager, 10);
    }

    /**
     * Animates an explosion for the given game object.
     *
     * @param gameObject         The game object for which the explosion is animated.
     * @param explosionFrameType The type of explosion frame to use ('E' = Enemy, 'B' = Boss, 'S' = Spaceship).
     * @param waitTime           The duration to wait between animation frames.
     * @param maxLoopRotations   The time between each loop rotation.
     * @param timeUpdateRate     The rate at which to update the elapsed time.
     */
    public static void animateExplosion(GameObject gameObject, char explosionFrameType, int waitTime, int maxLoopRotations, int timeUpdateRate) {
        int offset = (switch (explosionFrameType) {
                          case ('E') -> LogicController.scaleX(35);
                          case ('B') -> LogicController.scaleX(300);
                          case ('S') -> LogicController.scaleX(100);
                          default -> 0;
                      } - gameObject.getWidth()) / 2;

        new Thread(() -> {
            for (int i = 0; i < 13; ++i) {
                LogicController.showExplosionAnimationFrame(i, gameObject.x - offset, gameObject.y - offset, explosionFrameType);

                if (GameController.isGameRunning()) {
                    waitAndCheckForPause(waitTime, maxLoopRotations, timeUpdateRate, GameController::isGameRunning, GameController::isPaused);
                } else if (InfiniteGameController.isInfiniteGameRunning()) {
                    waitAndCheckForPause(waitTime, maxLoopRotations, timeUpdateRate, InfiniteGameController::isInfiniteGameRunning, InfiniteGameController::isPaused);
                } else {
                    waitAndCheckForPause(waitTime, maxLoopRotations, timeUpdateRate, TutorialController::isTutorialRunning, TutorialController::isPaused);
                }

                LogicController.removeExplosionAnimationLabel();
            }
        }).start();
    }

    /**
     * Waits for a specified duration while checking for game or tutorial pause conditions.
     *
     * @param waitTime           The duration to wait.
     * @param maxLoopRotations   The time between each loop rotation.
     * @param timeUpdateRate     The rate at which to update the time.
     * @param runningCondition   The condition for the game or tutorial to be running.
     * @param pausedCondition    The condition for the game or tutorial to be paused.
     */
    public static void waitAndCheckForPause(long waitTime, int maxLoopRotations, int timeUpdateRate, BooleanSupplier runningCondition, BooleanSupplier pausedCondition) {
        long waitStartTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (elapsedTime < waitTime && runningCondition.getAsBoolean()) {
            long currentTime = System.currentTimeMillis();
            if (pausedCondition.getAsBoolean()) {
                waitStartTime = currentTime - elapsedTime;
            } else {
                if (currentTime - waitStartTime >= timeUpdateRate) {
                    elapsedTime = currentTime - waitStartTime;
                }
            }
            WaitHelper.waitFor(maxLoopRotations);
        }
    }

    /**
     * Sets the game to a paused or unpaused state.
     * If the game is paused, it also manages UI elements accordingly.
     *
     * @param levelOrWaveAnnouncement Indicates if a level or wave announcement is active
     * @param paused                  Indicates if the (infinite) game is paused
     */
    public static void setGamePaused(AtomicBoolean levelOrWaveAnnouncement, AtomicBoolean paused) {
        LogicController.changePauseLabelVisibility();

        if (!levelOrWaveAnnouncement.get()) {
            LogicController.changeOrangePanelVisibility();
        }
        if (paused.get()) {
            paused.set(false);
            LogicController.resumeKeyListener();
        } else {
            paused.set(true);
            LogicController.pauseKeyListener();
        }
    }

    /**
     * Removes all projectiles and explosion animations from the game.
     * This method is called when transitioning between levels or when the game ends.
     */
    public static void removeProjectilesAndAnimations() {
        LogicController.clearExplosionAnimationLabels();
        ProjectileManagerHelper.deleteProjectiles();
    }
}
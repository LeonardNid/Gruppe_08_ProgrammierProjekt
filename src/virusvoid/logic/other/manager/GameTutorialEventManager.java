package virusvoid.logic.other.manager;

import virusvoid.logic.other.GameTutorialEvent;
import virusvoid.logic.projectilemanager.BossProjectileManager;
import virusvoid.logic.projectilemanager.EnemyProjectileManager;
import virusvoid.logic.projectilemanager.PlayerProjectileManager;
import virusvoid.help.WaitHelper;
import virusvoid.logic.enemymanager.BossManager;
import virusvoid.logic.enemymanager.DefaultEnemyManager;
import virusvoid.logic.enemymanager.ShootingEnemyManager;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages events and actions related to the game/tutorial.
 */
public class GameTutorialEventManager {

    /**
     * Performs a game/tutorial move event based on the specified parameters.
     *
     * @param gameTutorialEvent The type of game tutorial event to perform.
     * @param timeManager       The time manager for controlling the execution of events.
     */
    public static void performGameTutorialMoveEvent(GameTutorialEvent gameTutorialEvent, TimeManager timeManager) {
        performGameTutorialMoveEvent(gameTutorialEvent, timeManager, 0, null, null);
    }

    /**
     * Performs a game/tutorial move event with the specified step size.
     *
     * @param gameTutorialEvent The type of game tutorial event to perform.
     * @param timeManager       The time manager for controlling the execution of events.
     * @param stepSize          The step size for movement-related events.
     */
    public static void performGameTutorialMoveEvent(GameTutorialEvent gameTutorialEvent, TimeManager timeManager, int stepSize) {
        performGameTutorialMoveEvent(gameTutorialEvent, timeManager, stepSize, null, null);
    }

    /**
     * Performs a game/tutorial move event for spawning enemies with the specified amount and completion flag.
     *
     * @param gameTutorialEvent The type of game tutorial event to perform.
     * @param timeManager       The time manager for controlling the execution of events.
     * @param amount            The amount of enemies to spawn.
     * @param allSpawned        The flag indicating whether all enemies are spawned.
     */
    public static void performGameTutorialMoveEvent(GameTutorialEvent gameTutorialEvent, TimeManager timeManager, AtomicInteger amount, AtomicBoolean allSpawned) {
        performGameTutorialMoveEvent(gameTutorialEvent, timeManager, 0, amount, allSpawned);
    }

    /**
     * Performs a game/tutorial move event based on the specified parameters.
     *
     * @param gameTutorialEvent The type of game tutorial event to perform.
     * @param timeManager       The time manager for controlling the execution of events.
     * @param stepSize          The step size for movement-related events.
     * @param amount            The amount of enemies to spawn.
     * @param allSpawned        The flag indicating whether all enemies are spawned.
     */
    public static void performGameTutorialMoveEvent(GameTutorialEvent gameTutorialEvent, TimeManager timeManager, int stepSize, AtomicInteger amount, AtomicBoolean allSpawned) {
        timeManager.updateElapsedTime();

        if (timeManager.shouldExecute()) {
            switch (gameTutorialEvent) {
                case SPAWN_SHOOTING_ENEMY:
                    performEnemySpawnAction(amount, allSpawned, ShootingEnemyManager::spawnShootingEnemy);
                    break;
                case SPAWN_DEFAULT_ENEMY:
                    performEnemySpawnAction(amount, allSpawned, DefaultEnemyManager::spawnDefaultEnemy);
                    break;
                case MOVE_PLAYER_PROJECTILES:
                    PlayerProjectileManager.movePlayerProjectileAndDetectCollision(stepSize);
                    break;
                case MOVE_ENEMY_PROJECTILES:
                    EnemyProjectileManager.moveEnemyProjectileAndDetectCollision(stepSize);
                    break;
                case MOVE_SHOOTING_ENEMIES:
                    ShootingEnemyManager.moveShootingEnemiesAndDetectCollisions(stepSize);
                    break;
                case MOVE_DEFAULT_ENEMIES:
                    DefaultEnemyManager.moveDefaultEnemyAndDetectCollisions(stepSize);
                    break;
                case MOVE_BOSS_ORBITAL_PROJECTILES:
                    BossProjectileManager.moveBossOrbitalProjectiles(stepSize);
                    break;
                case MOVE_BOSS_HOMING_PROJECTILES:
                    BossProjectileManager.moveHomingBossProjectiles(stepSize);
                    break;
                case MOVE_BOSS:
                    BossManager.moveBossAndDetectCollision(stepSize);
                    break;
                case SPAWN_PLAYER_PROJECTILES:
                    PlayerProjectileManager.spawnPlayerProjectiles();
                    break;
                case SPAWN_ENEMY_PROJECTILES:
                    EnemyProjectileManager.spawnEnemyProjectiles();
                    break;
                case SPAWN_BOSS_PROJECTILES:
                    BossProjectileManager.spawnBossProjectiles();
                    break;
                default:
                    System.out.println("Unsupported operation type");
            }
        }
    }

    /**
     * Helper method to perform the enemy spawn action based on the specified parameters.
     *
     * @param amount      The amount of enemies to spawn.
     * @param allSpawned  The flag indicating whether all enemies are spawned.
     * @param spawnAction The runnable action for spawning enemies.
     */
    private static void performEnemySpawnAction(AtomicInteger amount, AtomicBoolean allSpawned, Runnable spawnAction) {
        if (amount.get() > 0) {
            spawnAction.run();

            if (amount.decrementAndGet() == 0) {
                new Thread(() -> {
                    WaitHelper.waitFor(100);
                    allSpawned.set(true);
                }).start();
            }
        }
    }
}
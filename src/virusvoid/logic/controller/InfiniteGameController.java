package virusvoid.logic.controller;

import virusvoid.logic.enemymanager.BossManager;
import virusvoid.logic.enemymanager.DefaultEnemyManager;
import virusvoid.logic.enemymanager.EnemyManagerHelper;
import virusvoid.logic.enemymanager.ShootingEnemyManager;
import virusvoid.logic.other.GameTutorialEvent;
import virusvoid.logic.other.TutorialAndGameHelper;
import virusvoid.logic.other.manager.GameTutorialEventManager;
import virusvoid.logic.other.manager.InfiniteGameTimeManager;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.projectilemanager.BossProjectileManager;
import virusvoid.logic.projectilemanager.EnemyProjectileManager;
import virusvoid.logic.sound.MusicManager;
import virusvoid.help.WaitHelper;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controller class for managing the logic of the infinite game.
 */
public class InfiniteGameController {

    private static final InfiniteGameTimeManager spawnBossProjectilesInfiniteGameTimeManager = new InfiniteGameTimeManager(1000);
    private static final InfiniteGameTimeManager enemyProjectileSpawnInfiniteGameTimeManager = new InfiniteGameTimeManager(3000);
    private static final InfiniteGameTimeManager playerProjectileSpawnInfiniteGameTimeManager = new InfiniteGameTimeManager(750);
    private static final InfiniteGameTimeManager shootingEnemySpawnInfiniteGameTimeManager = new InfiniteGameTimeManager(2500);
    private static final InfiniteGameTimeManager defaultEnemySpawnInfiniteGameTimeManager = new InfiniteGameTimeManager(1000);
    private static final AtomicBoolean allShootingEnemiesSpawned = new AtomicBoolean(false);
    private static final AtomicBoolean allDefaultEnemiesSpawned = new AtomicBoolean(false);
    private static final AtomicInteger shootingEnemySpawnAmount = new AtomicInteger(0);
    private static final AtomicInteger defaultEnemySpawnAmount = new AtomicInteger(0);
    private static final AtomicBoolean infiniteGameRunning = new AtomicBoolean(false);
    private static final AtomicBoolean waveAnnouncement = new AtomicBoolean(false);
    private static final AtomicBoolean bossSpawned = new AtomicBoolean(false);
    private static final AtomicInteger waveCounter = new AtomicInteger(0);
    private static final AtomicBoolean paused = new AtomicBoolean(false);
    private static boolean currentWaveInitialized;

    /**
     * Starts the infinite game, initializing necessary parameters and starting game management.
     */
    public static void startInifinteGame() {
        infiniteGameRunning.set(true);
        waveCounter.set(0);
        paused.set(false);

        currentWaveInitialized = false;
        waveAnnouncement.set(false);

        manageInfinteGame();
    }

    /**
     * Manages the flow of the infinite game, including spawning enemies, handling waves, and managing the boss.
     */
    private static void manageInfinteGame() {
        TutorialAndGameHelper.startManagers();

        InfiniteGameTimeManager moveBossOrbitalProjectilesInfiniteGameTimeManager = new InfiniteGameTimeManager(20);
        InfiniteGameTimeManager moveBossHomingProjectilesInfiniteGameTimeManager = new InfiniteGameTimeManager(20);
        InfiniteGameTimeManager playerProjectileMoveInfiniteGameTimeManager = new InfiniteGameTimeManager(20);
        InfiniteGameTimeManager enemyProjectileMoveInfiniteGameTimeManager = new InfiniteGameTimeManager(20);
        InfiniteGameTimeManager shootingEnemyMoveInfiniteGameTimeManager = new InfiniteGameTimeManager(300);
        InfiniteGameTimeManager defaultEnemyMoveInfiniteGameTimeManager = new InfiniteGameTimeManager(50);
        InfiniteGameTimeManager moveBossInfiniteGameTimeManager = new InfiniteGameTimeManager(20);
        AtomicBoolean bossAlive = new AtomicBoolean(false);
        int homingProjectileStepSize = 3;
        int enemyStepSize = 1;

        new Thread(() -> {
            while (infiniteGameRunning.get()) {
                if (!currentWaveInitialized) {
                    waveInitializer();
                } else {
                    if (!waveAnnouncement.get() && !SpaceshipManager.isSpaceshipExploding()) {
                        TutorialAndGameHelper.moveAndSpawnPlayerProjectiles(playerProjectileSpawnInfiniteGameTimeManager, playerProjectileMoveInfiniteGameTimeManager);
                    }

                    GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_DEFAULT_ENEMY, defaultEnemySpawnInfiniteGameTimeManager, defaultEnemySpawnAmount, allDefaultEnemiesSpawned);
                    GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_DEFAULT_ENEMIES, defaultEnemyMoveInfiniteGameTimeManager, enemyStepSize);

                    GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_SHOOTING_ENEMY, shootingEnemySpawnInfiniteGameTimeManager, shootingEnemySpawnAmount, allShootingEnemiesSpawned);
                    GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_SHOOTING_ENEMIES, shootingEnemyMoveInfiniteGameTimeManager, enemyStepSize);

                    GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_ENEMY_PROJECTILES, enemyProjectileSpawnInfiniteGameTimeManager);
                    GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_ENEMY_PROJECTILES, enemyProjectileMoveInfiniteGameTimeManager, homingProjectileStepSize);

                    if (DefaultEnemyManager.isDefaultEnemyListEmpty() && allDefaultEnemiesSpawned.get() && ShootingEnemyManager.isShootingEnemyListEmpty() && allShootingEnemiesSpawned.get()) {
                        if (waveCounter.get() % 10 == 0) {
                            if (!bossSpawned.get()) {
                                EnemyProjectileManager.removeEnemyProjectiles();
                                BossManager.spawnBoss(Math.min(250, 90 + waveCounter.get()));

                                BossManager.startBossManager();

                                BossProjectileManager.startBossProjectileManager();
                                bossSpawned.set(true);
                                bossAlive.set(true);
                            } else {
                                if (!BossManager.isBossExploding()) {
                                    GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_BOSS, moveBossInfiniteGameTimeManager, 4);
                                    GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_BOSS_PROJECTILES, spawnBossProjectilesInfiniteGameTimeManager);
                                }
                                GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_BOSS_ORBITAL_PROJECTILES, moveBossOrbitalProjectilesInfiniteGameTimeManager, 6);
                                GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_BOSS_HOMING_PROJECTILES, moveBossHomingProjectilesInfiniteGameTimeManager, homingProjectileStepSize);
                            }

                            if (BossManager.isBossExploding()) {
                                bossAlive.set(false);
                            }
                        }
                        if (!bossAlive.get()) {
                            TutorialAndGameHelper.removeProjectilesAndAnimations();
                            currentWaveInitialized = false;
                            bossSpawned.set(false);
                        }
                    }
                }
                WaitHelper.waitFor(10);
            }
            EnemyManagerHelper.deleteEnemies();
        }).start();
    }

    /**
     * Initializes a new wave, setting parameters for enemy spawning (projectile spawning etc.) based on the current wave.
     */
    private static void waveInitializer() {
        allShootingEnemiesSpawned.set(false);
        allDefaultEnemiesSpawned.set(false);
        defaultEnemySpawnInfiniteGameTimeManager.setExecuteInterval(Math.max(100, 1000 - waveCounter.get() * 10L));
        shootingEnemySpawnInfiniteGameTimeManager.setExecuteInterval(Math.max(300, 3000 - waveCounter.get() * 20L));

        playerProjectileSpawnInfiniteGameTimeManager.setExecuteInterval(Math.max(400, 750 - waveCounter.get() * 3L));
        enemyProjectileSpawnInfiniteGameTimeManager.setExecuteInterval(Math.max(1500, 3000 - waveCounter.get() * 8L));

        defaultEnemySpawnAmount.set(5 * waveCounter.incrementAndGet());
        shootingEnemySpawnAmount.set(2 * waveCounter.get());

        if (waveCounter.get() % 10 == 0) {
            spawnBossProjectilesInfiniteGameTimeManager.setExecuteInterval(Math.max(750, 1000 - waveCounter.get() * 5L));
        }

        showWaveAnnouncement();
        currentWaveInitialized = true;
    }

    /**
     * Displays a wave announcement for the specified wave and waits for a brief period.
     * This method is called when transitioning between waves.
     */
    private static void showWaveAnnouncement() {
        LogicController.showLevelOrWave(waveCounter.get());
        waveAnnouncement.set(true);

        TutorialAndGameHelper.waitAndCheckForPause(1000, 15, 20, InfiniteGameController::isInfiniteGameRunning, InfiniteGameController::isPaused);

        waveAnnouncement.set(false);
    }

    /**
     * Sets the game state to paused using TutorialAndGameHelper.
     */
    public static void setPaused() {
        TutorialAndGameHelper.setGamePaused(waveAnnouncement, paused);
    }

    /**
     * Sets the game state to game over, stopping the infinite game and cleaning up resources.
     */
    public static void setGameOver() {
        infiniteGameRunning.set(false);
        TutorialAndGameHelper.removeProjectilesAndAnimations();
        LogicController.shutKeyListenerDown();
        MusicManager.stopCurrentMusic();
    }

    /**
     * Checks if the infinite game is currently running.
     *
     * @return True if the infinite game is running, false otherwise.
     */
    public static boolean isInfiniteGameRunning() {
        return infiniteGameRunning.get();
    }

    /**
     * Checks if the game is currently paused.
     *
     * @return True if the game is paused, false otherwise.
     */
    public static boolean isPaused() {
        return paused.get();
    }

    /**
     * Checks if a wave announcement is currently being displayed.
     *
     * @return True if a wave announcement is displayed, false otherwise.
     */
    public static boolean isWaveAnnouncement() {
        return waveAnnouncement.get();
    }

    /**
     * Checks if a boss is currently spawned in the game.
     *
     * @return True if a boss is spawned, false otherwise.
     */
    public static boolean isBossSpawned() {
        return bossSpawned.get();
    }
}
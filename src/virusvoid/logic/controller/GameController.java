package virusvoid.logic.controller;

import virusvoid.help.WaitHelper;
import virusvoid.logic.enemymanager.BossManager;
import virusvoid.logic.enemymanager.DefaultEnemyManager;
import virusvoid.logic.enemymanager.EnemyManagerHelper;
import virusvoid.logic.enemymanager.ShootingEnemyManager;
import virusvoid.logic.other.GameTutorialEvent;
import virusvoid.logic.other.TutorialAndGameHelper;
import virusvoid.logic.other.manager.GameTimeManager;
import virusvoid.logic.other.manager.GameTutorialEventManager;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.projectilemanager.BossProjectileManager;
import virusvoid.logic.projectilemanager.EnemyProjectileManager;
import virusvoid.logic.sound.MusicManager;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The GameController class manages the game logic, enemy spawning, and overall game flow.
 */
public class GameController {

    private static final GameTimeManager enemyProjectileSpawnGameTimeManager = new GameTimeManager(3000);
    private static final GameTimeManager shootingEnemySpawnGameTimeManager = new GameTimeManager(2500);
    private static final GameTimeManager enemyProjectileMoveGameTimeManager = new GameTimeManager(20);
    private static final GameTimeManager defaultEnemySpawnGameTimeManager = new GameTimeManager(1000);
    private static final GameTimeManager shootingEnemyMoveGameTimeManager = new GameTimeManager(300);
    private static final GameTimeManager defaultEnemyMoveGameTimeManager = new GameTimeManager(50);
    private static final AtomicBoolean levelAnnouncement =  new AtomicBoolean(false);
    private static final AtomicBoolean gameRunning = new AtomicBoolean(false);
    private static final AtomicBoolean allShootingEnemiesSpawned = new AtomicBoolean();
    private static final AtomicBoolean allDefaultEnemiesSpawned = new AtomicBoolean();
    private static final AtomicBoolean paused = new AtomicBoolean(false);
    private static final int homingProjectileStepSize = 3;
    private static AtomicInteger shootingEnemySpawnAmount;
    private static AtomicInteger defaultEnemySpawnAmount;
    private static final int enemyStepSize = 1;
    private static boolean level1Initiated;
    private static boolean level2Initiated;
    private static boolean level3Initiated;
    private static boolean level1Active;
    private static boolean level2Active;
    private static boolean bossSpawned;

    /**
     * Starts the game controller, initializing various parameters and kicking off the game loop.
     * This method also starts background threads responsible for managing different aspects of the game.
     */
    protected static void startGameController() {
        levelAnnouncement.set(false);
        gameRunning.set(true);
        paused.set(false);

        level1Initiated = false;
        level2Initiated = false;
        level3Initiated = false;
        level1Active = true;
        level2Active = false;
        bossSpawned = false;

        manageGame();
    }

    /**
     * Manages the game loop, including enemy spawning, level progression, and various game events.
     * This method runs in a separate thread to allow for parallel execution.
     */
    private static void manageGame() {
        GameTimeManager moveBossOrbitalProjectilesGameTimeManager = new GameTimeManager(20);
        GameTimeManager moveBossHomingProjectilesGameTimeManager = new GameTimeManager(20);
        GameTimeManager playerProjectileSpawnGameTimeManager = new GameTimeManager(750);
        GameTimeManager spawnBossProjectilesGameTimeManager = new GameTimeManager(1000);
        GameTimeManager playerProjectileMoveGameTimeManager = new GameTimeManager(20);
        GameTimeManager moveBossGameTimeManager = new GameTimeManager(20);

        new Thread(() -> {
            TutorialAndGameHelper.startManagers();
            int updatesPerSecond = 0;
            long lastPrintTime = 0;

            while (gameRunning.get()) {
                long currentTime = System.nanoTime();

                if (currentTime - lastPrintTime >= 1_000_000_000) {
                    System.out.println("Updates pro Sekunde: " + updatesPerSecond);
                    updatesPerSecond = 0;
                    lastPrintTime = currentTime;
                }
                updatesPerSecond++;

                if (level1Active) {
                    if (!level1Initiated) {
                        levelInitializer(1, 50, 0); // 50
                    } else {
                        spawnAndMoveDefaultEnemy();

                        if (DefaultEnemyManager.isDefaultEnemyListEmpty() && allDefaultEnemiesSpawned.get()) {
                            TutorialAndGameHelper.removeProjectilesAndAnimations();
                            level1Active = false;
                            level2Active = true;
                        }
                    }
                } else if (level2Active) {
                    if (!level2Initiated) {
                        levelInitializer(2, 50, 20); // 50 20
                    } else {
                        level2And3();

                        if (DefaultEnemyManager.isDefaultEnemyListEmpty() && allDefaultEnemiesSpawned.get() && ShootingEnemyManager.isShootingEnemyListEmpty() && allShootingEnemiesSpawned.get()) {
                            TutorialAndGameHelper.removeProjectilesAndAnimations();
                            level2Active = false;
                        }
                    }
                } else {
                    if (!level3Initiated) {
                        levelInitializer(3, 75, 30); // 75 30
                    } else if (DefaultEnemyManager.isDefaultEnemyListEmpty() && allDefaultEnemiesSpawned.get() && ShootingEnemyManager.isShootingEnemyListEmpty() && allShootingEnemiesSpawned.get()) {
                        if (!bossSpawned) {
                            EnemyProjectileManager.removeEnemyProjectiles();
                            BossManager.spawnBoss(100);

                            BossManager.startBossManager();

                            BossProjectileManager.startBossProjectileManager();
                            bossSpawned = true;
                        } else {
                            if (!BossManager.isBossExploding()) {
                                GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_BOSS, moveBossGameTimeManager, 4);
                                GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_BOSS_PROJECTILES, spawnBossProjectilesGameTimeManager);
                            }
                            GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_BOSS_ORBITAL_PROJECTILES, moveBossOrbitalProjectilesGameTimeManager, 6);
                            GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_BOSS_HOMING_PROJECTILES, moveBossHomingProjectilesGameTimeManager, homingProjectileStepSize);
                        }
                    } else {
                        level2And3();
                    }
                }
                if (!levelAnnouncement.get() && !SpaceshipManager.isSpaceshipExploding()) {
                    TutorialAndGameHelper.moveAndSpawnPlayerProjectiles(playerProjectileSpawnGameTimeManager, playerProjectileMoveGameTimeManager);
                }
                WaitHelper.waitFor(10);
            }
            EnemyManagerHelper.deleteEnemies();
        }).start();
    }

    /**
     * Handles the spawning and movement of enemies for levels 2 and 3.
     * This method is responsible for coordinating the actions of different types of enemies.
     */
    private static void level2And3() {
        spawnAndMoveDefaultEnemy();

        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_SHOOTING_ENEMY, shootingEnemySpawnGameTimeManager, shootingEnemySpawnAmount, allShootingEnemiesSpawned);
        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_SHOOTING_ENEMIES, shootingEnemyMoveGameTimeManager, enemyStepSize);

        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_ENEMY_PROJECTILES, enemyProjectileSpawnGameTimeManager);
        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_ENEMY_PROJECTILES, enemyProjectileMoveGameTimeManager, homingProjectileStepSize);
    }

    /**
     * Spawns and moves default enemies based on the current game state.
     * This method is part of the game loop and contributes to the overall enemy management.
     */
    public static void spawnAndMoveDefaultEnemy() {
        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_DEFAULT_ENEMY, defaultEnemySpawnGameTimeManager, defaultEnemySpawnAmount, allDefaultEnemiesSpawned);
        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_DEFAULT_ENEMIES, defaultEnemyMoveGameTimeManager, enemyStepSize);
    }

    /**
     * Displays a level announcement for the specified level and waits for a brief period.
     * This method is called when transitioning between levels.
     *
     * @param currentLevel The level number to announce.
     */
    private static void showLevelAnnouncement(int currentLevel) {
        LogicController.showLevelOrWave(currentLevel);
        levelAnnouncement.set(true);

        TutorialAndGameHelper.waitAndCheckForPause(2000, 15, 20, GameController::isGameRunning, GameController::isPaused);

        levelAnnouncement.set(false);
    }

    /**
     * Calls the setGamePaused-method of the TutorialAndGameHelper
     */
    public static void setPaused() {
        TutorialAndGameHelper.setGamePaused(levelAnnouncement, paused);
    }

    /**
     * Initializes the game level by showing a level announcement and setting spawn parameters.
     * Also, updates flags to track the initiation of different levels.
     *
     * @param level          The level number.
     * @param defaultAmount  The number of default enemies to spawn.
     * @param shootingAmount The number of shooting enemies to spawn (for level 2 and 3).
     */
    private static void levelInitializer(int level, int defaultAmount, int shootingAmount) {
        showLevelAnnouncement(level);
        allDefaultEnemiesSpawned.set(false);
        defaultEnemySpawnAmount = new AtomicInteger(defaultAmount);

        if (level == 2 || level == 3) {
            allShootingEnemiesSpawned.set(false);
            shootingEnemySpawnAmount = new AtomicInteger(shootingAmount);
        }

        if (level == 1) {
            level1Initiated = true;
        } else if (level == 2) {
            defaultEnemySpawnGameTimeManager.setExecuteInterval(800);
            level2Initiated = true;
        } else {
            shootingEnemySpawnGameTimeManager.setExecuteInterval(2000);
            defaultEnemySpawnGameTimeManager.setExecuteInterval(700);
            level3Initiated = true;
        }
    }

    /**
     * Handles the end of the game by stopping the game loop and cleaning up resources.
     */
    public static void setGameOver() {
        gameRunning.set(false);
        TutorialAndGameHelper.removeProjectilesAndAnimations();
        LogicController.shutKeyListenerDown();
        MusicManager.stopCurrentMusic();
    }

    /**
     * Returns whether the boss has been spawned.
     *
     * @return true if the boss has been spawned, false otherwise.
     */
    public static boolean isBossSpawned() {
        return bossSpawned;
    }

    /**
     * Returns whether the game is currently paused.
     *
     * @return true if the game is paused, false otherwise.
     */
    public static boolean isPaused() {
        return paused.get();
    }

    /**
     * Returns whether the level announcement is currently being displayed.
     *
     * @return true if the level announcement is active, false otherwise.
     */
    public static boolean isLevelAnnouncement() {
        return levelAnnouncement.get();
    }

    /**
     * Returns whether the game is currently running.
     *
     * @return true if the game is running, false otherwise.
     */
    public static boolean isGameRunning() {
        return gameRunning.get();
    }
}
package virusvoid.logic.controller;

import virusvoid.help.WaitHelper;
import virusvoid.logic.enemymanager.DefaultEnemyManager;
import virusvoid.logic.enemymanager.EnemyManagerHelper;
import virusvoid.logic.enemymanager.ShootingEnemyManager;
import virusvoid.logic.other.GameTutorialEvent;
import virusvoid.logic.other.TutorialAndGameHelper;
import virusvoid.logic.other.*;
import virusvoid.logic.other.manager.GameTutorialEventManager;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.other.manager.TutorialTimeManager;
import virusvoid.logic.projectilemanager.ProjectileManagerHelper;
import virusvoid.logic.sound.MusicManager;
import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.logic.sound.SoundManager;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controller class for managing the tutorial within the game.
 */
public class TutorialController {

    private static final TutorialTimeManager playerProjectileSpawnTutorialTimeManager = new TutorialTimeManager(750);
    private static final TutorialTimeManager playerProjectileMoveTutorialTimeManager =  new TutorialTimeManager(20);
    private static final AtomicBoolean tutorialRunning = new AtomicBoolean(false);
    private static final AtomicBoolean paused = new AtomicBoolean(false);
    private static boolean drawEnemyInstructions = false;
    private static boolean drawPauseAndQuit = false;
    private static boolean kiVoiceOver = false;
    private static boolean drawMovement = true;
    private static Clip kiVoiceOverClip;

    /**
     * Starts the tutorial controller, initializing necessary components and managing the tutorial progression.
     */
    protected static void startTutorialController() {
        tutorialRunning.set(true);
        paused.set(false);

        drawEnemyInstructions = false;
        drawPauseAndQuit = false;
        drawMovement = false;
        kiVoiceOver = true;

        manageTutorial();
    }

    /**
     * Manages the tutorial progression in a separate thread, including the display of instructions,
     * enemy spawning and movement, and overall tutorial flow.
     */
    private static void manageTutorial() {
        new Thread(() -> {
            TutorialAndGameHelper.startManagers();

            kiVoiceOverClip = SoundManager.loadSound("VirusVoid_KIVoiceOver.wav");
            SoundEffectManager.playClip(kiVoiceOverClip);
            waitAndToggleFlags(91_000, () -> kiVoiceOver = false, true);
            drawMovement = true;

            waitAndToggleFlags(5000, () -> drawMovement = false, false); // 5000
            drawPauseAndQuit = true;

            waitAndToggleFlags(5000, () -> drawPauseAndQuit = false, false); // 5000
            drawEnemyInstructions = true;

            waitAndToggleFlags(15000, () -> drawEnemyInstructions = false, false); // 15000
            WaitHelper.waitFor(500);


            AtomicBoolean allDefaultEnemiesSpawned = new AtomicBoolean(false);
            AtomicInteger defaultEnemyAmount = new AtomicInteger(10); // 10
            AtomicBoolean allShootingEnemiesSpawned = new AtomicBoolean(false);
            AtomicInteger shootingEnemyAmount = new AtomicInteger(10); // 10
            boolean shootingEnemyTutorial = true;
            boolean defaultEnemyTutorial = true;
            boolean spaceshipRemoved = false;
            int enemyStepSize = 1;

            TutorialTimeManager defaultEnemySpawnTutorialTimeManager = new TutorialTimeManager(2500);
            TutorialTimeManager defaultEnemyMoveTutorialTimeManager = new TutorialTimeManager(50);
            TutorialTimeManager shootingEnemySpawnTutorialTimeManager = new TutorialTimeManager(4000);
            TutorialTimeManager shootingEnemyMoveTutorialTimeManager = new TutorialTimeManager(300);
            TutorialTimeManager enemyProjectileSpawnTutorialTimeManager = new TutorialTimeManager(3000);
            TutorialTimeManager enemyProjectileMoveTutorialTimeManager = new TutorialTimeManager(20);

            while (tutorialRunning.get()) {
                if (!SpaceshipManager.isSpaceshipExploding()) {
                    TutorialAndGameHelper.moveAndSpawnPlayerProjectiles(playerProjectileSpawnTutorialTimeManager, playerProjectileMoveTutorialTimeManager);
                } else if (!spaceshipRemoved) {
                    LogicController.removeSpaceshipLabel();
                    spaceshipRemoved = true;
                }

                if (defaultEnemyTutorial) {
                    GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_DEFAULT_ENEMY, defaultEnemySpawnTutorialTimeManager, defaultEnemyAmount, allDefaultEnemiesSpawned);

                    if (!allDefaultEnemiesSpawned.get() || !DefaultEnemyManager.isDefaultEnemyListEmpty()) {
                        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_DEFAULT_ENEMIES, defaultEnemyMoveTutorialTimeManager, enemyStepSize);
                    } else {
                        defaultEnemyTutorial = false;
                    }
                } else {
                    GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_SHOOTING_ENEMY, shootingEnemySpawnTutorialTimeManager, shootingEnemyAmount, allShootingEnemiesSpawned);

                    if (!allShootingEnemiesSpawned.get() || !ShootingEnemyManager.isShootingEnemyListEmpty()) {
                        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_SHOOTING_ENEMIES, shootingEnemyMoveTutorialTimeManager, enemyStepSize);
                        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.SPAWN_ENEMY_PROJECTILES, enemyProjectileSpawnTutorialTimeManager);
                        GameTutorialEventManager.performGameTutorialMoveEvent(GameTutorialEvent.MOVE_ENEMY_PROJECTILES, enemyProjectileMoveTutorialTimeManager, 3);
                    } else {
                        shootingEnemyTutorial = false;
                        break;
                    }
                }
                WaitHelper.waitFor(1);
            }

            LogicController.clearExplosionAnimationLabels();
            ProjectileManagerHelper.deleteProjectiles();
            EnemyManagerHelper.deleteEnemies();
            if (!shootingEnemyTutorial) {
                stopTutorial();
                LogicController.tutorialEndScreen("Congrats, you finished the Tutorial", new Color(0, 255, 0));
            }
        }).start();
    }

    /**
     * Waits for a specified duration while toggling flags, made for showing the instructions and story.
     *
     * @param sleepTime             The duration to wait in milliseconds.
     * @param setBoolean            A runnable to set a boolean flag.
     * @param waitingForKiVoiceOver If true, waits for the KiVoiceOver to complete before proceeding.
     */
    private static void waitAndToggleFlags(long sleepTime, Runnable setBoolean, boolean waitingForKiVoiceOver) {
        long waitStartTime = System.currentTimeMillis();
        long elapsedTime = 0;
        while (tutorialRunning.get() && elapsedTime < sleepTime) {

            if (waitingForKiVoiceOver && !kiVoiceOver) {
                break;
            }

            long currentTime = System.currentTimeMillis();
            if (paused.get()) {
                waitStartTime = currentTime - elapsedTime;
            } else {
                if (currentTime - waitStartTime >= 20) {
                    elapsedTime = currentTime - waitStartTime;
                }
            }
            WaitHelper.waitFor(2);
        }
        setBoolean.run();
    }

    /**
     * Stops the tutorial, shuts down the key listener, stops background music,
     * and halts the KiVoiceOver if it is currently playing.
     */
    public static void stopTutorial() {
        tutorialRunning.set(false);
        LogicController.shutKeyListenerDown();
        MusicManager.stopCurrentMusic();
        stopKiVoiceOver();
    }

    /**
     * Sets the paused state of the tutorial, manages visibility changes, and handles the KiVoiceOver playback.
     */
    public static void setPaused() {
        LogicController.changePauseLabelVisibility();

        if (!kiVoiceOver && !drawMovement && !drawEnemyInstructions && !drawPauseAndQuit) {
            LogicController.changeOrangePanelVisibility();
        }
        if (paused.get()) {
            paused.set(false);
            LogicController.resumeKeyListener();

            if (kiVoiceOverClip != null && !kiVoiceOverClip.isRunning()) {
                kiVoiceOverClip.start();
            }
        } else {
            paused.set(true);
            LogicController.pauseKeyListener();
            stopKiVoiceOver();
        }
    }

    /**
     * Stops the KiVoiceOver playback if it is currently running.
     */
    private static void stopKiVoiceOver() {
        if (kiVoiceOverClip != null && kiVoiceOverClip.isRunning()) {
            kiVoiceOverClip.stop();
        }
    }

    /**
     * Skips the KiVoiceOver, stops its playback, and sets KiVoiceOver to false.
     */
    public static void skipKiVoiceOver() {
        stopKiVoiceOver();
        kiVoiceOver = false;
        kiVoiceOverClip = null;
    }

    /**
     * Retrieves whether EnemyInstructions should be displayed in the tutorial.
     *
     * @return True if EnemyInstructions should be displayed, false otherwise.
     */
    protected static boolean isDrawEnemyInstructions() {
        return drawEnemyInstructions;
    }

    /**
     * Retrieves whether PauseAndQuitInstructions should be displayed in the tutorial.
     *
     * @return True if PauseAndQuitInstructions should be displayed, false otherwise.
     */
    protected static boolean isDrawPauseAndQuit() {
        return drawPauseAndQuit;
    }

    /**
     * Retrieves whether MovementInstructions should be displayed in the tutorial.
     *
     * @return True if MovementInstructions should be displayed, false otherwise.
     */
    protected static boolean isDrawMovement() {
        return drawMovement;
    }

    /**
     * Retrieves whether KIVoiceOver is active in the tutorial.
     *
     * @return True if KIVoiceOver is active, false otherwise.
     */
    public static boolean isKiVoiceOver() {
        return kiVoiceOver;
    }

    /**
     * Retrieves whether the tutorial is currently paused.
     *
     * @return True if the tutorial is paused, false otherwise.
     */
    public static boolean isPaused() {
        return paused.get();
    }

    /**
     * Retrieves whether the tutorial is currently running.
     *
     * @return True if the tutorial is running, false otherwise.
     */
    public static boolean isTutorialRunning() {
        return tutorialRunning.get();
    }
}
package NanoTechDefenders.Logic.Controlling;

import NanoTechDefenders.GUI.Controlling.GUIController;
import NanoTechDefenders.Sound.SoundController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Die Klasse {@code GameManager} steuert den Spielablauf und die Aktualisierung der Benutzeroberfläche.
 */
public class GameManager {
    private static Thread gameLoop;
    private static boolean breaking;
    private static final int MAX_ROTATIONS_PER_SECOND = 90;
    private static int rotationsPerSecond = MAX_ROTATIONS_PER_SECOND;
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * Konstruktor für den GameManager.
     * Erzeugt Instanzen der NanoTechDefenders.GUI- und NanoTechDefenders.Sound-Controller.
     */
    public GameManager() {
        new GUIController();
        new SoundController();
    }

    /**
     * Startet den Spiel-Loop, falls nicht bereits aktiv.
     */
    public static void startGameLoop() {
        if (gameLoop == null || !gameLoop.isAlive()) {
            loop();
            gameLoop.start();
            System.out.println("gameloop started");
        }
        breaking = false;
    }

    /**
     * Stoppt den Spiel-Loop.
     */
    public static void stopGameLoop() {
        breaking = true;
    }

    /**
     * Ändert die Geschwindigkeit des Spiel-Loops.
     */
    public static void changeSpeed()  {
        stopGameLoop();
        if (rotationsPerSecond == MAX_ROTATIONS_PER_SECOND) {
            rotationsPerSecond = MAX_ROTATIONS_PER_SECOND * 2;
        } else {
            rotationsPerSecond = MAX_ROTATIONS_PER_SECOND;
        }
        try {
            gameLoop.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        startGameLoop();
    }

    /**
     * Der Spiel-Loop, der die Logik-Controller und NanoTechDefenders.GUI-Controller in einem separaten Thread ausführt.
     */
    public static void loop() {
        gameLoop = new Thread(() -> {
            double drawInterval = (double) 1000000000 / rotationsPerSecond;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int drawCount = 0;

            do {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;

                if (delta >= 1) {
                    executor.submit(LogicController::moveEnemies);
                    executor.submit(LogicController::autoStartNextWave);
                    executor.submit(LogicController::countDownWaiting);
                    executor.submit(LogicController::shootingEnemies);
                    executor.submit(LogicController::moveProjectiles);
                    executor.submit(LogicController::checkCollision);
                    executor.submit(LogicController::checkGameOver);

                    executor.submit(GUIController::updateProjectileContainPanel);
                    executor.submit(GUIController::updateSoldierContainPanel);
                    executor.submit(GUIController::updateEnemyContainPanel);

                    --delta;
                    ++drawCount;
                }

                if (timer >= 1000000000) {
                    System.out.println("\u001B[32m" + "Rotations per Second: " + drawCount + "\u001B[0m");
                    drawCount = 0;
                    timer = 0;
                }
            } while (!breaking);
        });
    }
}

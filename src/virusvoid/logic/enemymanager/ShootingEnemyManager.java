package virusvoid.logic.enemymanager;

import virusvoid.logic.controller.TutorialController;
import virusvoid.logic.objects.Enemy;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.controller.InfiniteGameController;
import virusvoid.logic.controller.GameController;
import virusvoid.logic.controller.LogicController;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages the spawning, movement, and collision detection of shooting enemies.
 */
public class ShootingEnemyManager {

    private static final CopyOnWriteArrayList<Enemy> shootingEnemyList = new CopyOnWriteArrayList<>();
    private static int shootingEnemyId;
    private static int spaceshipSize;
    private static int maxX;
    private static int maxY;

    /**
     * Initializes the ShootingEnemyManager with the spaceship size, maximum X-coordinate, maximum Y-coordinate,
     * and starts the enemy ID at 0.
     */
    public static void startShootingEnemyManager() {
        spaceshipSize = SpaceshipManager.getSpaceship().getWidth();

        maxX = LogicController.getMainFrameWidth() - spaceshipSize - LogicController.scaleX(20) + 1;
        maxY = LogicController.scaleY(400);
        shootingEnemyId = 0;
    }

    /**
     * Spawns a shooting enemy on a separate thread and adds it to the shooting enemy list.
     */
    public static void spawnShootingEnemy() {
        new Thread(() -> {
            Enemy shootingEnemy = new Enemy(LogicController.getRandomWidthX(), 30, shootingEnemyId, 1);

            LogicController.createEnemyLabel(shootingEnemy.getLocation(), shootingEnemyId++, 1);

            shootingEnemyList.add(shootingEnemy);
        }).start();
    }

    /**
     * Moves shooting enemies in random directions and performs collision detection with the player.
     *
     * @param stepSize The step size for each movement.
     */
    public static void moveShootingEnemiesAndDetectCollisions(int stepSize) {
        new Thread(() -> {
            for (Enemy enemy : shootingEnemyList) {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                int direction = random.nextInt(4) + 1;
                int moveX;
                int moveY;
                if (direction == 1) {
                    moveY = 0;
                    moveX = stepSize;
                } else if (direction == 2) {
                    moveY = 0;
                    moveX = -stepSize;
                } else {
                    moveX = 0;
                    if (direction == 3) {
                        moveY = stepSize;
                    } else {
                        moveY = -stepSize;
                    }
                }
                AtomicInteger stepCounter = new AtomicInteger(0);

                ScheduledExecutorService innerExecutor = Executors.newSingleThreadScheduledExecutor();
                innerExecutor.scheduleAtFixedRate(() -> {

                    if (!GameController.isPaused() && !InfiniteGameController.isPaused() && !TutorialController.isPaused()) {
                        EnemyManagerHelper.damagePlayerIfEnemyCollision(enemy, shootingEnemyList);

                        if (stepCounter.getAndIncrement() < 15) {
                            double locationX = enemy.getX() + moveX;
                            double locationY = enemy.getY() + moveY;

                            locationX = Math.min(maxX, Math.max(spaceshipSize, locationX + moveX));
                            locationY = Math.min(maxY, Math.max(30, locationY + moveY));

                            enemy.setLocation(locationX, locationY);
                            LogicController.updateLabel(enemy.getLocation(), enemy.getId(), enemy.getType(), 0);
                        } else {
                            innerExecutor.shutdown();
                        }
                    }
                }, 0, 20, TimeUnit.MILLISECONDS); // when pausing, very small inaccuracies can occur due to if-statement and regular execution
            }
        }).start();
    }

    /**
     * Gets the list of shooting enemies.
     *
     * @return The list of shooting enemies.
     */
    public static CopyOnWriteArrayList<Enemy> getShootingEnemyList() {
        return shootingEnemyList;
    }

    /**
     * Checks if the shooting enemy list is empty.
     *
     * @return True if the shooting enemy list is empty, false otherwise.
     */
    public static boolean isShootingEnemyListEmpty() {
        return shootingEnemyList.isEmpty();
    }
}
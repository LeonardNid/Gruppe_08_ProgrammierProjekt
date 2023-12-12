package virusvoid.logic.enemymanager;

import virusvoid.logic.objects.Enemy;
import virusvoid.logic.objects.Planet;
import virusvoid.logic.other.manager.PlanetManager;
import virusvoid.logic.controller.LogicController;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages default enemy objects in the game, including their creation, movement, and collision detection.
 */
public class DefaultEnemyManager {

    private static final CopyOnWriteArrayList<Enemy> defaultEnemyList = new CopyOnWriteArrayList<>();
    private static int defaultEnemyId;
    private static int frameHeight;
    private static Planet planet;

    /**
     * Initializes the DefaultEnemyManager with the frame height and the reference to the game planet.
     */
    public static void startDefaultEnemyManager() {
        frameHeight = LogicController.getMainFrameHeight();
        planet = PlanetManager.getPlanet();
        defaultEnemyId = 0;
    }

    /**
     * Spawns a new default enemy in a separate thread and creates the corresponding GUI label.
     */
    public static void spawnDefaultEnemy() {
        new Thread(() -> {
            Enemy defaultEnemy = new Enemy(LogicController.getRandomWidthX(), 30, defaultEnemyId, 0);

            LogicController.createEnemyLabel(defaultEnemy.getLocation(), defaultEnemyId++, 0);

            defaultEnemyList.add(defaultEnemy);
        }).start();
    }

    /**
     * Moves default enemies and detects collisions with the game planet and the player's spaceship.
     *
     * @param stepSize The step size by which default enemies should move.
     */
    public static void moveDefaultEnemyAndDetectCollisions(int stepSize) {
        new Thread(() -> {
            for (Enemy enemy : defaultEnemyList) {
                enemy.setLocation(enemy.getX(), enemy.getY() + stepSize);
                LogicController.updateLabel(enemy.getLocation(), enemy.getId(), enemy.getType(), 0);

                if (frameHeight < enemy.getY()) {
                    planet.damage();
                    EnemyManagerHelper.removeEnemy(defaultEnemyList, enemy, false);
                }

                EnemyManagerHelper.damagePlayerIfEnemyCollision(enemy, defaultEnemyList);
            }
        }).start();
    }

    /**
     * Checks if the list of default enemies is currently empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    public static boolean isDefaultEnemyListEmpty() {
        return defaultEnemyList.isEmpty();
    }

    /**
     * Retrieves the list of default enemy objects.
     *
     * @return The list of default enemy objects.
     */
    public static CopyOnWriteArrayList<Enemy> getDefaultEnemyList() {
        return defaultEnemyList;
    }
}
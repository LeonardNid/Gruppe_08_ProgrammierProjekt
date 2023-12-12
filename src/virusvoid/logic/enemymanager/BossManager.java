package virusvoid.logic.enemymanager;

import virusvoid.logic.objects.Boss;
import virusvoid.logic.objects.Spaceship;
import virusvoid.logic.other.SpaceshipCollisionDetection;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.controller.LogicController;

/**
 * Manages the Boss object in the game, including its creation, movement, and collision detection.
 */
public class BossManager {

    private static Spaceship spaceship;
    private static final int minY = 30;
    private static double maxX;
    private static double minX;
    private static Boss boss;
    private static int maxY;

    /**
     * Initializes the BossManager with the reference to the player's spaceship and sets the movement boundaries for the boss.
     */
    public static void startBossManager() {
        spaceship = SpaceshipManager.getSpaceship();
        double bossSize = boss.getWidth();

        maxY = LogicController.scaleY(150);
        maxX = LogicController.getMainFrameWidth() - bossSize * 1.5;
        minX = bossSize / 2;
    }

    /**
     * Spawns a new boss and creates the corresponding GUI label.
     *
     * @param maxHp The maximum HP which the boss should have
     */
    public static void spawnBoss(int maxHp) {
        int size = LogicController.scaleX(125);

        boss = new Boss(LogicController.getMainFrameWidth() / 2 - size / 2, 30, maxHp);

        LogicController.createBossLabel(boss.getLocation());
    }

    /**
     * Moves the boss and detects collisions with the player's spaceship.
     *
     * @param stepSize The step size by which the boss should move.
     */
    public static void moveBossAndDetectCollision(int stepSize) {
        new Thread(() -> {
            int bossX = (int) boss.getX();
            int bossY = (int) boss.getY();

            if (bossX <= maxX && bossY <= minY) {
                bossX += stepSize;
            } else if (bossX >= minX && bossY >= maxY) {
                bossX -= stepSize;
            } else if (bossY <= maxY && bossX >= maxX) {
                bossY += stepSize;
            } else if (bossY >= minY && bossX <= minX) {
                bossY -= stepSize;
            }

            boss.setLocation(bossX, bossY);
            LogicController.updateBoss(boss.getLocation());

            if (SpaceshipCollisionDetection.collision(spaceship.getSpaceshipHitbox(), boss)) {
                spaceship.damage(20);
            }
        }).start();
    }

    /**
     * Retrieves the current Boss object.
     *
     * @return The Boss object.
     */
    public static Boss getBoss() {
        return boss;
    }

    /**
     * Checks if the boss is currently in the exploding state.
     *
     * @return True if the boss is exploding, false otherwise.
     */
    public static boolean isBossExploding() {
        if (boss != null) {
            return boss.isExploding();
        }
        return false;
    }

    /**
     * Deletes the reference of this manager if the boss is exploding
     */
    public static void removeBoss() {
        boss = null;
    }
}
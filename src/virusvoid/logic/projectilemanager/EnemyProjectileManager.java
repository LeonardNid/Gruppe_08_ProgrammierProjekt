package virusvoid.logic.projectilemanager;

import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.logic.controller.LogicController;
import virusvoid.logic.enemymanager.ShootingEnemyManager;
import virusvoid.logic.objects.Enemy;
import virusvoid.logic.objects.Projectile;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages the projectiles fired by shooting enemies in the game.
 */
public class EnemyProjectileManager {
    private static final CopyOnWriteArrayList<Projectile> enemyProjectileList = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Enemy> shootingEnemyList;
    private static int projectileId;
    private static int frameHeight;
    private static int height;
    private static int width;

    /**
     * Initializes the Enemy Projectile Manager.
     */
    public static void startEnemyProjectileManager() {
        shootingEnemyList = ShootingEnemyManager.getShootingEnemyList();
        frameHeight = LogicController.getMainFrameHeight();
        height = LogicController.scaleY(11);
        width = LogicController.scaleX(8);
        projectileId = 0;
    }

    /**
     * Spawns projectiles for shooting enemies (method for spawning corresponding label is called).
     */
    public static void spawnEnemyProjectiles() {
        new Thread(() -> {
            for (Enemy enemy : shootingEnemyList) {
                Projectile projectile = new Projectile((int) enemy.getX() + (enemy.getWidth() - width) / 2,
                        (int) enemy.getY() + enemy.getHeight() / 2, width, height, projectileId++, 1);

                ProjectileManagerHelper.callCreateProjectileLabel(projectile);

                enemyProjectileList.add(projectile);

            }

            if (!shootingEnemyList.isEmpty()) {
                SoundEffectManager.playAndLoadClip("VirusVoid_EnemyShootingSound.wav");
            }
        }).start();
    }

    /**
     * Moves and detects collisions for enemy projectiles.
     *
     * @param stepSize The step size for movement.
     */
    public static void moveEnemyProjectileAndDetectCollision(int stepSize) {
        new Thread(() -> {
            for (Projectile projectile : enemyProjectileList) {
                ProjectileManagerHelper.homing(projectile, stepSize);

                if (projectile.getY() > frameHeight + projectile.getHeight()) {
                    ProjectileManagerHelper.removeProjectile(enemyProjectileList, projectile);
                }

                ProjectileManagerHelper.damagePlayerIfProjectileCollision(projectile, enemyProjectileList, 1);
            }
        }).start();
    }

    /**
     * Removes all enemy projectiles from the projectile list.
     */
    public static void removeEnemyProjectiles() {
        for (Projectile projectile : enemyProjectileList) {
            ProjectileManagerHelper.removeProjectile(enemyProjectileList, projectile);
        }
    }
}
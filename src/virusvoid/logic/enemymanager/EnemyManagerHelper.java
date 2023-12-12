package virusvoid.logic.enemymanager;

import virusvoid.logic.objects.Enemy;
import virusvoid.logic.objects.Spaceship;
import virusvoid.logic.other.SpaceshipCollisionDetection;
import virusvoid.logic.other.TutorialAndGameHelper;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.logic.controller.LogicController;

import java.util.concurrent.*;

/**
 * Helper class for managing enemy-related operations such as removal, damage detection, and cleanup.
 */
public class EnemyManagerHelper {

    private static Spaceship spaceship;

    /**
     * Initializes the EnemyManagerHelper with a reference to the player's spaceship.
     */
    public static void startEnemyManager() {
        spaceship = SpaceshipManager.getSpaceship();
    }

    /**
     * Removes an enemy from the specified list and triggers explosion animation if it collides with the player
     * or player projectile.
     *
     * @param list                                  The list containing the enemy.
     * @param enemy                                 The enemy to be removed.
     * @param collisionWithPlayerProjectileOrPlayer True if the collision is with the player or player projectile, false otherwise.
     */
    public static void removeEnemy(CopyOnWriteArrayList<Enemy> list, Enemy enemy, boolean collisionWithPlayerProjectileOrPlayer) {
        if (collisionWithPlayerProjectileOrPlayer) {
            TutorialAndGameHelper.animateExplosion(enemy, 'E', 20, 2, 5);
        }

        LogicController.removeLabel(enemy.getId(), enemy.getType(), 0);
        list.remove(enemy);
    }

    /**
     * Checks for collision with the player's spaceship and damages the player if a collision occurs.
     *
     * @param enemy The enemy to check for collision with the player.
     * @param list  The list containing the enemy.
     */
    protected static void damagePlayerIfEnemyCollision(Enemy enemy, CopyOnWriteArrayList<Enemy> list) {
        if (!enemy.hasCollidedWithPlayer() && SpaceshipCollisionDetection.collision(spaceship.getSpaceshipHitbox(), enemy)) {
            enemy.setPlayerCollision(true);
            removeEnemy(list, enemy, true);
            spaceship.damage(2);
            SoundEffectManager.playAndLoadClip("VirusVoid_SmallExplosion.wav");
        }
    }

    /**
     * Deletes all enemies from both default and shooting enemy lists.
     */
    public static void deleteEnemies() {
        CopyOnWriteArrayList<Enemy> defaultEnemyList = DefaultEnemyManager.getDefaultEnemyList();
        for (Enemy enemy : defaultEnemyList) {
            removeEnemy(defaultEnemyList, enemy, false);
        }

        CopyOnWriteArrayList<Enemy> shootingEnemyList = ShootingEnemyManager.getShootingEnemyList();
        for (Enemy enemy : shootingEnemyList) {
            removeEnemy(shootingEnemyList, enemy, false);
        }
    }
}
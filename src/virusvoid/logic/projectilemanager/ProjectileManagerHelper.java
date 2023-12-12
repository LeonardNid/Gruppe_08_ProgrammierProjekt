package virusvoid.logic.projectilemanager;

import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.logic.other.SpaceshipCollisionDetection;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.controller.LogicController;
import virusvoid.logic.objects.Projectile;
import virusvoid.logic.objects.Spaceship;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Helper class for managing projectiles in the game.
 */
public class ProjectileManagerHelper {

    private static int defaultProjectileHeight;
    private static int defaultProjectileWidth;
    private static Spaceship spaceship;

    /**
     * Initializes the ProjectileManagerHelper with default values.
     */
    public static void startProjectileManagerHelper() {
        defaultProjectileHeight = LogicController.scaleY(25);
        defaultProjectileWidth = LogicController.scaleX(6);
        spaceship = SpaceshipManager.getSpaceship();
    }

    /**
     * Calls the method to create a projectile label which then adds the projectile to the active panel.
     *
     * @param projectile The projectile to be created and added.
     */
    protected static void callCreateProjectileLabel(Projectile projectile) {
        LogicController.createProjectileLabel(projectile.getLocation(), projectile.getId(), projectile.getType());
    }

    /**
     * Deletes all projectiles in the projectile lists.
     */
    public static void deleteProjectiles() {
        LogicController.removePlayeProjectileAnimationLabels();
        PlayerProjectileManager.removePlayerProjectiles();
        EnemyProjectileManager.removeEnemyProjectiles();

        CopyOnWriteArrayList<Projectile> bossOrbitalProjectileList = BossProjectileManager.getBossOrbitalProjectileList();
        CopyOnWriteArrayList<Projectile> bossHomingProjectileList = BossProjectileManager.getBossHomingProjectileList();

        for (Projectile projectile : bossOrbitalProjectileList) {
            removeProjectile(bossOrbitalProjectileList, projectile);
        }
        for (Projectile projectile : bossHomingProjectileList) {
            removeProjectile(bossHomingProjectileList, projectile);
        }
    }

    /**
     * Removes a projectile from the list and the acitve panel (label is removed via method in gui controller).
     *
     * @param list       The list from which the projectile should be removed.
     * @param projectile The projectile to be removed.
     */
    public static void removeProjectile(CopyOnWriteArrayList<Projectile> list, Projectile projectile) {
        LogicController.removeLabel(projectile.getId(), projectile.getType(), 1);
        list.remove(projectile);
    }

    /**
     * Deals damage to the player if an enemy projectile collides with the player's spaceship.
     *
     * @param projectile The projectile that collided with the player.
     * @param list       The list of projectiles.
     * @param damage     The amount of damage to be dealt to the player.
     */
    protected static void damagePlayerIfProjectileCollision(Projectile projectile, CopyOnWriteArrayList<Projectile> list, int damage) {
        if (SpaceshipCollisionDetection.collision(spaceship.getSpaceshipHitbox(), projectile)) {
            removeProjectile(list, projectile);
            spaceship.damage(damage);
            SoundEffectManager.playAndLoadClip("VirusVoid_SmallExplosion.wav");
        }
    }

    /**
     * Moves the projectile in a homing fashion towards the player's spaceship.
     *
     * @param projectile The projectile to be moved.
     * @param stepSize   The step size for the movement.
     */
    protected static void homing(Projectile projectile, int stepSize) {
        projectile.setLocation(projectile.getX() + (double) stepSize / 2 *
            Integer.compare((int) spaceship.getX() + spaceship.getHeight() / 2 - projectile.getWidth(), (int) projectile.getX()), 
            projectile.getY() + stepSize);

        LogicController.updateLabel(projectile.getLocation(), projectile.getId(), projectile.getType(), 1);
    }

    /**
     * Gets the default height of projectiles.
     *
     * @return The default height of projectiles.
     */
    public static int getDefaultProjectileHeight() {
        return defaultProjectileHeight;
    }

    /**
     * Gets the default width of projectiles.
     *
     * @return The default width of projectiles.
     */
    public static int getDefaultProjectileWidth() {
        return defaultProjectileWidth;
    }
}
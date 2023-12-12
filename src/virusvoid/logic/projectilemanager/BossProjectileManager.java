package virusvoid.logic.projectilemanager;

import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.logic.objects.Spaceship;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.controller.LogicController;
import virusvoid.logic.enemymanager.BossManager;
import virusvoid.logic.objects.Projectile;
import virusvoid.logic.objects.Boss;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the projectiles fired by the boss in the game.
 */
public class BossProjectileManager {

    private static final CopyOnWriteArrayList<Projectile> bossOrbitalProjectileList = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<Projectile> bossHomingProjectileList = new CopyOnWriteArrayList<>();
    private static int homingProjectileHeight;
    private static int homingProjectileWidth;
    private static int orbitalProjectileId;
    private static int homingProjectileId;
    private static int mainFrameHeight;
    private static Boss boss;

    /**
     * Initializes the Boss Projectile Manager.
     */
    public static void startBossProjectileManager() {
        homingProjectileHeight = LogicController.scaleY(16);
        homingProjectileWidth = LogicController.scaleX(12);
        mainFrameHeight = LogicController.getMainFrameHeight();
        boss = BossManager.getBoss();
        orbitalProjectileId = 0;
        homingProjectileId = 0;
    }

    /**
     * Spawns boss projectiles, both homing and orbital (methods for spawning corresponding labls are called).
     */
    public static void spawnBossProjectiles() {
        new Thread(() -> {
            Projectile homingProjectile = new Projectile((int) boss.getX() + boss.getWidth() / 2 - homingProjectileWidth / 2,
                    (int) boss.getY() + boss.getHeight(), homingProjectileWidth, homingProjectileHeight, homingProjectileId++, 2);

            ProjectileManagerHelper.callCreateProjectileLabel(homingProjectile);

            bossHomingProjectileList.add(homingProjectile);

            Spaceship spaceship = SpaceshipManager.getSpaceship();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int orbitalX = (int) Math.max((double) spaceship.getWidth() / 5, Math.min(LogicController.getMainFrameWidth() - (double) spaceship.getWidth() / 3,
                    spaceship.getX() + (double) spaceship.getWidth() / 2 - boss.getWidth() + random.nextInt(boss.getWidth() * 2)));

            Projectile orbitalProjectile = new Projectile(orbitalX, 30, ProjectileManagerHelper.getDefaultProjectileWidth(), ProjectileManagerHelper.getDefaultProjectileHeight(), orbitalProjectileId++, 3);

            ProjectileManagerHelper.callCreateProjectileLabel(orbitalProjectile);

            bossOrbitalProjectileList.add(orbitalProjectile);

            SoundEffectManager.playAndLoadClip("VirusVoid_EnemyShootingSound.wav");
        }).start();
    }

    /**
     * Moves homing boss projectiles.
     *
     * @param stepSize The step size for movement.
     */
    public static void moveHomingBossProjectiles(int stepSize) {
        new Thread(() -> {
            for (Projectile projectile : bossHomingProjectileList) {
                ProjectileManagerHelper.homing(projectile, stepSize);

                if (projectile.getY() > mainFrameHeight + homingProjectileHeight) {
                    ProjectileManagerHelper.removeProjectile(bossHomingProjectileList, projectile);
                }

                ProjectileManagerHelper.damagePlayerIfProjectileCollision(projectile, bossHomingProjectileList, 2);
            }
        }).start();
    }

    /**
     * Moves orbital boss projectiles.
     *
     * @param stepSize The step size for movement.
     */
    public static void moveBossOrbitalProjectiles(int stepSize) {
        new Thread(() -> {
            for (Projectile projectile : bossOrbitalProjectileList) {
                projectile.setLocation(projectile.getX(), projectile.getY() + stepSize);

                LogicController.updateLabel(projectile.getLocation(), projectile.getId(), projectile.getType(), 1);

                if (projectile.getY() > mainFrameHeight + projectile.getHeight()) {
                    ProjectileManagerHelper.removeProjectile(bossOrbitalProjectileList, projectile);
                }

                ProjectileManagerHelper.damagePlayerIfProjectileCollision(projectile, bossOrbitalProjectileList, 2);
            }
        }).start();
    }

    /**
     * Retrieves the list of boss orbital projectiles.
     *
     * @return The list of boss orbital projectiles.
     */
    protected static CopyOnWriteArrayList<Projectile> getBossOrbitalProjectileList() {
        return bossOrbitalProjectileList;
    }

    /**
     * Retrieves the list of boss homing projectiles.
     *
     * @return The list of boss homing projectiles.
     */
    protected static CopyOnWriteArrayList<Projectile> getBossHomingProjectileList() {
        return bossHomingProjectileList;
    }
}
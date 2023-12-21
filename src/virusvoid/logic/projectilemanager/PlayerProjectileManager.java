package virusvoid.logic.projectilemanager;

import virusvoid.logic.controller.TutorialController;
import virusvoid.logic.objects.GameObject;
import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.logic.controller.GameController;
import virusvoid.logic.controller.InfiniteGameController;
import virusvoid.logic.controller.LogicController;
import virusvoid.logic.enemymanager.BossManager;
import virusvoid.logic.enemymanager.EnemyManagerHelper;
import virusvoid.logic.other.TutorialAndGameHelper;
import virusvoid.logic.other.manager.SpaceshipManager;
import virusvoid.logic.enemymanager.DefaultEnemyManager;
import virusvoid.logic.enemymanager.ShootingEnemyManager;
import virusvoid.logic.objects.Enemy;
import virusvoid.logic.objects.Projectile;
import virusvoid.logic.objects.Spaceship;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages the projectiles fired by the player's spaceship in the game.
 */
public class PlayerProjectileManager {

    private static final CopyOnWriteArrayList<Projectile> playerProjectileList = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Enemy> shootingEnemyList;
    private static CopyOnWriteArrayList<Enemy> defaultEnemyList;
    private static int rightAnimationXOffset;
    private static int leftAnimationXOffset;
    private static int animationYOffset;
    private static Spaceship spaceship;
    private static int projectileId;
    private static int rightXOffset;
    private static int leftXOffset;
    private static int yOffset;

    /**
     * Initializes the Player Projectile Manager.
     */
    public static void startPlayerProjectileManager() {
        shootingEnemyList = ShootingEnemyManager.getShootingEnemyList();
        defaultEnemyList = DefaultEnemyManager.getDefaultEnemyList();

        spaceship = SpaceshipManager.getSpaceship();

        int scaled20 = LogicController.scaleX(20);
        rightAnimationXOffset = LogicController.scaleX(39) - scaled20 / 2;
        leftAnimationXOffset = LogicController.scaleX(10) - scaled20 / 2;
        animationYOffset = LogicController.scaleY(34) - scaled20;

        yOffset = LogicController.scaleY(35) - ProjectileManagerHelper.getDefaultProjectileHeight();
        rightXOffset = LogicController.scaleX(8);
        leftXOffset = LogicController.scaleX(36);
        projectileId = 0;
    }

    /**
     * Spawns projectiles for the player's spaceship (methods for spawning corresponding labls are called).
     */
    public static void spawnPlayerProjectiles() {
        int spaceshipX = (int) spaceship.getX();
        int spaceshipY = (int) spaceship.getY();

        new Thread(() -> {
            for (int i = 0; i < 5; ++i) {
                LogicController.showPlayerProjectileAnimationFrame(i, spaceshipX + leftAnimationXOffset,
                        spaceshipX + rightAnimationXOffset, spaceshipY + animationYOffset);

                if (GameController.isGameRunning()) {
                    TutorialAndGameHelper.waitAndCheckForPause(75, 2, 5, GameController::isGameRunning, GameController::isPaused);
                } else if (InfiniteGameController.isInfiniteGameRunning()) {
                    TutorialAndGameHelper.waitAndCheckForPause(75, 2, 5, InfiniteGameController::isInfiniteGameRunning, InfiniteGameController::isPaused);
                } else {
                    TutorialAndGameHelper.waitAndCheckForPause(75, 2, 5, TutorialController::isTutorialRunning, TutorialController::isPaused);
                }

                LogicController.removePlayeProjectileAnimationLabels();
            }
        }).start();

        new Thread(() -> {
            Projectile left = createPlayerProjectile(spaceshipX + rightXOffset, spaceshipY + yOffset, projectileId++);
            Projectile right = createPlayerProjectile(spaceshipX + leftXOffset, spaceshipY + yOffset, projectileId++);

            playerProjectileList.add(left);
            playerProjectileList.add(right);

            SoundEffectManager.playAndLoadClip("VirusVoid_ShootingSound.wav");
        }).start();
    }

    /**
     * Creates a player projectile with the specified parameters.
     *
     * @param x          The X-coordinate of the spaceship.
     * @param y          The Y-coordinate of the spaceship.
     * @param id         The unique identifier for the projectile.
     * @return The created player projectile.
     */
    private static Projectile createPlayerProjectile(int x, int y, int id) {
        Projectile projectile = new Projectile(x, y, ProjectileManagerHelper.getDefaultProjectileWidth(),
                ProjectileManagerHelper.getDefaultProjectileHeight(), id, 0);

        ProjectileManagerHelper.callCreateProjectileLabel(projectile);

        return projectile;
    }

    /**
     * Moves and detects collisions for player projectiles (updates corresponding labels).
     *
     * @param stepSize The step size for movement.
     */
    public static void movePlayerProjectileAndDetectCollision(int stepSize) {
        new Thread(() -> {
            for (Projectile projectile : playerProjectileList) {
                projectile.setLocation(projectile.getX(), projectile.getY() - stepSize);
                LogicController.updateLabel(projectile.getLocation(), projectile.getId(), projectile.getType(), 1);

                if (projectile.getY() < - ProjectileManagerHelper.getDefaultProjectileHeight()) {
                    ProjectileManagerHelper.removeProjectile(playerProjectileList, projectile);
                }
                playerProjectileCollision(defaultEnemyList, projectile);
                playerProjectileCollision(shootingEnemyList, projectile);

                if (BossManager.getBoss() != null) {
                    if (GameObject.collision(BossManager.getBoss(), projectile)) {
                        ProjectileManagerHelper.removeProjectile(playerProjectileList, projectile);
                        BossManager.getBoss().damage(1);
                    }
                }
            }
        }).start();
    }

    /**
     * Checks for collisions between a player projectile and enemies in the specified list.
     *
     * @param list The list of enemies to check for collisions.
     * @param projectile The player projectile to check for collisions.
     */
    private static void playerProjectileCollision(CopyOnWriteArrayList<Enemy> list, Projectile projectile) {
        for (Enemy enemy : list) {
            if (GameObject.collision(projectile, enemy)) {
                ProjectileManagerHelper.removeProjectile(playerProjectileList, projectile);
                EnemyManagerHelper.removeEnemy(list, enemy, true);
                SoundEffectManager.playAndLoadClip("VirusVoid_SmallExplosion.wav");
            }
        }
    }

    /**
     * Removes all player projectiles from the manager.
     */
    public static void removePlayerProjectiles() {
        for (Projectile projectile : playerProjectileList) {
            ProjectileManagerHelper.removeProjectile(playerProjectileList, projectile);
        }
    }
}
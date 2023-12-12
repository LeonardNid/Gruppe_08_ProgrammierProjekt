package virusvoid.logic.objects;

import virusvoid.logic.other.TutorialAndGameHelper;
import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.logic.controller.GameController;
import virusvoid.logic.controller.InfiniteGameController;
import virusvoid.logic.controller.LogicController;
import virusvoid.logic.enemymanager.BossManager;

import java.awt.*;

/**
 * Represents a boss enemy in the game.
 */
public class Boss extends GameObject {

    private boolean exploding;
    private final int maxHp;
    private int hp;

    /**
     * Initializes a Boss object with the specified position.
     *
     * @param x     The x-coordinate of the boss.
     * @param y     The y-coordinate of the boss.
     * @param maxHp The maximum HP which the boss should have
     */
    public Boss(int x, int y, int maxHp) {
        super(x, y, LogicController.scaleX(125), LogicController.scaleX(125));
        this.maxHp = maxHp;
        exploding = false;
        this.hp = maxHp;
    }

    /**
     * Inflicts damage on the boss and handles the consequences, such as animation and game over.
     *
     * @param damage The amount of damage to be inflicted on the boss.
     */
    public void damage(int damage) {
        if (hp > 0) {
            hp -= damage;
            System.out.println("BossHP: " + hp);
            SoundEffectManager.playAndLoadClip("VirusVoid_SmallExplosion.wav");
        } else if (!exploding) {
            exploding = true;
            new Thread(() -> {
                TutorialAndGameHelper.animateExplosion(this, 'B', 60, 2, 20);
                SoundEffectManager.playAndLoadClip("VirusVoid_BossExplosion.wav");
                LogicController.removeBossLabel();

                if (GameController.isGameRunning()) {
                    TutorialAndGameHelper.waitAndCheckForPause(2500, 10, 20, GameController::isGameRunning, GameController::isPaused);
                } else {
                    TutorialAndGameHelper.waitAndCheckForPause(2500, 10, 20, InfiniteGameController::isInfiniteGameRunning, InfiniteGameController::isPaused);
                }

                BossManager.removeBoss();

                if (GameController.isGameRunning()) {
                    GameController.setGameOver();
                    LogicController.gameOverScreen("You have saved your Planet", new Color(0, 102, 204), "Play again", false);
                }
            }).start();
        }
    }

    /**
     * Gets the current HP of the boss.
     *
     * @return The current HP of the boss.
     */
    public int getHp() {
        return Math.max(hp, 0);
    }

    /**
     * Gets the maximum HP of the boss.
     *
     * @return The maximum HP of the boss.
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Checks if the boss is currently exploding.
     *
     * @return True if the boss is exploding, false otherwise.
     */
    public boolean isExploding() {
        return exploding;
    }
}
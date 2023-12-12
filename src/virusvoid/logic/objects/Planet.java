package virusvoid.logic.objects;

import virusvoid.logic.sound.SoundEffectManager;
import virusvoid.logic.controller.GameController;
import virusvoid.logic.controller.InfiniteGameController;
import virusvoid.logic.controller.LogicController;

import java.awt.*;

/**
 * Represents a Planet in the game that can take damage.
 */
public class Planet {

    private int hp;

    /**
     * Initializes a new Planet with an initial health value.
     */
    public Planet() {
        hp = 20;
    }

    /**
     * Inflicts damage to the Planet, reducing its health.
     * Updates the user interface to reflect the new health value.
     * If the health drops to or below zero, triggers game over and displays a game over screen.
     */
    public void damage() {
        hp -= 1;
        LogicController.updatePlanetHp();

        // Play sound effects based on the current health status
        if (hp > 0) {
            SoundEffectManager.playAndLoadClip("VirusVoid_LargeExplosion.wav");
        }
        if (hp <= 0) {
            SoundEffectManager.playAndLoadClip("VirusVoid_HugeExplosion.wav");
            LogicController.gameOverScreen("Your Planet was destroyed", Color.RED, "Retry", InfiniteGameController.isInfiniteGameRunning());

            if (GameController.isGameRunning()) {
                GameController.setGameOver();
            } else if (InfiniteGameController.isInfiniteGameRunning()) {
                InfiniteGameController.setGameOver();
            }
        }
    }

    /**
     * Gets the current health points (HP) of the Planet.
     *
     * @return The current health points of the Planet.
     */
    public int getHp() {
        return hp;
    }
}
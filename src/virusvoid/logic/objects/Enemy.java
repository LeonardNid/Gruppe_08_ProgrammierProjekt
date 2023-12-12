package virusvoid.logic.objects;

import virusvoid.logic.controller.LogicController;

/**
 * Represents an enemy object in the game.
 */
public class Enemy extends GameObject {

    private boolean playerCollision;
    private final int type;
    private final int id;

    /**
     * Initializes an Enemy object with the specified position, ID, and type.
     *
     * @param x    The x-coordinate of the enemy.
     * @param y    The y-coordinate of the enemy.
     * @param id   The unique identifier of the enemy.
     * @param type The type of the enemy.
     */
    public Enemy(int x, int y, int id, int type) {
        super(x, y, LogicController.scaleX(25), LogicController.scaleX(25));
        this.playerCollision = false;
        this.type = type;
        this.id = id;
    }

    /**
     * Gets the unique identifier of the enemy.
     *
     * @return The ID of the enemy.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the type of the enemy.
     *
     * @return The type of the enemy.
     */
    public int getType() {
        return type;
    }

    /**
     * Checks if the enemy has collided with the player.
     *
     * @return True if the enemy has collided with the player, false otherwise.
     */
    public boolean hasCollidedWithPlayer() {
        return playerCollision;
    }

    /**
     * Sets the collision status with the player.
     *
     * @param playerCollision True if the enemy has collided with the player, false otherwise.
     */
    public void setPlayerCollision(boolean playerCollision) {
        this.playerCollision = playerCollision;
    }
}
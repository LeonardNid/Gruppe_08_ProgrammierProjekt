package virusvoid.logic.objects;

import virusvoid.logic.controller.LogicController;

/**
 * Represents a Projectile in the game, a moving object shot by the player's spaceship.
 */
public class Projectile extends GameObject {

    private final int height;
    private final int width;
    private final int type;
    private final int id;

    /**
     * Initializes a new Projectile with specified attributes.
     *
     * @param x      The x-coordinate of the Projectile.
     * @param y      The y-coordinate of the Projectile.
     * @param width  The width of the Projectile.
     * @param height The height of the Projectile.
     * @param id     The unique identifier of the Projectile.
     * @param type   The type of the Projectile.
     */
    public Projectile(int x, int y, int width, int height, int id, int type) {
        super(x, y);
        this.height = height;
        this.width = width;
        this.type = type;
        this.id = id;
    }

    /**
     * Gets the unique identifier of the Projectile.
     *
     * @return The unique identifier of the Projectile.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the type of the Projectile.
     *
     * @return The type of the Projectile.
     */
    public int getType() {
        return type;
    }

    /**
     * Gets the height of the projectile.
     *
     * @return The height of the projectile.
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the projectile.
     *
     * @return The width of the projectile.
     */
    @Override
    public int getWidth() {
        return width;
    }
}
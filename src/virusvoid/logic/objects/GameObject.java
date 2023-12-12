package virusvoid.logic.objects;

import java.awt.Point;

/**
 * Represents a generic game object with a position, width, and height.
 */
public class GameObject extends Point {

    private final int height;
    private final int width;

    /**
     * Initializes a GameObject with the specified position, width, and height.
     *
     * @param x      The x-coordinate of the game object.
     * @param y      The y-coordinate of the game object.
     * @param width  The width of the game object.
     * @param height The height of the game object.
     */
    protected GameObject(int x, int y, int width, int height) {
        super(x, y);
        this.height = height;
        this.width = width;
    }

    /**
     * Gets the height of the game object.
     *
     * @return The height of the game object.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the game object.
     *
     * @return The width of the game object.
     */
    public int getWidth() {
        return width;
    }
}
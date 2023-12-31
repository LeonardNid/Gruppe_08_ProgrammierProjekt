package virusvoid.logic.objects;

import java.awt.Point;

/**
 * Represents a generic game object with a position, width, and height.
 */
public abstract class GameObject extends Point {

    /**
     * Initializes a GameObject with the specified position, width, and height.
     *
     * @param x      The x-coordinate of the game object.
     * @param y      The y-coordinate of the game object.
     */
    protected GameObject(int x, int y) {
        super(x, y);
    }

    /**
     * Checks if two GameObjects (rectangles) collide.
     *
     * @param box1 The first logical box.
     * @param box2 The second logical box.
     * @return True if the rectangles collide, false otherwise.
     */
    public static boolean collision(GameObject box1, GameObject box2) {
        return (box1.getX() < box2.getX() + box2.getWidth()) &&
                (box1.getX() + box1.getWidth() > box2.getX()) &&
                (box1.getY() < box2.getY() + box2.getHeight()) &&
                (box1.getY() + box1.getHeight() > box2.getY());
    }

    /**
     * Gets the height of the game object.
     *
     * @return The height of the game object.
     */
    abstract public int getHeight();

    /**
     * Gets the width of the game object.
     *
     * @return The width of the game object.
     */
    abstract public int getWidth();
}
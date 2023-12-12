package virusvoid.gui.labels;

import java.awt.Point;

import javax.swing.*;

/**
 * The {@code ScaledLabel} class represents a JLabel with additional properties for handling scaled images.
 */
public class ScaledLabel extends JLabel {

    private final int id;

    /**
     * Constructs a new ScaledLabel with the specified image icon (ScaledLabel has width and height of the image icon), location, and ID.
     *
     * @param imageIcon The ImageIcon to be set as the label's icon.
     * @param location  The location (x, y) where the label will be positioned.
     * @param id        The unique identifier (ID) associated with the label.
     */
    public ScaledLabel(ImageIcon imageIcon, Point location, int id) {
        this.id = id;
        this.setIcon(imageIcon);
        this.setBounds((int) location.getX(), (int) location.getY(), imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }

    /**
     * Gets the unique identifier (ID) associated with the ScaledLabel.
     *
     * @return The unique identifier (ID) of the label.
     */
    public int getId() {
        return id;
    }
}
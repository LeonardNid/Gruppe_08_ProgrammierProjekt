package virusvoid.gui.labels;

import virusvoid.gui.other.GuiController;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code SpaceshipLabel} class represents a customized JLabel for displaying spaceship images.
 * It extends the ScaledLabel class and includes additional properties for a spaceship label.
 */
public class SpaceshipLabel extends ScaledLabel {

    /**
     * Constructs a new ScaledLabel with the specified image icon (spaceship label has width and (height + hp Bar offset) of the image icon), location, and identifier.
     *
     * @param imageIcon The ImageIcon representing the spaceship image.
     * @param location  The location (Point) at which the label is positioned.
     * @param id        The identifier for the spaceship label.
     */
    public SpaceshipLabel(ImageIcon imageIcon, Point location, int id) {
        super(imageIcon, location, id);
        this.setBounds((int) location.getX(), (int) location.getY(), imageIcon.getIconWidth(),
                imageIcon.getIconHeight() + GuiController.scaleY(10));
    }
}
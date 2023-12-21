package virusvoid.gui.labels;

import virusvoid.gui.other.GuiBasic;

import javax.swing.*;

/**
 * The {@code PlanetHPLabel} class represents a JLabel used to display the current health points (HP) of the planet.
 */
public class PlanetHPLabel extends JLabel {

    /**
     * Constructs a new PlanetHPLabel with default properties.
     * The label's text color is set to light blue, alignment to left, font size to 30,
     * and the label is positioned at (5, 0) with a size of 250x25 pixels.
     */
    public PlanetHPLabel() {
        this.setForeground(GuiBasic.LIGHT_BLUE);
        this.setHorizontalAlignment(JLabel.LEFT);
        this.setFont(new GuiBasic().pixelArtFont(30));
        this.setBounds(5, 0, 250, 25);
    }
}
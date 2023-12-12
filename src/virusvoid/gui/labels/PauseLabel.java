package virusvoid.gui.labels;

import virusvoid.gui.other.GuiBasic;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code PauseLabel} class extends JLabel and represents a label used to display a "Paused" message in the GUI.
 * It provides a method to create a new JLabel with the specified properties for a pause panel.
 */
public class PauseLabel extends JLabel {

    /**
     * Creates and returns a JLabel with specific properties for displaying a "Paused" message.
     *
     * @return A JLabel configured for displaying a "Paused" message.
     */
    public static JLabel createLabel() {
        GuiBasic guiBasic = new GuiBasic();
        JLabel pausedLabel = guiBasic.createLabel("Paused", new Color(255, 120, 0), guiBasic.pixelArtFont(30));
        pausedLabel.setBounds(260, 0, 150, 25);
        pausedLabel.setVisible(false);
        return pausedLabel;
    }
}
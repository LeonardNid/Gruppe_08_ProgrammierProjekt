package virusvoid.gui.panels;

import virusvoid.gui.other.GuiBasic;
import virusvoid.gui.other.GuiController;

import java.awt.*;

/**
 * A translucent orange panel used when the game/tutorial is paused.
 */
public class OrangePanel extends GuiBasic {

    /**
     * Constructs a new OrangePanel.
     */
    public OrangePanel() {
        this.setBackground(new Color(255, 165, 0, 100));
        this.setBounds(0, 0, GuiController.getMainFrameWidth(), GuiController.getMainFrameHeight());
        this.setVisible(false);
    }
}
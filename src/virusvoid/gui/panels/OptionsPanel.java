package virusvoid.gui.panels;

import virusvoid.gui.other.GuiBasic;
import virusvoid.gui.other.GuiController;
import virusvoid.gui.other.VolumeRegulatorBar;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel representing the options menu of the "Virus-Void" game.
 */
public class OptionsPanel extends GuiBasic {

    /**
     * Constructs a new OptionsPanel.
     */
    protected OptionsPanel() {
        Font pixelArtFont = pixelArtFont(FIFTY);

        JLabel music = createLabel("Music", Color.WHITE, pixelArtFont);

        JLabel soundEffects = createLabel("Sound Effects", Color.WHITE, pixelArtFont);

        JButton mainMenu = mainMenuButton(pixelArtFont);

        JPanel centerPanel = new JPanel();

        GroupLayout centralLayout = constructGroupLayout(centerPanel);

        VolumeRegulatorBar musicVolume = new VolumeRegulatorBar(400, 20, 0);
        VolumeRegulatorBar soundEffectsVolume = new VolumeRegulatorBar(400, 20, 1);

        centralLayout.setHorizontalGroup(
            centralLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(music)
            .addComponent(musicVolume)
            .addComponent(soundEffects)
            .addComponent(soundEffectsVolume)
            .addComponent(mainMenu));

        centralLayout.setVerticalGroup(
            centralLayout.createSequentialGroup()
            .addGap(GuiController.scaleY(170) + 50 - SCALED_BUTTON_GAP_SIZE * 2)
            .addComponent(music)
            .addGap(SCALED_BUTTON_GAP_SIZE)
            .addComponent(musicVolume)
            .addGap(SCALED_BUTTON_GAP_SIZE)
            .addComponent(soundEffects)
            .addGap(SCALED_BUTTON_GAP_SIZE)
            .addComponent(soundEffectsVolume)
            .addGap(SCALED_BUTTON_GAP_SIZE)
            .addComponent(mainMenu));

        this.add(centerPanel);
        this.setOpaque(false);
    }
}
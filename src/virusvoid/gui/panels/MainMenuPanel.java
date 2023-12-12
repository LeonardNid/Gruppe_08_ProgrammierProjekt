package virusvoid.gui.panels;

import virusvoid.gui.other.GuiBasic;
import virusvoid.gui.other.GuiController;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel representing the main menu of the "Virus-Void" game.
 */
public class MainMenuPanel extends GuiBasic {

	/**
	 * Constructs a new MainMenuPanel.
	 */
    public MainMenuPanel() {
		JLabel title = createLabel("Virus-Void", Color.GREEN, pixelArtFont( 120));

        Font pixelArtFont = pixelArtFont(FIFTY);

		JButton play = transparentButton("Play", Color.WHITE, pixelArtFont);
		play.addActionListener(e -> GuiController.switchPanel(new GameTutorialPanel()));

		JButton options = transparentButton("Options", Color.WHITE, pixelArtFont);
		options.addActionListener(e -> GuiController.switchPanel(new OptionsPanel()));
		
		JButton quit = transparentButton("Quit", Color.RED, pixelArtFont);
		quit.addActionListener(e -> GuiController.quitApplication());
		
		JPanel centerPanel = new JPanel();
		
		GroupLayout mainLayout = constructGroupLayout(centerPanel);
		int width = 700;
		int height = 100;

		mainLayout.setHorizontalGroup(
			mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
			.addComponent(title, width, width, width)
			.addComponent(play)
			.addComponent(options)
			.addComponent(quit));

		mainLayout.setVerticalGroup(
			mainLayout.createSequentialGroup()
			.addGap(GuiController.scaleY(100))
			.addComponent(title, height, height, height)
			.addGap(GuiController.scaleY(50))
			.addComponent(play)
			.addGap(SCALED_BUTTON_GAP_SIZE)
			.addComponent(options)
			.addGap(SCALED_BUTTON_GAP_SIZE)
			.addComponent(quit));

		this.add(centerPanel);
		this.setOpaque(false);
    }
}
package virusvoid.gui.panels;

import virusvoid.gui.other.GuiController;
import virusvoid.gui.other.GuiBasic;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel displayed at the end of the game or tutorial, providing end messages and options to retry or go back.
 */
public class GameTutorialEndPanel extends GuiBasic {

	/**
	 * Constructs a GameTutorialEndPanel with the specified end message, end message color,
	 * retry or restart option, and panel identifier for retrying.
	 *
	 * @param endMessage       The message displayed at the end.
	 * @param endMessageColor  The color of the end message.
	 * @param retryOrRestart   The label for the retry or restart button.
	 * @param panelToTryAgain  The panel identifier for retrying ('G' for GamePanel, 'T' for TutorialPanel).
     * @param infiniteOrNot    True if the infinite game should be started when pressing the retry button
	 */
    public GameTutorialEndPanel(String endMessage, Color endMessageColor, String retryOrRestart, char panelToTryAgain, boolean infiniteOrNot) {
        JLabel deathMessage = createLabel(endMessage, endMessageColor, pixelArtFont(60));

        Font pixelArtFont = pixelArtFont(FIFTY);

		JButton again = transparentButton(retryOrRestart, Color.WHITE, pixelArtFont);
		if (panelToTryAgain == 'G') {
			again.addActionListener(e -> {
				again.setEnabled(false);
				if (infiniteOrNot) {
					GuiController.toggleInfinteGame();
				}
				GuiController.switchPanel(new GamePanel());
			});
		} else {
			again.addActionListener(e -> {
				again.setEnabled(false);
				GuiController.switchPanel(new TutorialPanel());
			});
		}

		JButton back = transparentButton("Back", Color.RED, pixelArtFont);
		back.addActionListener(e -> GuiController.switchPanel(new GameTutorialPanel()));
		
		JPanel centerPanel = new JPanel();
		
		GroupLayout mainLayout = constructGroupLayout(centerPanel);
		int width = 1200;
		int height = 60;

		mainLayout.setHorizontalGroup(
			mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
			.addComponent(deathMessage, width, width, width)
			.addComponent(again)
			.addComponent(back));

		mainLayout.setVerticalGroup(
			mainLayout.createSequentialGroup()
			.addGap(GuiController.scaleY(100) + 40)
			.addComponent(deathMessage, height, height, height)
			.addGap(GuiController.scaleY(50) + 60)
			.addGap(SCALED_BUTTON_GAP_SIZE)
			.addComponent(again)
			.addGap(SCALED_BUTTON_GAP_SIZE)
			.addComponent(back));

		this.add(centerPanel);
		this.setOpaque(false);
    }
}
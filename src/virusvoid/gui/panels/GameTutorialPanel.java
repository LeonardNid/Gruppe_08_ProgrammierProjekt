package virusvoid.gui.panels;
import virusvoid.gui.other.GuiBasic;
import virusvoid.gui.other.GuiController;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Panel allowing the user to choose between starting the game (infinite game is also an option), accessing the tutorial, or returning to the main menu.
 */
public class GameTutorialPanel extends GuiBasic {

    /**
     * Constructs a new GameTutorialPanel.
     */
    public GameTutorialPanel() {
        Font pixelArtFont = pixelArtFont(FIFTY);

        JButton game = transparentButton("Game", Color.WHITE, pixelArtFont);
        game.addActionListener(e -> {
            game.setEnabled(false);
            GuiController.switchPanel(new GamePanel());
        });

        JButton tutorial = transparentButton("Tutorial", Color.WHITE, pixelArtFont);
        tutorial.addActionListener(e -> GuiController.switchPanel(new TutorialPanel()));

        JButton mainMenu = mainMenuButton(pixelArtFont);

        JButton infiniteGame = transparentButton("INFINITE MODE", Color.ORANGE, pixelArtFont);
        infiniteGame.addActionListener(e -> {
            infiniteGame.setEnabled(false);
            GuiController.toggleInfinteGame();
            GuiController.switchPanel(new GamePanel());
        });

        JPanel centerPanel = new JPanel();

		GroupLayout centralLayout = constructGroupLayout(centerPanel);

        centralLayout.setHorizontalGroup(
			centralLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
			.addComponent(game)
			.addComponent(tutorial)
            .addComponent(mainMenu)
            .addComponent(infiniteGame));
        
		centralLayout.setVerticalGroup(
			centralLayout.createSequentialGroup()
			.addGap(GuiController.scaleY(150) + 100)
			.addComponent(game)
			.addGap(SCALED_BUTTON_GAP_SIZE)
			.addComponent(tutorial)
            .addGap(SCALED_BUTTON_GAP_SIZE)
            .addComponent(mainMenu)
            .addGap(SCALED_BUTTON_GAP_SIZE * 4)
            .addComponent(infiniteGame));


        this.add(centerPanel);
        this.setOpaque(false);
    }
}
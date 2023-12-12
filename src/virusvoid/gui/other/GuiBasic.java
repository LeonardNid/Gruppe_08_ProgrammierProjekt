package virusvoid.gui.other;
import virusvoid.gui.panels.MainMenuPanel;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.JButton;

/**
 * The {@code GuiBasic} class is a JPanel extension providing basic GUI utility methods
 * for creating transparent components, buttons, labels, and handling font-related tasks.
 */
public class GuiBasic extends JPanel {

	/**
	 * The light blue color used in the GUI components.
	 */
	public static final Color LIGHT_BLUE = new Color(0, 102, 204);

	/**
	 * The gap size for scaled buttons.
	 */
	protected static final int SCALED_BUTTON_GAP_SIZE = GuiController.scaleY(18);

	/**
	 * The constant value 50.
	 */
	protected static final int FIFTY = 50;

	/**
	 * Constructs and returns a transparent GroupLayout for the specified JPanel.
	 *
	 * @param j The JPanel for which to create a transparent GroupLayout.
	 * @return A transparent GroupLayout for the specified JPanel.
	 */
	protected GroupLayout constructGroupLayout(JPanel j) {
		GroupLayout layout = new GroupLayout(j);
		layout.setAutoCreateContainerGaps(true);
		j.setLayout(layout);
		j.setOpaque(false);

		return layout;
	}

	/**
	 * Constructs and returns a transparent JButton with the specified text, font color, and font.
	 *
	 * @param text  The text to be displayed on the button.
	 * @param color The font color of the button.
	 * @param font  The font to be used for the button text.
	 * @return A transparent JButton with the specified properties.
	 */
	protected JButton transparentButton(String text, Color color, Font font) {
		JButton b = new JButton(text);
		b.setForeground(color);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.setFont(font);
		return b;
	}

	/**
	 * Constructs and returns a transparent JButton leading to the main menu, with the specified font.
	 *
	 * @param pixelArtFont The font to be used for the button text.
	 * @return A transparent JButton leading to the main menu.
	 */
	protected JButton mainMenuButton(Font pixelArtFont) {
		JButton mainMenu = transparentButton("MainMenu", Color.RED, pixelArtFont);
		mainMenu.addActionListener(e -> GuiController.switchPanel(new MainMenuPanel()));
		return mainMenu;
	}

	/**
	 * Constructs and returns a transparent JLabel with the specified text, font color, and font.
	 *
	 * @param text  The text to be displayed on the label.
	 * @param color The font color of the label.
	 * @param font  The font to be used for the label text.
	 * @return A transparent JLabel with the specified properties.
	 */
	public JLabel createLabel(String text, Color color, Font font) {
		JLabel l = new JLabel(text);
		l.setForeground(color);
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setFont(font);
		return l;
	}

	/**
	 * Imports a font from a file and returns it with the specified size.
	 *
	 * @param size The desired size of the imported font.
	 * @return A Font object derived from the imported font file.
	 */
    public Font pixelArtFont(int size) {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("VirusVoid_04B_03__.TTF")) {
			if (inputStream != null) {
				return Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(Font.BOLD, size);
			} else {
				System.out.println("Schriftart-Datei nicht gefunden: VirusVoid_04B_03__.TTF");
				return null;
			}
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
			return null;
		}
	}
}
package de.inform.hsh.layers.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.inform.hsh.layers.controller.GUIController;

/**
 * 
 * @author thorb Diese Klasse stellt sowohl das Panel für den Start als auch für
 *         die Wahl der Schwierigkeit dar.
 */
public class GameStartPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -944959023168069853L;
	private BufferedImage backgroundImage;

	/**
	 * Konstruktor zum erstellen des Panels
	 */
	public GameStartPanel(JButton[] buttons, String[] btnHints, ActionListener[] btnActions) {
        super(new GridBagLayout());
        backgroundImage = GUIController.getInstance().getBackground();
        setOpaque(false);
        JLabel title = new JLabel(" TechMaster ");
        title.setForeground(Color.GREEN);
        GUIController.getInstance().setStyle(0, new JComponent[]{title}, 70f);
        title.setOpaque(true);
        title.setBackground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, createConstraints(0, 0, 1, 1, GridBagConstraints.CENTER, new Insets(10, 0, 20, 0)));
        for (int i = 0; i < buttons.length; i++) {
        	GUIController.getInstance().setStyle(i, buttons, 40f);
            buttons[i].setToolTipText(btnHints[i]);
            buttons[i].addActionListener(btnActions[i]);
            buttons[i].setPreferredSize(new Dimension(300, 100));
            add(buttons[i], createConstraints(i + 1, 0, 1, 1, GridBagConstraints.CENTER, new Insets(0, 0, 10, 0)));
        }
    }

    private GridBagConstraints createConstraints(int gridy, int gridx, int gridheight, int gridwidth, int anchor, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = gridy;
        gbc.gridx = gridx;
        gbc.gridheight = gridheight;
        gbc.gridwidth = gridwidth;
        gbc.anchor = anchor;
        gbc.insets = insets;
        return gbc;
    }
    
	/**
	 * paintMethode zum setzen des Hintergrunds
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
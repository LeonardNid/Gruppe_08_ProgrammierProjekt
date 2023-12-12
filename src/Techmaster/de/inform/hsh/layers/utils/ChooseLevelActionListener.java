package de.inform.hsh.layers.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.inform.hsh.layers.controller.GUIController;

/**
 * Actionlistener um das Level zu wählen
 * 
 * @author thorb
 *
 */
public class ChooseLevelActionListener implements ActionListener {
	/**
	 * Übergibt die Auswahl an den GUIController und ruft setNewView auf
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		GUIController.getInstance().chooseFragenKatalog(e.getActionCommand());
		GUIController.getInstance().setNewView(GUIController.getInstance().getGameLevelPanel(),
				GUIController.getInstance().getGameMainPanel());
	}
}
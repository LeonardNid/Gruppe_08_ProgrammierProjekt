package de.inform.hsh.layers.Techmaster.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.inform.hsh.layers.Techmaster.controller.GUIController;

/**
 * ActionListener für den Start-Button des Hauptmenüs
 * 
 * @author thorb
 *
 */
public class StartActionListener implements ActionListener {

	/**
	 * actionPerformed-Methode zum wechseln der View sobald der Startbutton gedrückt wurde.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		GUIController.getInstance().setNewView(GUIController.getInstance().getGameStartPanel(),
				GUIController.getInstance().getGameLevelPanel());
	}
}
package de.inform.hsh.layers.Techmaster.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.inform.hsh.layers.Techmaster.controller.GUIController;

/**
 * Actionlistener um die Antwort aus der Komponente zu laden
 * 
 * @author thorb
 *
 */
public class ChooseAntwortActionListener implements ActionListener {
	/**
	 * übergibt die Antwort an den GUIController
	 */
	public void actionPerformed(ActionEvent e) {
		GUIController.getInstance().setAntwort(e.getActionCommand());
	}

}

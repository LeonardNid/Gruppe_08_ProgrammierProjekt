package de.inform.hsh.layers.Techmaster.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.inform.hsh.layers.Techmaster.controller.GUIController;

/**
 * ActionListener welcher sich um die verarbeitung von Joker anfragen kümmert
 * 
 * @author thorb
 *
 */
public class JokerActionListener implements ActionListener {
	/**
	 * Falls ein Joker bereits genutzt wurde wird ein JOptionPane aufgerufen. Sollte
	 * dies nicht der Fall sein wird vom GUIController updateAntwortundJoker
	 * aufgerufen um einen Joker zu benutzen
	 */
	public void actionPerformed(ActionEvent e) {
		if (GUIController.getInstance().useJoker()) {
			String text = "Joker bereits genutzt!";
			String title = "Joker";
			Object[] options = { "Ok" };
			GUIController.getInstance().showJOptionPane(text, title, options);
		} else {
			GUIController.getInstance().getGameMainPanel().updateAntwortUndJoker();
		}
	}
}
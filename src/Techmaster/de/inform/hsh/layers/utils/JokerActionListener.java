package de.inform.hsh.layers.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import de.inform.hsh.layers.controller.GUIController;
import de.inform.hsh.layers.view.GameMainPanel;

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
package de.inform.hsh.layers.Techmaster.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.inform.hsh.layers.Techmaster.controller.GUIController;
/**
 * Actionlistener um das Spiel zu beenden
 * 
 * @author thorb
 *
 */
public class ExitActionListener implements ActionListener{
	
	public void actionPerformed(ActionEvent e) {
		GUIController.getInstance().showJOptionPane(null, null, null);
	}
}

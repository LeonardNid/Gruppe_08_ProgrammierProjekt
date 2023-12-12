package de.inform.hsh.layers.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import de.inform.hsh.layers.controller.GUIController;
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

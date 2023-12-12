package de.inform.hsh.layers.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import de.inform.hsh.layers.controller.GUIController;
import de.inform.hsh.layers.view.GameMainPanel;

/**
 * Actionlistener um die Spielregeln aufzurufen
 * 
 * @author thorb
 *
 */
public class HelpActionListener implements ActionListener {
	/**
	 * öffnet mittels GUIController ein JOptionPane welches die Spielregeln anzeigt
	 */
	public void actionPerformed(ActionEvent e) {
		String text = "Spielregeln:\n"
				+ "                        \"1. Sie haben 60 Sekunden Zeit, um jede Frage zu beantworten.\\n\" +\r\n"
				+ "                        \"2. Jede Frage hat 4 mögliche Antworten, von denen nur eine korrekt ist.\\n\" +\r\n"
				+ "                        \"3. Wählen Sie die richtige Antwort aus, um 100 Punkte zu erhalten.\\n\" +\r\n"
				+ "                        \"4. Wählen Sie eine falsche Antwort aus, um 1 Leben zu verlieren.\\n\" +\r\n"
				+ "                        \"5. Sie haben insgesamt 3 Leben, bevor das Spiel endet.\";";
		String title = "Help";
		Object[] options = { "Ok" };
		GUIController.getInstance().showJOptionPane(text, title, options);
	}
}
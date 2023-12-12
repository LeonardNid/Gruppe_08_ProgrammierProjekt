package Techmaster;

import de.inform.hsh.layers.controller.GUIController;
import de.inform.hsh.layers.controller.QuizController;
import de.inform.hsh.layers.utils.QuizFacade;

/**
 * HauptKlasse zum Starten der Anwendung. Enthält die main-Methode.
 * 
 * @author thorb
 * 
 */
public class Techmaster {
	public Techmaster() {
		new Thread() {
			@Override
			public void run() {
				// Anwendungsschicht instanziieren
				final QuizFacade quizFacade = QuizController.getInstance();
				// Und zum Schluss die GUI starten und mit der zu verwendenden
				// Anwendungsschicht verknüpfen
				GUIController.getInstance().runApplication(quizFacade);
			}
		}.start();
	}
//	public static void main(String[] args) {
//		
//	}
}
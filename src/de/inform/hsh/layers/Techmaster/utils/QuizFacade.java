package de.inform.hsh.layers.Techmaster.utils;

import de.inform.hsh.layers.Techmaster.model.Frage;
import de.inform.hsh.layers.Techmaster.model.Spieler;

public interface QuizFacade {
	/**
	 * Gibt die nächste Frage zurück
	 * 
	 * @return Frage
	 */
	Frage getFrage();

	/**
	 * überprüft die Antwort auf Korrektheit und ändert die Spieler Informationen
	 * dementsprechend
	 * 
	 * @return boolean
	 * @param a
	 */
	boolean proofAntwort(String a);

	/**
	 * Überprüft ob bereits ein Joker genutzt wurde oder nutzt den Joker und ändert
	 * die Spieler Informationen.
	 * 
	 * @return boolean
	 * @param frage
	 */
	boolean useJoker(Frage frage);

	/**
	 * @return Spieler
	 */
	Spieler getSpieler();

	/**
	 * Setzt die Spieler Informationen zurück sowie die Fragen
	 */
	void restart();

	/**
	 * Methode zum setzen des Dateipfads
	 *
     */
	void chooseFragenKatalog(String level);
}
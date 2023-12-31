package de.inform.hsh.layers.Techmaster.model;

import java.util.ArrayList;

import de.inform.hsh.layers.Techmaster.view.GameMainPanel;

/**
 * Model-Klasse für den Spieler
 * 
 * @author thorb
 *
 */
public class Spieler {
	// Singleton
	private static volatile Spieler instance;
	// Punktestand des Spielers
	private int punkte = 0;
	// Zahl der richtigen Antwort nacheinander
	private int riar;
	// Leben des Spielers
	private int leben = 3;
	// Anzahl der Joker
	private int joker;
	// Liste aller Observer die benachrichtigt werden sobald der Zustand des
	// Spielers sich ändert
	private ArrayList<GameMainPanel> observer = new ArrayList<GameMainPanel>();

	/**
	 * Methode zum erstellen bzw bekommen eines Spielers
	 * 
	 * @return
	 */
	public static synchronized Spieler getInstance() {
		if (instance == null)
			instance = new Spieler(3);
		return instance;
	}

	/**
	 * Methode um die Observer dem Spieler hinzuzufügen
	 * 
	 * @param sub
	 */
	public void subscribe(GameMainPanel sub) {
		observer.add(sub);
	}

	/**
	 * Methode um die Observer abzumelden
	 * 
	 * @param sub
	 */
	public void unsubscribe(GameMainPanel sub) {
		observer.remove(sub);
	}

	/**
	 * Methode um die Observer zu benachrichtigen
	 */
	public void notifyObserver() {
		for (GameMainPanel gameMainPanel : observer) {
			gameMainPanel.updateInfoPanelData();
		}
	}

	/**
	 * Konstruktor
	 * 
	 * @param j
	 */
	private Spieler(int j) {
		this.joker = j;
	}

	// setter und getter
	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}

	public void setLeben(int leben) {
		this.leben = leben;
	}

	public void setJoker(int joker) {
		this.joker = joker;
	}

	public int getPunkte() {
		return punkte;
	}

	public int getLeben() {
		return leben;
	}

	public int getJoker() {
		return joker;
	}

	public int getRiar() {
		return riar;
	}

	/**
	 * inkrementieren des Folgepunktestandes um 1
	 */
	public void addRiar() {
		riar += 1;
		if (riar == 5) {
			addLeben();
			riar = 0;
		}
	}

	/**
	 * zurücksetzen des Folgepunktestandes
	 */
	public void removeRiar() {
		riar = 0;
	}

	/**
	 * erhöhen des Punktestandes um 100
	 */
	public void addPunkte() {
		punkte += 100;
		addRiar();
		notifyObserver();
	}

	/**
	 * erhöhen der Leben um 1
	 */
	public void addLeben() {
		leben++;
		notifyObserver();
	}

	/**
	 * entfernen der Leben um 1
	 */
	public void removeLeben() {
		leben--;
		notifyObserver();
	}

	/**
	 * entfernen der Joker um 1
	 */
	public void useJoker() {
		joker--;
		notifyObserver();
	}
}
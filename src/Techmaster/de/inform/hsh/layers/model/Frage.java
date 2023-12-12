package de.inform.hsh.layers.model;

import java.util.ArrayList;
/**
 * Model-Klasse zum halten einer Frage
 * @author thorb
 *
 */
public class Frage {
	//Frage inform eines Strings
	private String f;
	//Mögliche Antworten als ArrayList
	private ArrayList<Antwort> a;
	//Wahrheitswert ob Joker bereits genutzt wurde
	private boolean jokerUsed;
	/**
	 * Konstruktor
	 * @param a
	 * @param f
	 */
	public Frage(ArrayList<Antwort> a, String f) {
		this.a = a;
		this.f = f;
		this.jokerUsed = false;
	}
	//getter und setter
	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}
	
	public void jokerUsed() {
		jokerUsed = true;
	}
	
	public boolean getJokerUsed() {
		return jokerUsed;
	}

	public ArrayList<Antwort> getA() {
		return a;
	}

	public void setA(ArrayList<Antwort> antworten) {
		this.a = antworten;
	}
}
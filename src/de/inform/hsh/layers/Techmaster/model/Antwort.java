package de.inform.hsh.layers.Techmaster.model;
/**
 * Model-Klasse zum halten einer Antwort
 * @author thorb
 *
 */
public class Antwort {
	//Antwort inform eines Strings
	private String a;
	//Wahrheitswert der Antwort
	private boolean k;
	/**
	 * Konstruktor
	 * @param a
	 * @param k
	 */
	public Antwort(String a, boolean k) {
		this.a = a;
		this.k = k;
	}
	//Getter und Setter
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public boolean isK() {
		return k;
	}
	public void setK(boolean k) {
		this.k = k;
	}
}

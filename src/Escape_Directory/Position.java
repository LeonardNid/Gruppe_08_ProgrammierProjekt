package Escape_Directory;

/** Ein Datentyp. Vergleichbar mit 2 Dimensionaler Vektor. */
public class Position {
	private double x;
	private double y;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	/** Gibt eine Position zurück, welche zwei Positionen miteinander addiert */
	public Position plus(Position p2) {
		return new Position(getX() + p2.getX(), getY() + p2.getY());
	}

	/** Gibt eine Position an Stelle (0,0) zurück */
	public static Position ZERO() {
		return new Position(0, 0);
	}

	/**
	 * Gibt eine Position zurück, welche zwei Positionen miteinander multipliziert.
	 */
	public Position times(double l) {
		return new Position(getX() * l, getY() * l);
	}

	/**
	 * Überschreibt eine Methode von Object. Nur fürs debuggen gedacht, damit
	 * System.out.println(Position); eine richtige Position ausgibt
	 */
	@Override
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}
	public boolean equals(Position pos) {
		return (pos.getX() == x) && (pos.getY() == y);
	}
	/**
	 * Berechnet die Länge einer Position zum Ursprung
	 */
	public double length() {
		return Math.sqrt(getX() * getX() + getY() * getY());
	}
}

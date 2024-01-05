package Escape_Directory;

/**
 * Speichert die Informationen welche wichtig für Spieler und Spielerbewegungen
 * sind. Außerdem wurden Methoden implementiert, welche die Verarbeitung
 * vereinfacht.
 */
public class Player {
	private Position pos = new Position(0, 0);
	private Position vel = new Position(0, 0);
	private int maxvel = 0;
	private Position acc = new Position(0, 0);
	public boolean isInAir = true;
	public double timeSinceJump;

	public Player(Position pos, Position vel, int maxvel, Position acc, double t) {
		this.pos = pos;
		this.vel = vel;
		this.maxvel = maxvel;
		this.acc = acc;
		timeSinceJump = t;
	}

	public void calcNewPosition(double deltaTime) {
		pos = pos.plus(vel.times(deltaTime));
	}

	/**
	 * Berechnet die neue Geschwindigkeit welche abhängig von der bis zum letzten
	 * Frame vergangenen Zeit und der sich bewegenden Richtung des Spielers ist. Die
	 * Geschwindigkeit muss im Interval [-maxvel;maxvel] sein
	 */
	
	public void calcNewVel(String direction, double dT, double Time) {
		switch (direction) {
		case "LEFT":
			vel.setX((vel.getX() - acc.getX() * dT));
			break;
		case "RIGHT":
			vel.setX((vel.getX() + acc.getX() * dT));
			break;
		case "UP":
			if (!isInAir) {
				if(Time - timeSinceJump > 1000) {
					timeSinceJump = Time;
					vel.setY((vel.getY() - acc.getY()));
			}
			}

		}
		if (vel.getX() > maxvel) {
			vel.setX(maxvel);
		}
		if (vel.getX() < -maxvel) {
			vel.setX(-maxvel);
		}
	}

	public void velXToward0(double dT) {
		if (vel.getX() > 0) {
			vel.setX((vel.getX() - acc.getX() * dT * 3 / 4));

		} else if (vel.getX() < 0) {
			vel.setX((vel.getX() + acc.getX() * dT * 3 / 4));
		}
		double toleranz = 10;
		if (vel.getX() > -toleranz && vel.getX() < toleranz) {
			vel.setX(0);
		}
	}

	public Position getVel() {
		return vel;
	}

	public void setVel(Position vel) {
		this.vel = vel;
	}

	public int getMaxvel() {
		return maxvel;
	}

	public void setMaxvel(int maxvel) {
		this.maxvel = maxvel;
	}

	public Position getAcc() {
		return acc;
	}

	public void setAcc(Position acc) {
		this.acc = acc;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
}

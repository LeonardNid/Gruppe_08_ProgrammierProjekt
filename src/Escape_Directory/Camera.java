/*Hier wird die Position und Bewegung der Kamera berechnet**/
package Escape_Directory;

public class Camera {
	private double maxvel;
	private double acc;
	private Position vel = Position.ZERO();
	private Position pos;
	private Position savedPos;

	/* Konstruktor **/
	public Camera(double acc, double maxvel, Position pos) {
		this.pos = pos;
		this.savedPos = pos;
		this.maxvel = maxvel;
		this.acc = acc;
	}

	/** Wenn das Level restartet */
	public void restart() {
		pos = savedPos;
		vel = Position.ZERO();
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public void updateCamera(double deltaTime, Position goal) {
		calcNewVelocity(deltaTime, goal);
		calcNewPosition(deltaTime, goal);
	}

	private void calcNewVelocity(double dT, Position goal) {
		double g = goal.getX() - pos.getX();
		vel.setX(acc * g * g * g); // es wird f(g) = g^3 genutzt, da der Graph die gewollten Kurven hat: -g ->
									// negativ, +g -> positiv, g -> 0 -> 0
		if (vel.getX() > maxvel) {
			vel.setX(maxvel);
		} else if (vel.getX() < -maxvel) {
			vel.setX(-maxvel);
		}
		if (vel.getY() > maxvel) {
			vel.setY(maxvel);
		} else if (vel.getY() < -maxvel) {
			vel.setY(-maxvel);
		}
	}

	private void calcNewPosition(double dT, Position goal) {
		pos = pos.plus(vel.times(dT));
		if (pos.getX() == goal.getX()) {
			vel = Position.ZERO();
		}
	}
}

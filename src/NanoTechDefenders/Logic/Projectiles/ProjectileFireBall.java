package NanoTechDefenders.Logic.Projectiles;

import NanoTechDefenders.Logic.Soldier;

import java.awt.*;
import java.util.Random;

/**
 * Diese Klasse repräsentiert einen Feuerball als spezielle Form eines Projektils.
 */
public class ProjectileFireBall extends Projectile {
    private final int SPEED = 10;
    private double angle;
    private int xMovement, yMovement;
    private final int MAXFlightTime = 100;
    private int flightTime = MAXFlightTime;
    private final int MAXDamageTime = 2;
    private int damageTime = MAXDamageTime;

    /**
     * Konstruktor für den Feuerball.
     *
     * @param soldier         Der Soldat, der den Feuerball abfeuert.
     * @param initialLocation Die initiale Position des Ziels, zu dem der Feuerball fliegt.
     */
    public ProjectileFireBall(Soldier soldier, Point initialLocation) {
        super(soldier, new Point(initialLocation));
        super.setWidth(15);
        super.setHeight(15);

        calcMovement();
    }

    private void calcMovement() {
        // Vektor vom Projektil zum Ziel berechnen
        int dx = super.getEnemyLocation().x + 25 - super.getProjectileLocation().x; // +25 um auf das Zentrum des Ziels zu zentrieren
        int dy = super.getEnemyLocation().y + 25 - super.getProjectileLocation().y;

        // Winkel zum Ziel berechnen
        double angleToEnemy = Math.atan2(dy, dx);

        // Streuwinkel durch Hinzufügen einer zufälligen Abweichung zum Winkel einführen
        Random random = new Random();
        double maxSpreadAngle = Math.toRadians(30); // Passen Sie den Streuwinkel bei Bedarf an
        double randomAngleOffset = (random.nextDouble() * 2 - 1) * maxSpreadAngle;

        // Winkelabweichung anwenden
        double finalAngle = angleToEnemy + randomAngleOffset;
        this.angle = finalAngle;

        // Die neue Position des Projektils basierend auf dem angepassten Winkel berechnen
        double distance = Math.sqrt(dx * dx + dy * dy);
        double ratio = SPEED / distance;

        xMovement = (int) (ratio * distance * Math.cos(finalAngle));
        yMovement = (int) (ratio * distance * Math.sin(finalAngle));
    }

    /**
     * Bewegt den Feuerball und überprüft, ob er genügend Flugzeit hatte.
     */
    @Override
    public void moveProjectile() {
        super.getProjectileLocation().translate(xMovement, yMovement);
        if (flightTime >= 0) {
            --flightTime;
        } else {
            flightTime = MAXFlightTime;
            super.setFinished();
        }
    }

    /**
     * Gibt den Winkel des Feuerballs zurück.
     *
     * @return Der Winkel des Feuerballs in Grad.
     */
    @Override
    public double getAngle() {
        return Math.toDegrees(angle) + 90;
    }

    /**
     * Überprüft, ob der Feuerball bereit ist (Schaden anrichten kann).
     *
     * @return True, wenn der Feuerball bereit ist, sonst false.
     */
    @Override
    public boolean ready() {
        if (damageTime >= 0) {
            --damageTime;
        } else {
            damageTime = MAXDamageTime;
            return true;
        }
        return false;
    }

    /**
     * Gibt den Dateinamen für den Feuerball zurück.
     *
     * @return Der Dateiname für den Feuerball.
     */
    @Override
    public String getFileName() {
        return "NTD_FireBall.png";
    }
}

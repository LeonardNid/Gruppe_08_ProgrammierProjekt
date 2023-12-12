package NanoTechDefenders.Logic.Projectiles;

import NanoTechDefenders.Logic.Soldier;

import java.awt.*;

/**
 * Diese Klasse repräsentiert ein durchdringendes Projektil.
 */
public class ProjectilePierce extends Projectile {
    private final int SPEED = 20;
    private Point projectileLocation;

    /**
     * Konstruktor für das durchdringende Projektil.
     *
     * @param soldier       Der Soldat, der das Projektil abfeuert.
     * @param enemyLocation Die Position des Ziels, zu dem das Projektil fliegt.
     */
    public ProjectilePierce(Soldier soldier, Point enemyLocation) {
        super(soldier, new Point(enemyLocation));
        super.setWidth(30);
        super.setHeight(30);
    }

    /**
     * Bewegt das durchdringende Projektil in Richtung des Ziels.
     */
    @Override
    public void moveProjectile() {
        Point enemyLocation = super.getEnemyLocation();
        this.projectileLocation = super.getProjectileLocation();
        int dx = enemyLocation.x + 25 - projectileLocation.x; // +25 um Projektils Ziel in die Mitte der Spinne zu verschieben
        int dy = enemyLocation.y + 25 - projectileLocation.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double ratio = SPEED / distance;
        int moveX = (int) (ratio * dx);
        int moveY = (int) (ratio * dy);

        projectileLocation.translate(moveX, moveY);
        enemyLocation.translate(moveX, moveY);

        // Überprüfen, ob das Projektil die Grenzen überschreitet
        if (Math.abs(projectileLocation.x) > 3000 || Math.abs(projectileLocation.y) > 3000) {
            super.setFinished();
        }
    }

    /**
     * Gibt an, ob das durchdringende Projektil bereit ist (Schaden anrichten kann).
     *
     * @return True, da das durchdringende Projektil immer bereit ist.
     */
    @Override
    public boolean ready() {
        return true;
    }

    /**
     * Gibt den Dateinamen für das durchdringende Projektil zurück.
     *
     * @return Der Dateiname für das durchdringende Projektil.
     */
    @Override
    public String getFileName() {
        return "NTD_ProjectilePiercer.png";
    }
}

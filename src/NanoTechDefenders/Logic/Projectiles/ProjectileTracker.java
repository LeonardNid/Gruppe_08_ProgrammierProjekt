package NanoTechDefenders.Logic.Projectiles;

import NanoTechDefenders.Logic.Controlling.SoldierManager;
import NanoTechDefenders.Logic.Soldier;

import java.awt.*;

/**
 * Diese Klasse repräsentiert ein Projektil, das einem Gegner folgt und nach einer bestimmten Zeit detonierte.
 */
public class ProjectileTracker extends Projectile {
    private final int MAXtimeTillDetonation = 150;
    private int timeTillDetonation = MAXtimeTillDetonation;
    private Soldier soldier;
    private Point enemyLocation;
    private boolean onEnemy;

    /**
     * Konstruktor für das nachverfolgende Projektil.
     *
     * @param soldier       Der Soldat, der das Projektil abfeuert.
     * @param enemyLocation Die Position des Ziels, zu dem das Projektil fliegt.
     */
    public ProjectileTracker(Soldier soldier, Point enemyLocation) {
        super(soldier, enemyLocation);
        this.enemyLocation = enemyLocation;
        this.soldier = soldier;
        super.setWidth(50);
        super.setHeight(50);
    }

    /**
     * Bewegt das nachverfolgende Projektil in Richtung des Ziels und überprüft die Zeit bis zur Detonation.
     */
    @Override
    public void moveProjectile() {
        if (!onEnemy) {
            super.moveProjectile();
        }
        --timeTillDetonation;
        if (timeTillDetonation <= 0) {
            ready();
        }
    }

    /**
     * Überprüft die Distanz zum Ziel und markiert das Projektil als auf dem Ziel, wenn die Distanz sehr klein ist.
     *
     * @param distance Die Distanz zwischen Projektil und Ziel.
     */
    @Override
    public void checkDistance(double distance) {
        if (distance < 10) {
            onEnemy = true;
            setProjectileLocation(enemyLocation);
        }
    }

    /**
     * Markiert das Projektil als bereit und detonierte es, indem ein neues Projektil erstellt wird.
     *
     * @return True, wenn das Projektil bereit ist, sonst false.
     */
    @Override
    public boolean ready() {
        if (timeTillDetonation <= 0) {
            timeTillDetonation = MAXtimeTillDetonation;
            super.setFinished();
            SoldierManager.createBeamProjectile(soldier, enemyLocation);
            return true;
        }
        return false;
    }

    /**
     * Gibt den Dateinamen für das nachverfolgende Projektil zurück.
     *
     * @return Der Dateiname für das nachverfolgende Projektil.
     */
    @Override
    public String getFileName() {
        return "NTD_CrossHair.png";
    }

    /**
     * Gibt den Winkel des nachverfolgenden Projektils zurück.
     *
     * @return Der Winkel des nachverfolgenden Projektils (immer 0).
     */
    @Override
    public double getAngle() {
        return 0;
    }
}

package NanoTechDefenders.Logic.Projectiles;

import NanoTechDefenders.Logic.Controlling.LogicController;
import NanoTechDefenders.Logic.Soldier;

import java.awt.*;

/**
 * Diese Klasse repräsentiert einen Laserstrahl als spezielle Form eines Projektils.
 */
public class ProjectileBeam extends Projectile {
    private final int WIDTH = 100, HEIGHT = (int) (900 * LogicController.getYFactor());
    private final int MaxRotationTime = 200;
    private int rotationTime = MaxRotationTime;
    private final int MaxDamageTime = 3;
    private int damageTime = MaxDamageTime;

    /**
     * Konstruktor für den Laserstrahl.
     *
     * @param soldier       Der Soldat, der den Laserstrahl abfeuert.
     * @param enemyLocation Die Position des Ziels, zu dem der Laserstrahl fliegt.
     */
    public ProjectileBeam(Soldier soldier, Point enemyLocation) {
        super(soldier, enemyLocation);
        super.setProjectileLocation(new Point((int) enemyLocation.getX() - WIDTH / 2 + 25, 0));
        super.setWidth(WIDTH);
        super.setHeight(HEIGHT);
    }

    /**
     * Überprüft, ob der Laserstrahl bereit ist (Schaden anrichten kann).
     *
     * @return True, wenn der Laserstrahl bereit ist, sonst false.
     */
    @Override
    public boolean ready() {
        --damageTime;
        if (damageTime <= 0) {
            damageTime = MaxDamageTime;
            return true;
        }
        return false;
    }

    /**
     * Bewegt den Laserstrahl und überprüft, ob er sich genügend gedreht hat.
     */
    @Override
    public void moveProjectile() {
        --rotationTime;
        if (rotationTime <= 0) {
            super.setFinished();
            rotationTime = MaxRotationTime;
        }
    }

    /**
     * Gibt den Winkel des Laserstrahls zurück (immer 0, da sich der Laserstrahl nicht dreht).
     *
     * @return Der Winkel des Laserstrahls.
     */
    @Override
    public double getAngle() {
        return 0;
    }

    /**
     * Gibt den Dateinamen für den Laserstrahl zurück.
     *
     * @return Der Dateiname für den Laserstrahl.
     */
    @Override
    public String getFileName() {
        return "array";
    }

    /**
     * Gibt den Dateinamen als Array zurück, falls der Laserstrahl animiert ist.
     *
     * @return Das Array der Dateinamen für die Animation des Laserstrahls.
     */
    @Override
    public String[] getFileNameArray() {
        return new String[]{"NTD_LaserBeamProjectile.png", "NTD_LaserBeamProjectile2.png"};
    }
}

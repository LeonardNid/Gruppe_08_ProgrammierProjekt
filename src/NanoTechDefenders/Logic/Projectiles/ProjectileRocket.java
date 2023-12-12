package NanoTechDefenders.Logic.Projectiles;

import NanoTechDefenders.Logic.Soldier;

import java.awt.*;

/**
 * Diese Klasse repräsentiert eine Rakete als spezielle Form eines Projektils.
 */
public class ProjectileRocket extends Projectile {
    private String fileName = "NTD_BulletRocket.png";

    /**
     * Konstruktor für die Rakete.
     *
     * @param soldier       Der Soldat, der die Rakete abfeuert.
     * @param enemyLocation Die Position des Ziels, zu dem die Rakete fliegt.
     */
    public ProjectileRocket(Soldier soldier, Point enemyLocation) {
        super(soldier, enemyLocation);
        super.setSPEED(20);
        super.setWidth(30);
        super.setHeight(30);
    }

    /**
     * Gibt den Dateinamen für die Rakete zurück.
     *
     * @return Der Dateiname für die Rakete.
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * Gibt den Schaden der Rakete zurück.
     *
     * @return Der Schaden der Rakete.
     */
    @Override
    public int getDamage() {
        return 45;
    }

    /**
     * Markiert die Rakete als bereit, löst den On-Hit-Effekt aus, ändert den Dateinamen für die Explosion und setzt das Projektil als beendet.
     *
     * @return True, da die Rakete immer bereit ist.
     */
    @Override
    public boolean ready() {
        super.setOnHitEffekt(true);
        fileName = "NTD_explosion.png";
        super.setFinished();
        return true;
    }
}

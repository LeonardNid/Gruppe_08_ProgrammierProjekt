package NanoTechDefenders.Logic.Controlling;

import NanoTechDefenders.Help.Soldiers;
import NanoTechDefenders.Logic.Projectiles.*;
import NanoTechDefenders.Logic.Soldier;
import NanoTechDefenders.Sound.SoundController;

import java.awt.*;

/**
 * Die SoldierManager-Klasse enthält Hilfsmethoden für die Verwaltung von Soldaten und deren Projektilen.
 */
public class SoldierManager {

    /**
     * Gibt die maximale Nachladezeit für die angegebene Soldatenvariante zurück.
     *
     * @param soldierVariant Die ausgewählte Soldatenvariante.
     * @return Die maximale Nachladezeit der Soldatenvariante.
     */
    public static int getMaxReloadTime(Soldiers soldierVariant) {
        return soldierVariant.getMaxReloadTime();
    }

    /**
     * Gibt den Dateinamen für die angegebene Soldatenvariante zurück.
     *
     * @param soldierVariant Die ausgewählte Soldatenvariante.
     * @return Der Dateiname der Soldatenvariante.
     */
    public static String getFileName(Soldiers soldierVariant) {
        return soldierVariant.getFileName();
    }

    /**
     * Erstellt ein Projektil für den angegebenen Soldaten basierend auf seiner Variante und der Position des Gegners.
     * Spielt auch den entsprechenden Soundeffekt ab.
     *
     * @param soldier       Der Soldat, der das Projektil erstellt.
     * @param enemyLocation Die Position des Gegners, auf den geschossen wird.
     */
    public static void createProjectile(Soldier soldier, Point enemyLocation) {
        switch (soldier.getVariant()) {
            case NORMAL -> {
                LogicController.createProjectile(new Projectile(soldier, enemyLocation));
                SoundController.playNormal();
            }
            case PIERCER -> {
                LogicController.createProjectile(new ProjectilePierce(soldier, enemyLocation));
                SoundController.playPiercer();
            }
            case SNIPER -> {
                LogicController.createProjectile(new ProjectileRocket(soldier, enemyLocation));
                SoundController.playSniper();
            }
            case FLAMETHROWER -> {
                LogicController.createProjectile(new ProjectileFireBall(soldier, enemyLocation));
                SoundController.playFlameThrower();
            }
            case BEAM -> {
                ProjectileTracker projectileTracker = new ProjectileTracker(soldier, enemyLocation);
                LogicController.createProjectile(projectileTracker);
            }
        }
    }

    /**
     * Erstellt ein Strahlprojektil für den angegebenen Soldaten und die Position des Gegners.
     * Spielt auch den entsprechenden Soundeffekt ab.
     *
     * @param soldier       Der Soldat, der das Projektil erstellt.
     * @param enemyLocation Die Position des Gegners, auf den geschossen wird.
     */
    public static void createBeamProjectile(Soldier soldier, Point enemyLocation) {
        SoundController.playBeam();
        LogicController.createProjectile(new ProjectileBeam(soldier, enemyLocation));
    }
}

package NanoTechDefenders.Logic;

import NanoTechDefenders.GUI.Controlling.GUIController;
import NanoTechDefenders.Help.Soldiers;
import NanoTechDefenders.Logic.Controlling.SoldierManager;

import java.awt.*;

/**
 * Die Klasse Soldier repräsentiert einen Soldaten im Spiel.
 */
public class Soldier {
    private Point location;
    private int MAXRELOADTIME = 5;
    private int reloadTime = MAXRELOADTIME; // 5 Ticks
    private double angle;
    private Soldiers variant;
    private int enemyView;
    private String filename;
    private volatile int damageDone = 0;
    private volatile int kills = 0;

    /**
     * Konstruktor für einen Soldaten.
     *
     * @param location       Die Startposition des Soldaten.
     * @param soldierVariant Die Variant des Soldaten.
     */
    public Soldier(Point location, Soldiers soldierVariant) {
        this.location = location;
        this.variant = soldierVariant;
        setUpSoldier();
    }

    private void setUpSoldier() {
        enemyView = variant.getEnemyView();
        MAXRELOADTIME = SoldierManager.getMaxReloadTime(variant);
        filename = SoldierManager.getFileName(variant);
    }

    /**
     * Gibt die aktuelle Position des Soldaten zurück.
     *
     * @return Die Position des Soldaten als Point-Objekt.
     */
    public Point getlocation() {
        return location;
    }

    /**
     * Überprüft, ob der Soldat auf den Gegner schießen kann, basierend auf der Distanz und der Wiederladezeit.
     *
     * @param locationOfEnemy Die Position des Gegners als Point-Objekt.
     * @return True, wenn der Soldat schießen kann, sonst false.
     */
    public boolean canShoot(Point locationOfEnemy) {
        if (checkDistance(locationOfEnemy) && reloading()) {
            return true;
        }
        return false;
    }

    private boolean checkDistance(Point locationOfEnemy) {
        return enemyView * GUIController.getXFactor() >
                Math.sqrt(Math.pow(locationOfEnemy.x + 25 - location.x, 2) + Math.pow(locationOfEnemy.y + 25 - location.y, 2));
    }

    private boolean reloading() {
        if (reloadTime <= 0) {
            reloadTime = MAXRELOADTIME;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verringert die Wiederladezeit des Soldaten.
     */
    public void reload() {
        --reloadTime;
    }

    /**
     * Berechnet den Winkel zwischen dem Soldaten und dem Gegner.
     *
     * @param enemyLocation Die Position des Gegners als Point-Objekt.
     */
    public void calcAngle(Point enemyLocation) {
        angle = Math.atan2(enemyLocation.getY() - this.location.getY(), enemyLocation.getX() - this.location.getX());
        angle = Math.toDegrees(angle) + 90;
    }

    /**
     * Gibt den aktuellen Winkel des Soldaten zurück.
     *
     * @return Der Winkel des Soldaten.
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Gibt die Variant des Soldaten zurück.
     *
     * @return Die Variant des Soldaten.
     */
    public Soldiers getVariant() {
        return variant;
    }

    /**
     * Gibt den Dateinamen des Soldatenbildes zurück.
     *
     * @return Der Dateiname des Soldatenbildes.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Zählt den verursachten Schaden und die Anzahl der erzielten Kills.
     *
     * @param damage      Der verursachte Schaden.
     * @param enemyLifes  Die Lebenspunkte des Gegners.
     */
    public void countDamage(int damage, int enemyLifes) {
        damageDone += damage;
        if (enemyLifes <= 0) {
            ++kills;
            damageDone += enemyLifes;
        }
    }

    /**
     * Gibt den verursachten Gesamtschaden des Soldaten zurück.
     *
     * @return Der verursachte Gesamtschaden des Soldaten.
     */
    public int getDamageDone() {
        return damageDone;
    }

    /**
     * Gibt die Anzahl der erzielten Kills des Soldaten zurück.
     *
     * @return Die Anzahl der erzielten Kills des Soldaten.
     */
    public int getKills() {
        return kills;
    }
}

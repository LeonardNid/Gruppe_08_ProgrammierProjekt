package NanoTechDefenders.Logic.Projectiles;

import NanoTechDefenders.Logic.Soldier;
import java.awt.*;

/**
 * Die Klasse repräsentiert ein Projektil, das von einem Soldaten abgefeuert wird.
 */
public class Projectile {
    private int width = 15, height = 15;
    private int SPEED = 10;
    private Soldier logicSoldier;
    private Point projectileLocation;
    private Point enemyLocation;
    private double angle;
    private boolean finished;
    private int onHitEffectTime = -1;

    /**
     * Konstruktor für das Projektil.
     *
     * @param soldier        Der Soldat, der das Projektil abfeuert.
     * @param enemyLocation  Die Position des Ziels, zu dem das Projektil fliegt.
     */
    public Projectile(Soldier soldier, Point enemyLocation) {
        this.logicSoldier = soldier;
        this.projectileLocation = new Point(soldier.getlocation());
        this.projectileLocation.translate(25, 25); // Um die Position von oben links in die Mitte zu verschieben
        this.enemyLocation = enemyLocation;
    }

    /**
     * Bewegt das Projektil in Richtung des Ziels.
     */
    public void moveProjectile() {
        int dx = enemyLocation.x + 25 - projectileLocation.x;
        int dy = enemyLocation.y + 25 - projectileLocation.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double ratio = SPEED / distance;
        int moveX = (int) (ratio * dx);
        int moveY = (int) (ratio * dy);

        projectileLocation.translate(moveX, moveY);

        checkDistance(distance);
    }

    /**
     * Überprüft die Distanz zum Ziel und markiert das Projektil als abgeschlossen, wenn es das Ziel erreicht hat.
     *
     * @param distance Die Distanz zwischen Projektil und Ziel.
     */
    protected void checkDistance(double distance) {
        if (distance < SPEED) {
            setFinished();
        }
    }

    /**
     * Gibt an, ob das Projektil bereit ist (beendet).
     *
     * @return True, wenn das Projektil beendet ist, sonst false.
     */
    public boolean ready() {
        setFinished();
        return true;
    }

    /**
     * Zählt den Schaden basierend auf der Anzahl der Lebenspunkte des Ziels.
     *
     * @param enemyLifes Die aktuellen Lebenspunkte des Ziels.
     */
    public void countDamage(int enemyLifes) {
        logicSoldier.countDamage(getDamage(), enemyLifes);
    }

    /**
     * Gibt den aktuellen Status des Projektils zurück.
     *
     * @return True, wenn das Projektil beendet ist, sonst false.
     */
    public boolean getStatus() {
        return finished;
    }

    /**
     * Gibt die aktuelle Position des Projektils zurück.
     *
     * @return Die Position des Projektils.
     */
    public Point getLocation() {
        return projectileLocation;
    }

    /**
     * Berechnet den Winkel zwischen dem Projektil und dem Ziel.
     *
     * @return Der Winkel in Grad.
     */
    public double getAngle() {
        angle = Math.atan2(enemyLocation.getY() + 25 - projectileLocation.getY(), enemyLocation.getX() + 25 - projectileLocation.getX());
        angle = Math.toDegrees(angle) + 90;
        return angle;
    }

    /**
     * Gibt den verursachten Schaden des Projektils zurück.
     *
     * @return Der Schaden des Projektils.
     */
    public int getDamage() {
        return 2;
    }

    /**
     * Gibt den Dateinamen des Bilds für das Projektil zurück.
     *
     * @return Der Dateiname des Bilds.
     */
    public String getFileName() {
        return "NTD_Projektil.png";
    }

    /**
     * Gibt den Dateinamen als Array zurück, falls das Projektil animiert ist. Andernfalls null.
     *
     * @return Das Array der Dateinamen oder null.
     */
    public String[] getFileNameArray() {
        return null;
    }

    /**
     * Gibt das Begrenzungsrechteck des Projektils zurück.
     *
     * @return Das Begrenzungsrechteck des Projektils.
     */
    public Rectangle getRectangle() {
        return new Rectangle(projectileLocation, new Dimension(width, height));
    }

    /**
     * Gibt die Breite des Projektils zurück.
     *
     * @return Die Breite des Projektils.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gibt die Höhe des Projektils zurück.
     *
     * @return Die Höhe des Projektils.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gibt die aktuelle Position des Projektils zurück.
     *
     * @return Die Position des Projektils.
     */
    protected Point getProjectileLocation() {
        return projectileLocation;
    }

    /**
     * Gibt die Position des Ziels zurück.
     *
     * @return Die Position des Ziels.
     */
    protected Point getEnemyLocation() {
        return enemyLocation;
    }

    /**
     * Überprüft, ob der On-Hit-Effekt noch aktiv ist und aktualisiert die Zeit.
     *
     * @return True, wenn der On-Hit-Effekt noch aktiv ist, sonst false.
     */
    public boolean getOnHitEffect() {
        --onHitEffectTime;
        return onHitEffectTime >= 0;
    }

    /**
     * Setzt den On-Hit-Effekt basierend auf dem übergebenen Wert.
     *
     * @param onHitEffect True, wenn der On-Hit-Effekt aktiviert werden soll, sonst false.
     */
    public void setOnHitEffekt(boolean onHitEffect) {
        if (!onHitEffect) {
            this.onHitEffectTime = -1;
        } else {
            this.onHitEffectTime = 25;
        }
    }

    /**
     * Setzt die Breite des Projektils.
     *
     * @param width Die neue Breite des Projektils.
     */
    protected void setWidth(int width) {
        this.width = width;
    }

    /**
     * Setzt die Höhe des Projektils.
     *
     * @param height Die neue Höhe des Projektils.
     */
    protected void setHeight(int height) {
        this.height = height;
    }

    /**
     * Markiert das Projektil als beendet.
     */
    protected void setFinished() {
        this.finished = true;
    }

    /**
     * Setzt die Geschwindigkeit des Projektils.
     *
     * @param speed Die neue Geschwindigkeit des Projektils.
     */
    protected void setSPEED(int speed) {
        this.SPEED = speed;
    }

    /**
     * Setzt die Position des Projektils.
     *
     * @param projectileLocation Die neue Position des Projektils.
     */
    protected void setProjectileLocation(Point projectileLocation) {
        this.projectileLocation = projectileLocation;
    }
}

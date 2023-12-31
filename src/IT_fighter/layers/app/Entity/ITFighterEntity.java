package IT_fighter.layers.app.Entity;

import java.awt.geom.Rectangle2D;

/**
 * Abstrakte Klasse die ein Gerüst darstellt für alle Komponente des Spiels, die sich bewegen.
 */
public abstract class ITFighterEntity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    /**
     *
     * @param x X-Koordinate obere linke Ecke
     * @param y Y-Koordinate obere linke Ecke
     * @param width Breite der Entity
     * @param height Höhe der Entity
     */
    public ITFighterEntity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Die Hitbox ist der Bereich, in dem die Entität eine Kollision hat,
     * in der Regel etwas kleiner als das Bilder Entität
     * @param x X-Koordinate obere linke Ecke
     * @param y Y-Koordinate obere linke Ecke
     * @param width Breite der Hitbox
     * @param height Höhe der Hitbox
     */
    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    /**
     * @return gibt die Hitbox der Entität zurück
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

}

package IT_fighter.layers.app.Entity;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class ITFighterEntity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    public ITFighterEntity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    protected void drawHitbox(Graphics g) {
        // For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);

    }
    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    /*protected void setHitbox(Float x, Float y) {
        hitbox.x = x;
        hitbox.y = y;
    }*/

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

}

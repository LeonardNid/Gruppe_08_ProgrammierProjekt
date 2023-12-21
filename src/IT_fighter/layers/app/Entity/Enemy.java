package IT_fighter.layers.app.Entity;

import IT_fighter.layers.app.Game;

import static IT_fighter.utilities.UtilMethods.canMoveHere;

public abstract class Enemy extends Entity {
    private float xDrawOffset;
    private float yDrawOffset;
    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    public int getX() {
        return (int) (hitbox.x - xDrawOffset);
    }
    public int getY() {
        return (int) (hitbox.y - yDrawOffset);
    }

    public float getxDrawOffset() {
        return xDrawOffset;
    }

    public void setxDrawOffset(float xDrawOffset) {
        this.xDrawOffset = xDrawOffset;
    }

    public float getyDrawOffset() {
        return yDrawOffset;
    }

    public void setyDrawOffset(float yDrawOffset) {
        this.yDrawOffset = yDrawOffset;
    }
}

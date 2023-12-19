package IT_fighter.layers.app.Entity;

import IT_fighter.layers.app.Game;

import static IT_fighter.utilities.UtilMethods.canMoveHere;

public abstract class Enemy extends Entity {
    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    public void update() {

    }

    public abstract boolean updatePosition();
}

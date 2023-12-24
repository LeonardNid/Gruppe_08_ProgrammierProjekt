package IT_fighter.layers.app.Entity;

import IT_fighter.layers.app.Game;
import IT_fighter.layers.app.ITFighterAppController;

import static IT_fighter.utilities.UtilMethods.canMoveHere;
import static IT_fighter.utilities.UtilMethods.colisionWithPlayer;

public abstract class Enemy extends Entity {
    private float ySpeed;
    private float xSpeed;
    private float xDrawOffset;
    private float yDrawOffset;
    public Enemy(float x, float y, int width, int height, float xSpeed, float ySpeed) {
        super(x, y, width, height);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public boolean updatePosition() {
        if(canMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, Game.levelOneData)) {
            if(colisionWithPlayer(ITFighterAppController.getInstance().getCurrentPlayerPosition(), hitbox)) {
                killPlayer();
                return false;
            }
            hitbox.y += ySpeed;
            hitbox.x += xSpeed;
            return true;
        }
        return false;
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

    public void killPlayer() {
        ITFighterAppController.getInstance().killPlayer();
    }
    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
}
package IT_fighter.layers.app.Entity;

import static IT_fighter.utilities.UtilMethods.canMoveHere;
import static IT_fighter.utilities.UtilMethods.colisionWithPlayer;

import IT_fighter.layers.app.EnemyManager;
import IT_fighter.layers.app.Game;
import IT_fighter.layers.app.ITFighterAppController;


public class Virus extends Enemy{
    private float ySpeed = 2.0f;

    public Virus(float x, float y, int width, int height) {
        super(x,y,width,height);
        initHitbox(x,y,(int) (24* Game.SCALE), (int)(24* Game.SCALE));//x = 24,y = 24 Größe der Hitbox
        setxDrawOffset(2 * Game.SCALE);
        setyDrawOffset(4 * Game.SCALE);
    }


    /**
     * if the return value is false the Virus has to be deleted
     *
     * */
    public boolean updatePosition() {
        if(canMoveHere(hitbox.x, hitbox.y + ySpeed, hitbox.width, hitbox.height, Game.levelOneData)) {
            if(colisionWithPlayer(ITFighterAppController.getInstance().getCurrentPlayerPosition(), hitbox)) {
                killPlayer();
                return false;
            }
            hitbox.y += ySpeed;
            return true;
        }
        return false;
    }

    public void killPlayer() {

    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
//TODO virus implementieren

}

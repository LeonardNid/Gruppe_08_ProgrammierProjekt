package IT_fighter.layers.app.Entity;

import static IT_fighter.utilities.UtilMethods.canMoveHere;
import static IT_fighter.utilities.UtilMethods.colisionWithPlayer;

import IT_fighter.layers.app.EnemyManager;
import IT_fighter.layers.app.Game;
import IT_fighter.layers.app.ITFighterAppController;


public class Virus extends Enemy{

    public Virus(float x, float y, int width, int height) {
        super(x,y,width,height,0, 2.0f);
        initHitbox(x,y,(int) (24* Game.SCALE), (int)(24* Game.SCALE));//x = 24,y = 24 Größe der Hitbox
        setxDrawOffset(2 * Game.SCALE);
        setyDrawOffset(4 * Game.SCALE);
    }
}

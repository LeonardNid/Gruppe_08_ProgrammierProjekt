package IT_fighter.layers.app.Entity;

import IT_fighter.layers.app.Game;
import IT_fighter.layers.app.ITFighterAppController;

import static IT_fighter.utilities.UtilMethods.canMoveHere;
import static IT_fighter.utilities.UtilMethods.colisionWithPlayer;

public class BinaryCode extends Enemy {
    public BinaryCode(float x, float y, int width, int height) {
        super(x,y,width,height, -2.0f, 0);
        initHitbox(x,y,(int) (56* Game.SCALE), (int)(28* Game.SCALE));//x = 28,y = 56 Größe der Hitbox
        setxDrawOffset(2 * Game.SCALE);
        setyDrawOffset(4 * Game.SCALE);
    }
}

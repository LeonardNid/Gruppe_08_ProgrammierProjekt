package IT_fighter.layers.app.Entity;

import IT_fighter.layers.app.ITFighterGame;


public class ITFighterVirus extends ITFighterEnemy {

    public ITFighterVirus(float x, float y, int width, int height) {
        super(x,y,width,height,0, 2.0f);
        initHitbox(x,y,(int) (24* ITFighterGame.SCALE), (int)(24* ITFighterGame.SCALE));//x = 24,y = 24 Größe der Hitbox
        setxDrawOffset(2 * ITFighterGame.SCALE);
        setyDrawOffset(4 * ITFighterGame.SCALE);
    }
}

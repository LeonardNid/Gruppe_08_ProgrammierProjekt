package IT_fighter.layers.app.Entity;

import IT_fighter.layers.app.ITFighterGame;

/**
 * stellt die Logik des Gegners BinärCode dar, der durch eine Kanone im Spiel verschossen wird.
 */
public class ITFighterBinaryCode extends ITFighterEnemy {
    /**
     * Konstruktor des Binär Code Gegners
     * @param x X-Koordinate der oberen linken Ecke des BinärCodes
     * @param y Y-Koordinate der oberen linken Ecke des BinärCodes
     * @param width breite eines BinärCodes
     * @param height Höhe eines BinärCodes
     */
    public ITFighterBinaryCode(float x, float y, int width, int height) {
        super(x,y,width,height, -2.0f, 0);
        initHitbox(x,y,(int) (56* ITFighterGame.SCALE), (int)(28* ITFighterGame.SCALE));//x = 28,y = 56 Größe der Hitbox
        setxDrawOffset(2 * ITFighterGame.SCALE);
        setyDrawOffset(4 * ITFighterGame.SCALE);
    }
}

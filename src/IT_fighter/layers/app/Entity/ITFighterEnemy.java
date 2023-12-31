package IT_fighter.layers.app.Entity;

import IT_fighter.layers.app.ITFighterGame;
import IT_fighter.layers.app.ITFighterAppController;

import static IT_fighter.utilities.UtilMethods.canMoveHere;
import static IT_fighter.utilities.UtilMethods.colisionWithPlayer;

/**
 * Der Enemy stellt eine abstrakte Klasse dar, die als Grundgerüst für alles Gegner des Spiels dient
 */
public abstract class ITFighterEnemy extends ITFighterEntity {
    private final float ySpeed;
    private final float xSpeed;
    private float xDrawOffset;
    private float yDrawOffset;
    public ITFighterEnemy(float x, float y, int width, int height, float xSpeed, float ySpeed) {
        super(x, y, width, height);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    /**
     * Die Methode wird 120-mal pro Sekunde durch die GameLoop aufgerufen.
     * Kollidiert der Enemy mit dem Spieler wird die killPlayer() Methode aufgerufen und false zurückgegeben.
     * @return gibt true zurück, wenn der Enemy sich an die Position in der CanMoveHere Methode bewegen kann false, wenn
     * nicht
     */
    public boolean updatePosition() {
        if(canMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, ITFighterGame.levelOneData)) {
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

    /**
     *
     * @return gibt die x-Koordinate abzüglich des zu zeichnenden Offsets zurück
     */
    public int getX() {
        return (int) (hitbox.x - xDrawOffset);
    }

    /**
     *
     * @return gibt die y-Koordinate abzüglich des zu zeichnenden Offsets zurück
     */
    public int getY() {
        return (int) (hitbox.y - yDrawOffset);
    }

    /**
     * @param xDrawOffset ist der Wert, um den die Position verschoben werden muss, damit der Enemy
     *                    sich im GamePanel befindet
     */
    public void setxDrawOffset(float xDrawOffset) {
        this.xDrawOffset = xDrawOffset;
    }

    /**
     * @param yDrawOffset ist der Wert, um den die Position verschoben werden muss, damit der Enemy
     *                    sich im GamePanel befindet
     */
    public void setyDrawOffset(float yDrawOffset) {
        this.yDrawOffset = yDrawOffset;
    }

    /**
     * tötet die Spielfigur und beendet das Level
     */
    public void killPlayer() {
        ITFighterAppController.getInstance().killPlayer();
    }

}
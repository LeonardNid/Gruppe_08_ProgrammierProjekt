package IT_fighter.layers.app.Entity;

import IT_fighter.layers.app.ITFighterGame;
import IT_fighter.layers.app.ITFighterAppController;

import static IT_fighter.utilities.UtilMethods.*;

import static IT_fighter.utilities.Constants.PlayerConstants.IDLE;
import static IT_fighter.utilities.Constants.PlayerConstants.RUNING;

/**
 * Der Character stellt die Logik der Spielfigur dar
 */
public class ITFighterCharacter extends ITFighterEntity {
    private float playerSpeed = 2.0f;
    private int action = IDLE;
    private boolean moving = false;
    private boolean left, right, jump;
    //Pixelanzahl von 0,0 bis dahin wo Hitbox anfängt
    private final float xDrawOffset = 9 * ITFighterGame.SCALE;
    private final float yDrawOffset = 6 * ITFighterGame.SCALE;
    //##################################################################################################################
    //Variablen für Schwerkraft der Spielfigur
    private float ySpeed = 0f;
    private final float gravity = 0.04f * ITFighterGame.SCALE;//0.04
    private final float jumpSpeed = -2.25f * ITFighterGame.SCALE;
    private boolean inAir = false;
    //wird benötigt, wenn im Sprung eine Kollision auftritt
    private final float fallSpeed = 0.5f * ITFighterGame.SCALE;
    //##################################################################################################################

    /**
     * Konstruktor der Spielfigur
     * @param x X-Koordinate der linken oberen Ecke der Spielfigur
     * @param y Y-Koordinate der linken oberen Ecke der Spielfigur
     * @param width Breite der Spielfigur
     * @param height Höhe der Spielfigur
     */
    public ITFighterCharacter(float x, float y, int width, int height) {
        super(x, y, width, height);
        initHitbox(x, y, (int) (12 * ITFighterGame.SCALE), (int) (24 * ITFighterGame.SCALE)); //x = 12,y = 24 Größe der Hitbox
    }
    //##################################################################################################################
    //getter und setter

    /**
     * @return gibt die Aktion zurück, in der sich die Spielfigur befindet z.B. laufen oder springen
     */
    public int getAction() {
        return this.action;
    }

    /**
     * @return gibt die X-Koordinate der Spielfigur zurück
     */
    public int getX() {
        return (int) (hitbox.x-xDrawOffset);
    }

    /**
     * @return gibt die Y-Koordinate der Spielfigur zurück
     */
    public int getY() {
        return (int) (hitbox.y-yDrawOffset);
    }

    /**
     * @param left true, wenn die Spielfigur sich nach links bewegen soll
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * @param right true, wenn die Spielfigur sich nach rechts bewegen soll
     */
    public void setRight(boolean right) {
        this.right = right;
    }
    /**
     * @param playerSpeed sollte zwischen 0.5f und 2.0f liegen, damit das Spiel spielbar bleibt
     */
    public void setPlayerSpeed(Float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    /**
     * @param jump true, die Spielfigur springen soll
     */
    public void setJump(boolean jump) {
        this.jump = jump;
    }

    //##################################################################################################################
    //update Methods

    /**
     * wird von der GameLoop 200-mal pro Sekunde aufgerufen und updated die Position
     * der Spielfigur und die Aktion
     */
    public void update() {
        updatePlayerPosition();
        setAction();
    }
    //müsste eigentlich setActionByValueOfMoving heißen

    /**
     * setzt die Aktion, in der sich die Spielfigur befindet in abhängigkeit von der Richtung in die
     * sie sich bewegt
     */
    private void setAction() {
        int startAction = action;
        if (moving) {
            action = RUNING;
        } else {action = IDLE;}
        if (startAction != action) {
            ITFighterAppController.getInstance().getCurrentGameController().setAnimationIndex();
        }
    }

    /**
     * verändert die Position in abhängigkeit der Wahrheitswerte von left, right und jump, welche durch den
     * PlayerKeyHandler manipuliert werden
     */
    public void updatePlayerPosition() {
        moving = false;
        float xSpeed = 0;
        if (jump) {
            if (!inAir) {
                inAir = true;
                ySpeed = jumpSpeed;
            }
        }
        if(!inAir) {
            if ((!left && !right) || (left && right)) {
                return;
            }
        }
        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        }
        if(!inAir) {
            if (!entityOnFloor(hitbox, ITFighterGame.levelOneData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y+ySpeed, hitbox.width, hitbox.height, ITFighterGame.levelOneData)) {
                //Spielfigur kollidiert mit keinem anderen Objekt beim Sprung
                hitbox.y += this.ySpeed;
                ySpeed += gravity;
                updateXPos(xSpeed);

            } else {
                hitbox.y = entityYPosNotUpOrDown(hitbox, this.ySpeed);
                if (ySpeed > 0) { //Spieler fällt, muss also mit dem Boden kollidieren
                    resetInAir();
                } else { //Spieler berührt ein Objekt mit Kollision in der Luft
                    ySpeed = fallSpeed;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    /**
     * setzt das Verhalten der Spielfigur zurück auf eine auf dem Boden befindliche Position
     */
    private void resetInAir() {
        inAir = false;
        ySpeed = 0;
    }

    /**
     * updated die X-Koordinate der Spielfigur
     * @param xSpeed Geschwindigkeit der Spielfigur in X-Richtung
     */
    private void updateXPos(float xSpeed) {
        if(canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, ITFighterGame.levelOneData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = entityNextToWallPosX(hitbox, xSpeed);
        }
    }
}
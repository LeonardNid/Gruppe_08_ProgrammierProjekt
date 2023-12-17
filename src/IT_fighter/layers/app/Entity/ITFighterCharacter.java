package IT_fighter.layers.app.Entity;

import IT_fighter.layers.app.Game;
import IT_fighter.layers.app.ITFighterAppController;

import static IT_fighter.utilities.UtilMethods.*;

import static IT_fighter.utilities.Constants.PlayerConstants.IDLE;
import static IT_fighter.utilities.Constants.PlayerConstants.RUNING;

public class ITFighterCharacter extends Entity {
    private float playerSpeed = 2.0f;
    private int action = IDLE;
    private boolean moving = false;
    private boolean left, up, right, down, jump;
    //Pixelanzahl von 0,0 bis dahin wo Hitbox anfängt
    private float xDrawOffset = 9 * Game.SCALE;
    private float yDrawOffset = 6 * Game.SCALE;
    //##################################################################################################################
    //Variablen für Schwerkraft der Spielfigur
    private float ySpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;//0.04
    private float jumpSpeed = -2.25f * Game.SCALE;
    private boolean inAir = false;
    //wird benötigt, wenn im Sprung eine Kollision auftritt
    private float fallSpeed = 0.5f * Game.SCALE;
    //##################################################################################################################
    public ITFighterCharacter(float x, float y, int width, int height) {
        super(x, y, width, height);
        initHitbox(x, y, (int) (12 * Game.SCALE),(int) (24 * Game.SCALE)); //x = 12,y = 24 Größe der Hitbox
    }


    //##################################################################################################################
    //getter und setter
    public int getAction() {
        return this.action;
    }

    public int getX() {
        return (int) (hitbox.x-xDrawOffset);
    }
    public int getY() {
        return (int) (hitbox.y-yDrawOffset);
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public Float getPlayerSpeed() {
        return playerSpeed;
    }
    public void setPlayerSpeed(Float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    //##################################################################################################################
    //update Methods
    public void update() {
        updatePlayerPosition();
        setAction();
//        System.out.println("playerlogikupdate");
    }
    //müsste eigentlich setActionByValueOfMoving heißen

    private void setAction() {
        int startAction = action;
        if (moving) {
            action = RUNING;
        } else {action = IDLE;}
        if (startAction != action) {
            ITFighterAppController.getInstance().getActualGameController().setAnimationIndex();
        }
    }

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
            if (!entityOnFloor(hitbox, Game.levelOneData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y+ySpeed, hitbox.width, hitbox.height, Game.levelOneData)) {
                //Spielfigur kollidiert mit keinem anderem Objekt beim Sprung
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

    private void resetInAir() {
        inAir = false;
        ySpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if(canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, Game.levelOneData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = entityNextToWallPosX(hitbox, xSpeed);
        }
    }


    //##################################################################################################################
    //dient zum Stoppen des Characters bei Verlust des Window focuses
    public void resetPlayerDirections() {
        down = false;
        up = false;
        right = false;
        left = false;
    }

}



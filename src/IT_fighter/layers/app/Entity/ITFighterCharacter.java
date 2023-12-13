package IT_fighter.layers.app.Entity;

import IT_fighter.layers.app.Game;
import IT_fighter.layers.app.ITFighterAppController;
import IT_fighter.utilities.LoadAndSaveData;

import static IT_fighter.utilities.UtilMethods.*;

import static IT_fighter.utilities.Constants.PlayerConstants.IDLE;
import static IT_fighter.utilities.Constants.PlayerConstants.RUNING;

public class ITFighterCharacter extends Entity {
    private float playerSpeed = 2.0f;
    private int action = IDLE;
    private boolean moving = false;
    private boolean left, up, right, down;
    //Pixelanzahl von 0,0 bis dahin wo Hitbox anfängt
    private float xDrawOffset = 9 * Game.SCALE;
    private float yDrawOffset = 6 * Game.SCALE;

    //TODO darf der KeyHandler hier stehen bleiben? nicht focusable resource
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
        if (!left && !right && !up && !down) {

        } else {
            float xSpeed = 0, ySpeed = 0;

            if (left && !right) {
                xSpeed -= playerSpeed;
            } else if (right && !left) {
                xSpeed += playerSpeed;
            }
            if(up && !down) {
                ySpeed -= playerSpeed;
            } else if (down && !up) {
                ySpeed += playerSpeed;
            }
            if(canMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, Game.levelOneData)) {
                hitbox.x += xSpeed;
                hitbox.y += ySpeed;
                moving = true;
            }

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



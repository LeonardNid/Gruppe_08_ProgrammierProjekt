package IT_fighter.utilities;

import IT_fighter.layers.app.ITFighterAppController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import static IT_fighter.layers.app.Game.*;

public class UtilMethods {
    public static boolean canMoveHere(Float x, Float y, float width, float height, int [][] levelData) {
        if(validPosition(x, y, levelData)) {
            if (validPosition(x + width,y + height, levelData)) {

                if (validPosition(x + width, y, levelData)) {
                    if (validPosition(x, y+height, levelData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean colisionWithPlayer(Rectangle2D.Float playerHitbox, Rectangle2D.Float enemyHitbox) {
        if(enemyHitbox.intersects(playerHitbox)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean validPosition(Float x, Float y, int [][] levelData) {
        float xIndex = x/TILES_SIZE;
        float yIndex = y/TILES_SIZE;


        if (x < 0 || x >= levelData[0].length*TILES_SIZE) {
            return false;
        }
        if (y < 0 || y >= GAME_HEIGHT) {
            return false;
        }
        int value = levelData[(int) yIndex][(int) xIndex];
        //value sind werte aus aus dem levelarray die gleichzeitig angeben welcher Stein an dieser Position gezeichnet
        //wurde 11 z.B.
        if(value >= 47 || value < 0) {
            return false;
        }
        //value 5 und 4 sind Spikes und töten den Spieler
        if(value == 5 || value == 4) {
            ITFighterAppController.getInstance().killPlayer();
        }
        if (value != 11) {
            return false;
        }

        return true;
    }


    public static boolean entityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottomleft and bottomright
        if (validPosition(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (validPosition(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;

        return true;

    }
    public static float entityYPosNotUpOrDown(Rectangle2D.Float hitbox, float ySpeed) {
        int actualTileNumber = (int) (hitbox.y / TILES_SIZE);

        if (ySpeed > 0) {//wenn ySpeed größer als 0 ist fällt der Spieler
            int tileYPos = actualTileNumber * TILES_SIZE;
            int yOffset = (int) (TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {//wenn ySpeed kleiner als 0 ist, springt die Spielfigur
            return actualTileNumber * TILES_SIZE;
        }
    }
    public static float entityNextToWallPosX(Rectangle2D.Float hitbox, float xSpeed) {
        int actualTileNumber = (int) (hitbox.x / TILES_SIZE);
        //xSpeed ist positiv, wenn Spieler sich nach rechts bewegt
        if (xSpeed > 0) {
            int tileXPos = actualTileNumber * TILES_SIZE;
            int OffsetX = (int) (TILES_SIZE - hitbox.width);
            return tileXPos + OffsetX-1;
        //bei negativen xSpeed bewegt sich der Spieler nach links
        } else {
            return actualTileNumber * TILES_SIZE;
        }
    }
    public static JPanel createGap(Dimension dimension) {
        JPanel gap = new JPanel();
        gap.setOpaque(false);
        gap.setPreferredSize(dimension);
        return gap;
    }

}

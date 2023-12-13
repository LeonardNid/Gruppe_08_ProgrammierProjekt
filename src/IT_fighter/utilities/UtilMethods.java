package IT_fighter.utilities;

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

    private static boolean validPosition(Float x, Float y, int [][] levelData) {
        float xIndex = x/TILES_SIZE;
        float yIndex = y/TILES_SIZE;


        if (x < 0 || x >= levelData[0].length*32*TILES_SIZE) {
            return false;
        }
        if (y < 0 || y >= GAME_HEIGHT) {
            return false;
        }
        int value = levelData[(int) yIndex][(int) xIndex];
        //value sind werte aus aus dem levelarray die gleichzeitig angeben welcher Stein an dieser Position gezeichnet
        //wurde 11 z.B.
        if ((value >= 48 || value < 0 || value != 11)) {
            return false;
        }
        return true;
    }
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * TILES_SIZE;
            int xOffset = (int) (TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * TILES_SIZE;
            int yOffset = (int) (TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * TILES_SIZE;

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottomleft and bottomright
        if (!validPosition(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!validPosition(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;

        return true;

    }
}

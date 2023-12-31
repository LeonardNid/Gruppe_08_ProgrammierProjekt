package IT_fighter.utilities;

import IT_fighter.layers.app.ITFighterAppController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import static IT_fighter.layers.app.ITFighterGame.*;

/**
 * verwaltet allgemeine Methoden des Spiels, welche auch anderweitig verwendet werden könnten.
 */

public class UtilMethods {
    /**
     * überprüft, ob sich ein Rechteck an der Position befinden kann. (Collision Detection)
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @param width Breite des Rechtecks
     * @param height Höhe des Rechtecks
     * @param levelData Level-Daten
     * @return true, wenn das Rechteck an der Position existieren kann.
     */
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

    /**
     * überprüft, ob ein Icon oder ein Gegner mit der Spielfigur kollidiert.
     * @param playerHitbox hitbox der Spielfigur.
     * @param enemyHitbox hitbox eines Icons oder eines Gegners.
     * @return true, wenn es zu einer Kollision kommt.
     */
    public static boolean colisionWithPlayer(Rectangle2D.Float playerHitbox, Rectangle2D.Float enemyHitbox) {
        if(enemyHitbox.intersects(playerHitbox)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Überprüft, ob die als Parameter übergebene Position valide ist bzw. eine Kollision hat.
     * @param x X-Koordinate des Punktes
     * @param y Y-Koordinate des Punktes
     * @param levelData Level-Daten
     * @return true, wenn eine Entität auf dem Punkt sein kann, ohne eine Kollision mit einem anderen Objekt zu haben.
     */
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
        //value sind werte aus dem levelarray, die gleichzeitig angeben welcher Stein an dieser Position gezeichnet
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

    /**
     * @param hitbox Hitbox einer Entität.
     * @param lvlData Level-Daten des Levels in dem sich die Entität befindet.
     * @return true, wenn die Entität auf dem Boden, der Kollision hat, steht.
     */
    public static boolean entityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Checks the pixel below bottomleft and bottomright
        if (validPosition(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (validPosition(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }

    /**
     * @param hitbox Hitbox der Entität
     * @param ySpeed Sprunggeschwindigkeit der Entity
     * @return gibt float mit Entfernung einer Entity zum Boden, der Kollision hat zurück.
     */
    public static float entityYPosNotUpOrDown(Rectangle2D.Float hitbox, float ySpeed) {
        int actualTileNumber = (int) (hitbox.y / TILES_SIZE);
        if (ySpeed > 0) {//wenn ySpeed größer als 0 ist, fällt der Spieler
            int tileYPos = actualTileNumber * TILES_SIZE;
            int yOffset = (int) (TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {//wenn ySpeed kleiner als 0 ist, springt die Spielfigur
            return actualTileNumber * TILES_SIZE;
        }
    }

    /**
     * @param hitbox Hitbox der Entität
     * @param xSpeed Geschwindigkeit der Entity
     * @return gibt float mit Entfernung einer Entity zu einer Wand, die Kollision hat zurück.
     */
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

    /**
     * erstellt eine Lücke für ein beliebiges Layout.
     * @param dimension gibt die größe der zu erzeugenden Lücke an.
     * @return gibt die gewünschte Lücke als durchsichtiges JPanel zurück.
     */
    public static JPanel createGap(Dimension dimension) {
        JPanel gap = new JPanel();
        gap.setOpaque(false);
        gap.setPreferredSize(dimension);
        return gap;
    }

}

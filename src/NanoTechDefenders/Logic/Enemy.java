package NanoTechDefenders.Logic;

import java.awt.*;
import NanoTechDefenders.Help.Direction;
import NanoTechDefenders.Logic.Controlling.LevelManager;

/**
 * Diese Klasse repräsentiert einen Gegner im Spiel.
 */
public class Enemy {
    private final int speed = 5;
    private final int MAXLIFES = LevelManager.getEnemyStartLife();
    private int enemyLifes = MAXLIFES;
    private int currentPathIndex = 1;
    private Direction currentDirection = Direction.RIGHT;
    private Point location;
    private Point[] path;
    private boolean pcReached;
    private boolean canBeRemoved;
    private boolean finished;

    /**
     * Konstruktor für einen Gegner.
     * Setzt den Pfad und die Startposition des Gegners.
     */
    public Enemy() {
        path = Path.getCorners();
        location = new Point(path[0]);
    }

    /**
     * Bewegt den Gegner in seiner aktuellen Richtung um eine Entfernung von "speed" Pixeln.
     * Aktualisiert den Pfadindex basierend auf der erreichten Position.
     */
    public void move() {
        switch (currentDirection) {
            case LEFT -> location.translate(-speed, 0);
            case RIGHT -> location.translate(speed, 0);
            case UP -> location.translate(0, -speed);
            case DOWN -> location.translate(0, speed);
            default -> System.out.println("error: no current direction");
        }
        updatePathIndex();
    }

    /**
     * Aktualisiert den Pfadindex.
     * Falls die Distanz vom Gegner zur nächsten Ecke kleiner als "speed" ist, wird die Ecke erreicht.
     * Dann wird der Pfadindex hochgezählt und die Richtung wird angepasst.
     * Je nachdem, ob der nächste Punkt rechts, links, oben oder unten von der Position ist.
     * Sobald der Gegner den PC erreicht hat, wird der Pfadindex runtergezählt.
     * Sobald der Gegner den kompletten Pfad zurückgelaufen ist, wird die Variable "finished" auf true gesetzt.
     */
    private void updatePathIndex() {
        Point nextCorner = path[currentPathIndex];
        double distance = location.distance(nextCorner);

        if (distance < speed || Math.abs(location.getX()) > 5000 || Math.abs(location.getY()) > 5000) {
            location.setLocation(nextCorner);

            // Falls der Gegner den PC erreicht hat.
            if (currentPathIndex == path.length - 1) {
                pcReached = true;
            }

            // Falls der PC erreicht wurde, wird der Pfadindex runtergezählt.
            if (pcReached) {
                if (currentPathIndex > 0) {
                    --currentPathIndex;
                } else {
                    canBeRemoved = true;
                    finished = true;
                }
            } else {
                ++currentPathIndex;
            }

            nextCorner = path[currentPathIndex];

            if (nextCorner.x > location.x) {
                currentDirection = Direction.RIGHT;
            } else if (nextCorner.x < location.x) {
                currentDirection = Direction.LEFT;
            } else if (nextCorner.y > location.y) {
                currentDirection = Direction.DOWN;
            } else if (nextCorner.y < location.y) {
                currentDirection = Direction.UP;
            }
        }
    }

    /**
     * Gibt die aktuelle Position des Gegners zurück.
     *
     * @return Die Position des Gegners als Point-Objekt.
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Gibt die aktuelle Richtung des Gegners zurück.
     *
     * @return Die aktuelle Richtung des Gegners.
     */
    public Direction getDirection() {
        return currentDirection;
    }

    /**
     * Gibt den Status des Gegners zurück, ob er entfernt werden kann oder nicht.
     *
     * @return True, wenn der Gegner entfernt werden kann, sonst false.
     */
    public boolean getRemovableStatus() {
        return canBeRemoved;
    }

    /**
     * Gibt den Status des Gegners zurück, ob er das Ziel erreicht hat oder nicht.
     *
     * @return True, wenn der Gegner das Ziel erreicht hat, sonst false.
     */
    public boolean getFinishedStatus() {
        return finished;
    }

    /**
     * Gibt die Anzahl der Lebenspunkte des Gegners zurück.
     *
     * @return Die Lebenspunkte des Gegners.
     */
    public int getEnemyLifes() {
        return enemyLifes;
    }

    /**
     * Gibt die maximale Anzahl der Lebenspunkte des Gegners zurück.
     *
     * @return Die maximale Anzahl der Lebenspunkte des Gegners.
     */
    public int getMaxLifes() {
        return MAXLIFES;
    }

    /**
     * Gibt zurück, ob der Gegner den PC erreicht hat oder nicht.
     *
     * @return True, wenn der Gegner den PC erreicht hat, sonst false.
     */
    public boolean getPcReached() {
        return pcReached;
    }

    /**
     * Gibt das Rechteck zurück, das den Gegner darstellt.
     *
     * @return Das Rechteck, das den Gegner darstellt.
     */
    public Rectangle getRectangle() {
        return new Rectangle(location, new Dimension(50, 50));
    }

    /**
     * Verringert die Lebenspunkte des Gegners um den angegebenen Schaden.
     * Wenn die Lebenspunkte nach dem Angriff kleiner oder gleich null sind, wird der Gegner als entfernt markiert.
     *
     * @param damage Der Schaden, der dem Gegner zugefügt wird.
     */
    public void lostxLife(int damage) {
        enemyLifes -= damage;
        if (enemyLifes <= 0) {
            canBeRemoved = true;
        }
    }
}

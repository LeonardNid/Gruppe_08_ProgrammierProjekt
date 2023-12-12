package NanoTechDefenders.GUI.Enemy;

import NanoTechDefenders.Help.Direction;
import NanoTechDefenders.Help.MyImage;
import NanoTechDefenders.Logic.Enemy;

import javax.swing.*;
import java.awt.*;

/**
 * Die EnemyLabel-Klasse erweitert JLabel und dient dazu, einen Gegner auf dem Bildschirm zu zeichnen.
 */
public class EnemyLabel extends JLabel {
    private final int WIDTH = 50, HEIGHT = 50;
    private ImageIcon[] originalImageArray = new ImageIcon[2];
    private ImageIcon[] imageArray = new ImageIcon[2];
    private int rotationsTillImageChange = 20;
    private int imageIndex = 0;
    private Enemy enemyLogic;
    private Direction currentDirection;
    private boolean changedImage = false;

    /**
     * Konstruktor für das EnemyLabel.
     *
     * @param newEnemy Der Gegner, der angezeigt werden soll.
     */
    public EnemyLabel(Enemy newEnemy) {
        originalImageArray[0] = MyImage.createScaledImageIcon("NTD_Spider1A.png", WIDTH, HEIGHT);
        originalImageArray[1] = MyImage.createScaledImageIcon("NTD_Spider1B.png", WIDTH, HEIGHT);

        enemyLogic = newEnemy;

        this.setLocation(enemyLogic.getLocation());
        this.setIcon(originalImageArray[0]);
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setVisible(true);
    }

    /**
     * Gibt die Logik des Gegners zurück.
     *
     * @return Das Enemy-Objekt.
     */
    public Enemy getEnemyLogic() {
        return enemyLogic;
    }

    /**
     * Aktualisiert die Position des Labels basierend auf der Position des Gegners.
     */
    public void updateLocation() {
        this.setLocation(enemyLogic.getLocation());
        Direction enemyDirection = enemyLogic.getDirection();
        if (currentDirection != enemyDirection) {
            rotateImage(enemyDirection);
            if (enemyLogic.getPcReached() && !changedImage) {
                changedImage = true;
                originalImageArray[0] = MyImage.createScaledImageIcon("NTD_Spider2A.png", WIDTH, HEIGHT);
                originalImageArray[1] = MyImage.createScaledImageIcon("NTD_Spider2B.png", WIDTH, HEIGHT);
            }
            this.setIcon(imageArray[imageIndex]);
        }
        animation();
    }

    /**
     * Führt eine einfache Animation durch, um zwischen den Bildern zu wechseln.
     */
    private void animation() {
        --rotationsTillImageChange;
        if (rotationsTillImageChange < 0) {
            rotationsTillImageChange = 20;
            ++imageIndex;
            if (imageIndex == originalImageArray.length) {
                imageIndex = 0;
            }
            this.setIcon(imageArray[imageIndex]);
        }
    }

    /**
     * Dreht das Bild entsprechend der Richtung des Gegners.
     *
     * @param enemyDirection Die Richtung des Gegners.
     */
    public void rotateImage(Direction enemyDirection) {
        double angle;
        currentDirection = enemyDirection;
        switch (currentDirection) {
            case LEFT -> angle = 270;
            case RIGHT -> angle = 90;
            case UP -> angle = 0;
            case DOWN -> angle = 180;
            default -> throw new IllegalStateException("Unexpected value: " + enemyLogic.getDirection());
        }

        imageArray[0] = MyImage.rotateImageIcon(originalImageArray[0], angle);
        imageArray[1] = MyImage.rotateImageIcon(originalImageArray[1], angle);
    }

    /**
     * Berechnet die Breite der Gesundheitsleiste basierend auf der aktuellen Lebenspunkte des Gegners.
     *
     * @return Die Breite der Gesundheitsleiste.
     */
    private int calcHealthBarWidth() {
        return (int) (((double) enemyLogic.getEnemyLifes() / enemyLogic.getMaxLifes()) * WIDTH);
    }

    /**
     * Zeichnet die Gesundheitsleiste auf dem Label.
     *
     * @param g Das Graphics-Objekt zum Zeichnen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawLine(0, HEIGHT - 1, calcHealthBarWidth(), HEIGHT - 1);
    }
}

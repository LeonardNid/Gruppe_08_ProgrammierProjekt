package NanoTechDefenders.GUI.Projectile;

import NanoTechDefenders.Help.MyImage;
import NanoTechDefenders.Logic.Projectiles.Projectile;

import javax.swing.*;
import java.awt.*;

/**
 * Das ProjectileLabel erweitert JLabel und dient dazu, ein Projektil auf dem Bildschirm zu zeichnen.
 */
public class ProjectileLabel extends JLabel {
    private ImageIcon image;
    private double angle = Math.toRadians(45);
    private final Projectile logicProjectile;
    private boolean animated = false;
    private int imageIndex = 0;
    private static final int MAX_ROTATIONS_TILL_IMAGE_CHANGE = 10;
    private static int rotationsTillImageChange = MAX_ROTATIONS_TILL_IMAGE_CHANGE;
    private ImageIcon[] imageArr;

    /**
     * Konstruktor für das ProjectileLabel.
     *
     * @param newProjectile Das Projektil, das angezeigt werden soll.
     */
    public ProjectileLabel(Projectile newProjectile) {
        logicProjectile = newProjectile;

        getImages();

        this.setIcon(image); // Setze das Hintergrundbild des Labels
        this.setSize(new Dimension(logicProjectile.getWidth(), logicProjectile.getHeight())); // Setzt die Größe des Labels
        this.setLocation(logicProjectile.getLocation()); // Setzt die Position des Labels
        this.setVisible(true); // Sichtbar machen
    }

    /**
     * Lädt die Bilder für die Animation, falls vorhanden.
     */
    public void getImages() {
        if (logicProjectile.getFileName().equals("array")) {
            animated = true;
            int length = logicProjectile.getFileNameArray().length;
            imageArr = new ImageIcon[length];
            for (int i = 0; i < length; ++i) {
                imageArr[i] = MyImage.createScaledImageIcon(logicProjectile.getFileNameArray()[i], logicProjectile.getWidth(), logicProjectile.getHeight());
            }
            image = imageArr[0];
        } else {
            image = MyImage.createScaledImageIcon(logicProjectile.getFileName(), logicProjectile.getWidth(), logicProjectile.getHeight());
        }
    }

    /**
     * Gibt das zugehörige Projektil-Objekt zurück.
     *
     * @return Das Projektil-Objekt.
     */
    public Projectile getLogicProjectile() {
        return logicProjectile;
    }

    /**
     * Aktualisiert die Position des Labels basierend auf der Position des Projektils.
     */
    public void updateLocation() {
        this.setLocation(logicProjectile.getLocation());
        if (animated) {
            animation();
        }
        rotate();
        if (logicProjectile.getStatus() && logicProjectile.getOnHitEffect()) {
            this.setIcon(MyImage.createScaledImageIcon(logicProjectile.getFileName(), logicProjectile.getWidth(), logicProjectile.getHeight()));
        }
    }

    /**
     * Führt eine einfache Animation durch, um zwischen den Bildern zu wechseln.
     */
    public void animation() {
        --rotationsTillImageChange;
        if (rotationsTillImageChange < 0) {
            rotationsTillImageChange = MAX_ROTATIONS_TILL_IMAGE_CHANGE;
            ++imageIndex;
            if (imageIndex == imageArr.length) {
                imageIndex = 0;
            }
            this.setIcon(imageArr[imageIndex]);
        }
    }

    /**
     * Rotiert das Bild entsprechend des Winkels des Projektils.
     */
    public void rotate() {
        double logicAngle = logicProjectile.getAngle();
        if (Math.abs(angle - logicAngle) > 1) {
            angle = logicAngle;
            this.setIcon(MyImage.rotateImageIcon(image, angle));
        }
    }
}

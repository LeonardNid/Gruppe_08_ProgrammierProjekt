package NanoTechDefenders.GUI.Soldier;

import NanoTechDefenders.Help.MyImage;
import NanoTechDefenders.Logic.Soldier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Das SoldierLabel ist dafür da, einen Soldaten auf dem Bildschirm anzuzeigen.
 */
public class SoldierLabel extends JLabel {
    private final int WIDTH = 50, HEIGHT = 50;
    private ImageIcon image;
    private Soldier logicSoldier;
    private double angle;

    /**
     * Konstruktor für das SoldierLabel.
     *
     * @param soldier Die Logik des Soldiers.
     */
    public SoldierLabel(Soldier soldier) {
        logicSoldier = soldier;

        image = MyImage.createScaledImageIcon(logicSoldier.getFilename(), WIDTH, HEIGHT);

        this.setIcon(image); // Setze das Hintergrundbild des Labels
        this.setSize(new Dimension(WIDTH, HEIGHT)); // Setzt die Größe des Labels
        this.setLocation((int) logicSoldier.getlocation().getX(), (int) logicSoldier.getlocation().getY()); // Setzt die Position des Labels

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoldierContainPanel.showSoldierInfo(e.getLocationOnScreen(), logicSoldier.getDamageDone(), logicSoldier.getKills());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                SoldierContainPanel.hideSoldierInfo();
            }
        });
    }

    /**
     * Gibt das zugehörige Soldaten-Objekt zurück.
     *
     * @return Das Soldaten-Objekt.
     */
    public Soldier getLogicSoldier() {
        return logicSoldier;
    }

    /**
     * Rotiert das Bild entsprechend des Winkels des Soldaten.
     */
    public void rotate() {
        double logicAngle = logicSoldier.getAngle();
        if (Math.abs(angle - logicAngle) > 1) {
            angle = logicAngle;
            this.setIcon(MyImage.rotateImageIcon(image, angle));
        }
    }
}

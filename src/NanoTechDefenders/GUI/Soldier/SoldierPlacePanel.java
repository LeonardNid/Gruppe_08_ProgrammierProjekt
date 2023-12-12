package NanoTechDefenders.GUI.Soldier;

import NanoTechDefenders.GUI.Controlling.GUIController;
import NanoTechDefenders.Help.MyImage;
import NanoTechDefenders.Help.Soldiers;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Das SoldierPlacePanel ist dafür da, einen Soldaten zu positionieren.
 * Es ermöglicht das Ziehen und Positionieren eines Soldaten auf dem Spielfeld.
 */
public class SoldierPlacePanel extends JPanel {
    private ImageIcon image;
    private Point imageCorner;
    private int ringSize;

    /**
     * Konstruktor für das SoldierPlacePanel.
     * erstellt den mouseMoved und mousePressed Listener.
     *
     * @param frameHeight     Die Höhe des Frames.
     * @param soldierVariant Die Art der Soldaten, die platziert werden können.
     */
    public SoldierPlacePanel(int frameHeight, Soldiers soldierVariant) {
        ringSize = soldierVariant.getEnemyView();
        String fileName = soldierVariant.getFileName();
        image = MyImage.createScaledImageIcon(fileName, 50, 50);

        // Startposition des Bildes setzen
        imageCorner = new Point((int) (800 * GUIController.getXFactor()), (int) (450 * GUIController.getYFactor()));

        // MouseMotionListener für das Ziehen des Soldaten
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point currentLocation = e.getPoint();
                imageCorner.setLocation(currentLocation.getX() - 25, currentLocation.getY() - 25);
                repaint();
            }
        });

        // MouseListener für das Platzieren des Soldaten
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getY() <= frameHeight - (120 * GUIController.getYFactor()) - 25 && SoldierContainPanel.checkFree(e.getPoint())) {
                    GUIController.placeSoldier(imageCorner, soldierVariant);
                    System.out.println("Maus gedrückt");
                }
            }
        });

        this.setBorder(new LineBorder(Color.RED, 2)); // Rahmen um das Panel zeichnen
        this.setOpaque(false); // Hintergrund transparent machen
    }

    /**
     * Überschreibt die paintComponent-Methode von JPanel.
     * Zeichnet das Bild.
     * Zeichnet die SoldierView.
     *
     * @param g Das Graphics-Objekt zum Zeichnen.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Bild zeichnen.
        g.drawImage(image.getImage(), (int) imageCorner.getX(), (int) imageCorner.getY(), this);
        g.setColor(Color.RED);
        g.drawOval((int) (imageCorner.getX() - (ringSize - 25) * GUIController.getXFactor()),
                (int) (imageCorner.getY() - (ringSize - 25) * GUIController.getYFactor()),
                (int) (ringSize * GUIController.getXFactor() * 2),
                (int) (ringSize * GUIController.getYFactor() * 2)
        );
    }
}

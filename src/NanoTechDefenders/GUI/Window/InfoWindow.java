package NanoTechDefenders.GUI.Window;

import NanoTechDefenders.Help.MyFont;
import NanoTechDefenders.Help.MyImage;
import NanoTechDefenders.Help.Soldiers;

import javax.swing.*;
import java.awt.*;

/**
 * Das InfoWindow ist dafür da, Informationen zu den Soldaten anzuzeigen.
 */
public class InfoWindow extends JWindow {
    private int width;

    /**
     * Konstruktor für das InfoWindow.
     *
     * @param width Die Breite des Fensters, in dem das InfoWindow angezeigt wird.
     */
    public InfoWindow(int width) {
        this.width = width;
        // Get the soldier information
        makeLabels();
        // Create a JWindow to display the information
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        // Get Location of x of the InfoWindow
        // Set the size of the window
        this.pack();
        // Make Background Black
        this.getContentPane().setBackground(Color.BLACK);
        // setVisible
        this.setVisible(true);
    }

    /**
     * Setzt die Position des InfoWindows.
     *
     * @param location Die Position des InfoWindows.
     */
    @Override
    public void setLocation(Point location) {
        int xOfIntoBoard = Math.max(0, Math.min(location.x - this.getWidth() / 2, width - this.getWidth()));
        // Set the location of the window
        this.setLocation(xOfIntoBoard, location.y + 20); // Adjusted for better visibility
    }

    /**
     * Erstellt die Labels mit den Informationen zu den Soldaten und fügt sie dem InfoWindow hinzu.
     */
    protected void makeLabels() {
        System.out.println("label1");
        for (Soldiers soldier : Soldiers.values()) {
            ImageIcon icon = MyImage.createScaledImageIcon(soldier.getFileName(), 50, 50);
            String infoText = "<html>Cost: " + soldier.getSoldierCost() + "<br>" +
                    "Range: " + soldier.getEnemyView() + "</html>";
            JLabel label = new JLabel();
            label.setIcon(icon);
            label.setText(infoText);
            label.setFont(MyFont.getMyFont().deriveFont(20f));
            label.setForeground(Color.GREEN);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.add(label);
        }
    }
}

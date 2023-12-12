package NanoTechDefenders.GUI.Soldier;

import NanoTechDefenders.GUI.Window.InfoWindowSoldiers;
import NanoTechDefenders.Help.Soldiers;
import NanoTechDefenders.Logic.Soldier;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Das SoldierContainPanel ist dafür da, Soldaten zu verwalten und zu platzieren.
 * Es enthält ein SoldierPlacePanel.
 */
public class SoldierContainPanel extends JPanel {
    private static CopyOnWriteArrayList<SoldierLabel> soldierLabelList = new CopyOnWriteArrayList<>();
    private SoldierPlacePanel soldierPlacePanel;
    private static int frameWidth, frameHeight;
    private static InfoWindowSoldiers infoWindowSoldiers;

    /**
     * Konstruktor für das SoldierContainPanel.
     *
     * @param iframeWidth  Die Breite des Frames.
     * @param iframeHeight Die Höhe des Frames.
     */
    public SoldierContainPanel(int iframeWidth, int iframeHeight) {
        frameWidth = iframeWidth;
        frameHeight = iframeHeight;

//        this.setBorder(new LineBorder(Color.BLUE, 5)); // Rahmen um das Panel zeichnen
        this.setOpaque(false); // Hintergrund transparent machen
        this.setLayout(null); // Kein spezielles Layout verwenden

    }

    /**
     * Überprüft, ob die angegebene Position für einen neuen Soldaten frei ist.
     *
     * @param newSoldierLocation Die Position des neuen Soldaten.
     * @return true, wenn die Position frei ist, sonst false.
     */
    public static boolean checkFree(Point newSoldierLocation) {
        for (SoldierLabel soldierLabel : soldierLabelList) {
            if (soldierLabel.getBounds().contains(newSoldierLocation)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Aktualisiert die Rotation der vorhandenen Soldaten.
     */
    public void update() {
        for (SoldierLabel soldierLabel : soldierLabelList) {
            soldierLabel.rotate();
        }
    }

    /**
     * Erstellt das SoldierPlacePanel und fügt es zum SoldierContainPanel hinzu.
     *
     * @param soldierVariant Die Art der Soldaten, die platziert werden können.
     */
    public void createSoldierPlacePanel(Soldiers soldierVariant) {
        soldierPlacePanel = new SoldierPlacePanel(frameHeight, soldierVariant);
        soldierPlacePanel.setBounds(0, 0, frameWidth, frameHeight);

        this.add(soldierPlacePanel);
        this.revalidate(); // Layout der Komponenten aktualisieren
        this.repaint(); // Komponenten neu zeichnen
    }

    /**
     * Platziert einen Soldaten an der angegebenen Position und entfernt das SoldierPlacePanel.
     *
     * @param soldier Die Logik des Soldiers.
     */
    public void placeSoldier(Soldier soldier) {
        SoldierLabel newSoldierLabel = new SoldierLabel(soldier);
        soldierLabelList.add(newSoldierLabel);
        this.add(newSoldierLabel); // Neues SoldierPanel hinzufügen
        this.remove(soldierPlacePanel); // SoldierPlacePanel entfernen
    }

    /**
     * Entfernt das SoldierLabel des angegebenen Soldiers.
     *
     * @param removableSoldier Der Soldier, dessen Label entfernt werden soll.
     */
    public void removeSoldierLabel(Soldier removableSoldier) {
        for (SoldierLabel soldierLabel : soldierLabelList) {
            if (soldierLabel.getLogicSoldier().equals(removableSoldier)) {
                this.remove(soldierLabel);
            }
        }
    }

    /**
     * Entfernt alle SoldierLabels.
     */
    public void clearSoldier() {
        soldierLabelList.clear();
    }

    /**
     * Zeigt das InfoWindowSoldiers an der angegebenen Position mit den gegebenen Informationen an.
     *
     * @param locationOfStats Die Position des InfoWindows.
     * @param damage          Der Schaden des Soldiers.
     * @param kills           Die Anzahl der erzielten Kills.
     */
    public static void showSoldierInfo(Point locationOfStats, int damage, int kills) {
        if (infoWindowSoldiers != null) {
            infoWindowSoldiers.setLocation(locationOfStats);
            infoWindowSoldiers.updateDamage(damage, kills);
            infoWindowSoldiers.setVisible(true);
        } else {
            infoWindowSoldiers = new InfoWindowSoldiers(frameWidth);
            infoWindowSoldiers.setLocation(locationOfStats);
            infoWindowSoldiers.updateDamage(damage, kills);
        }
    }

    /**
     * Versteckt das InfoWindowSoldiers.
     */
    public static void hideSoldierInfo() {
        if (infoWindowSoldiers != null) {
            infoWindowSoldiers.setVisible(false);
        }
    }
}

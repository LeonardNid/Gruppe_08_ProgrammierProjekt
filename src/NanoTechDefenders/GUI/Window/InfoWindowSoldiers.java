package NanoTechDefenders.GUI.Window;

import NanoTechDefenders.Help.MyFont;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Das InfoWindowSoldiers erweitert das InfoWindow und zeigt zusätzliche Informationen zu den jeweiligen Soldaten an.
 */
public class InfoWindowSoldiers extends InfoWindow {
    private JLabel label;

    /**
     * Konstruktor für das InfoWindowSoldiers.
     *
     * @param width Die Breite des Fensters, in dem das InfoWindowSoldiers angezeigt wird.
     */
    public InfoWindowSoldiers(int width) {
        super(width);
    }

    /**
     * Erstellt das Label mit den Informationen zu den Soldaten und fügt es dem InfoWindowSoldiers hinzu.
     */
    @Override
    protected void makeLabels() {
        String infotext = "Damage: " + "0";
        label = new JLabel();
        label.setText(infotext);
        label.setFont(MyFont.getMyFont().deriveFont(20f));
        label.setForeground(Color.GREEN);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(label);
    }

    /**
     * Aktualisiert die Schadens- und Tötungsinformationen im Label.
     *
     * @param damage Der verursachte Schaden.
     * @param kills  Die Anzahl der getöteten Gegner.
     */
    public void updateDamage(int damage, int kills) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String infotext = "<html>Kills: " + numberFormat.format(kills) + "<br>" +
                "Damage: " + numberFormat.format(damage);
        label.setText(infotext);
        label.setSize(label.getPreferredSize());
        super.setSize(new Dimension(label.getPreferredSize().width, label.getPreferredSize().height));
    }
}

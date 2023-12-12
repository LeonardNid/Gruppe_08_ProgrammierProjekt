package NanoTechDefenders.GUI;

import javax.swing.*;

/**
 * Der HauptFrame ist das Hauptfenster der Anwendung.
 * Er erbt von JFrame und stellt die zentrale Benutzeroberfläche dar.
 */
public class HauptFrame extends JFrame {
    /**
     * Konstruktor für den HauptFrame.
     * Initialisiert die Einstellungen des Fensters.
     */
    public HauptFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Beendet die Anwendung beim Schließen des Fensters

        // this.setSize(1600, 900);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Setzt die Größe auf maximal (Vollbildmodus)
        this.setUndecorated(true); // Entfernt die Standard-Fensterrahmen (Titelleiste)
        this.setResizable(false); // Verhindert, dass das Fenster in der Größe verändert werden kann
        this.setVisible(true); // Macht das Fenster sichtbar
    }
}

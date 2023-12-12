package NanoTechDefenders.GUI;

import Main.Main;
import NanoTechDefenders.GUI.Controlling.GUIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 * Das GameMenuPanel ist ein spezielles Menüpanel für das Pause-Menü während des Spiels.
 */
public class GameMenuPanel extends MenuPanel {
    /**
     * Konstruktor für das GameMenuPanel.
     *
     * @param frameWidth  Die Breite des übergeordneten Frames.
     * @param frameHeight Die Höhe des übergeordneten Frames.
     */
    public GameMenuPanel(int frameWidth, int frameHeight) {
        super(frameWidth, frameHeight);
        super.makeTitle("Pause Menu");
        super.makeButtons("Play", "Restart", "Quit");
    }

    /**
     * Verarbeitet Aktionen, die durch Benutzerinteraktionen ausgelöst werden.
     *
     * @param e Das ActionEvent-Objekt, das die ausgelöste Aktion repräsentiert.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Übergeordnetes Fenster extrahieren
        HauptFrame frame = (HauptFrame) SwingUtilities.getWindowAncestor(this);
        if (e.getSource() == super.btn1) { // Button "Play" gedrückt
            GUIController.closeGameMenu(this);
            System.out.println("Play gedrückt");
        }
        if (e.getSource() == super.btn2) { // Button "Restart" gedrückt
            GUIController.createLevelPanels(0);
            System.out.println("Restart gedrückt");
        }
        if (e.getSource() == super.btn3) { // Button "Quit" gedrückt
            System.out.println("Quit gedrückt");
            GUIController.switchMenuPanel();
        }
        if (e.getSource() == super.backtobibBtn) { // Button "Back" gedrückt
            System.out.println("Back gedrückt");
//            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); // schließt die Anwendung wie wenn man auf X klickt
            frame.dispose();
            Main.createMainFrame();
        }
    }
}

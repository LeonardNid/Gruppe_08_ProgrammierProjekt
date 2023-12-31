package IT_fighter.layers.ui;
import javax.swing.*;

/**
 * Das MainFrame ist ein JFrame in das alle anderen grafischen Komponenten (in der Regel JPanels)
 * eingefügt und wieder gelöscht werden, je nach Gebrauch
 */
public class ITFighterMainFrame extends JFrame {
    /**
     * Konstruktor erstellt das MainFrame.
     * Die Größe des MainFrames wird durch die JPanels bestimmt, die dem MainFrame hinzugefügt werden.
     */
    public ITFighterMainFrame() {
        this.setTitle("ITFighter");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}


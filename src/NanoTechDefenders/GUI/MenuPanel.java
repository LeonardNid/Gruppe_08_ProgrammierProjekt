package NanoTechDefenders.GUI;

import Main.Main;
import NanoTechDefenders.GUI.Controlling.GUIController;
import NanoTechDefenders.Help.MyFont;
import NanoTechDefenders.Help.MyImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Die MenuPanel-Klasse ist ein JPanel, das das Hauptmenü des Spiels darstellt.
 * Es enthält Buttons für die Schwierigkeitsstufen und einen Zurück-Button.
 */
public class MenuPanel extends JPanel implements ActionListener {
    // Panel Hintergrund
    private final ImageIcon backgroundImage;
    private static final Font font = MyFont.getMyFont();
    private JPanel buttonPanelCenter;
    protected JButton btn1;
    protected JButton btn2;
    protected JButton btn3;
    protected JButton backtobibBtn;
    private final int frameWidth;
    private final int frameHeight;

    /**
     * Konstruktor für das MenuPanel.
     * Es initialisiert das Layout und erstellt die UI-Komponenten.
     *
     * @param frameWidth  Die Breite des übergeordneten Frames.
     * @param frameHeight Die Höhe des übergeordneten Frames.
     */
    public MenuPanel(int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        this.setLayout(new BorderLayout());
        this.setVisible(true);

        backgroundImage = MyImage.createScaledImageIcon("NTD_firewall_locks.jpg", frameWidth, frameHeight);

        makeTitle("NanoTech Defenders");
        makeButtons("Easy", "Normal", "Hard");
        makeBackButton();
    }

    /*
     * Methode überschreibt paintComponent von JPanel
     * sorgt dafür, dass backgroundImage als Hintergrund gemalt wird anstatt blank
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Hintergrundbild zeichnen
        g.drawImage(backgroundImage.getImage(), 0, 0, frameWidth, frameHeight, this);
    }

    /**
     * Erstellt das Titel-Panel und fügt es hinzu.
     *
     * @param title Der Titeltext, der angezeigt wird.
     */
    protected void makeTitle(String title) {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        titlePanel.setVisible(true);

        // Label Text für Title
        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(font.deriveFont(Font.BOLD, 80));
        titleLbl.setForeground(Color.RED);
        titleLbl.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 10));
        titlePanel.add(titleLbl);

        this.add(titlePanel, BorderLayout.NORTH);
    }

    /**
     * Erstellt die Schwierigkeits-Buttons und fügt sie hinzu.
     *
     * @param button1 Text für den ersten Button.
     * @param button2 Text für den zweiten Button.
     * @param button3 Text für den dritten Button.
     */
    protected void makeButtons(String button1, String button2, String button3) {
        buttonPanelCenter = new JPanel();
        buttonPanelCenter.setOpaque(false);
        buttonPanelCenter.setLayout(new BoxLayout(buttonPanelCenter, BoxLayout.PAGE_AXIS));
        buttonPanelCenter.add(Box.createVerticalGlue()); // Leerzeichen
        // Button für Schwierigkeitsstufe "Easy"
        makeOneButton(btn1 = new JButton(), button1);
        buttonPanelCenter.add(Box.createVerticalGlue()); // Leerzeichen
        // Button für Schwierigkeitsstufe "Normal"
        makeOneButton(btn2 = new JButton(), button2);
        buttonPanelCenter.add(Box.createVerticalGlue()); // Leerzeichen
        // Button für Schwierigkeitsstufe "Hard"
        makeOneButton(btn3 = new JButton(), button3);
        buttonPanelCenter.add(Box.createVerticalGlue()); // Leerzeichen
        this.add(buttonPanelCenter, BorderLayout.CENTER);
    }

    /**
     * Hilfsmethode zur Erstellung eines Buttons für eine Schwierigkeitsstufe.
     *
     * @param button Der Button, der erstellt wird.
     * @param text   Der Text, der auf dem Button angezeigt wird.
     */
    private void makeOneButton(JButton button, String text) {
        button.setText(text);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setOpaque(false); // Macht den Button-Hintergrund transparent
        button.setContentAreaFilled(false); // Entfernt das Füllen des Inhaltsbereichs
        button.setBorderPainted(false); // Entfernt die Button-Grenze
        button.setForeground(Color.GREEN); // Setzt die Textfarbe
        button.addActionListener(this);
        button.setFocusable(false);
        // Add a mouse listener to the button to change the Font when hovered
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change the Font when the mouse enters the button
                button.setFont(font.deriveFont(Font.BOLD, 100));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Reset the Font when the mouse exits the button
                button.setFont(font.deriveFont(Font.BOLD, 60));
            }
        });
        buttonPanelCenter.add(button);
    }

    /**
     * Erstellt den "Zurück zur Bibliothek"-Button und fügt ihn hinzu.
     */
    private void makeBackButton() {
        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        BottomPanel.setOpaque(false);
        BottomPanel.setVisible(true);

        // Button "Back zur Bibliothek"
        backtobibBtn = new JButton();
        backtobibBtn.setText("Back");
        backtobibBtn.setFont(font);
        backtobibBtn.setOpaque(false); // Macht den Button-Hintergrund transparent
        backtobibBtn.setContentAreaFilled(false); // Entfernt das Füllen des Inhaltsbereichs
        backtobibBtn.setBorderPainted(false); // Entfernt die Button-Grenze
        backtobibBtn.setForeground(Color.RED); // Setzt die Textfarbe
        backtobibBtn.addActionListener(this);
        backtobibBtn.setFocusable(false);
        BottomPanel.add(backtobibBtn);

        this.add(BottomPanel, BorderLayout.SOUTH);
    }


    /**
     * Behandelt Aktionen, wenn ein Button geklickt wird.
     *
     * @param e Das ActionEvent, das die Aktion auslöst.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // übergeordnetes Fenster extrahieren
        HauptFrame frame = (HauptFrame) SwingUtilities.getWindowAncestor(this);

        if (e.getSource() == btn1) { // Button "Easy" gedrückt
            GUIController.createLevelPanels(1);
            System.out.println("Easy gedrückt");
        }

        if (e.getSource() == btn2) { // Button "Normal" gedrückt
            GUIController.createLevelPanels(2);
            System.out.println("Normal gedrückt");
        }

        if (e.getSource() == btn3) { // Button "Hard" gedrückt
            GUIController.createLevelPanels(3);
            System.out.println("Hard gedrückt");
        }

        if (e.getSource() == backtobibBtn) { // Button "Back" gedrückt
            System.out.println("Back gedrückt");
//            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); // schließt die Anwendung wie wenn man auf X klickt
            frame.dispose();
            Main.setMainFrameVisible();
        }
    }
}

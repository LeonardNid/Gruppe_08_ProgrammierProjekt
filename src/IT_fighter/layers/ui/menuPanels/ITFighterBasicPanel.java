package IT_fighter.layers.ui.menuPanels;

import IT_fighter.utilities.LoadAndSaveData;

import javax.swing.*;
import java.awt.*;

/**
 * Das BasicPanel ist verwaltet Methoden, die alle Panel die ein Menü darstellen brauchen
 */
public abstract class ITFighterBasicPanel extends JPanel {

    //ScreenDimension bestimmt die Größe der erstellten BasicPanels
    public static final Dimension ScreenDimension = new Dimension(1920, 1056);
    //##################################################################################################################
    public static final int screenWidth = 1920;//tileSize * maxScreenCol; //1280 pixels -angestrebt 1280
    public static final int screenHeight = 1080;//tileSize * maxScreenRow;// 704 pixels -angestrebt 720
    public static final int BUTTON_WIDTH = 550;
    public static final int BUTTON_HEIGHT = 100;
    public static final int BUTTON_GAP_SIZE = 80;
    public static final int ButtonStandardSize = 70;
    //##################################################################################################################
    /**
     * generiert JButton mit den gewünschten Parametern
     * @param text Inhalt des Buttons
     * @param color Schriftfarbe des Buttons
     * @param font Schriftart und Schriftgröße des Buttons
     * @return gibt den mit den gegebenen Parametern erstellten JButton zurück
     */
    // gibt transparenten Button mit Text, Schriftfarbe und Schriftart zurück
    public JButton buttonGenerator(String text, Color color, Font font) {
        JButton b = new JButton(text);
        b.setForeground(color);
        b.setBackground(Color.black);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setFont(font);
        return b;
    }
    /**
     * gibt transparentes Label mit Text, Schriftfarbe und Schriftart zurück
     * @param text Inhalt des JLabel
     * @param color Farbe des zu erzeugenden JLabels
     * @param font Schrift des zu erzeugenden JLabels
     * @return gibt das mit den gegebenen Parametern erstellte JLabel zurück
     */
    public JLabel labelGenerator(String text, Color color, Font font) {
        JLabel l = new JLabel(text);
        l.setForeground(color);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setFont(font);
        return l;
    }

    /**
     * Lädt die Standardschrift des Spiels
     * @param size ist die Größe der Schrift
     * @return gibt die Standardschrift des Spiels in der gewünschten Größe zurück
     */
    public static Font getFont(int size) {
        return LoadAndSaveData.getFont(size);
    }

    /**
     * setzt die Größe eines Panels, welches von BasicPanel erbt
     */
    public void setSize() {
        this.setMinimumSize(ScreenDimension);
        this.setMaximumSize(ScreenDimension);
        this.setPreferredSize(ScreenDimension);
    }
}


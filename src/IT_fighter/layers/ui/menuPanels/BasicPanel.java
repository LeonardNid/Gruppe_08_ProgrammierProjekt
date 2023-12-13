package IT_fighter.layers.ui.menuPanels;

import IT_fighter.utilities.LoadAndSaveData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class BasicPanel extends JPanel {

    //Screen variables
    public static final Dimension ScreenDimension = new Dimension(1920, 1056);

    //##################################################################################################################
    //Größen für das GamePanel
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.0f;
    public final static int TILES_IN_WIDTH = 60;
    public final static int TILES_IN_HEIGHT = 33;

    public final static int TILE_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILE_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILE_SIZE * TILES_IN_HEIGHT;
    //##################################################################################################################
    public static final int screenWidth = 1920;//tileSize * maxScreenCol; //1280 pixels -angestrebt 1280
    public static final int screenHeight = 1080;//tileSize * maxScreenRow;// 704 pixels -angestrebt 720
    public static final int TITLE_WIDTH = 600;
    public static final int BUTTON_WIDTH = 550;
    public static final int BUTTON_HEIGHT = 100;
    public static final int BUTTON_GAP_SIZE = 80;
    public static final int BUTTON_WIDTH_MAIN_MENU = 250;
    public static final int ButtonStandardSize = 70;

    // gibt transparenten Button mit Text, Schriftfarbe und Schriftart zurück
    public JButton buttonGenerator(String text, Color color, Font font) {
        JButton b = new JButton(text);
        b.setForeground(color);
        b.setBackground(Color.black);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setFont(font);

        return b;
    }

    // gibt transparentes Label mit Text, Schriftfarbe und Schriftart zurück
    public JLabel labelGenerator(String text, Color color, Font font) {
        JLabel l = new JLabel(text);
        l.setForeground(color);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setFont(font);
        return l;
    }

    // importiert aus Datei eine Schriftart und gibt diese zurück
    public static Font getFont(int size) {
        Font myfont = LoadAndSaveData.getFont(size);
        return myfont;
    }

    public void setSize() {
        this.setMinimumSize(ScreenDimension);
        this.setMaximumSize(ScreenDimension);
        this.setPreferredSize(ScreenDimension);
    }
    public BufferedImage getImage(String source) {
        BufferedImage image = LoadAndSaveData.getImage(source);
        return image;
    }
}


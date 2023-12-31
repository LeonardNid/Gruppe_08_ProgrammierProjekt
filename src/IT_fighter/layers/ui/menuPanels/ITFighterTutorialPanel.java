package IT_fighter.layers.ui.menuPanels;

import IT_fighter.utilities.LoadAndSaveData;

import javax.swing.*;
import java.awt.*;


public class ITFighterTutorialPanel extends ITFighterBasicPanel {
    private Image backgroundImage;
    private JButton close;
    public ITFighterTutorialPanel() {
        setSize();
        backgroundImage = LoadAndSaveData.getImage("ITF_menu_background.jpg");
        backgroundImage = backgroundImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);

    }
    public void setComponents() {
        close = buttonGenerator("Close", Color.red, LoadAndSaveData.getFont(40));
//        JTextPane = new
    }


    private void setLayout() {
        this.setLayout(new BorderLayout());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }

}

package IT_fighter.layers.ui.menuPanels;

import java.awt.*;


public class ITFighterTutorialPanel extends BasicPanel {
    private Image backgroundImage;
    public ITFighterTutorialPanel() {
        setSize();
        backgroundImage = getImage("ITF_menu_background.jpg");
        backgroundImage = backgroundImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }

}

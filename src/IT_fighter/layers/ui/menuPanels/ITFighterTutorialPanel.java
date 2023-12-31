package IT_fighter.layers.ui.menuPanels;

import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.utilities.LoadAndSaveData;
import IT_fighter.utilities.UtilMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * zeigt Bedienungshinweise für das Spiel an.
 */
public class ITFighterTutorialPanel extends ITFighterBasicPanel {
    private final BufferedImage tutorialImage;

    /**
     * Konstruktor lädt das für das TutorialPanel
     */
    public ITFighterTutorialPanel() {
        setSize();
        tutorialImage = LoadAndSaveData.getImage("ITF_tutorial_image.jpg");
        setComponents();
    }

    /**
     * setzt das tutorialImage, erzeugt den Close-Button und setzt das Layout für das Panel
     */
    public void setComponents() {
        //this.setBackground(Color.black);
        this.setBackground(new Color(38, 38, 38));
        JPanel tutorialImagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(tutorialImage, 0,0, tutorialImage.getWidth(), tutorialImage.getHeight(), null);
            }
        };
        tutorialImagePanel.setPreferredSize(new Dimension(tutorialImage.getWidth(), tutorialImage.getHeight()));
        JButton close = buttonGenerator("Close", Color.red, LoadAndSaveData.getFont(50));
        close.setPreferredSize(new Dimension(100,100));
        close.setAlignmentY(JComponent.TOP_ALIGNMENT);
        close.addActionListener(e -> {
            ITFighterGuiController.getInstance().createMenuPanel();
        });
        this.setLayout(new BorderLayout());
        this.add(tutorialImagePanel, BorderLayout.CENTER);
        this.add(close, BorderLayout.SOUTH);
    }
}

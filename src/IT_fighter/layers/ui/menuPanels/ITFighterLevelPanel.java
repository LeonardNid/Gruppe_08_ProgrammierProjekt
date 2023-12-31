package IT_fighter.layers.ui.menuPanels;

import IT_fighter.layers.ui.ctrl.ITFighterGuiController;

import javax.swing.*;
import java.awt.*;


/**
 * Das LevelPanel ermöglicht dem Nutzer zwischen den Schwierigkeitsgraden leicht, mittel und schwer zu wählen
 */
public class ITFighterLevelPanel extends ITFighterBasicPanel {

    //Buttons des Panels
    private JButton level1_button, level2_button, level3_button, back_button;

    /**
     * erzeugt eine Level-Übersicht inform eines JPanels
     */
    public ITFighterLevelPanel() {
        setSize();
        //buttons erzeugen
        setButtons();
        //Layout erzeugten
        setLayout();
        //Hintergrund setzen
        this.setBackground(Color.BLACK);

    }

    /**
     * erzeugt das Layout für das LevelPanel und fügt alle Komponenten dem Layout hinzu
     */
    private void setLayout() {
        BoxLayout levelPanelLayout = new BoxLayout(this, BoxLayout.X_AXIS);
        this.setLayout(levelPanelLayout);
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.add(Box.createVerticalGlue());
        buttonContainer.add(Box.createVerticalGlue());
        buttonContainer.add(level1_button);
        buttonContainer.add(Box.createVerticalGlue());
        buttonContainer.add(level2_button);
        buttonContainer.add(Box.createVerticalGlue());
        buttonContainer.add(level3_button);
        buttonContainer.add(Box.createVerticalGlue());
        buttonContainer.add(back_button);
        buttonContainer.add(Box.createVerticalGlue());
        buttonContainer.add(Box.createVerticalGlue());
        buttonContainer.setBackground(Color.BLACK);

        this.add(Box.createHorizontalGlue());
        this.add(buttonContainer);
        this.add(Box.createHorizontalGlue());
    }

    /**
     * erzeugt die 4 Buttons des LevelPanels und setzt deren ActionListener
     */
    private void setButtons() {
        level1_button = buttonGenerator("EASY", Color.GREEN, getFont(ButtonStandardSize));
        level2_button = buttonGenerator("NORMAL", Color.YELLOW, getFont(ButtonStandardSize));
        level3_button = buttonGenerator("HARD", new Color(195,78,1), getFont(ButtonStandardSize));
        back_button = buttonGenerator("CLOSE", Color.RED, getFont(ButtonStandardSize));
        //setzen der ActionListener
        back_button.addActionListener(e -> ITFighterGuiController.getInstance().createMenuPanel());
        level1_button.addActionListener(e -> ITFighterGuiController.getInstance().createLevel1());
        level2_button.addActionListener(e -> ITFighterGuiController.getInstance().createLevel2());
        level3_button.addActionListener(e -> ITFighterGuiController.getInstance().createLevel3());
    }
}

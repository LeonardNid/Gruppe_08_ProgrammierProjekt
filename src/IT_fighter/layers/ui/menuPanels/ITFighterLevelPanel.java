package IT_fighter.layers.ui.menuPanels;

import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.layers.ui.menuPanels.BasicPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ITFighterLevelPanel extends BasicPanel {
    private JButton level1_button, level2_button, level3_button, back_button;

    public ITFighterLevelPanel() {
        setSize();
        //buttons erzeugen
        setButtons();
        //Layout erzeugten
        setLayout();
        //Hintergrund setzen
        this.setBackground(Color.BLACK);

    }

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

    private void setButtons() {

        level1_button = buttonGenerator("EASY", Color.GREEN, getFont(ButtonStandardSize));

        level2_button = buttonGenerator("NORMAL", Color.YELLOW, getFont(ButtonStandardSize));
        level3_button = buttonGenerator("HARD", new Color(195,78,1), getFont(ButtonStandardSize));
        back_button = buttonGenerator("CLOSE", Color.RED, getFont(ButtonStandardSize));

        back_button.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  ITFighterGuiController.getInstance().createMenuPanel();
              }
          });
        level1_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().createLevel1();
            }
        });
        level2_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().createLevel2();
            }
        });
        level3_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().createLevel3();
            }
        });
    }
}

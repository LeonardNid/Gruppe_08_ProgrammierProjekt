package de.Luca.ScamOrNot.UI;

import de.Luca.ScamOrNot.Logic.Options;
import de.Luca.ScamOrNot.Logic.main;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.round;

public class OptionsUI {
    public static void init() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel volumenPnl = new JPanel();
        volumenPnl.setLayout(new BoxLayout(volumenPnl, BoxLayout.X_AXIS));
        volumenPnl.setPreferredSize(new Dimension(300, 300));

        JLabel sliderLbl = new JLabel("Sound volume:");
        sliderLbl.setFont(main.pixelFont.deriveFont(20f));
        sliderLbl.setForeground(Color.DARK_GRAY);
        volumenPnl.add(sliderLbl);

        JSlider test = new JSlider();
        test.setPreferredSize(new Dimension(100, 10));
        test.setMinimum(0);
        test.setValue((int) round(Options.getVolume()*0.03));
        test.setMaximum(100);
        volumenPnl.add(test);

        JCheckBox test2 = new JCheckBox();
        test2.setText("Full Mute");
        test2.setSelected(Options.getMute());
        volumenPnl.add(test2);

        panel.add(volumenPnl);

        JPanel difficultyPnl = new JPanel();
        difficultyPnl.setLayout(new BoxLayout(difficultyPnl, BoxLayout.X_AXIS));
        difficultyPnl.setPreferredSize(new Dimension(300, 300));

        JLabel difficultyLbl = new JLabel("Difficulty: ");
        difficultyLbl.setFont(main.pixelFont.deriveFont(20f));
        difficultyLbl.setForeground(Color.DARK_GRAY);
        difficultyPnl.add(difficultyLbl);

        String[] difficulty = {"Easy", "Normal", "Hard"};
        JComboBox test3 = new JComboBox(difficulty);
        test3.setSelectedItem(Options.getDifficulty());

        difficultyPnl.add(test3);

        panel.add(volumenPnl);
        panel.add(difficultyPnl);

        MainFrame.addScreen(panel, "OptionsUI");
        MainFrame.showScreen("OptionsUI");
    }

}

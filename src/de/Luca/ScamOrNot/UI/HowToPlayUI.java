package de.Luca.ScamOrNot.UI;

import de.Luca.ScamOrNot.Logic.main;

import javax.swing.*;
import java.awt.*;


public class HowToPlayUI {

    public static void init() {
        displayGUI();
    }

    private static void displayGUI() {
        UIManager um=new UIManager();
        um.put("OptionPane.background",Color.BLACK);
        um.put("Panel.background",Color.BLACK);
        JOptionPane.showMessageDialog(null, getPanel(),"How to play.exe",
                JOptionPane.PLAIN_MESSAGE);

        um.put("OptionPane.background",Color.WHITE);
        um.put("Panel.background",Color.WHITE);
    }

    private static JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        JLabel label = new JLabel("ADD HOW TO PLAY");
        label.setFont(main.pixelFont.deriveFont(30f));
        label.setForeground(Color.green);
        panel.add(label);

        return panel;
    }

}


package de.Luca.ScamOrNot.UI;

import de.Luca.ScamOrNot.Logic.main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);

        List<String> strings = new ArrayList<>();
        strings.add("Scam or Not?");
        strings.add("");
        strings.add("Erkenne und entscheide, ob eine Email eine reguläre, phishing oder scam Email ist.");
        strings.add("Pass auf, auf welche Links und Anhänge du klickst.");
        strings.add("");
        strings.add("user@MacBook-Pro ~ % ");

        for(String st : strings) {
            JLabel label = new JLabel(st + "\n");
            label.setFont(main.pixelFont.deriveFont(16f));
            label.setForeground(Color.green);
            panel.add(label);
        }

        return panel;
    }

}


package de.Luca.ScamOrNot.UI;

import de.Luca.ScamOrNot.Logic.Options;
import de.Luca.ScamOrNot.Logic.main;
import de.Luca.ScamOrNot.UI.CustomElements.CustomSlider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class OptionsUI {

    public static void init() {
        displayGUI();
    }

    private static void displayGUI() {
        UIManager um=new UIManager();
        um.put("OptionPane.background",Color.BLACK);
        um.put("Panel.background",Color.BLACK);
        int result = JOptionPane.showConfirmDialog(null, getPanel(),"Options.exe", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if(result == JOptionPane.OK_OPTION) {
            Options.setMute(mute);
            Options.setVolume(vol);
            Options.setDifficulty(diff);
            Options.reloadOptions();
        }

        um.put("OptionPane.background",Color.WHITE);
        um.put("Panel.background",Color.WHITE);
    }

    private static boolean mute = Options.getMute();
    private static int vol = Options.getVolume();
    private static String diff = Options.getDifficulty();

    private static JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);

        JPanel volumenPnl = new JPanel();
        volumenPnl.setLayout(new BoxLayout(volumenPnl, BoxLayout.X_AXIS));

        JLabel sliderLbl = new JLabel("Sound volume:");
        sliderLbl.setFont(main.pixelFont.deriveFont(20f));
        sliderLbl.setForeground(Color.WHITE);
        volumenPnl.add(sliderLbl);


        JSlider slider = new JSlider() {
            @Override
            public void updateUI() {
                setUI(new CustomSlider(this));
            }
        };
        slider.setMinimum(0);
        slider.setMaximum(30);
        slider.setValue(vol);
        slider.addChangeListener(e -> vol = slider.getValue());
        volumenPnl.add(slider);

        JCheckBox fullMuteBtn = new JCheckBox();
        fullMuteBtn.setText("Full Mute");
        fullMuteBtn.setForeground(Color.WHITE);
        fullMuteBtn.setSelected(mute);
        fullMuteBtn.addItemListener(e -> {
            mute = fullMuteBtn.isSelected();
        });
        volumenPnl.add(fullMuteBtn);

        panel.add(volumenPnl);

        JPanel difficultyPnl = new JPanel();
        difficultyPnl.setLayout(new BoxLayout(difficultyPnl, BoxLayout.X_AXIS));

        JLabel difficultyLbl = new JLabel("Difficulty: ");
        difficultyLbl.setFont(main.pixelFont.deriveFont(20f));
        difficultyLbl.setForeground(Color.WHITE);
        difficultyPnl.add(difficultyLbl);

        String[] difficulty = {"Easy", "Normal", "Hard"};
        JComboBox difficultyBox = new JComboBox(difficulty);
        difficultyBox.setSelectedItem(diff);

        difficultyBox.addItemListener(event -> {
            String item = (String) event.getItem();
            if (event.getStateChange() == ItemEvent.SELECTED) {
                System.out.println(item);
                diff = item;
            }
        });


        difficultyPnl.add(difficultyBox);

        panel.add(volumenPnl);
        panel.add(difficultyPnl);

        return panel;
    }
}
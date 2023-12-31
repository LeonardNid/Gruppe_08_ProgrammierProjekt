package de.Luca.ScamOrNot.UI;

import Main.Main;
import de.Luca.ScamOrNot.Logic.Logic;
import de.Luca.ScamOrNot.Logic.MusicPlayer;
import de.Luca.ScamOrNot.Logic.main;
import de.Luca.ScamOrNot.UI.CustomElements.BackgroundPanel;
import de.Luca.ScamOrNot.UI.CustomElements.ImageButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenueUI {
    public static void init() {

        BackgroundPanel backgroundPanel = new BackgroundPanel("ScamOrNot/background.png");
        backgroundPanel.setLayout(new BorderLayout());

        JButton startGameBtn = new ImageButton("ScamOrNot/Icons/Buttons/play.png", 96, 96, "Start", 20f);
        startGameBtn.addActionListener(e -> {
            GameUI.init(Logic.getRandomMail());
        });
        JButton howToPlayBtn = new ImageButton("ScamOrNot/Icons/Buttons/placeholder.png", 96, 96, "How To?", 20f);
        howToPlayBtn.addActionListener(e -> {
            HowToPlayUI.init();
        });
        JButton optionsBtn = new ImageButton("ScamOrNot/Icons/Buttons/credits.png", 96, 96, "Options", 20f);
        optionsBtn.addActionListener(e -> {
            OptionsUI.init();
        });
        JButton quitBtn = new ImageButton("ScamOrNot/Icons/Buttons/quit.png", 96, 96, "Quit", 20f);
        quitBtn.addActionListener(e -> {
            MainFrame.GameFrame.dispose();
            main.player.stop(true);
            Main.setMainFrameVisible();
        });

        JPanel oberesPanel = new JPanel();
        oberesPanel.setLayout(new BoxLayout(oberesPanel, BoxLayout.Y_AXIS));
        oberesPanel.setOpaque(false);

        oberesPanel.add(startGameBtn);
        oberesPanel.add(howToPlayBtn);
        oberesPanel.add(optionsBtn);
        oberesPanel.add(quitBtn);

        backgroundPanel.add(oberesPanel, BorderLayout.NORTH);

        backgroundPanel.add(getTaskleiste(), BorderLayout.SOUTH);

        MainFrame.addScreen(backgroundPanel, "MainUI");
        MainFrame.showScreen("MainUI");
    }

    private static JPanel getTaskleiste() {
        JPanel unteresPanel = new JPanel();
        unteresPanel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.decode("#38135c")));
        unteresPanel.setLayout(new BorderLayout());

        JLabel time = new JLabel();
        time.setFont(main.pixelFont.deriveFont(18f));
        time.setForeground(Color.WHITE);
        time.setText("12:00");

        JLabel date = new JLabel();
        date.setFont(main.pixelFont.deriveFont(18f));
        date.setForeground(Color.WHITE);
        date.setText("01. Jan. 1970");

        JPanel timeDatePanel = new JPanel();
        timeDatePanel.setOpaque(false);
        timeDatePanel.setLayout(new BoxLayout(timeDatePanel, BoxLayout.Y_AXIS));

        time.setAlignmentX(Component.RIGHT_ALIGNMENT);
        date.setAlignmentX(Component.RIGHT_ALIGNMENT);

        timeDatePanel.add(Box.createVerticalGlue());
        timeDatePanel.add(time);
        timeDatePanel.add(date);
        timeDatePanel.add(Box.createVerticalGlue());

        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String timeStr = dateFormat.format(new Date());
            time.setText(timeStr);

            dateFormat = new SimpleDateFormat("dd. MMM yyyy");
            String dateStr = dateFormat.format(new Date());
            date.setText(dateStr);
        });
        timer.start();

        EmptyBorder emptyBorder = new EmptyBorder(0, 0, 0, 30);
        timeDatePanel.setBorder(emptyBorder);

        unteresPanel.add(timeDatePanel, BorderLayout.EAST);

        unteresPanel.setPreferredSize(new Dimension(unteresPanel.getWidth(), 70));
        unteresPanel.setBackground(Color.decode("#2b0f47"));

        return unteresPanel;
    }
}

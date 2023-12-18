package de.Luca.ScamOrNot.UI;


import de.Luca.ScamOrNot.Logic.Email;
import de.Luca.ScamOrNot.Logic.MusicPlayer;
import de.Luca.ScamOrNot.Logic.main;

import javax.swing.*;
import java.awt.*;

public class EndScreenUI {

    /*
    reason 0 -> no emails left
    reason 1 -> klicked on dangerous attachment
    reason 2 -> marked phishing email as normal
    reason 3 -> marked scam email as normal
    reason 4 -> clicked on suspecious and dangerous link
    reason 5 -> run out of time
     */

    public static void init(int reason, String explanation) {
        main.player.stop(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        JLabel theEnd = new JLabel("YOU LOST!");
        theEnd.setFont(new Font("Arial", Font.BOLD, 24));
        theEnd.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(theEnd);

        JLabel reasonLbl = new JLabel(getReasonText(reason));
        reasonLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        reasonLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(reasonLbl);

        if (!explanation.isEmpty()) {
            JLabel explanationLbl = new JLabel("<html><body style='text-align: center;'>" + "Explanation of the last email: " + explanation + "</body></html>");
            explanationLbl.setFont(new Font("Arial", Font.PLAIN, 14));
            explanationLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            explanationLbl.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0)); // Add padding
            contentPanel.add(explanationLbl, BorderLayout.CENTER); // Add the explanation label to the center
        }

        JButton backToMain = new JButton("Back to Desktop");
        backToMain.setFont(new Font("Arial", Font.PLAIN, 16));
        backToMain.addActionListener(e -> {
            main.player = new MusicPlayer(main.loaded_tracks);
            main.player.play();
            Email.init();
            MainMenueUI.init();
        });
        backToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(backToMain);

        panel.add(contentPanel, BorderLayout.CENTER);

        MainFrame.addScreen(panel, "EndScreenUI");
        MainFrame.showScreen("EndScreenUI");
    }

    private static String getReasonText(int reason) {
        switch (reason) {
            case 0:
                return "You've read all emails for today!";
            case 1:
                return "You downloaded a virus through an attachment. You've been fired.";
            case 2:
                return "You fell for a phishing email, and all company data was stolen. You've been fired!";
            case 3:
                return "You fell for a scam email and got fired!";
            case 4:
                return "You clicked on a dangerous link, infecting the company network with a trojan. You've been fired!";
            case 5:
                return "You've run out of time...";
            default:
                return "";
        }
    }
}
package de.Luca.ScamOrNot.UI;


import de.Luca.ScamOrNot.Logic.main;

import javax.swing.*;

public class EndScreenUI {

    /*
    reason 0 -> no emails left
    reason 1 -> klicked on dangerous attachment
    reason 2 -> marked phishing email as normal
    reason 3 -> marked scam email as normal
    reason 4 -> clicked on suspecious and dangerous link
     */

    public static void init(int reason) {
        JPanel panel = new JPanel();

        main.player.stop();

        JLabel test = new JLabel();

        if(reason == 0) {
            test.setText("Du hast alle Mails für heute gelesen!");
        }
        else if(reason == 1) {
            test.setText("Du hast durch einen Anhang einen Virus heruntergeladen. Du wurdest gekündigt");
        }
        else if(reason == 2) {
            test.setText("Du bist auf eine Phishing Email reingefallen und alle Daten der Firma wurden geklaut. Du wurdest gekündigt!");
        }
        else if(reason == 3) {
            test.setText("Du bist auf eine Scam Email reingefallen und wurdest gekündigt!");
        }
        else if(reason == 4) {
            test.setText("Du hast einen gefährlichen Link angedrückt und das Firmennetzwerk mit einem Trojaner infiziert. Du wurdest gekündigt!");
        }

        panel.add(test);

        MainFrame.addScreen(panel, "EndScreenUI");
        MainFrame.showScreen("EndScreenUI");
    }
}

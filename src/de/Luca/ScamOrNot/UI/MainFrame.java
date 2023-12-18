package de.Luca.ScamOrNot.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class MainFrame {

    /*
    Case Diagramm:
    normal mail              -> right choice -> next mail
                             -> wrong choice -> popup -> next mail
    phishing mail            -> right choice -> popup -> next mail
                             -> wrong choice -> game over
    scam mail                -> right choice -> popup -> next mail
                             -> wrong choice -> game over
    dangerous attachment     -> game over
    not dangerous attachment -> popup


     */
    public static JFrame GameFrame = new JFrame("Scam or not?");
    public static Container GameFrameContainer = GameFrame.getContentPane();
    public static CardLayout GameCardLayout = new CardLayout();

    public static HashSet<String> Screens = new HashSet<>();

    public static ArrayList<String> dangerousExtension = new ArrayList<>();

    public static void init() {
        GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameFrame.getContentPane().setLayout(GameCardLayout);

        GameFrame.setSize(1080, 1920);
        GameFrame.setLocationRelativeTo(null);
        GameFrame.setUndecorated(true);
        GameFrame.setVisible(true);

        GameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        dangerousExtension.add("exe");
        dangerousExtension.add("iso");
        dangerousExtension.add("jar");
        dangerousExtension.add("sys");
        dangerousExtension.add("dmg");

        GameFrame.validate();
        GameFrame.setVisible(true);
    }

    public static void addScreen(JPanel Panel, String Name) {
        if(!Screens.contains(Name)) {
            MainFrame.GameFrameContainer.add(Name, Panel);
            Screens.add(Name);
        }
    }

    public static void showScreen(String Name) {
        if(Screens.contains(Name)) {
            MainFrame.GameCardLayout.show(MainFrame.GameFrameContainer, Name);
            refresh();
        }
    }

    public static void refresh() {
        MainFrame.GameFrame.validate();
        MainFrame.GameFrame.setVisible(true);
    }

}

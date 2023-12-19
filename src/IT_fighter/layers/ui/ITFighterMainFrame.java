package IT_fighter.layers.ui;

import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import Main.Main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class ITFighterMainFrame extends JFrame {

    public ITFighterMainFrame() {
        this.setTitle("ITFighter");
        this.setResizable(false);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                ITFighterGuiController.getInstance().lostWindowFoucs();
            }
        });
    }

    /**
    public static JPanel getBackroundPanel() {

    }*/
}


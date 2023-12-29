package IT_fighter.layers.ui;


import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.utilities.LoadAndSaveData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static IT_fighter.utilities.LoadAndSaveData.getImage;

public class ITFighterTiktokPanel extends JPanel {
    private BufferedImage tiktokPhone = getImage("ITF_tiktok_phone.jpg");
    public ITFighterTiktokPanel() {

        this.setPreferredSize(new Dimension(tiktokPhone.getWidth(), tiktokPhone.getHeight()));
        this.setLayout(new BorderLayout());
        JButton close = new JButton("Close");
        close.setPreferredSize(new Dimension(200, 70));
        close.setFont(LoadAndSaveData.getFont(30));
        close.setForeground(Color.GREEN);
        close.setOpaque(false);
        close.setFocusPainted(false);
        close.setBorderPainted(false);
        close.setContentAreaFilled(false);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().removeTiktokPanel();
            }
        });
        this.add(close, BorderLayout.SOUTH);
        this.setOpaque(false);

    }
    @Override
    public void paintComponent(Graphics graphics) {
        graphics.drawImage(tiktokPhone, 0, 0, tiktokPhone.getWidth(), tiktokPhone.getHeight(), null);
    }





}

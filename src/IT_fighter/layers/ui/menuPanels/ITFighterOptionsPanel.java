package IT_fighter.layers.ui.menuPanels;

import IT_fighter.layers.ui.ctrl.ITFighterGuiController;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static IT_fighter.utilities.UtilMethods.createGap;


public class ITFighterOptionsPanel extends BasicPanel {
    private JLabel soundLabel, gameMusicLabel, gameSoundLabel;
    private JButton gameMusicVolumeUpBtn, gameMusicVolumeDownBtn, gameSoundVolumeUpBtn, gameSoundVolumeDownBtn, backBtn;
    public ITFighterOptionsPanel(){
        initOptionsPanel();
    }
    private void initOptionsPanel() {
        setSize();
        backgroundImage = getImage("ITF_menu_background.jpg");
        backgroundImage = backgroundImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);

        setButtons();
        setLabels();
        setLayout();
        setActionListeners();
    }
    private void setLabels() {
        soundLabel = labelGenerator("Sound", Color.WHITE, getFont(70));
        soundLabel.setHorizontalAlignment(JLabel.LEFT);
        soundLabel.setVerticalAlignment(JLabel.NORTH);
        gameMusicLabel= labelGenerator("Background music", Color.WHITE, getFont(40));
        gameSoundLabel = labelGenerator("Game sound", Color.WHITE, getFont(40));
    }

    private void setButtons() {
        gameMusicVolumeUpBtn = buttonGenerator("+", Color.WHITE, getFont(40));
        gameMusicVolumeDownBtn = buttonGenerator("-", Color.WHITE, getFont(40));
        gameSoundVolumeUpBtn = buttonGenerator("Hallo", Color.WHITE, getFont(40));
        gameSoundVolumeDownBtn = buttonGenerator("-", Color.WHITE, getFont(40));
        backBtn = buttonGenerator("Back", Color.RED,getFont(70));

    }
    private void setLayout() {
        this.setLayout(new BorderLayout());
        //Lücke Links
        JPanel leftGap = new JPanel();
        leftGap.setBackground(Color.RED);
        leftGap.setPreferredSize(new Dimension(300,100));
        this.add(leftGap,BorderLayout.WEST);

        JPanel rightGap = new JPanel();
        rightGap.setBackground(Color.BLUE);
        rightGap.setPreferredSize(new Dimension(300,100));
        this.add(rightGap, BorderLayout.EAST);

        JPanel upGap = new JPanel();
        upGap.setBackground(Color.yellow);
        upGap.setPreferredSize(new Dimension(300, 100));
        this.add(upGap, BorderLayout.NORTH);

        JPanel downGap = new JPanel();
        downGap.setBackground(Color.gray);
        downGap.setPreferredSize(new Dimension(300,100));
        this.add(downGap, BorderLayout.SOUTH);


        //erstellen der notwendigen Panels
        JPanel gameSoundPanel = new JPanel();
        gameSoundPanel.setOpaque(false);
        gameSoundPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        gameSoundPanel.add(gameSoundVolumeUpBtn);
        gameSoundPanel.add(gameSoundVolumeDownBtn);

        JPanel gameMusicPanel = new JPanel();
        gameMusicPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        gameMusicPanel.add(gameMusicVolumeUpBtn);
        gameMusicPanel.add(gameMusicVolumeDownBtn);


        JPanel standardGap1 = createGap(new Dimension(300,100));
        JPanel standardGap2 = createGap(new Dimension());
        standardGap1.setBackground(Color.green);



        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridLayout(4,2));
        centerPanel.add(soundLabel);
        centerPanel.add(standardGap1);
        centerPanel.add(gameMusicLabel);
        centerPanel.add(gameMusicPanel);
        centerPanel.add(gameSoundLabel);
        centerPanel.add(gameSoundPanel);
        centerPanel.add(backBtn);
        centerPanel.add(standardGap2);
        this.add(centerPanel, BorderLayout.CENTER);



    }
    private void setActionListeners() {
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().createMenuPanel();
            }
        });

    }




    private Image backgroundImage;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }


}

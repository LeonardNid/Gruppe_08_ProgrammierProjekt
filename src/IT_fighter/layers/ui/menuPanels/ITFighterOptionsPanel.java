package IT_fighter.layers.ui.menuPanels;

import IT_fighter.layers.app.Sound.ITFighterSoundManager;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.utilities.LoadAndSaveData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static IT_fighter.utilities.UtilMethods.createGap;

/**
 * Das OptionsPanel bietet die Möglichkeit die Lautstärke der Spielmusik und der Soundeffekte zu
 * regulieren
 */
public class ITFighterOptionsPanel extends ITFighterBasicPanel {
    private JLabel soundLabel, gameMusicLabel, gameSoundLabel;
    private JButton gameMusicVolumeUpBtn, gameMusicVolumeDownBtn, gameSoundVolumeUpBtn, gameSoundVolumeDownBtn, backBtn;
    private Image backgroundImage;

    /**
     *
     */
    public ITFighterOptionsPanel(){
        initOptionsPanel();
    }

    /**
     *
     */
    private void initOptionsPanel() {
        setSize();
        backgroundImage = LoadAndSaveData.getImage("ITF_menu_background.jpg");
        backgroundImage = backgroundImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);

        setButtons();
        setLabels();
        setLayout();
        setActionListeners();
    }

    /**
     *
     */
    private void setLabels() {
        soundLabel = labelGenerator("Sound", new Color(63, 75, 85), getFont(70));
        soundLabel.setHorizontalAlignment(JLabel.LEFT);
        gameMusicLabel= labelGenerator("Background music", Color.WHITE, getFont(40));
        gameMusicLabel.setHorizontalAlignment(JLabel.LEFT);
        gameMusicLabel.setVerticalAlignment(JLabel.NORTH);
        gameSoundLabel = labelGenerator("Game sound", Color.WHITE, getFont(40));
        gameSoundLabel.setHorizontalAlignment(JLabel.LEFT);
        gameSoundLabel.setVerticalAlignment(JLabel.NORTH);
    }
    /**
     *
     */
    private void setButtons() {
        gameMusicVolumeUpBtn = buttonGenerator("+", Color.BLACK, getFont(40));
        gameMusicVolumeUpBtn.setBackground(Color.WHITE);
        gameMusicVolumeUpBtn.setHorizontalAlignment(JButton.LEFT);
        gameMusicVolumeUpBtn.setVerticalAlignment(JButton.NORTH);

        gameMusicVolumeDownBtn = buttonGenerator("-", Color.BLACK, getFont(40));
        gameMusicVolumeDownBtn.setBackground(Color.white);
        gameMusicVolumeDownBtn.setHorizontalAlignment(JButton.LEFT);
        gameMusicVolumeDownBtn.setVerticalAlignment(JButton.NORTH);

        gameSoundVolumeUpBtn = buttonGenerator("+", Color.BLACK, getFont(40));
        gameSoundVolumeUpBtn.setBackground(Color.white);
        gameSoundVolumeUpBtn.setHorizontalAlignment(JButton.LEFT);
        gameSoundVolumeUpBtn.setVerticalAlignment(JButton.NORTH);

        gameSoundVolumeDownBtn = buttonGenerator("-", Color.BLACK, getFont(40));
        gameSoundVolumeDownBtn.setBackground(Color.white);
        gameSoundVolumeDownBtn.setHorizontalAlignment(JButton.LEFT);
        gameSoundVolumeDownBtn.setVerticalAlignment(JButton.NORTH);

        backBtn = buttonGenerator("Back", Color.RED, getFont(60));
        backBtn.setHorizontalAlignment(JButton.LEFT);
        backBtn.setVerticalAlignment(JButton.NORTH);
        backBtn.setOpaque(false);
    }

    /**
     *
     */
    private void setLayout() {
        this.setLayout(new BorderLayout());
        //Lücken erstellen
        //Lücke links
        JPanel leftGap = new JPanel();
        leftGap.setOpaque(false);
        leftGap.setPreferredSize(new Dimension(300,100));
        this.add(leftGap,BorderLayout.WEST);
        //Lücke rechts
        JPanel rightGap = new JPanel();
        rightGap.setOpaque(false);
        rightGap.setPreferredSize(new Dimension(300,100));
        this.add(rightGap, BorderLayout.EAST);
        //Lücke zum oberen Bildschirmrand
        JPanel upGap = new JPanel();
        upGap.setOpaque(false);
        upGap.setPreferredSize(new Dimension(300, 100));
        this.add(upGap, BorderLayout.NORTH);
        //Lücke zum unteren Bildrand
        JPanel downGap = new JPanel();
        downGap.setOpaque(false);
        downGap.setPreferredSize(new Dimension(300,100));
        this.add(downGap, BorderLayout.SOUTH);

        //erstellen der notwendigen Panels, um die Sounds zu steuern
        JPanel gameSoundPanel = new JPanel();
        gameSoundPanel.setOpaque(false);
        gameSoundPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        gameSoundPanel.add(gameSoundVolumeUpBtn);
        gameSoundPanel.add(gameSoundVolumeDownBtn);

        JPanel gameMusicPanel = new JPanel();
        gameMusicPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        gameMusicPanel.setOpaque(false);
        gameMusicPanel.add(gameMusicVolumeUpBtn);
        gameMusicPanel.add(gameMusicVolumeDownBtn);

        JPanel standardGap1 = createGap(new Dimension(300,100));
        JPanel standardGap2 = createGap(new Dimension());

        //beinhaltet alle Componenten
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridLayout(4,2, 10, 20));
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

    /**
     * erstellt die ActionListeners für die Buttons des OptionsPanels
     */
    private void setActionListeners() {
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().createMenuPanel();
            }
        });
        gameMusicVolumeDownBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().setSoundVolume(ITFighterSoundManager.GAMEMUSIC, ITFighterSoundManager.DOWN);
            }
        });
        gameMusicVolumeUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().setSoundVolume(ITFighterSoundManager.GAMEMUSIC, ITFighterSoundManager.UP);
            }
        });
        gameSoundVolumeDownBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().setSoundVolume(ITFighterSoundManager.GAMESOUND, ITFighterSoundManager.DOWN);
            }
        });
        gameSoundVolumeUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().setSoundVolume(ITFighterSoundManager.GAMESOUND, ITFighterSoundManager.UP);
            }
        });
    }
    /**
     * erzeugt ein JLabel im Style der Anwendung
     * @param text ist der Inhalt des Labels
     * @param color ist die Farbe des Labels
     * @param font ist die Schrift des Labels
     * @return gibt das erstellte JLabel zurück
     */
    @Override
    public JLabel labelGenerator(String text, Color color, Font font) {
        JLabel l = new JLabel(text);
        l.setForeground(color);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setFont(font);
        return l;
    }
    /**
     * zeichnet das Hintergrundbild des Optionpanels
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }


}

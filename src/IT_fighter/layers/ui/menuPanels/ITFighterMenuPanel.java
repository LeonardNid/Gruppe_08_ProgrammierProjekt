package IT_fighter.layers.ui.menuPanels;

import IT_fighter.layers.ui.ctrl.ITFighterGuiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ITFighterMenuPanel extends BasicPanel {
    private Image backgroundImage;
    private JButton play_button, tutorial_button, options_button, close_button;
    private JLabel title;
    public ITFighterMenuPanel() {
        setSize();
        backgroundImage = getImage("ITF_menu_background.jpg");
        backgroundImage = backgroundImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);

        //setze Titel
        title = labelGenerator("ITFighter", Color.WHITE, getFont(80));

        //erzeugen der Buttons des Menupanels
        play_button = buttonGenerator("Play", Color.GREEN, getFont(ButtonStandardSize));
        tutorial_button = buttonGenerator("Tutorial", Color.WHITE, getFont(ButtonStandardSize));
        options_button = buttonGenerator("Options", Color.WHITE, getFont(ButtonStandardSize));
        close_button = buttonGenerator("Close", Color.RED, getFont(ButtonStandardSize));

        //setze Action Listner für Buttons
        //TODO alle actionlistner umbauen, so dass Panles imer neu erzeugt werden
        play_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().createLevelPanel();
            }
        });
        tutorial_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().createTutorialPanel();
            }
        });


        options_button.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 ITFighterGuiController.getInstance().createOptionsPanel();
             }
         }

        );
        close_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterGuiController.getInstance().closeITFighterapplication();
            }
        });


        //Erstellen eines Layouts für das MenuPanel inklusive dem Einfügen aller Komponenten
        GroupLayout menuLayout = new GroupLayout(this);
        menuLayout.setAutoCreateContainerGaps(false);
        menuLayout.setAutoCreateGaps(false);
        //TODO Button weiter nach unten versetzen, damit Überschrift lesbar wird
        //Layout des MenuPanels
        menuLayout.setHorizontalGroup(
                menuLayout.createSequentialGroup()
                        .addGroup(menuLayout.createParallelGroup()
                                .addComponent(title, screenWidth/2-BUTTON_WIDTH/2,
                                        screenWidth/2-BUTTON_WIDTH/2, screenWidth/2-BUTTON_WIDTH/2))
//                                .addGap(screenWidth/2-BUTTON_WIDTH/2))
                        .addGroup(menuLayout.createParallelGroup()
                                .addGap(BUTTON_GAP_SIZE*3)
                                .addComponent(play_button, BUTTON_WIDTH, BUTTON_WIDTH, BUTTON_WIDTH)
                                .addGap(BUTTON_GAP_SIZE)
                                .addComponent(tutorial_button, BUTTON_WIDTH, BUTTON_WIDTH, BUTTON_WIDTH)
                                .addGap(BUTTON_GAP_SIZE)
                                .addComponent(options_button, BUTTON_WIDTH, BUTTON_WIDTH, BUTTON_WIDTH)
                                .addGap(BUTTON_GAP_SIZE)
                                .addComponent(close_button, BUTTON_WIDTH, BUTTON_WIDTH, BUTTON_WIDTH)
                        )

        );
        menuLayout.setVerticalGroup(
                menuLayout.createParallelGroup()
                        .addGroup(menuLayout.createSequentialGroup()
                                .addComponent(title, 100, 100, 100))

                        .addGroup(menuLayout.createSequentialGroup()

                                .addGap(BUTTON_GAP_SIZE*3)
                                .addComponent(play_button, BUTTON_HEIGHT, BUTTON_HEIGHT, BUTTON_HEIGHT)
                                .addGap(BUTTON_GAP_SIZE)
                                .addComponent(tutorial_button, BUTTON_HEIGHT, BUTTON_HEIGHT, BUTTON_HEIGHT)
                                .addGap(BUTTON_GAP_SIZE)
                                .addComponent(options_button, BUTTON_HEIGHT, BUTTON_HEIGHT, BUTTON_HEIGHT)
                                .addGap(BUTTON_GAP_SIZE)
                                .addComponent(close_button, BUTTON_HEIGHT, BUTTON_HEIGHT, BUTTON_HEIGHT)
                        )
        );

        this.setLayout(menuLayout);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}

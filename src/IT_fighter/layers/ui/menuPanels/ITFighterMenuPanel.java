package IT_fighter.layers.ui.menuPanels;

import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.utilities.LoadAndSaveData;

import javax.swing.*;
import java.awt.*;


/**
 * Das MenuPanel bietet dem Spieler die Möglichkeit das Spiel zu starten,
 * das Spiel zu schließen, ins OptionsPanel zu kommen oder das TutorialPanel
 * zu öffen
 */
public class ITFighterMenuPanel extends ITFighterBasicPanel {
    private Image backgroundImage;
    private final JButton play_button, tutorial_button, options_button, close_button;
    private final JLabel title;
    /**
     * erzeugt das MenuPanel, erzeugt alle Komponenten und setzt die Komponenten in ein GroupLayout
     */
    public ITFighterMenuPanel() {
        setSize();
        backgroundImage = LoadAndSaveData.getImage("ITF_menu_background.jpg");
        try {
            backgroundImage = backgroundImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        } catch (NullPointerException e) {
            System.out.println("kein Bild zum skalieren erhalten");
        }
        //setze Titel
        title = labelGenerator("ITFighter", Color.WHITE, getFont(80));

        //erzeugen der Buttons des MenuPanels
        play_button = buttonGenerator("Play", Color.GREEN, getFont(ButtonStandardSize));
        tutorial_button = buttonGenerator("Tutorial", Color.WHITE, getFont(ButtonStandardSize));
        options_button = buttonGenerator("Options", Color.WHITE, getFont(ButtonStandardSize));
        close_button = buttonGenerator("Close", Color.RED, getFont(ButtonStandardSize));

        //setze Action Listener für Buttons
        play_button.addActionListener(e -> ITFighterGuiController.getInstance().createLevelPanel());
        tutorial_button.addActionListener(e -> ITFighterGuiController.getInstance().createTutorialPanel());
        options_button.addActionListener(e -> ITFighterGuiController.getInstance().createOptionsPanel());
        close_button.addActionListener(e -> ITFighterGuiController.getInstance().closeITFighterapplication());
        //Erstellen eines Layouts für das MenuPanel inklusive dem Einfügen aller Komponenten
        GroupLayout menuLayout = new GroupLayout(this);
        menuLayout.setAutoCreateContainerGaps(false);
        menuLayout.setAutoCreateGaps(false);
        //Layout des MenuPanels
        menuLayout.setHorizontalGroup(
                menuLayout.createSequentialGroup()
                        .addGroup(menuLayout.createParallelGroup()
                                .addComponent(title, screenWidth/2-BUTTON_WIDTH/2,
                                        screenWidth/2-BUTTON_WIDTH/2, screenWidth/2-BUTTON_WIDTH/2))
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
    /**
     * zeichnet das Hintergrundbild des MenuPanels
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}

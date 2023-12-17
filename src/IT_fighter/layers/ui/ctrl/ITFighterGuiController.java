package IT_fighter.layers.ui.ctrl;

import IT_fighter.layers.app.Game;
import IT_fighter.layers.app.ITFighterAppController;
import IT_fighter.layers.ui.*;
import IT_fighter.layers.ui.menuPanels.ITFighterLevelPanel;
import IT_fighter.layers.ui.menuPanels.ITFighterMenuPanel;
import IT_fighter.layers.ui.menuPanels.ITFighterOptionsPanel;
import IT_fighter.layers.ui.menuPanels.ITFighterTutorialPanel;

import javax.swing.*;
import java.awt.*;

public class ITFighterGuiController {
    /** Der ITFighterGuiController ist "einmalig", darum wird hier mittels Singleton-Pattern
     * dafür gesorgt, dass die Klasse nur einmal instanziiert werden kann.
     */

    //Alle Panel die vom GuiController verwaltet werden
    private static volatile ITFighterGuiController instance;
    private ITFighterAppController mAppFacade;
    private JFrame mainFrame;
    //TODO actualGamePanel muss langfristig hier weg
    private GamePanelController actualGamePanelController;


    //############################################################################################################
    //GuiControler insatanziieren und GUI starten
    private ITFighterGuiController() {
        //singelton-Pattern
        this.mAppFacade = getmAppFacade();
        startGui();
        //this.gamePanel3 = new ITFighterGamePanel3();

    }

    public static synchronized ITFighterGuiController getInstance() {
        if (instance == null) {
            instance = new ITFighterGuiController();
        }
        return instance;
    }

    public void closeGame() {
        changePanel(new ITFighterLevelPanel());
    }

    private void startGui() {
        mAppFacade = ITFighterAppController.getInstance();
        mAppFacade.setmGuiController(this);
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                mainFrame = new ITFighterMainFrame();
                ITFighterMenuPanel menuPanel = new ITFighterMenuPanel();
                mainFrame.getContentPane().add(menuPanel);
                mainFrame.pack();
                mainFrame.setVisible(true);
            }
        });
    }

    //############################################################################################################
    //getter und setter für Attribute

    public ITFighterAppController getmAppFacade() {
        if (mAppFacade == null) {
            return ITFighterAppController.getInstance();
        }
        return mAppFacade;
    }
    public void setmAppFacade(ITFighterAppController appFacade) {
        mAppFacade = appFacade;
    }

    public ITFighterCharacterPanel getCharacterPanel() {
        return new ITFighterCharacterPanel(mAppFacade.getCharacter());
    }




    //############################################################################################################

    public void updateGraphics() {
        actualGamePanelController.renderGame();

    }
    public void setAnimationIndex() {
        actualGamePanelController.setAnimationIndex();
    }


    public void updatePlayerPosition() {

    }

    //TODO in Gamepanel integrieren
    // Stop player bei Focus verlust des Mainframes
    public void lostWindowFoucs() {
        //hi
    }


















    //############################################################################################################
    //Panels erzeugen
    //TODO changePanel vielleicht noch Code sparender machen
    public void changePanel(JPanel panel) {
        mainFrame.getContentPane().removeAll();
        System.out.println(mainFrame.getContentPane().getComponents());
        mainFrame.getContentPane().add(panel);
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.repaint();
        panel.requestFocus();
    }
    //Methoden zum erzeugen von neuen Panels
    public void createMenuPanel() {
        changePanel(new ITFighterMenuPanel());
    }

    public void createTutorialPanel() {
        changePanel(new ITFighterTutorialPanel());

    }

    public void createOptionsPanel() {
        changePanel(new ITFighterOptionsPanel());
    }



    public void createLevelPanel() {
        changePanel(new ITFighterLevelPanel());
    }

    public void createLevel1() {
        //TODO level 1 erstellen

        mAppFacade.setUpLevelOne();
        actualGamePanelController = new GamePanelController(new GamePanel(Game.levelOneData),
                new ITFighterCharacterPanel(ITFighterAppController.getInstance().getActualCharacter()));

        changePanel(actualGamePanelController.getGamePanel());
        mAppFacade.startLevel();

    }

    public void createLevel2() {
        //TODO level 2 erstellen
    }

    public void createLevel3() {
        //TODO level 3 erstellen


    }


    public int getLevelOffset() {
       return mAppFacade.getLevelOffset();
    }
}


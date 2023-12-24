package IT_fighter.layers.ui.ctrl;

import IT_fighter.layers.app.EnemyManager;
import IT_fighter.layers.app.Game;
import IT_fighter.layers.app.ITFighterAppController;
import IT_fighter.layers.app.Sound.SoundManager;
import IT_fighter.layers.ui.*;
import IT_fighter.layers.ui.menuPanels.ITFighterLevelPanel;
import IT_fighter.layers.ui.menuPanels.ITFighterMenuPanel;
import IT_fighter.layers.ui.menuPanels.ITFighterOptionsPanel;
import IT_fighter.layers.ui.menuPanels.ITFighterTutorialPanel;
import Main.Main;

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
    private GamePanelController currentGamePanelController;


    //############################################################################################################
    //GuiControler insatanziieren und GUI starten
    private ITFighterGuiController() {
        //singelton-Pattern
        this.mAppFacade = getmAppFacade();


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
    public void closeITFighterapplication() {
        SoundManager.stopGameMusic();
        mainFrame.dispose();
        Main.createMainFrame();
    }

    public void startGui() {
        System.out.println("Aufruf startGui");
        mAppFacade = ITFighterAppController.getInstance();
        mAppFacade.setmGuiController(this);
        SoundManager.playGameMusic();
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                mainFrame = new ITFighterMainFrame();
                mainFrame.setUndecorated(true);
                ITFighterMenuPanel menuPanel = new ITFighterMenuPanel();
                mainFrame.getContentPane().add(menuPanel);
                mainFrame.pack();
                mainFrame.setVisible(true);
                mainFrame.setLocationRelativeTo(null);
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
    public int getLevelOffset() {
        return mAppFacade.getLevelOffset();
    }

    public void setSoundVolume(int sound, boolean down) {
        mAppFacade.setSoundVolume(sound, down);
    }

    public EnemyManager getCurrentEnemyManager() {
        return mAppFacade.getCurrentEnemyManager();
    }




    //############################################################################################################

    public void updateGraphics() {
        currentGamePanelController.renderGame();

    }
    public void setAnimationIndex() {
        currentGamePanelController.setAnimationIndex();
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
        currentGamePanelController = new GamePanelController(
                new GamePanel(Game.levelOneData),
                new ITFighterCharacterPanel(ITFighterAppController.getInstance().getActualCharacter()),
                new ITFighterEnemyPanel(getCurrentEnemyManager())
                );

        changePanel(currentGamePanelController.getGamePanel());
        mAppFacade.startLevel();

    }

    public void createLevel2() {
        //TODO level 2 erstellen
    }

    public void createLevel3() {
        //TODO level 3 erstellen


    }

    public void setGameOverScreen() {
        currentGamePanelController.setGameOverPanel();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}


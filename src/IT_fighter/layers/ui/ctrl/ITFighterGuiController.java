package IT_fighter.layers.ui.ctrl;

import IT_fighter.layers.app.ITFighterEnemyManager;
import IT_fighter.layers.app.ITFighterGame;
import IT_fighter.layers.app.ITFighterAppController;
import IT_fighter.layers.app.Sound.ITFighterSoundManager;
import IT_fighter.layers.ui.*;
import IT_fighter.layers.ui.menuPanels.ITFighterLevelPanel;
import IT_fighter.layers.ui.menuPanels.ITFighterMenuPanel;
import IT_fighter.layers.ui.menuPanels.ITFighterOptionsPanel;
import IT_fighter.layers.ui.menuPanels.ITFighterTutorialPanel;
import Main.Main;

import javax.swing.*;
import java.awt.*;

public class ITFighterGuiController {
    /**
     * controls all graphical components of the ITFighterApplication and gives logical operations
     * the ITFighterAppController
     */

    //Alle Panel die vom GuiController verwaltet werden
    private static volatile ITFighterGuiController instance;
    private ITFighterAppController mAppFacade;
    private JFrame mainFrame;
    private ITFighterGamePanelController currentGamePanelController;


    //############################################################################################################
    //GuiControler insatanziieren und GUI starten

    /**
     * constructor with singelton-Pattern
     */
    private ITFighterGuiController() {
        //singelton-Pattern
        this.mAppFacade = getmAppFacade();
    }

    /**
     * Because of the singelton-Pattern only one gui controller can exist and this method returns the
     * instance of it
     * @return the gui controller
     */
    public static synchronized ITFighterGuiController getInstance() {
        if (instance == null) {
            instance = new ITFighterGuiController();
        }
        return instance;
    }

    /**
     * shows the LevelPanel after quiting the Game
     */
    public void closeGame() {
        changePanel(new ITFighterLevelPanel());
    }

    /**
     * closes the ITFighterApplication and brings the Player back to the other games
     */
    public void closeITFighterapplication() {
        ITFighterSoundManager.stopGameMusic();
        mainFrame.dispose();
        Main.createMainFrame();
    }

    /**
     * starts the Application, shows the menuPanel and starts the GameMusic
     */
    public void startGui() {
        mAppFacade = ITFighterAppController.getInstance();
        mAppFacade.setmGuiController(this);
        ITFighterSoundManager.playGameMusic();
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

    /**
     *
     * @return the current ITFighterAppController, which is known by the gui controller
     */
    public ITFighterAppController getmAppFacade() {
        if (mAppFacade == null) {
            return ITFighterAppController.getInstance();
        }
        return mAppFacade;
    }
    /**
     * @return the current LevelOffset of the Game
     */
    public int getLevelOffset() {
        return mAppFacade.getLevelOffset();
    }
    /**
     * @param sound 0 or SoundManager.Gamemusic for the changing the gameMusic's Volume
     * @param down if down is true the volume is decreased
     */
    public void setSoundVolume(int sound, boolean down) {
        mAppFacade.setSoundVolume(sound, down);
    }

    /**
     * @return the current EnemyManager of the shown Game
     */
    public ITFighterEnemyManager getCurrentEnemyManager() {
        return mAppFacade.getCurrentEnemyManager();
    }
    //############################################################################################################
    /**
     * is called 120 times per Second by the GameController and repaints all shown graphical components
     */
    public void updateGraphics() {
        currentGamePanelController.renderGame();
    }
    /**
     * calls the setAnimation method of the GamePanelController
     */
    public void setAnimationIndex() {
        currentGamePanelController.setAnimationIndex();
    }
    //############################################################################################################
    //Panels erzeugen

    /**
     * removes all Components from the mainFrame and sets the new Panel inside the Mainframe
     * @param panel is a one Panel of the menuPanels Package or the gamePanel
     */
    public void changePanel(JPanel panel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(panel);
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.repaint();
        panel.requestFocus();
    }
    //Methoden zum erzeugen von neuen Panels

    /**
     * creates a new MenuPanel and set it visible
     */
    public void createMenuPanel() {
        changePanel(new ITFighterMenuPanel());
    }
    /**
     * creates a new MenuPanel and set it visible
     */
    public void createTutorialPanel() {
        changePanel(new ITFighterTutorialPanel());
    }
    /**
     * creates a new OptionPanel and set it visible
     */
    public void createOptionsPanel() {
        changePanel(new ITFighterOptionsPanel());
    }
    /**
     * creates a new LevelPanel and set it visible
     */
    public void createLevelPanel() {
        changePanel(new ITFighterLevelPanel());
    }
    /**
     * creates the logic and graphic for the first level, in the gui called by easy button
     */
    public void createLevel1() {
        mAppFacade.setUpLevelOne();
    }
    /**
     * creates the logic and graphic for the second level, in the gui called by medium button
     */
    public void createLevel2() {
        mAppFacade.setUpLevelTwo();
    }
    /**
     * creates the logic and graphic for the third level, in the gui called by hard button
     */
    public void createLevel3() {
        mAppFacade.setUpLevelThree();
    }
    /**
     * creates the graphics for a Level
     */
    public void createLevelGraphics() {
        currentGamePanelController = new ITFighterGamePanelController(
                new ITFighterGamePanel(ITFighterGame.levelOneData),
                new ITFighterCharacterGraphics(ITFighterAppController.getInstance().getActualCharacter()),
                new ITFighterEnemyGraphics(getCurrentEnemyManager())
        );
        changePanel(currentGamePanelController.getGamePanel());
    }

    /**
     * sets the gameOverScreen to the GamePanel
     */
    public void setGameOverScreen() {
        currentGamePanelController.setGameOverPanel();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * sets the FinishedPanel to the GamePanel
     */
    public void setGameFinishedScreen() {
        currentGamePanelController.setGameFinishedPanel();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * sets the TiktokPanel
     */
    public void setTiktokScreen() {
        currentGamePanelController.setTiktokPanel();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * removes the tiktokPanel from the gamePanel
     */
    public void removeTiktokPanel() {
        new Thread(()->{
            mAppFacade.removeTiktok();
            currentGamePanelController.removeTiktokPanel();
            mainFrame.revalidate();
            mainFrame.repaint();
        }).start();
    }
}


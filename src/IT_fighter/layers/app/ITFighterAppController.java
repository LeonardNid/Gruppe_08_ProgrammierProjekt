package IT_fighter.layers.app;


import IT_fighter.layers.app.Entity.ITFighterCharacter;
import IT_fighter.layers.app.Sound.SoundManager;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;

import java.awt.geom.Rectangle2D;

public class ITFighterAppController {
    private static volatile ITFighterAppController instance;
    private GameController currentGameController;

    private ITFighterGuiController mGuiController;




    //##################################################################################################################

    private ITFighterAppController() {
        //singelton
    }

    public static synchronized ITFighterAppController getInstance() {
        if (instance==null)
            instance = new ITFighterAppController();
        return instance;
    }

    public void setUpLevelOne() {
        currentGameController = new GameController();
        currentGameController.setPlayerSpeed(1.0f);
        //TODO enemy spawn frequency
    }
    public void startLevel() {
        currentGameController.startGame();
    }
    public void startLevelTwo() {

    }
    public void startLevelThree() {

        //TODO enemy spawn frequency
    }





    //##################################################################################################################
    //getter und setter
    public void setmGuiController(ITFighterGuiController guiController) {
        this.mGuiController = guiController;
    }
    public ITFighterCharacter getActualCharacter() {
        return currentGameController.getmCharacter();
    }
    public EnemyManager getCurrentEnemyManager() {return currentGameController.getmEnemyManager();}

    public GameController getCurrentGameController() {
        return currentGameController;
    }
    //##################################################################################################################
    //Zugriff auf Player
    //TODO Zugriff auf Player aus AppController Regeln

    public ITFighterCharacter getCharacter() {
        return currentGameController.getmCharacter();
    }

    public void killPlayer() {
        SoundManager.playKillSound();
        currentGameController.stopGame();
        mGuiController.setGameOverScreen();

    }


    public void closeGame() {
        currentGameController.stopGame();
        mGuiController.closeGame();
    }

    public int getLevelOffset() {
        return currentGameController.getxLevelOffset();
    }

    public void setSoundVolume(int sound, boolean down) {
        if(sound == SoundManager.GAMEMUSIC) {
            SoundManager.setGameMusicVolume(down);
        } else {
            SoundManager.setGameSoundVolume(down);
        }
    }
    public Rectangle2D.Float getCurrentPlayerPosition() {
        return currentGameController.getmCharacter().getHitbox();
    }
}

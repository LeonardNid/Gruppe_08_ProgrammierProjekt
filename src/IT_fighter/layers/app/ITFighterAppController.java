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
        currentGameController.setmCharacter(new ITFighterCharacter(100, 620, 16, 28));
        currentGameController.setPlayerSpeed(1.0f);
        //TODO enemy spawn frequency
    }
    public void startLevel() {
        currentGameController.startGameLoop();
    }
    public void startLevelTwo() {
        currentGameController = new GameController();
        currentGameController.setmCharacter(new ITFighterCharacter(100, 620, (int) 16, (int) 28));
        currentGameController.setPlayerSpeed(2.0f);
        currentGameController.startGameLoop();
        //TODO enemy spawn frequency
    }
    public void startLevelThree() {
        currentGameController = new GameController();
        currentGameController.setmCharacter(new ITFighterCharacter(100, 620, (int) (32 * Game.SCALE),
                (int) (32 * Game.SCALE)));
        currentGameController.setPlayerSpeed(2.0f);
        currentGameController.startGameLoop();
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

    public GameController getCurrentGameController() {
        return currentGameController;
    }
    //##################################################################################################################
    //Zugriff auf Player
    //TODO Zugriff auf Player aus AppController Regeln

    public ITFighterCharacter getCharacter() {
        return currentGameController.getmCharacter();
    }


    public void closeGame() {
        currentGameController.closeGame();
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

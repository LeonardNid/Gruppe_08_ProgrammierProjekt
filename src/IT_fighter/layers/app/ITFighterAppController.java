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
    public int getLevelOffset() {
        return currentGameController.getxLevelOffset();
    }
    //##################################################################################################################
    //Zugriff auf Player
    //TODO Zugriff auf Player aus AppController Regeln
    public Rectangle2D.Float getCurrentPlayerPosition() {
        return currentGameController.getmCharacter().getHitbox();
    }
    public ITFighterCharacter getCharacter() {
        return currentGameController.getmCharacter();
    }
    public void killPlayer() {
        SoundManager.playKillSound();
        currentGameController.stopGame();
        mGuiController.setGameOverScreen();

    }
    public void setSoundVolume(int sound, boolean down) {
        if(sound == SoundManager.GAMEMUSIC) {
            SoundManager.setGameMusicVolume(down);
        } else {
            SoundManager.setGameSoundVolume(down);
        }
    }

    //##################################################################################################################
    public void touchedTiktok() {
        new Thread(() ->{
            SoundManager.stopGameMusic();
            SoundManager.playTiktokSound();
            mGuiController.setTiktokScreen();
            currentGameController.getmIconManager().setShowingTiktokIcon(true);
            System.out.println("touched tiktok appcontroller");
        }).start();
    }
    public void removeTiktok() {
        SoundManager.stopTiktokSound();
        SoundManager.playGameMusic();
        System.out.println("should play music");
        currentGameController.getmIconManager().setShowingTiktokIcon(false);
    }
    //##################################################################################################################
    //level
    public void levelFinished() {
        currentGameController.stopGame();
        mGuiController.setGameFinishedScreen();
    }
    public void closeGame() {
        currentGameController.stopGame();
        mGuiController.closeGame();
    }
    //##################################################################################################################
    //level creation
    public void setUpLevelOne() {
        setUpLevel(1.0f, 25000, 25000);
    }
    public void setUpLevelTwo() {
        setUpLevel(1.0f, 10000, 15000);
    }

    public void setUpLevelThree() {
        setUpLevel(2.0f, 10000, 10000);
    }
    public void setUpLevel(float playerSpeed, long virusSpeed, long binaryCodeSpeed) {
        currentGameController = new GameController();
        currentGameController.setPlayerSpeed(playerSpeed);
        currentGameController.setVirusSpeed(virusSpeed);
        currentGameController.setBinaryCodeSpeed(binaryCodeSpeed);
        mGuiController.createLevelGraphics();
        currentGameController.startGame();
    }

}

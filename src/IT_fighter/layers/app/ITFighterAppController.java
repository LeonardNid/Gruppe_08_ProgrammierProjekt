package IT_fighter.layers.app;


import IT_fighter.layers.app.Entity.ITFighterCharacter;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;

public class ITFighterAppController {
    private static volatile ITFighterAppController instance;
    private GameController actualGameController;

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
        actualGameController = new GameController();
        actualGameController.setmCharacter(new ITFighterCharacter(100, 620, 16, 28));
        actualGameController.setPlayerSpeed(1.0f);
        //TODO enemy spawn frequency
    }
    public void startLevel() {
        actualGameController.startGameLoop();
    }
    public void startLevelTwo() {
        actualGameController = new GameController();
        actualGameController.setmCharacter(new ITFighterCharacter(100, 620, (int) 16, (int) 28));
        actualGameController.setPlayerSpeed(2.0f);
        actualGameController.startGameLoop();
        //TODO enemy spawn frequency
    }
    public void startLevelThree() {
        actualGameController = new GameController();
        actualGameController.setmCharacter(new ITFighterCharacter(100, 620, (int) (32 * Game.SCALE),
                (int) (32 * Game.SCALE)));
        actualGameController.setPlayerSpeed(2.0f);
        actualGameController.startGameLoop();
        //TODO enemy spawn frequency
    }





    //##################################################################################################################
    //getter und setter
    public void setmGuiController(ITFighterGuiController guiController) {
        this.mGuiController = guiController;
    }
    public ITFighterCharacter getActualCharacter() {
        return actualGameController.getmCharacter();
    }

    public GameController getActualGameController() {
        return actualGameController;
    }
    //##################################################################################################################
    //Zugriff auf Player
    //TODO Zugriff auf Player aus AppController Regeln

    public ITFighterCharacter getCharacter() {
        return actualGameController.getmCharacter();
    }


    public void closeGame() {
        ITFighterGuiController.getInstance().closeGame();
    }

    public int getLevelOffset() {
        return actualGameController.getxLevelOffset();
    }
}

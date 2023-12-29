package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.ITFighterCharacter;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;

import static IT_fighter.layers.app.ITFighterGame.*;

public class ITFighterGameController {

    //##################################################################################################################
    //Attribute für GameLoop
    private ITFighterGameLoop gameThread;

    //##################################################################################################################

    //##################################################################################################################
    //Level Daten
    private int levelTiles_In_Width = levelOneData[0].length;
    private int xLevelOffset = 0;
    private int rightBorder = (int) (0.8 * GAME_WIDTH);
    private int leftBorder = (int) (0.2 * GAME_WIDTH);
    private int maxTilesOffset = levelTiles_In_Width - TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTilesOffset * TILES_SIZE;
    //##################################################################################################################
    //Spielfigur
    private ITFighterCharacter mCharacter;
    private ITFighterEnemyManager mEnemyManager;
    private ITFighterIconManager mIconManager;
    //##################################################################################################################
    public ITFighterGameController() {
        mCharacter = new ITFighterCharacter(32, 678, (int) 16, (int) 28);
        mEnemyManager = new ITFighterEnemyManager();
        mIconManager = new ITFighterIconManager();


    }
    public void startGame() {
        mEnemyManager.startSpawnThread();
        startGameLoop();
    }
    public void stopGame() {
        stopGameLoop();
        mEnemyManager.stopSpawnThread();
    }


    //##########################################################################


    //##################################################################################################################
    //Zugriff auf Player
    //TODO Zugriff auf Player aus AppController Regeln


    // ##########################################################################
    //GameLoop
    public void startGameLoop() {
        gameThread = new ITFighterGameLoop(this);
        gameThread.start();
    }


    public void stopGameLoop() {
        gameThread.stopGameLoop();
    }
    // sorgt für ein Update aller logischen Komponenten des Spiels
    public void update() {
        mEnemyManager.updateEnemies();
        mCharacter.update();
        checkCloseToBorder();
        mIconManager.checkCollisionWithIcon();

    }

    private void checkCloseToBorder() {
        int playerX = (int) mCharacter.getHitbox().x;
        int difference = playerX - xLevelOffset;
        if (difference > rightBorder) {
            xLevelOffset += difference - rightBorder;
        } else if (difference < leftBorder) {
            xLevelOffset += difference - leftBorder;
        }
        if(xLevelOffset > maxLevelOffsetX) {
            xLevelOffset = maxLevelOffsetX;
        } else if (xLevelOffset < 0) {
            xLevelOffset = 0;

        }
    }
    //##################################################################################################################
    //sorgt für ein Update aller grafischen Komponenten des Spiels
    public void repaint() {
        ITFighterGuiController.getInstance().updateGraphics();
    }
    public void setAnimationIndex() {
        ITFighterGuiController.getInstance().setAnimationIndex();
    }
    //##################################################################################################################
    //getter und setter

    public ITFighterCharacter getmCharacter() {
        return mCharacter;
    }
    public void setmCharacter(ITFighterCharacter mCharacter) {
        this.mCharacter = mCharacter;
    }
    public void setPlayerSpeed(Float playerSpeed) {
        mCharacter.setPlayerSpeed(playerSpeed);
    }

    public ITFighterEnemyManager getmEnemyManager() {
        return mEnemyManager;
    }
    public void setVirusSpeed(long virusSpeed) {
        //TODO methode zum einstellen der Bewegungs geschwindigtkeit der Spielfigur
        mEnemyManager.setVirusSpawnTime(virusSpeed);
    }
    public void setBinaryCodeSpeed(long BinaryCodeSpeed) {
        mEnemyManager.setBinaryCodeSpawnTime(BinaryCodeSpeed);

    }

    public void setmIconManager(ITFighterIconManager mIconManager) {
        this.mIconManager = mIconManager;
    }

    public ITFighterIconManager getmIconManager() {
        return mIconManager;
    }

    public int getLevelTiles_In_Width() {
        return levelTiles_In_Width;
    }
    public int getxLevelOffset() {
        return xLevelOffset;
    }

    public int getRightBorder() {
        return rightBorder;
    }

    public int getLeftBorder() {
        return leftBorder;
    }

    public int getMaxTilesOffset() {
        return maxTilesOffset;
    }

    public int getMaxLevelOffsetX() {
        return maxLevelOffsetX;
    }


}
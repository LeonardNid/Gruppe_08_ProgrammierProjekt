package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.ITFighterCharacter;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;

import static IT_fighter.layers.app.ITFighterGame.*;

/**
 * Der GameController verwaltet die Logik eines Levels
 */
public class ITFighterGameController {

    //##################################################################################################################
    //Attribute für GameLoop
    private ITFighterGameLoop gameThread;
    //##################################################################################################################
    //Level Daten
    private final int levelTiles_In_Width = levelOneData[0].length;
    private int xLevelOffset = 0;
    private final int rightBorder = (int) (0.8 * GAME_WIDTH);
    private final int leftBorder = (int) (0.2 * GAME_WIDTH);
    private final int maxTilesOffset = levelTiles_In_Width - TILES_IN_WIDTH;
    private final int maxLevelOffsetX = maxTilesOffset * TILES_SIZE;
    //##################################################################################################################
    //Spielfigur
    private final ITFighterCharacter mCharacter;
    private final ITFighterEnemyManager mEnemyManager;
    private final ITFighterIconManager mIconManager;
    //##################################################################################################################

    /**
     * bei Instanziierung des GameControllers werden eine Spielfigur, ein EnemyManager und ein IconManager instanziiert
     */
    public ITFighterGameController() {
        mCharacter = new ITFighterCharacter(32, 678, 16, 28);
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
    // ##########################################################################
    //GameLoop
    /**
     * startet einen Thread in dem die GameLoop läuft
     */
    public void startGameLoop() {
        gameThread = new ITFighterGameLoop(this);
        gameThread.start();
    }

    /**
     * stoppt die GameLoop und somit das Level
     */
    public void stopGameLoop() {
        gameThread.stopGameLoop();
    }

    /**
     * sorgt für ein Update aller logischen Komponenten des Spiels
     */
    public void update() {
        mEnemyManager.updateEnemies();
        mCharacter.update();
        checkCloseToBorder();
        mIconManager.checkCollisionWithIcon();

    }

    /**
     * überprüft wie nah die Spielfigur bzw. dessen Hitbox am rechten oder linken Bildrand ist.
     * Je nach Nähe der Spielfigur zum Bildrand verschiebt sich das LevelOffset
     */
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
    /**
     * sorgt für ein Update aller grafischen Komponenten des Spiels
     */
    public void repaint() {
        ITFighterGuiController.getInstance().updateGraphics();
    }

    /**
     * setzt den AnimationIndex zurück
     */
    public void setAnimationIndex() {
        ITFighterGuiController.getInstance().setAnimationIndex();
    }
    //##################################################################################################################
    //getter und setter
    /**
     * @return gibt die Logik der Spielfigur zurück
     */
    public ITFighterCharacter getmCharacter() {
        return mCharacter;
    }
    /**
     * setzt die Geschwindigkeit der Spielfigur in x-Richtung
     * @param playerSpeed Geschwindigkeit in X-Richtung
     */
    public void setPlayerSpeed(Float playerSpeed) {
        mCharacter.setPlayerSpeed(playerSpeed);
    }
    /**
     * @return gibt den EnemyManager eines Levels zurück
     */
    public ITFighterEnemyManager getmEnemyManager() {
        return mEnemyManager;
    }
    /**
     * @param virusSpeed zeitlicher Abstand zwischen dem Erstellen von Virus Objekten
     */
    public void setVirusSpeed(long virusSpeed) {
        mEnemyManager.setVirusSpawnTime(virusSpeed);
    }
    /**
     * @param BinaryCodeSpeed zeitlicher Abstand zwischen dem Erstellen von BinärCode Objekten
     */
    public void setBinaryCodeSpeed(long BinaryCodeSpeed) {
        mEnemyManager.setBinaryCodeSpawnTime(BinaryCodeSpeed);
    }

    /**
     *
     * @return gibt den IconManger eines Levels zurück
     */
    public ITFighterIconManager getmIconManager() {
        return mIconManager;
    }

    /**
     * @return gibt xLevelOffset zurück
     */
    public int getxLevelOffset() {
        return xLevelOffset;
    }
}
package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.ITFighterCharacter;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.utilities.LoadAndSaveData;
import static IT_fighter.layers.app.Game.*;

public class GameController{

    //##################################################################################################################
    //Attribute für GameLoop
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
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

    //##################################################################################################################


    public GameController() {
        System.out.println(maxTilesOffset);

    }


    //##########################################################################


    //##################################################################################################################
    //Zugriff auf Player
    //TODO Zugriff auf Player aus AppController Regeln


    // ##########################################################################
    //GameLoop
    public void startGameLoop() {
        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                double timePerFrame = 1000000000.0 / FPS_SET;
                double timePerUpdate = 1000000000.0 / UPS_SET;

                long previousTime = System.nanoTime();

                int frames = 0;
                int updates = 0;
                long lastCheck = System.currentTimeMillis();

                double deltaU = 0;
                double deltaF = 0;

                while (true) {
                    long currentTime = System.nanoTime();

                    deltaU += (currentTime - previousTime) / timePerUpdate;
                    deltaF += (currentTime - previousTime) / timePerFrame;
                    previousTime = currentTime;

                    if (deltaU >= 1) {
                        update();
                        updates++;
                        deltaU--;
                    }

                    if (deltaF >= 1) {
                        repaint();
                        frames++;
                        deltaF--;
                    }

                    if (System.currentTimeMillis() - lastCheck >= 1000) {
                        lastCheck = System.currentTimeMillis();
                        System.out.println("FPS: " + frames + " | UPS: " + updates);
                        frames = 0;
                        updates = 0;

                    }
                }
            }
        });

        gameThread.start();


    }
    // sorgt für ein Update aller logischen Komponenten des Spiels
    public void update() {
        mCharacter.update();
        checkCloseToBorder();

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


    public void setVirusSpeed() {
        //TODO methode zum einstellen der Bewegungs geschwindigtkeit der Spielfigur
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
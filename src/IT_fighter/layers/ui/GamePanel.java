package IT_fighter.layers.ui;

import IT_fighter.layers.app.EnemyManager;
import IT_fighter.layers.app.Entity.Virus;
import IT_fighter.layers.app.PlayerKeyHandler;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.utilities.LoadAndSaveData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static IT_fighter.layers.app.Game.*;

public class GamePanel extends JPanel {
    //TODO ist Final hier ok?
    private final PlayerKeyHandler keyHandler = new PlayerKeyHandler();
    private int[][] levelData;
    private BufferedImage[] levelSprite;

    private BufferedImage backgroundImage, cannon;
    private ITFighterCharacterPanel characterPanel;
    private ITFighterEnemyPanel enemyPanel;
    private JPanel levelFinishedPanel, gameOverPanel;
    //##################################################################################################################

    public GamePanel(int[][] levelData) {
        initGamePanel(levelData);
    }
    private void initGamePanel(int[][] levelData) {
        this.addKeyListener(keyHandler);
        setPanelSize();
        this.levelData = levelData;
        loadPictures();

        importOutsideSprites();
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }


    private void loadPictures() {

        this.backgroundImage = LoadAndSaveData.getImage("ITF_gameBackground.jpg");
        cannon = LoadAndSaveData.getImage("ITF_Kanone.jpg");

    }
    //##################################################################################################################

    //###################################################################################################################
    //getter und setter
    public int[][] getLevelData() {
        return levelData;
    }
    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }
    //##################################################################################################################

    private void importOutsideSprites() {
        BufferedImage img = LoadAndSaveData.getImage(LoadAndSaveData.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
    }

    //##################################################################################################################
    //getter und setter
    private void setPanelSize() {
        Dimension screenDimension = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setMinimumSize(screenDimension);
        setMaximumSize(screenDimension);
        setPreferredSize(screenDimension);
    }

    public void setCharacterPanel(ITFighterCharacterPanel characterPanel) {
        this.characterPanel = characterPanel;
    }

    public void setEnemyPanenl(ITFighterEnemyPanel enemyPanel) {
        this.enemyPanel = enemyPanel;
    }
    public void setGameOverPanel(JPanel gameOverPanel) {
        this.gameOverPanel = gameOverPanel;
    }
    public void setLevelFinishedPanel(JPanel levelFinishedPanel) {
        this.levelFinishedPanel = levelFinishedPanel;
    }




    //##################################################################################################################
    //
    public void renderPanel() {
        //TODO Komponenten die repainted werden müssen

        this.repaint();
    }
    //##################################################################################################################
    //zeichen der Bildkomponenten
    @Override
    public void paintComponent(Graphics graphics) {
        int levelOffset = ITFighterGuiController.getInstance().getLevelOffset();
        super.paintComponent(graphics);
        graphics.drawImage(backgroundImage,0,0, GAME_WIDTH, GAME_HEIGHT, null);
        drawLevel(graphics, levelOffset);
        characterPanel.render(graphics);
        graphics.drawImage(cannon, 600 - levelOffset, 600, 46,32, null);
        enemyPanel.drawEnemies(graphics, levelOffset);
//        System.out.println("paintGamePanle");
    }

    public void drawLevel(Graphics g, int levelOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levelOneData[0].length; i++) {
                int index = getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * i - levelOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
    }




}

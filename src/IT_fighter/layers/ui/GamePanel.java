package IT_fighter.layers.ui;

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

    private BufferedImage backgroundImage, cannon, cloud, windowsDefender, tiktok;
    private ITFighterCharacterPanel characterPanel;
    private ITFighterEnemyPanel enemyPanel;
    private JPanel tiktokPanel;
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
        cloud = LoadAndSaveData.getImage("ITF_cloud.jpg");
        windowsDefender = LoadAndSaveData.getImage("ITF_windows_defender.jpg");
        tiktok = LoadAndSaveData.getImage("ITF_tiktok.jpg");

    }
    //##################################################################################################################
    public void showScreen(JPanel panel) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 200));
        this.add(panel);
    }
    public void showTiktokScreen() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 200));
        this.add(tiktokPanel);
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
    public int[][] getLevelData() {
        return levelData;
    }
    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }
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

    public void setTiktokPanel(JPanel tiktokPanel) {
        this.tiktokPanel = tiktokPanel;
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

        enemyPanel.drawEnemies(graphics, levelOffset);
        drawClouds(graphics, levelOffset);
        graphics.drawImage(cannon, 2816 - levelOffset, 736, 46,32, null);
        graphics.drawImage(windowsDefender, 5720 - levelOffset, 730, 32, 32, null);
        graphics.drawImage(tiktok, 1088 - levelOffset, 890, 32, 32, null);


        characterPanel.render(graphics);
//        System.out.println("paintGamePanle");
    }
    public void drawClouds(Graphics graphics, int levelOffset) {
        graphics.drawImage(cloud, 90 - levelOffset, 100, null);
        graphics.drawImage(cloud, 1000 - levelOffset, 140, null);
        graphics.drawImage(cloud, 2500 - levelOffset, 100, null);
        graphics.drawImage(cloud, 3842 - levelOffset, 120, null);
    }

    public void drawLevel(Graphics g, int levelOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levelOneData[0].length; i++) {
                int index = getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * i - levelOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
    }


    public void removerTiktokPanel() {
        this.remove(tiktokPanel);
        this.setLayout(new FlowLayout());
    }
}

package IT_fighter.layers.ui;

import IT_fighter.layers.app.ITFighterPlayerKeyHandler;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.utilities.LoadAndSaveData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static IT_fighter.layers.app.ITFighterGame.*;

/**
 * Das GamePanel verwaltet alle grafischen Komponenten des Spiels und der Welt.
 */
public class ITFighterGamePanel extends JPanel {
    private final ITFighterPlayerKeyHandler keyHandler = new ITFighterPlayerKeyHandler();
    private int[][] levelData;
    private BufferedImage[] levelSprite;

    private BufferedImage backgroundImage, cannon, cloud, windowsDefender, tiktok;
    private ITFighterCharacterGraphics characterPanel;
    private ITFighterEnemyGraphics enemyPanel;
    private JPanel tiktokPanel;
    //##################################################################################################################

    /**
     * @param levelData zweidimensionales Array mit den Daten für die Spielwelt
     */
    public ITFighterGamePanel(int[][] levelData) {
        initGamePanel(levelData);
    }

    /**
     * initialisiert das alle Komponenten des GamePanels und soll den Konstruktor schlag halten.
     * @param levelData zweidimensionales Array mit den Daten für die Spielwelt
     */
    private void initGamePanel(int[][] levelData) {
        this.addKeyListener(keyHandler);
        setPanelSize();
        this.levelData = levelData;
        loadPictures();
        importOutsideSprites();
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    /**
     * lädt alle Bilder die im GamePanel gezeichnet werden.
     */
    private void loadPictures() {
        this.backgroundImage = LoadAndSaveData.getImage("ITF_gameBackground.jpg");
        cannon = LoadAndSaveData.getImage("ITF_Kanone.jpg");
        cloud = LoadAndSaveData.getImage("ITF_cloud.jpg");
        windowsDefender = LoadAndSaveData.getImage("ITF_windows_defender.jpg");
        tiktok = LoadAndSaveData.getImage("ITF_tiktok.jpg");
    }
    //##################################################################################################################

    /**
     * Methode mit der, der GameOverScreen und das LevelFinishedPanel angezeigt werden.
     * @param panel GameOverScreen oder LevelFinishedPanel
     */
    public void showScreen(JPanel panel) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 200));
        this.add(panel);
    }

    /**
     * zeichnet das TiktokPanel
     */
    public void showTiktokScreen() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 200));
        this.add(tiktokPanel);
    }

    //##################################################################################################################

    /**
     * importiert die Bilder für den Boden und die Spikes
     */
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

    /**
     * @param x x Koordinate
     * @param y y Koordinate
     * @return gibt den Index (damit auch die Art) des Felds zurück, welches sich an der x, y Position befindet
     */
    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }

    /**
     * setzt die Größe des GamePanels
     */
    private void setPanelSize() {
        Dimension screenDimension = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setMinimumSize(screenDimension);
        setMaximumSize(screenDimension);
        setPreferredSize(screenDimension);
    }

    /**
     * @param characterPanel CharacterPanel, welches vom GamePanelController übergeben wird.
     */
    public void setCharacterPanel(ITFighterCharacterGraphics characterPanel) {
        this.characterPanel = characterPanel;
    }

    /**
     * @param enemyPanel EnemyPanel, welches vom GamePanelController übergeben wird.
     */
    public void setEnemyPanel(ITFighterEnemyGraphics enemyPanel) {
        this.enemyPanel = enemyPanel;
    }

    /**
     * @param tiktokPanel TiktokPanel, welches vom GamePanelController übergeben wird.
     */
    public void setTiktokPanel(JPanel tiktokPanel) {
        this.tiktokPanel = tiktokPanel;
    }
    //##################################################################################################################
    //zeichen der Bildkomponenten

    /**
     * wird vom Gui-Controller 120-mal aufgerufen und sorgt dafür, dass das GamePanel neu gezeichnet wird.
     */
    public void renderPanel() {
        this.repaint();
    }
    /**
     * zeichnet alle Komponenten des GamePanels.
     * Wird 120-mal pro Sekunde aufgerufen.
     * Ruft an der Methoden auf und übergibt das Graphics Objekt
     * @param graphics the <code>Graphics</code> object to protect
     */
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
    }

    /**
     * Zeichnet die Wolken aus den die Viren fallen
     * @param graphics grafische Komponente des GamePanels
     * @param levelOffset größe des aktuellen Offsets des Spiels
     */
    public void drawClouds(Graphics graphics, int levelOffset) {
        graphics.drawImage(cloud, 90 - levelOffset, 100, null);
        graphics.drawImage(cloud, 1000 - levelOffset, 140, null);
        graphics.drawImage(cloud, 2500 - levelOffset, 100, null);
        graphics.drawImage(cloud, 3842 - levelOffset, 120, null);
    }

    /**
     * Zeichnet den Boden und die Spikes des Levels
     * @param g grafische Komponente des GamePanels
     * @param levelOffset größe des aktuellen Offsets des Spiels
     */
    public void drawLevel(Graphics g, int levelOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levelOneData[0].length; i++) {
                int index = getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * i - levelOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
    }

    /**
     * entfernt das TiktokPanel vom GamePanel
     */
    public void removerTiktokPanel() {
        this.remove(tiktokPanel);
    }
}

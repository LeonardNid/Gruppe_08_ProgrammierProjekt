package IT_fighter.layers.ui.ctrl;

import IT_fighter.layers.ui.GamePanel;
import IT_fighter.layers.ui.ITFighterCharacterPanel;
import IT_fighter.layers.ui.ITFighterEnemyPanel;

/**
 * Verwaltet alle graphischen Objekte eines Levels
 */
public class GamePanelController {
    //TODO hier erweitern wenn weitere graphische Objekte zum Level hinzukommen
    private ITFighterCharacterPanel characterPanel;
    private GamePanel gamePanel;
    private ITFighterEnemyPanel enemyPanel;
    public GamePanelController(GamePanel gamePanel, ITFighterCharacterPanel characterPanel,
                               ITFighterEnemyPanel enemyPanel) {
        this.characterPanel = characterPanel;
        this.gamePanel = gamePanel;
        this.enemyPanel = enemyPanel;
        setUpGamePanel();
    }

    private void setUpGamePanel() {
        gamePanel.setCharacterPanel(characterPanel);
        gamePanel.setEnemyPanenl(enemyPanel);
        //gamePanel
    }

    public void setAnimationIndex() {
        characterPanel.setAnimationIndex();
    }

    public ITFighterCharacterPanel getCharacterPanel() {
        return characterPanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void renderGame() {
        gamePanel.renderPanel();
        characterPanel.update();
    }

    public void setCharacterPanel(ITFighterCharacterPanel characterPanel) {
        this.characterPanel = characterPanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void showGameOverPanel() {

    }
}

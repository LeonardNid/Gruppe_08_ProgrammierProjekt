package IT_fighter.layers.ui.ctrl;

import IT_fighter.layers.app.GameController;
import IT_fighter.layers.ui.GamePanel;
import IT_fighter.layers.ui.ITFighterCharacterPanel;

/**
 * Verwaltet alle graphischen Objekte eines Levels
 */
public class GamePanelController {
    //TODO hier erweitern wenn weitere graphische Objekte zum Level hinzukommen
    private ITFighterCharacterPanel characterPanel;
    private GamePanel gamePanel;
    public GamePanelController(GamePanel gamePanel, ITFighterCharacterPanel characterPanel) {
        this.characterPanel = characterPanel;
        this.gamePanel = gamePanel;
        setUpGamePanel();
    }

    private void setUpGamePanel() {
        gamePanel.setCharacterPanel(characterPanel);
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
        gamePanel.repaintPanel();
        characterPanel.update();
    }
}

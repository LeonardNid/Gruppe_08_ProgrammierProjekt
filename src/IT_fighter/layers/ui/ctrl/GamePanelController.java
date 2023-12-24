package IT_fighter.layers.ui.ctrl;

import IT_fighter.layers.app.ITFighterAppController;
import IT_fighter.layers.ui.GamePanel;
import IT_fighter.layers.ui.ITFighterCharacterPanel;
import IT_fighter.layers.ui.ITFighterEnemyPanel;
import IT_fighter.utilities.LoadAndSaveData;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        gamePanel.setGameOverPanel(createGameOverPanel());
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
    //TODO entfernen falls es nicht mehr implementiert wirda
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public JPanel createGameOverPanel() {
        Container gameOvercontainer = new Container();
        gameOvercontainer.setPreferredSize(new Dimension(500,300));
        gameOvercontainer.setLayout(new BoxLayout(gameOvercontainer,BoxLayout.Y_AXIS));


        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setPreferredSize(new Dimension(1000,600));
        gameOverPanel.setBackground(Color.black);
        gameOverPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));


        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(LoadAndSaveData.getFont(50));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setPreferredSize(new Dimension(100,150));
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER);


        JTextPane gameOverText = new JTextPane();
        gameOverText.setEditable(false);
        gameOverText.setText("Your fight against the DarkIT failed.\nJust try it again!");
        gameOverText.setOpaque(false);
        gameOverText.setFont(new Font(Font.DIALOG, 3, 20));
        gameOverText.setForeground(Color.WHITE);
        StyledDocument doc = gameOverText.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        JButton back_button = new JButton("Back");
        back_button.setSize(new Dimension(100,100));
        back_button.setFont(LoadAndSaveData.getFont(40));
        back_button.setForeground(Color.GREEN);
        back_button.setOpaque(false);
        back_button.setBorderPainted(false);
        back_button.setContentAreaFilled(true);
        back_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ITFighterAppController.getInstance().closeGame();
            }
        });

        gameOvercontainer.add(gameOverLabel);
        gameOvercontainer.add(gameOverText);
        gameOvercontainer.add(back_button);
        gameOverPanel.add(gameOvercontainer);
        return gameOverPanel;

    }

    public void setGameOverPanel() {
        gamePanel.showGameOverScreen();

    }
}

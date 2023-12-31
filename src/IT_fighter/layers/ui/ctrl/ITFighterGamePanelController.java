package IT_fighter.layers.ui.ctrl;

import IT_fighter.layers.app.ITFighterAppController;
import IT_fighter.layers.ui.ITFighterGamePanel;
import IT_fighter.layers.ui.ITFighterCharacterGraphics;
import IT_fighter.layers.ui.ITFighterEnemyGraphics;
import IT_fighter.layers.ui.ITFighterTiktokPanel;
import IT_fighter.utilities.LoadAndSaveData;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

/**
 * Verwaltet alle grafischen Objekte eines Levels
 */
public class ITFighterGamePanelController {
    private final ITFighterCharacterGraphics characterPanel;
    private final ITFighterGamePanel gamePanel;
    private boolean showingTiktokPanel = false;
    private final ITFighterEnemyGraphics enemyPanel;
    /**
     * Konstruktor des GamePanelControllers
     * @param gamePanel dessen Komponenten vom GamePanelController verwaltet werden
     * @param characterPanel das dem übergebenen GamePanel übergeben wird
     * @param enemyPanel das dem übergebenen GamePanel übergeben wird
     */
    public ITFighterGamePanelController(ITFighterGamePanel gamePanel, ITFighterCharacterGraphics characterPanel,
                                        ITFighterEnemyGraphics enemyPanel) {
        this.characterPanel = characterPanel;
        this.gamePanel = gamePanel;
        this.enemyPanel = enemyPanel;
        setUpGamePanel();
    }

    /**
     * setzt in einem GamePanel das TiktokPanel, das EnemyPanel und das CharacterPanel
     */
    private void setUpGamePanel() {
        gamePanel.setCharacterPanel(characterPanel);
        gamePanel.setEnemyPanel(enemyPanel);
        gamePanel.setTiktokPanel(new ITFighterTiktokPanel());
    }

    /**
     * setzt den AnimationIndex der Spielfigur zurück
     * die Methode sollte aufgerufen werden, wenn sich die Animation der Spielfigur verändert
     */
    public void setAnimationIndex() {
        characterPanel.setAnimationIndex();
    }

    /**
     * @return gibt das GamePanel des GamePanelControllers zurück
     */
    public ITFighterGamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * wird 120-mal pro Sekunde aufgerufen und bewirkt, dass das GamePanel und dessen Komponenten neu gezeichnet werden
     */
    public void renderGame() {
        gamePanel.renderPanel();
        characterPanel.updateAnimationTick();
    }

    /**
     * @return gibt ein neu erzeugtes Panel zurück, welches angezeigt werden soll, wenn das Spiel durch den Tod der
     * Spielfigur beendet wird
     */
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
        back_button.setContentAreaFilled(false);
        back_button.addActionListener(e -> ITFighterAppController.getInstance().closeGame());

        gameOvercontainer.add(gameOverLabel);
        gameOvercontainer.add(gameOverText);
        gameOvercontainer.add(back_button);
        gameOverPanel.add(gameOvercontainer);
        return gameOverPanel;

    }

    /**
     * @return gibt ein Panel zurück, welches angezeigt werden soll, wenn das Level erfolgreich beendet wurde
     */
    public JPanel createFinishedPanel() {
        Container finishedContainer = new Container();
        finishedContainer.setPreferredSize(new Dimension(500,300));
        finishedContainer.setLayout(new BoxLayout(finishedContainer,BoxLayout.Y_AXIS));


        JPanel finishedPanel = new JPanel();
        finishedPanel.setPreferredSize(new Dimension(1000,600));
        finishedPanel.setBackground(Color.black);
        finishedPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));


        JLabel finishedLabel = new JLabel("Finished");
        finishedLabel.setFont(LoadAndSaveData.getFont(50));
        finishedLabel.setForeground(Color.RED);
        finishedLabel.setPreferredSize(new Dimension(100,150));
        finishedLabel.setHorizontalAlignment(JLabel.CENTER);


        JTextPane finishedText = new JTextPane();
        finishedText.setEditable(false);
        finishedText.setText("Your fight against the DarkIT was successful.\nTry another Level!");
        finishedText.setOpaque(false);
        finishedText.setFont(new Font(Font.DIALOG, 3, 20));
        finishedText.setForeground(Color.WHITE);
        StyledDocument doc = finishedText.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        JButton back_button = new JButton("Back");
        back_button.setSize(new Dimension(100,100));
        back_button.setFont(LoadAndSaveData.getFont(40));
        back_button.setForeground(Color.GREEN);
        back_button.setOpaque(false);
        back_button.setBorderPainted(false);
        back_button.setContentAreaFilled(false);
        back_button.addActionListener(e -> ITFighterAppController.getInstance().closeGame());
        finishedContainer.add(finishedLabel);
        finishedContainer.add(finishedText);
        finishedContainer.add(back_button);
        finishedPanel.add(finishedContainer);
        return finishedPanel;


    }
    /**
     * sorgt dafür, dass das GamePanel den GameOverScreen anzeigt
     */
    public void setGameOverPanel() {
        if(showingTiktokPanel) {
            ITFighterGuiController.getInstance().removeTiktokPanel();
        }
        gamePanel.showScreen(createGameOverPanel());
    }

    /**
     * sorgt dafür, dass das GamePanel das Panel für ein erfolgreich beendetes Level anzeigt
     */
    public void setGameFinishedPanel() {
        gamePanel.showScreen(createFinishedPanel());
    }

    /**
     * sorgt dafür, dass das TiktokPanel angezeigt wird
     */
    public void setTiktokPanel() {
        if (!showingTiktokPanel) {
            gamePanel.showTiktokScreen();
            showingTiktokPanel = true;
        }
    }

    /**
     * entfernt das TiktokPanel aus dem GamePanel
     */
    public void removeTiktokPanel() {
        gamePanel.removerTiktokPanel();
        showingTiktokPanel = false;
    }
}

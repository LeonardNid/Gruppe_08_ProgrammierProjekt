package NanoTechDefenders.GUI;

import NanoTechDefenders.GUI.Controlling.GUIController;
import NanoTechDefenders.GUI.Controlling.UIManager;
import NanoTechDefenders.GUI.Window.InfoWindow;
import NanoTechDefenders.Help.MyFont;
import NanoTechDefenders.Help.MyImage;
import NanoTechDefenders.Help.Soldiers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Das UIPanel ist die Benutzeroberfläche des Spiels.
 * Es zeigt Informationen wie Lebenspunkte und enthält Buttons zum Öffnen des Menüs, zum Platzieren von Soldaten und zum Spawnen von Gegnern.
 */
public class UIPanel extends JPanel implements ActionListener {
    // WIDTH and HEIGTH for Buttons and Images
    private final int WIDTH = (int)(110 * GUIController.getXFactor());
    private final int HEIGTH = (int)(110 * GUIController.getYFactor());
    private JPanel bottomUIPanel;
    private JPanel topPanel;
    private JButton menuButton;
    private static JButton speedBtn;
    private static JButton spawnBtn;
    private static JButton autoplayBtn;
    private static HashMap<Soldiers, JButton> soldierBtns = new HashMap<>();
    private static JLabel lifeLabel = new JLabel();
    private static JLabel currentWaveLabel = new JLabel();
    private static JLabel amountOfMoneyLabel = new JLabel();
    private static JLabel infoLabel = new JLabel();
    private InfoWindow infoWindow;

    /**
     * Konstruktor für das UIPanel.
     * Es initialisiert das Layout und erstellt die UI-Komponenten.
     */
    public UIPanel() {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        makeTopPanel();
        makebottomPanel();

        infoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showInfoWindow(e.getLocationOnScreen());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hideInfoWindow();
            }
        });
    }

    /**
     * Aktualisiert die Lebensanzeige, indem es sich die übrigen Leben vom LogicController holt.
     * Wird aufgerufen, sobald ein EnemyPanel gelöscht wird.
     */
    public void updateLifeLabel(int playerLifes) {
        lifeLabel.setText("Lives: " + playerLifes);
    }
    public void updateCurrentWave(String currentWave) {
        currentWaveLabel.setText("Wave: " + (currentWave));
    }
    public void activateSpawnbtn() {
        spawnBtn.setEnabled(true);
    }
    public void updateMoney(int moneyAmont) {
        amountOfMoneyLabel.setText("Money: " + moneyAmont);
    }
    public void toggleSoldierBtn(Soldiers soldierVariant, boolean isEnabled) {
        soldierBtns.get(soldierVariant).setEnabled(isEnabled);
    }

    /**
     * Erstellt das obere Panel, das die Lebenspunkte und den Menübutton enthält.
     */
    private void makeTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setOpaque(false);
        topPanel.setVisible(true);
        // Life Anzeige Label
        makeLabel(lifeLabel,"Lives: 0");
        topPanel.add(Box.createHorizontalStrut(50));
        // current Wave Label
        makeLabel(currentWaveLabel,"Wave: 1");
        topPanel.add(Box.createHorizontalStrut(50));
        // Amount of Money Label
        makeLabel(amountOfMoneyLabel,"Money: 0");
        topPanel.add(Box.createHorizontalGlue());
        // InfoWindow Label
        makeLabel(infoLabel,"?");
        topPanel.add(Box.createHorizontalStrut(50));
        // MenuButton
        menuButton = new JButton("Menu");
        menuButton.setFont(MyFont.getMyFont().deriveFont(40f));
        menuButton.setForeground(Color.GREEN); // Setzt die Textfarbe
        menuButton.setBackground(Color.black);
        menuButton.addActionListener(this);
        topPanel.add(menuButton);
        this.add(topPanel, BorderLayout.NORTH);
    }

    private void makeLabel(JLabel label, String text) {
        label.setText(text);
        label.setFont(MyFont.getMyFont().deriveFont(40f));
        label.setForeground(Color.GREEN);
        topPanel.add(label);
    }

    /**
     * Erstellt das untere Panel, das die Buttons zum Platzieren von Soldaten und zum Spawnen von Gegnern enthält.
     */
    private void makebottomPanel() {
        bottomUIPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        // Normal Buttons
        addButton(speedBtn = new JButton(), "SPEED");
        addButton(autoplayBtn = new JButton(), "<html>AUTO<br>PLAY");
        addButton(spawnBtn = new JButton(), "<html>NEXT<br>WAVE");

        for (int i = 0; i < Soldiers.values().length; ++i) {
            Soldiers soldierVar = Soldiers.values()[i];
            soldierBtns.put(soldierVar, new JButton());
            addSoldierButton(soldierBtns.get(soldierVar), soldierVar.getFileName());
        }

        // Bottom UIPanel settings
        bottomUIPanel.setBackground(new Color(86, 102, 102, 70));
        bottomUIPanel.setPreferredSize(new Dimension(1, (int)(120 * GUIController.getYFactor())));
        bottomUIPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomUIPanel.setBorder(new LineBorder(Color.BLACK, 5));
        this.add(bottomUIPanel, BorderLayout.SOUTH);
    }

    /**
     * Hilfsmethode zum Hinzufügen eines Buttons zum unteren Panel.
     * @param button Der hinzuzufügende Button.
     * @param text Der Text des Buttons.
     */
    private void addButton(JButton button, String text) {
        button.setText(text);
        button.setFont(MyFont.getMyFont().deriveFont(30f));
        button.setPreferredSize(new Dimension(200, HEIGTH));
        button.setForeground(Color.GREEN); // Setzt die Textfarbe
        button.setBackground(Color.black);
        button.addActionListener(this);
        bottomUIPanel.add(button);
    }
    private void addSoldierButton(JButton button, String file) {
        button.setIcon(MyImage.createScaledImageIcon(file, WIDTH, HEIGTH));
        button.setPreferredSize(new Dimension(WIDTH, HEIGTH));
        button.addActionListener(this);
        button.setBackground(Color.black);
        bottomUIPanel.add(button);
    }

    /**
     * Behandelt Aktionen, wenn ein Button geklickt wird.
     * @param e Das ActionEvent, das die Aktion auslöst.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            System.out.println("Menu Button gedrückt");
            GUIController.openGameMenu();
        }

        if (e.getSource() == speedBtn) {
            System.out.println("Speed Button gedrückt");
            GUIController.changeSpeed();
        }

        if (e.getSource() == autoplayBtn) {
            System.out.println("AutoPlay Button gedrückt");
            spawnBtn.setEnabled(false);
            if (autoplayBtn.getText().equals("<html>AUTO<br>PLAY")) {
                autoplayBtn.setText("PLAYING");
            } else {
                autoplayBtn.setText("<html>AUTO<br>PLAY");
            }
            UIManager.changeAutoplay();
        }

        if (e.getSource() == spawnBtn) {
            System.out.println("Spawn Button gedrückt");
            GUIController.startNextWave();
            spawnBtn.setEnabled(false);
        }

        for (Soldiers soldierVar : soldierBtns.keySet()) {
            if (e.getSource() == soldierBtns.get(soldierVar)) {
                System.out.println("Soldier Button " + soldierVar + " gedrückt");
                GUIController.createSoldierPlacePanel(soldierVar);
                UIManager.toggleSoldierBtn(false);
            }
        }
    }

    private void showInfoWindow(Point location) {
        if (infoWindow != null) {
            infoWindow.setLocation(location);
            infoWindow.setVisible(true);
        } else {
            infoWindow = new InfoWindow(this.getWidth());
            infoWindow.setLocation(location);
        }
    }

    private void hideInfoWindow() {
        if (infoWindow != null) {
            infoWindow.setVisible(false);
        }
    }
}

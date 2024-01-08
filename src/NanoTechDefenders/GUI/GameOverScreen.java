package NanoTechDefenders.GUI;

import NanoTechDefenders.GUI.Controlling.GUIController;
import NanoTechDefenders.Help.MyFont;
import NanoTechDefenders.Help.MyImage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Das GameOverScreen ist dafür da, den Bildschirm anzuzeigen, wenn das Spiel vorbei ist.
 */
public class GameOverScreen extends JPanel {
    private int frameWidth, frameHeight;
    private ImageIcon backgroundImage;
    private JPanel statsPanel;

    /**
     * Konstruktor für das GameOverScreen.
     *
     * @param frameWidth  Die Breite des übergeordneten Frames.
     * @param frameHeight Die Höhe des übergeordneten Frames.
     * @param stats       Die Statistikinformationen, die angezeigt werden sollen.
     */
    public GameOverScreen(int frameWidth, int frameHeight, String[] stats) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        backgroundImage = MyImage.createScaledImageIcon("NTD_firewall_locks.jpg", frameWidth, frameHeight);
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        statsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Hintergrund zeichnen
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        JLabel statsLabel = getStatsLabel(stats);
        statsPanel.add(statsLabel);

        JButton backToMenuBtn = makeBackBtn();
        statsPanel.add(backToMenuBtn);

        // Einstellungen für das untere UIPanel
        statsPanel.setLayout(null);
        statsPanel.setBackground(new Color(0, 0, 0, 70));
        statsPanel.setPreferredSize(new Dimension(1000, 700));
        statsPanel.setBorder(new LineBorder(Color.RED, 5));

        this.add(statsPanel, gbc);
    }

    /**
     * Erstellt den "Zurück zum Hauptmenü"-Button.
     *
     * @return Der erstellte Button.
     */
    private JButton makeBackBtn() {
        Font myFont = MyFont.getMyFont();

        JButton button = new JButton();
        button.setText("Main Menu");
        button.setFont(myFont);
        button.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.GREEN);
        button.setBounds(100, 500, 800, 100);
        button.setFocusable(false);
        button.addActionListener(e -> GUIController.switchMenuPanel());
        // Fügt dem Button einen MouseListener hinzu, um die Schriftart zu ändern, wenn er überfahren wird.
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Ändert die Schriftart, wenn die Maus den Button betritt.
                button.setFont(myFont.deriveFont(Font.BOLD, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Setzt die Schriftart zurück, wenn die Maus den Button verlässt.
                button.setFont(myFont.deriveFont(Font.BOLD, 60));
            }
        });
        return button;
    }

    /**
     * Erstellt das JLabel für die Statistikinformationen.
     *
     * @param stats Die Statistikinformationen als String-Array.
     * @return Das erstellte JLabel.
     */
    private JLabel getStatsLabel(String[] stats) {
        JLabel statsLabel = new JLabel();
        statsLabel.setText("<html>" +
                "<center>GAME OVER</center><br><br>" +
                "Waves: <font color='red'>" + stats[0] + "</font><br>" +
                "Kills: <font color='red'>" + stats[1] + "</font><br>" +
                "Time Played: <font color='red'>" + stats[2] + "</font><br>" +
                "</html>");
        statsLabel.setFont(MyFont.getMyFont().deriveFont(60f));
        statsLabel.setForeground(Color.GREEN);
        statsLabel.setBounds(10, 10, 1000, 400);
        statsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return statsLabel;
    }

    /**
     * Zeichnet das Hintergrundbild des GameOverScreens.
     *
     * @param g Das Graphics-Objekt, das zum Zeichnen verwendet wird.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Zeichnet das Hintergrundbild.
        g.drawImage(backgroundImage.getImage(), 0, 0, frameWidth, frameHeight, this);
    }

}

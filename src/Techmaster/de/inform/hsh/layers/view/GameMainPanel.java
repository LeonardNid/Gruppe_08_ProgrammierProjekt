package de.inform.hsh.layers.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import de.inform.hsh.layers.controller.GUIController;
import de.inform.hsh.layers.model.Antwort;
import de.inform.hsh.layers.model.Frage;
import de.inform.hsh.layers.model.Spieler;
import de.inform.hsh.layers.utils.ChooseAntwortActionListener;
import de.inform.hsh.layers.utils.ExitActionListener;
import de.inform.hsh.layers.utils.HelpActionListener;
import de.inform.hsh.layers.utils.JokerActionListener;
import de.inform.hsh.layers.view.design.RoundedButton;
/**
 * Stellt das SpielPanel da
 * @author thorb
 *
 */
public class GameMainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4465516239345143338L;
	//Singleton
	private static volatile GameMainPanel instance;
	//Komponenten
	private JButton[] buttons;
	private JLabel[] topLabels;
	private JLabel frageLabel;
	private JPanel antwortPanel;
	//Timer
	private Timer timer;
	private int secondsRemaining = 60;
	//Objekt zum halten der Frage
	private Frage frage;
	//Hintergrund
	private BufferedImage backgroundImage;
	/**
	 * Methode zum erstllen bzw. bekommen des GameMainPanel
	 * @return GameMainPanel
	 */
	public static synchronized GameMainPanel getInstance() {
		if (instance == null) {
			instance = new GameMainPanel();
		}
		return instance;
	}
	/**
	 * Konstruktor
	 */
	private GameMainPanel() {
        super();
        backgroundImage = GUIController.getInstance().getBackground();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        buttons = new JButton[]{
                new RoundedButton(""), new RoundedButton(""), new RoundedButton(""), new RoundedButton(""),
                new RoundedButton("Help"), new RoundedButton(""), new RoundedButton("Exit"),
        };
        topLabels = new JLabel[]{new JLabel("Runde: "), new JLabel(""), new JLabel("Punkte: ")};
        final String[] btnHints = {"Wähle Antwort", "Wähle Antwort", "Wähle Antwort", "Wähle Antwort", "Öffne Hilfe",
                "Nutze Joker", "Verlasse Spiel"};
        final ActionListener[] btnActions = {
                new ChooseAntwortActionListener(), new ChooseAntwortActionListener(),
                new ChooseAntwortActionListener(), new ChooseAntwortActionListener(),
                new HelpActionListener(), new JokerActionListener(), new ExitActionListener()
        };
        final JPanel spielPanel = new JPanel();
        spielPanel.setPreferredSize(new Dimension(800, 50));
        spielPanel.setLayout(new BoxLayout(spielPanel, BoxLayout.LINE_AXIS));
        spielPanel.setOpaque(false);
        spielPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setTimer();
        final JPanel fragePanel = new JPanel();
        fragePanel.setPreferredSize(new Dimension(800, 100));
        fragePanel.setLayout(new GridBagLayout());
        fragePanel.setOpaque(false);
        frageLabel = new JLabel("");
        frageLabel.setOpaque(true);
        frageLabel.setBackground(Color.BLACK);
        frageLabel.setForeground(Color.GREEN);
        GUIController.getInstance().setStyle(0, new JComponent[]{frageLabel}, 30f);
        frageLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        antwortPanel = new JPanel(new GridLayout(2, 2));
        antwortPanel.setPreferredSize(new Dimension(800, 200));
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(800, 50));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setOpaque(false);
        for (int i = 0; i < btnHints.length; i++) {
            buttons[i].setBackground(Color.BLACK);
            buttons[i].setForeground(Color.GREEN);
            buttons[i].setToolTipText(btnHints[i]);
            buttons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            buttons[i].addActionListener(btnActions[i]);
            if (i >= 4) {
            	GUIController.getInstance().setStyle(i, buttons, 20f);
                bottomPanel.add(buttons[i]);
                if (i != 6) {
                    bottomPanel.add(Box.createHorizontalGlue());
                }
            } else {
            	GUIController.getInstance().setStyle(i, buttons, 15f);
                antwortPanel.add(buttons[i]);
            }
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Etwas Innenabstand
        gbc.fill = GridBagConstraints.NONE; // Kein Stretching
        gbc.anchor = GridBagConstraints.CENTER; // Zentrieren
        fragePanel.add(frageLabel, gbc);
        for (int i = 0; i < topLabels.length; i++) {
        	GUIController.getInstance().setStyle(i, topLabels, 20f);
            topLabels[i].setForeground(Color.GREEN);
            spielPanel.add(topLabels[i]);
            if (i < 2) {
                spielPanel.add(Box.createHorizontalGlue());
            }
        }
        add(spielPanel);
        add(fragePanel);
        add(antwortPanel);
        add(bottomPanel);
    }
	
	/**
	 * Methode zum setzen des Timers
	 */
	private void setTimer() {
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (secondsRemaining > 0) {
					secondsRemaining--;
					topLabels[1].setText(String.valueOf(secondsRemaining));
				} else {
					timer.stop();
					Object[] options = { "Restart", "Beenden" };
					GUIController.getInstance().showJOptionPane("Timer abgelaufen \n HighScore: " + GUIController.getInstance().getSpieler().getPunkte(), "GameOver", options);
				}
			}
		});
	}
	
	/**
	 * Getter-Methode des Timers
	 * @return Timer
	 */
	public Timer getTimer() {
		return timer;
	}
	
	/**
	 * Methode zum setzen der Daten in die Komponenten
	 * @param frage
	 */
	public void setData(Frage frage) {
		this.frage = frage;
		secondsRemaining = 60;
		setInfoPanelData(GUIController.getInstance().getSpieler());
		GUIController.getInstance().setStyle(0,new JComponent[]{frageLabel}, 20f);
		frageLabel.setText(frage.getF());
		for (int i = 0; i < 4; i++) {
			antwortPanel.add(buttons[i]);
			buttons[i].setText(frage.getA().get(i).getA());
		}
	}
	
	/**
	 * Methode zum setzen der Spieler-Informationen in das InfoPanel
	 * @param spieler
	 */
	private void setInfoPanelData(Spieler spieler) {
		timer.start();
		topLabels[0].setText("Leben: " + spieler.getLeben());
		topLabels[0].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		GUIController.getInstance().setStyle(0, topLabels, 20f);
		topLabels[1].setText(String.valueOf(secondsRemaining));
		topLabels[1].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		GUIController.getInstance().setStyle(1, topLabels, 20f);
		topLabels[2].setText("Punkte: " + spieler.getPunkte());
		topLabels[2].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		GUIController.getInstance().setStyle(2, topLabels, 20f);
		buttons[5].setText("Joker: " + spieler.getJoker());
	}
	
	/**
	 * Update-Methode für das InfoPanel
	 */
	public void updateInfoPanelData() {
		setInfoPanelData(GUIController.getInstance().getSpieler());
	}
	
	/**
	 * Methode zum ändern der Antwotten sowie anzahl der Joker
	 */
	public void updateAntwortUndJoker() {
		updateInfoPanelData();
		int j = 0;
		for (Antwort antwort : frage.getA()) {
			if (!antwort.isK() && j < 2) {
				j++;
				for (int i = 0; i < 4; i++) {
					if (buttons[i].getText().equals(antwort.getA())) {
						antwortPanel.remove(buttons[i]);
					}
				}
			}
		}
	}
	
	/**
	 * Methode zum zeichnen des Hintergrundes
	 */
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
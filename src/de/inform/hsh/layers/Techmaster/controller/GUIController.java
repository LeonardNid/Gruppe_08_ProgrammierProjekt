package de.inform.hsh.layers.Techmaster.controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Main.Main;
import de.inform.hsh.layers.Techmaster.model.Frage;
import de.inform.hsh.layers.Techmaster.model.Spieler;
import de.inform.hsh.layers.Techmaster.utils.ChooseLevelActionListener;
import de.inform.hsh.layers.Techmaster.utils.ExitActionListener;
import de.inform.hsh.layers.Techmaster.utils.HelpActionListener;
import de.inform.hsh.layers.Techmaster.utils.QuizFacade;
import de.inform.hsh.layers.Techmaster.utils.StartActionListener;
import de.inform.hsh.layers.Techmaster.view.GameMainFrame;
import de.inform.hsh.layers.Techmaster.view.GameMainPanel;
import de.inform.hsh.layers.Techmaster.view.GameStartPanel;
import de.inform.hsh.layers.Techmaster.view.design.RoundedButton;

/**
 * Diese Klasse bildet den GUIController ab und übernimmt jegliche Kommunikation
 * zwischen Logik und GUI
 * 
 * @author thorb
 *
 */
public class GUIController {
	// Singleton
	private static volatile GUIController instance;
	// Die Facade der Anwendungsschicht (Schnittstelle)
	private QuizFacade quizFacade;
	// Hauptfenster
	private GameMainFrame frame;
	// Panels die auf dem Fenster angezeigt werden
	private GameStartPanel gameStartPanel;
	private GameMainPanel gameMainPanel;
	private GameStartPanel gamelevelPanel;
	// Daten
	private Frage frage;
	/**
	 * Konstruktor
	 */
	private GUIController() {

	}

	/**
	 * Methode zum erstellen bzw Aufrufen des Controllers
	 * 
	 * @return
	 */
	public static synchronized GUIController getInstance() {
		if (instance == null)
			instance = new GUIController();
		return instance;
	}

	/**
	 * Initialisiert die Facade und das Hauptfenster
	 * 
	 * @param app
	 */
	public void runApplication(QuizFacade app) {
		this.quizFacade = app;
		frame = GameMainFrame.getInstance();
		frame.setVisible(true);
	}

	/**
	 * Erstellt das MainPanel bzw gibt es zurück
	 * 
	 * @return GameMainPanel
	 */
	public GameMainPanel getGameMainPanel() {
		if (gameMainPanel == null) {
			gameMainPanel = GameMainPanel.getInstance();
			gameMainPanel.setData(loadFrage());
			getQuizFacade().getSpieler().subscribe(gameMainPanel);
		}
		return gameMainPanel;
	}

	/**
	 * Erstellt das LevelPanel bzw gibt es zurück
	 * 
	 * @return GameStartPanel
	 */
	public GameStartPanel getGameLevelPanel() {
		if (gamelevelPanel == null) {
			JButton[] buttons = new JButton[] { new RoundedButton("Leicht"), new RoundedButton("Mittel"), new RoundedButton("Schwer") };
			String[] btnHints = { "Leicht", "Mittel", "Schwer" };
			ActionListener[] btnActions = { new ChooseLevelActionListener(), new ChooseLevelActionListener(),
					new ChooseLevelActionListener() };
			gamelevelPanel = new GameStartPanel(buttons, btnHints, btnActions);
		}
		return gamelevelPanel;
	}

	/**
	 * Erstellt das StartPanel bzw gibt es zurück
	 * 
	 * @return GameStartPanel
	 */
	public GameStartPanel getGameStartPanel() {
		if (gameStartPanel == null) {
			JButton[] buttons = new JButton[] { new RoundedButton("Start"), new RoundedButton("Help"), new RoundedButton("Exit") };
			String[] btnHints = { "Spiel starten", "Öffnet die Hilfe", "Beendet das Spiel" };
			ActionListener[] btnActions = { new StartActionListener(), new HelpActionListener(),
					new ExitActionListener() };
			gameStartPanel = new GameStartPanel(buttons, btnHints, btnActions);
		}
		return gameStartPanel;
	}

	/**
	 * lädt die Frage mittels Facade
	 * 
	 * @return Frage
	 */
	public Frage loadFrage() {
		frage = getQuizFacade().getFrage();
		if(frage.getF().equals("") && frage.getA().isEmpty()) {
			Object[] options = { "Restart", "Beenden" };
			showJOptionPane("Alle Fragen beantwortet \n Highscore: " +  this.getSpieler().getPunkte(), "Ende", options);
		}
		return frage;
	}

	/**
	 * wählt den Fragenkatalog
	 */
	public void chooseFragenKatalog(String level) {
		getQuizFacade().chooseFragenKatalog(level);
	}

	/**
	 * holt die SpielerInformationen
	 * 
	 * @return Spieler
	 */
	public Spieler getSpieler() {
		return getQuizFacade().getSpieler();
	}

	/**
	 * 
	 * @return QuizFacade
	 */
	public QuizFacade getQuizFacade() {
		if (quizFacade == null) {
			quizFacade = QuizController.getInstance();
		}
		return quizFacade;
	}

	/**
	 * übergibt die Antwort und prüft die leben des Spielers
	 */
	public void setAntwort(String a) {
		boolean isk = getQuizFacade().proofAntwort(a);
		if(isk) {
			playSound("techmaster-correct.wav");
		} else {
			playSound("techmaster-incorrect.wav");
		}
		if (getSpieler().getLeben() == 0) {
			playSound("techmaster-lose.wav");
			Object[] options = { "Restart", "Beenden" };
			showJOptionPane("HighScore: " + Spieler.getInstance().getPunkte(), "GameOver", options);
		} else {
			gameMainPanel.setData(this.loadFrage());
		}
	}

	/**
	 * Jokernutzung mittels Facade
	 * 
	 * @return boolean
	 */
	public boolean useJoker() {
		return getQuizFacade().useJoker(frage);
	}

	/**
	 * Erstellt ein JOptionPane
	 *
	 * @param message, String title, Object[] options, JPanel panel
	 */
	public void showJOptionPane(String message, String title, Object[] options) {
		// Hier muss eine abfrage stattfinden ob das MainPanel bereits genutzt wird
		if (gameMainPanel != null) {
			gameMainPanel.getTimer().stop();
		}
		int result = -1;
		if (message != null && title != null && options != null) {
			result = JOptionPane.showOptionDialog(null, message, title, JOptionPane.PLAIN_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (gameMainPanel != null) {
				gameMainPanel.setEnabled(false);
			}
			if (result == JOptionPane.OK_OPTION) {
				if (gameMainPanel != null) {
					gameMainPanel.setEnabled(true);
					gameMainPanel.getTimer().start();
				}
			}
			if (options[0].equals("Ok")) {
				if (gameMainPanel != null) {
					if (result == JOptionPane.CLOSED_OPTION) {
						gameMainPanel.getTimer().start();
					}
				}
			} else {
				if (result == JOptionPane.CLOSED_OPTION || result == JOptionPane.NO_OPTION) {
					exitJOP(message, title, options);
				} else {
					if (options[0].equals("Restart")) {
						this.restart();
					}
				}
			}
		} else {
			exitJOP(null, null, null);
		}
	}

	/**
	 * Methode zum anzeigen der Bestätigung
	 * 
	 * @param message
	 * @param title
	 * @param options
	 */
	private void exitJOP(String message, String title, Object[] options) {
		int option = JOptionPane.showConfirmDialog(null, "Möchten Sie die Anwendung wirklich beenden?", "Bestätigen",
				JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
//			System.exit(0);
			frame.dispose();
			Main.setMainFrameVisible();
		} else {
			if (message != null && title != null && options != null) {
				showJOptionPane(message, title, options);
			} else if (gameMainPanel != null) {
				gameMainPanel.getTimer().start();
			}
		}
	}
	
	/**
	 * Methode um den Style des Textes festzusetzen
	 * @param i
	 */
	public void setStyle(int i, JComponent[] topLabels, float size) {
		createFont(topLabels[i], size);
		Border greenBorder = BorderFactory.createLineBorder(Color.GREEN, 2);
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border compoundBorder = BorderFactory.createCompoundBorder(greenBorder, emptyBorder);
        topLabels[i].setBorder(compoundBorder);
		topLabels[i].setBackground(Color.BLACK);
		topLabels[i].setForeground(Color.GREEN);
		topLabels[i].setOpaque(true);
	}

	/**
	 * erstellt den Font für die gegebenene Komponente
	 * 
	 * @param title, float size
	 */
	public void createFont(Component title, float size) {
	    try {
	        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("techmaster-PressStart2P-Regular.ttf");
	        if (inputStream != null) {
	            Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
	            customFont = customFont.deriveFont(size);
	            title.setFont(customFont);
	        } else {
	            System.err.println("Die Ressource 'PressStart2P-Regular.ttf' wurde nicht gefunden.");
	        }
	    } catch (FontFormatException | IOException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * setzt den Hintergrund
	 * 
	 * @return BufferedImage
	 */
	public BufferedImage getBackground() {
	    try {
	        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("techmaster-background.jpg");
	        if (inputStream != null) {
	            return ImageIO.read(inputStream);
	        } else {
	            System.err.println("Die Ressource 'background.jpg' wurde nicht gefunden.");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	/**
	 * wechselt die Panels in dem Frame
	 * 
	 * @param old, JPanel new
	 */
	public void setNewView(JPanel old, JPanel newOne) {
		frame.getContentPane().add(newOne, BorderLayout.CENTER);
		frame.getContentPane().remove(old);
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * setzt alles für einen neustart zurück
	 */
	public void restart() {
		getQuizFacade().restart();
		getGameMainPanel().setData(loadFrage());
	}
	/**
	 * Lädt die Ressource und spielt den Sound ab
	 * @param resourcePath
	 */
	public void playSound(String resourcePath) {
	    try {
	    	URL url = getClass().getClassLoader().getResource(resourcePath);
	        if (url != null) {
	            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	            Clip clip = AudioSystem.getClip();
	            clip.open(audioIn);
	            clip.start();
	        } else {
	            System.err.println("Die Ressource '" + resourcePath + "' wurde nicht gefunden.");
	        }
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	        e.printStackTrace();
	    }
	}
}
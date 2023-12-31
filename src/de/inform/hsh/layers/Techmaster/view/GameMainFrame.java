package de.inform.hsh.layers.Techmaster.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.inform.hsh.layers.Techmaster.controller.GUIController;

/**
 * Hauptfenster der Anwendung
 * 
 * @author thorb
 *
 */
public class GameMainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5478304565006011544L;
	// Singleton
	private static volatile GameMainFrame instance;
	// GUIController
	private GUIController guiCtrl;

	/**
	 * Konstruktor
	 */
	private GameMainFrame() {
		super("TechMaster");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		guiCtrl = GUIController.getInstance();
		getContentPane().add(guiCtrl.getGameStartPanel(), BorderLayout.CENTER);
	}

	/**
	 * Methode zum erstellen des Fensters bzw. zum bekommen
	 * 
	 * @return GameMainFrame
	 */
	public static synchronized GameMainFrame getInstance() {
		if (instance == null) {
			instance = new GameMainFrame();
		}
		return instance;
	}
}
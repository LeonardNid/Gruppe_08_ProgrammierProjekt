package Main;

import IT_fighter.layers.RunIT_fighterApp;
import NanoTechDefenders.Help.MyFont;
import NanoTechDefenders.Logic.Controlling.GameManager;
import Techmaster.Techmaster;
import de.Luca.ScamOrNot.Logic.main;
import virusvoid.logic.controller.LogicController;

import javax.swing.*;
import java.awt.*;

/**
 * Die Klasse Main fungiert als Einstiegspunkt für die Anwendung und bietet eine grafische Benutzeroberfläche (GUI)
 * mit Schaltflächen zum Starten verschiedener Anwendungen. Die Klasse enthält Methoden zum Erstellen und Verwalten des
 * Haupt-JFrame- und JPanel-Komponenten sowie Schaltflächen für verschiedene Anwendungen.
 */
public class Main {
    /**
     * Das Haupt-JFrame für die Anwendung.
     */
    public static JFrame frame;

    /**
     * Das Haupt-JPanel mit Schaltflächen zum Starten verschiedener Anwendungen.
     */
    private static JPanel panel = new JPanel();

    /**
     * Die Hauptmethode, die die GUI-Komponenten der Anwendung initialisiert und einrichtet.
     *
     * @param args Kommandozeilenargumente (werden in dieser Anwendung nicht verwendet).
     */
    public static void main(String[] args) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Beendet die Anwendung beim Schließen des Fensters
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Setzt die Größe auf maximal (Vollbildmodus)

        frame.add(panel);

        frame.setUndecorated(true); // Entfernt den Standard-Fensterrahmen (Titelleiste)
        frame.setResizable(false); // Verhindert, dass das Fenster in der Größe verändert werden kann
        frame.setVisible(true); // Macht das Fenster sichtbar

        createJPanel();
        addButtons();
    }

    /**
     * Methode, um das Haupt-JFrame wieder sichtbar zu machen.
     */
    public static void setMainFrameVisible() {
        frame.setVisible(true);
    }

    /**
     * Erstellt das Haupt-JPanel mit einem Rasterlayout und einer festgelegten Hintergrundfarbe.
     */
    private static void createJPanel() {
        panel.setSize(new Dimension(frame.getWidth(), frame.getHeight()));
        panel.setLayout(new GridLayout(2, 3, 20, 20));
        panel.setBackground(new Color(0, 150, 180));
        panel.setVisible(true);
    }

    /**
     * Fügt dem Haupt-JPanel Schaltflächen hinzu, die jeweils eine andere Anwendung starten.
     */
    private static void addButtons() {
        JButton nanoTechDefendersBtn = createBtn("<html>nanoTech<br>Defenders");
        JButton virusVoidBtn = createBtn("virusVoid");
        JButton scamOrNot = createBtn("ScamOrNot");
        JButton techmaster = createBtn("Techmaster");
        JButton escape_Directory = createBtn("<html>Escape<br>Directory");
        JButton itfighter = createBtn("ITFighter");

        Dimension buttonsDimension = new Dimension(frame.getWidth() / 3, frame.getHeight() / 2);

        nanoTechDefendersBtn.setSize(buttonsDimension);
        virusVoidBtn.setSize(buttonsDimension);
        scamOrNot.setSize(buttonsDimension);
        techmaster.setSize(buttonsDimension);
        escape_Directory.setSize(buttonsDimension);
        itfighter.setSize(buttonsDimension);

        nanoTechDefendersBtn.addActionListener(e -> {
            new GameManager();
            frame.setVisible(false);
        });

        virusVoidBtn.addActionListener(e -> {
            LogicController.startApplication();
            frame.setVisible(false);
        });

        scamOrNot.addActionListener(e -> {
            main.main();
            frame.setVisible(false);
        });

        techmaster.addActionListener(e -> {
            new Techmaster();
            frame.setVisible(false);
        });

        escape_Directory.addActionListener(e -> {
            Escape_Directory.Main.main();
            frame.setVisible(false);
        });

        itfighter.addActionListener(e -> {
            RunIT_fighterApp.start();
            frame.setVisible(false);
        });

        panel.add(nanoTechDefendersBtn);
        panel.add(virusVoidBtn);
        panel.add(scamOrNot);
        panel.add(techmaster);
        panel.add(escape_Directory);
        panel.add(itfighter);
    }

    /**
     * Erstellt eine JButton mit benutzerdefinierter Formatierung und Ausrichtung.
     *
     * @param name Der auf der Schaltfläche anzuzeigende Text.
     * @return Die konfigurierte JButton.
     */
    private static JButton createBtn(String name) {
        System.out.println(name);
        JButton button = new JButton(name);
        button.setFont(MyFont.getMyFont());
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setContentAreaFilled(false); // Entfernt das Füllen des Inhaltsbereichs
        button.setOpaque(false); // Macht den Button-Hintergrund transparent
        button.setBorderPainted(false); // Entfernt die Button-Grenze
        button.setForeground(Color.BLACK); // Setzt die Textfarbe
        button.setFocusable(false);
        return button;
    }
}

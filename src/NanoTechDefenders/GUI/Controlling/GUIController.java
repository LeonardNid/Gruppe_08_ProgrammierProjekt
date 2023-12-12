package NanoTechDefenders.GUI.Controlling;

import NanoTechDefenders.GUI.*;
import NanoTechDefenders.GUI.Enemy.EnemyContainPanel;
import NanoTechDefenders.GUI.Enemy.EnemyLabel;
import NanoTechDefenders.GUI.Projectile.ProjectileContainPanel;
import NanoTechDefenders.GUI.Soldier.SoldierContainPanel;
import NanoTechDefenders.Help.Soldiers;
import NanoTechDefenders.Logic.Controlling.GameManager;
import NanoTechDefenders.Logic.Enemy;
import NanoTechDefenders.Logic.Controlling.LogicController;
import NanoTechDefenders.Logic.Projectiles.Projectile;
import NanoTechDefenders.Logic.Soldier;

import java.awt.*;

/**
 * Der GUIController verwaltet die NanoTechDefenders.GUI-Komponenten und steuert die Interaktionen zwischen den Panels.
 */
public class GUIController {
    private static HauptFrame hauptframe;
    private static EnemyContainPanel enemyContainPanel;
    private static SoldierContainPanel soldierContainPanel;
    private static ProjectileContainPanel projectileContainPanel;

    /**
     * Konstruktor für den GUIController.
     * Initialisiert den Hauptframe und fügt das MenuPanel hinzu.
     * Wartet 100millis um zu garantieren, dass das Hauptframe initialisiert ist.
     */
    public GUIController() {
        hauptframe = new HauptFrame();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        switchMenuPanel();
    }

    /**
     * Aktualisiert die Darstellung des Hauptframes.
     */
    public static void repaint() {
        hauptframe.revalidate();
        hauptframe.repaint();
    }

    /**
     * Gibt den X-Faktor basierend auf der Breite des Hauptframes zurück.
     *
     * @return Der X-Faktor.
     */
    public static double getXFactor() {
        return (double) hauptframe.getWidth() / 1600;
    }

    /**
     * Gibt den Y-Faktor basierend auf der Höhe des Hauptframes zurück.
     *
     * @return Der Y-Faktor.
     */
    public static double getYFactor() {
        return (double) hauptframe.getHeight() / 900;
    }

    /**
     * Startet die nächste Angriffswelle im Spiel.
     */
    public static void startNextWave() {
        LogicController.startNextWave();
    }

    /**
     * Wechselt zum MenuPanel und setzt die Spiellogik zurück.
     */
    public static void switchMenuPanel() {
        LogicController.resetLogic();
        hauptframe.getContentPane().removeAll();
        hauptframe.add(new MenuPanel(hauptframe.getWidth(), hauptframe.getHeight()));
        repaint();
    }

    /**
     * Erstellt ein LevelPanel und fügt es dem Hauptframe hinzu.
     * Initialisiert außerdem das SoldierContainPanel, das projectileContainPanel, das EnemyPathPanel und das UIPanel.
     * Startet die Game loop für die Spiel-Updates.
     *
     * @param difficulty Der Schwierigkeitsgrad des Levels.
     */
    public static void createLevelPanels(int difficulty) {
        hauptframe.getContentPane().removeAll();

        UIPanel uiPanel = new UIPanel();
        UIManager.setUiPanel(uiPanel);
        hauptframe.add(uiPanel);
        hauptframe.revalidate();

        hauptframe.add(soldierContainPanel = new SoldierContainPanel(hauptframe.getWidth(), hauptframe.getHeight()));
        hauptframe.revalidate();

        hauptframe.add(projectileContainPanel = new ProjectileContainPanel());
        hauptframe.revalidate();

        hauptframe.add(enemyContainPanel = new EnemyContainPanel());
        hauptframe.revalidate();

        hauptframe.add(new LevelPanel(hauptframe.getWidth(), hauptframe.getHeight()));
        repaint();

        GameManager.startGameLoop();

        LogicController.resetLogic();
        LogicController.createLevelController(difficulty);
    }

    /**
     * Erstellt eine neue Enemy-Instanz, fügt sie der Logik hinzu und aktualisiert das EnemyPathPanel.
     *
     * @param newEnemy Die neue Enemy-Instanz.
     */
    public static void addNewEnemy(Enemy newEnemy) {
        EnemyLabel newEnemyLabel = new EnemyLabel(newEnemy);
        enemyContainPanel.addEnemyPanel(newEnemyLabel);
    }

    /**
     * Erstellt ein Soldatenplatzierungs-Panel.
     *
     * @param soldierVariant Die Art des Soldaten.
     */
    public static void createSoldierPlacePanel(Soldiers soldierVariant) {
        soldierContainPanel.createSoldierPlacePanel(soldierVariant);
    }

    /**
     * Entfernt einen Soldaten aus dem SoldierContainPanel.
     *
     * @param soldier Der zu entfernende Soldat.
     */
    public static void removeSoldier(Soldier soldier) {
        soldierContainPanel.removeSoldierLabel(soldier);
    }

    /**
     * Löscht alle Soldaten aus dem SoldierContainPanel.
     */
    public static void clearSoldier() {
        if (soldierContainPanel != null) {
            soldierContainPanel.clearSoldier();
        }
    }

    /**
     * Platziert einen Soldaten an der angegebenen Position und informiert die Logik darüber.
     *
     * @param soldierLocation Die Position, an der der Soldat platziert werden soll.
     * @param soldierVariant  Die Art des Soldaten.
     */
    public static void placeSoldier(Point soldierLocation, Soldiers soldierVariant) {
        Soldier soldier = LogicController.createSoldier(soldierLocation, soldierVariant);
        soldierContainPanel.placeSoldier(soldier);
        LogicController.soldierBtnPressed(soldierVariant);
    }

    /**
     * Erstellt ein ProjectileLabel und fügt es dem ProjectileContainPanel hinzu.
     *
     * @param newProjectile Das neue Projectile.
     */
    public static void createProjectileLabel(Projectile newProjectile) {
        projectileContainPanel.addProjectile(newProjectile);
    }

    /**
     * Wechselt zum Game Over Screen und zeigt die Spielerstatistiken an.
     *
     * @param stats Die Spielerstatistiken.
     */
    public static void switchGameOverScreen(String[] stats) {
        hauptframe.getContentPane().removeAll();
        hauptframe.add(new GameOverScreen(hauptframe.getWidth(), hauptframe.getHeight(), stats));
        repaint();
    }

    /**
     * Öffnet das Game Menu, pausiert das Spiel und macht die aktuellen Komponenten unsichtbar.
     */
    public static void openGameMenu() {
        GameManager.stopGameLoop();
        LogicController.stop();
        GameMenuPanel gameMenuPanel = new GameMenuPanel(hauptframe.getWidth(), hauptframe.getHeight());
        for (Component c : hauptframe.getContentPane().getComponents()) {
            c.setVisible(false);
        }
        hauptframe.add(gameMenuPanel);
        hauptframe.revalidate();
    }

    /**
     * Schließt das Game Menu, setzt die Komponenten wieder sichtbar und setzt das Spiel fort.
     *
     * @param gameMenuPanel Das Game Menu Panel, das geschlossen werden soll.
     */
    public static void closeGameMenu(GameMenuPanel gameMenuPanel) {
        hauptframe.remove(gameMenuPanel);
        for (Component c : hauptframe.getContentPane().getComponents()) {
            c.setVisible(true);
        }
        repaint();
        LogicController.start();
        GameManager.startGameLoop();
    }

    /**
     * Aktualisiert das ProjectileContainPanel.
     */
    public static void updateProjectileContainPanel() {
        projectileContainPanel.update();
    }

    /**
     * Aktualisiert das EnemyContainPanel.
     */
    public static void updateEnemyContainPanel() {
        enemyContainPanel.update();
    }

    /**
     * Aktualisiert das SoldierContainPanel.
     */
    public static void updateSoldierContainPanel() {
        soldierContainPanel.update();
    }

    /**
     * Ändert die Geschwindigkeit des Spiels.
     */
    public static void changeSpeed() {
        GameManager.changeSpeed();
    }
}

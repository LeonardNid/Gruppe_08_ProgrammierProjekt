package NanoTechDefenders.GUI.Enemy;

import javax.swing.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Das EnemyContainPanel dient dazu, alle EnemyPanels zu hosten.
 * Es verwaltet die Bewegung der Gegner und entfernt sie, wenn sie das Ziel erreicht haben.
 */
public class EnemyContainPanel extends JPanel {
    private static CopyOnWriteArrayList<EnemyLabel> enemyLabelList = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<EnemyLabel> removableEnemyLabels = new CopyOnWriteArrayList<>();

    /**
     * Erstellt ein neues EnemyContainPanel.
     * Setzt die Layout-Manager auf null für eine benutzerdefinierte Anordnung der Komponenten.
     * Macht den Hintergrund des Panels transparent.
     */
    public EnemyContainPanel() {
        this.setLayout(null);
        this.setOpaque(false);
    }

    /**
     * Fügt ein neues EnemyPanel zum EnemyContainPanel hinzu.
     *
     * @param newEnemyLabel Das neue EnemyPanel, das hinzugefügt werden soll.
     */
    public void addEnemyPanel(EnemyLabel newEnemyLabel) {
        enemyLabelList.add(newEnemyLabel);
        this.add(newEnemyLabel);
    }

    /**
     * Aktualisiert die Position der EnemyPanels und entfernt diejenigen, die das Ziel erreicht haben.
     */
    public void update() {
        for (EnemyLabel enemypanel : enemyLabelList) {
            enemypanel.updateLocation();
            if (enemypanel.getEnemyLogic().getRemovableStatus()) {
                removableEnemyLabels.add(enemypanel);
            }
        }
        for (EnemyLabel removableEP : removableEnemyLabels) {
            enemyLabelList.remove(removableEP);
            this.remove(removableEP);
        }
        removableEnemyLabels.clear();
    }
}

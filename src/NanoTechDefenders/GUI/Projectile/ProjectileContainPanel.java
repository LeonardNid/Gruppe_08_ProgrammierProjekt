package NanoTechDefenders.GUI.Projectile;

import NanoTechDefenders.Logic.Projectiles.Projectile;

import javax.swing.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Das ProjectileContainPanel ist dafür da, Projektile zu verwalten und anzuzeigen.
 * Es enthält eine Liste von ProjectileLabel-Objekten.
 */
public class ProjectileContainPanel extends JPanel {
    private static CopyOnWriteArrayList<ProjectileLabel> projectileLabelList = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<ProjectileLabel> removableProjPanelList = new CopyOnWriteArrayList<>();

    /**
     * Konstruktor für das ProjectileContainPanel.
     * Setzt den Hintergrund transparent und verwendet kein spezielles Layout.
     */
    public ProjectileContainPanel() {
        this.setOpaque(false); // Hintergrund transparent machen
        this.setLayout(null); // Kein spezielles Layout verwenden
    }

    /**
     * Fügt ein neues Projektil dem Panel hinzu.
     *
     * @param newProjectile Das hinzuzufügende Projektil.
     */
    public void addProjectile(Projectile newProjectile) {
        ProjectileLabel projectileLabel = new ProjectileLabel(newProjectile);
        this.add(projectileLabel);
        projectileLabelList.add(projectileLabel);
    }

    /**
     * Aktualisiert die Positionen der Projektile und entfernt diejenigen, die ihre Aufgabe erfüllt haben.
     */
    public void update() {
        for (ProjectileLabel projectileLabel : projectileLabelList) {
            projectileLabel.updateLocation();
            if (projectileLabel.getLogicProjectile().getStatus()) {
                if (!projectileLabel.getLogicProjectile().getOnHitEffect()) {
                    removableProjPanelList.add(projectileLabel);
                }
            }
        }
        for (ProjectileLabel removableProjectileLabel : removableProjPanelList) {
            projectileLabelList.remove(removableProjectileLabel);
            this.remove(removableProjectileLabel);
        }
        this.repaint();
        removableProjPanelList.clear();
    }
}

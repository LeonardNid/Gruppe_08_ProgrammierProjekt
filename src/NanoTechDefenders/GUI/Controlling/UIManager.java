package NanoTechDefenders.GUI.Controlling;

import NanoTechDefenders.GUI.UIPanel;
import NanoTechDefenders.Help.Soldiers;
import NanoTechDefenders.Logic.Controlling.LogicController;

/**
 * Der UIManager verwaltet die Aktualisierung der NanoTechDefenders.GUI-Elemente und steuert die Interaktionen mit dem UIPanel.
 */
public class UIManager {
    private static UIPanel uiPanel;

    /**
     * Setzt das UIPanel, das vom UIManager verwaltet wird.
     *
     * @param newUiPanel Das neue UIPanel.
     */
    public static void setUiPanel(UIPanel newUiPanel) {
        uiPanel = newUiPanel;
    }

    /**
     * Aktualisiert das Anzeige-Label für die verbleibenden Spielerleben.
     *
     * @param playerLifes Die Anzahl der verbleibenden Leben des Spielers.
     */
    public static void updateLifeLabel(int playerLifes) {
        uiPanel.updateLifeLabel(playerLifes);
    }

    /**
     * Informiert das UIPanel darüber, dass die aktuelle Angriffswelle abgeschlossen ist.
     *
     * @param currentWave Nummer von der aktuellen Angriffswelle als String.
     */
    public static void currentWaveFinished(String currentWave) {
        uiPanel.updateCurrentWave(currentWave);
    }

    /**
     * Aktiviert den Spawn-Button im UIPanel.
     */
    public static void enableSpawnBtn() {
        uiPanel.activateSpawnbtn();
    }

    /**
     * Aktualisiert die Anzeige für den aktuellen Geldbetrag im UIPanel.
     *
     * @param currentMoneyAmount Der aktuelle Geldbetrag des Spielers.
     */
    public static void updateMoneyCount(int currentMoneyAmount) {
        uiPanel.updateMoney(currentMoneyAmount);
    }

    /**
     * Ändert den Autoplay-Status über die Logiksteuerung.
     */
    public static void changeAutoplay() {
        LogicController.changeAutoplay();
    }

    /**
     * Schaltet einen bestimmten Soldaten-Button im UIPanel ein oder aus.
     *
     * @param soldierVariant Der Soldatentyp, dessen Button geschaltet wird.
     * @param isEnabled      Der Zustand, ob der Button aktiviert oder deaktiviert werden soll.
     */
    public static void toggleSoldierBtn(Soldiers soldierVariant, boolean isEnabled) {
        uiPanel.toggleSoldierBtn(soldierVariant, isEnabled);
    }

    /**
     * Schaltet alle Soldaten-Buttons im UIPanel ein oder aus.
     *
     * @param isEnabled Der Zustand, ob die Buttons aktiviert oder deaktiviert werden sollen.
     */
    public static void toggleSoldierBtn(boolean isEnabled) {
        for (Soldiers soldierVariant : Soldiers.values()) {
            uiPanel.toggleSoldierBtn(soldierVariant, isEnabled);
        }
    }
}

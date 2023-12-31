package IT_fighter.layers;

import IT_fighter.layers.ui.ctrl.ITFighterGuiController;

/**
 * Klasse mit der die ITFighter Anwendung gestartet werden kann
 */
public class RunIT_fighterApp {
    /**
     * startet die Anwendung
     */
    public static void start() {
        ITFighterGuiController.getInstance().startGui();
    }
}

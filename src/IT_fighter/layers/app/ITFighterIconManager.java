package IT_fighter.layers.app;

import java.awt.geom.Rectangle2D;

import static IT_fighter.utilities.UtilMethods.colisionWithPlayer;

/**
 * Der IconManager ist verwaltet die Logik des Tiktok Icons und das Windowsdefender Icons
 */
public class ITFighterIconManager {
    private final Rectangle2D.Float tiktok;
    private final Rectangle2D.Float windowsDefender;
    private boolean isTouchingTiktokIcon = false;
    private boolean showingTiktokIcon = false;

    /**
     * Das Tiktok- und WindowsDefender-Icon werden durch Rechtecke der Klasse Rectangle2D dargestellt
     */
    public ITFighterIconManager() {
        windowsDefender = new Rectangle2D.Float(5720, 730, 32, 32);
        tiktok = new Rectangle2D.Float(1088, 890, 32, 32);
    }

    /**
     * überprüft, ob die Spielfigur eines der beiden Icons berührt.
     * Die Methode wird 200-mal pro Sekunde in der GameLoop aufgerufen.
     */
    public void checkCollisionWithIcon() {
        if(colisionWithPlayer(ITFighterAppController.getInstance().getCurrentPlayerPosition(), windowsDefender)){
            ITFighterAppController.getInstance().levelFinished();
        }
        if(colisionWithPlayer(ITFighterAppController.getInstance().getCurrentPlayerPosition(), tiktok)) {
            if (!isTouchingTiktokIcon && !showingTiktokIcon) {
                ITFighterAppController.getInstance().touchedTiktok();
                isTouchingTiktokIcon = true;
            }
        } else if (isTouchingTiktokIcon) {
            isTouchingTiktokIcon = false;
        }
    }

    /**
     *
     * @param showingTiktokIcon mit true aufrufen, wenn das TiktokPanel angezeigt wird
     */
    public void setShowingTiktokIcon(boolean showingTiktokIcon) {
        this.showingTiktokIcon = showingTiktokIcon;
    }
}

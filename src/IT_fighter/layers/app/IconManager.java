package IT_fighter.layers.app;

import java.awt.geom.Rectangle2D;

import static IT_fighter.utilities.UtilMethods.colisionWithPlayer;

public class IconManager {
    private Rectangle2D.Float tiktok;
    private Rectangle2D.Float windowsDefender;
    private boolean isTouchingTiktokIcon = false;
    private boolean showingTiktokIcon = false;

    public IconManager() {
        windowsDefender = new Rectangle2D.Float(5720, 730, 32, 32);
        tiktok = new Rectangle2D.Float(1088, 890, 32, 32);
    }

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

    public void setTouchingTiktokIcon(boolean touchingTiktokIcon) {
        isTouchingTiktokIcon = touchingTiktokIcon;
    }

    public void setShowingTiktokIcon(boolean showingTiktokIcon) {
        this.showingTiktokIcon = showingTiktokIcon;
    }
}

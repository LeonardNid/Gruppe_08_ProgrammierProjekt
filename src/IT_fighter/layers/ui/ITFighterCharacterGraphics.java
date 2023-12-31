package IT_fighter.layers.ui;

import IT_fighter.layers.app.Entity.ITFighterCharacter;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.utilities.LoadAndSaveData;

import java.awt.*;
import java.awt.image.BufferedImage;

import static IT_fighter.utilities.Constants.PlayerConstants.getSpritesPerLine;

/**
 * verwaltet die grafischen Komponenten der Spielfigur
 */
public class ITFighterCharacterGraphics {
    private final ITFighterCharacter mCharacter;
    private BufferedImage playerImage;
    private BufferedImage[][] animations;
    private final int aniSpeed = 50;// anispeed = UPS // 4 Bilder der Animation pro Sekunde
    private int aniTick, aniIndex;

    /**
     * @param mCharacter ist die logische Komponente der Spielfigur und muss an dem Konstruktor übergeben werden
     */
    public ITFighterCharacterGraphics(ITFighterCharacter mCharacter) {
        this.mCharacter = mCharacter;
        loadAnimations();
    }
    //##################################################################################################################

    /**
     * lädt die Bilder der Spielfigur aus einem Bild und speichert diese in einem 2 dimensionalen Array
     */
    private void loadAnimations() {
        this.playerImage = LoadAndSaveData.getImage("ITF_AnimationSheet_Character.jpg");
        animations = new BufferedImage[9][8];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = playerImage.getSubimage(i*32, j*32, 32, 32);

            }

        }
    }
    //##################################################################################################################

    /**
     * Eine Animation der Spielfigur besteht aus mehreren Bildern, die hintereinander gezeichnet werden.
     * Diese Methode sorgt dafür, dass alle Bilder, die zu einer Animation gehören wiederholt durchlaufen werden.
     */
    public void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpritesPerLine(mCharacter.getAction())) {
                aniIndex = 0;
            }
        }
    }
    //##################################################################################################################
    //getter und setter

    /**
     * setzt den Animationsindex.
     * Wird hauptsächlich dafür den AnimationTick auf 0 zurückzusetzen, wenn sich die Animation geändert hat.
     * @param aniTick ist eine Variable, die hochgezählt wird mit jedem Aufruf von loadAnimations(), ist sie so
     *                groß wie der aniSpeed wird der AnimtionIndex erhöht
     */
    public void setAniTick(int aniTick) {
        this.aniTick = aniTick;
    }

    /**
     * setzt den Animationsindex.
     * Wird hauptsächlich dafür den AnimationTick auf 0 zurückzusetzen, wenn sich die Animation geändert hat.
     * @param aniIndex gibt den Index, welches Bild einer Animation aufgerufen werden soll.
     */
    public void setAniIndex(int aniIndex) {
        this.aniIndex = aniIndex;
    }

    /**
     * setzt den AnimationIndex und den AnimationTick auf 0
     * wird verwendet, wenn sich die Animation verändert
     */
    public void setAnimationIndex() {
        setAniTick(0);
        setAniIndex(0);
    }

    /**
     * zeichnet das Bild der Spielfigur
     * @param g Grafik Objekt, welches in der regel von der paintComponent einer JComponent stammt
     */
    public void render(Graphics g) {
        g.drawImage(animations[mCharacter.getAction()][aniIndex],
                 mCharacter.getX() - ITFighterGuiController.getInstance().getLevelOffset(),
                 mCharacter.getY(),
                32, 32, null);

    }
}
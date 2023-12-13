package IT_fighter.layers.ui;

import IT_fighter.layers.app.Entity.ITFighterCharacter;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.layers.ui.menuPanels.BasicPanel;
import IT_fighter.utilities.LoadAndSaveData;

import java.awt.*;
import java.awt.image.BufferedImage;

import static IT_fighter.utilities.Constants.PlayerConstants.getSpritesPerLine;

public class ITFighterCharacterPanel{

    private ITFighterCharacter mCharacter;
    private BufferedImage playerImage;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 50; // anispeed = UPS // 4 animations per Second

    public ITFighterCharacterPanel(ITFighterCharacter mCharacter) {
        this.mCharacter = mCharacter;
        loadAnimations();
    }





    //##################################################################################################################
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


    public void update() {
        updateAnimationTick();
    }

    private void updateAnimationTick() {
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
    public void setAniTick(int aniTick) {
        this.aniTick = aniTick;
    }

    public void setAniIndex(int aniIndex) {
        this.aniIndex = aniIndex;
    }

    public void setAnimationIndex() {
        setAniTick(0);
        setAniIndex(0);
    }
    public void render(Graphics g) {
        g.drawImage(animations[mCharacter.getAction()][aniIndex],
                 mCharacter.getX() - ITFighterGuiController.getInstance().getLevelOffset(),
                 mCharacter.getY(),
                32, 32, null);

    }
}
package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.ITFighterCharacter;
import IT_fighter.layers.app.ITFighterAppController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static IT_fighter.utilities.Constants.Directions.*;
import static IT_fighter.utilities.Constants.PlayerConstants.*;

public class PlayerKeyHandler implements KeyListener {
    ITFighterCharacter character;
    public PlayerKeyHandler() {
        character = ITFighterAppController.getInstance().getActualCharacter();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    //TODO hier müssen noch die richtigen Aufrufe für den AppController hinterlegt werden
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                character.setUp(true);
                break;
            case KeyEvent.VK_A:
                character.setLeft(true);
                break;
            case KeyEvent.VK_S:
                character.setDown(true);
                break;
            case KeyEvent.VK_D:
                character.setRight(true);
                break;
            case KeyEvent.VK_ESCAPE:
                ITFighterAppController.getInstance().closeGame();

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                character.setUp(false);
                break;
            case KeyEvent.VK_A:
                character.setLeft(false);
                break;
            case KeyEvent.VK_S:
                character.setDown(false);
                break;
            case KeyEvent.VK_D:
                character.setRight(false);
                break;

        }
    }
}


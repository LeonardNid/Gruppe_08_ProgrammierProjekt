package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.ITFighterCharacter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ITFighterPlayerKeyHandler implements KeyListener {
    ITFighterCharacter character;
    public ITFighterPlayerKeyHandler() {
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
                character.setJump(true);
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
                character.setJump(false);
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


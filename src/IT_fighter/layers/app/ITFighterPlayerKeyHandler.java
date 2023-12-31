package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.ITFighterCharacter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Der KeyHandler manipuliert in Abhängigkeit der gedrückten Tasten die Wahrheitswerte für right, left und jump
 * im Character
 */
public class ITFighterPlayerKeyHandler implements KeyListener {
    ITFighterCharacter character;

    /**
     * Konstruktor des KeyHandlers
     */
    public ITFighterPlayerKeyHandler() {
        character = ITFighterAppController.getInstance().getActualCharacter();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * gibt an was passiert, wenn die Tasten W, A, D und Esc gedrückt werden
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                character.setJump(true);
                break;
            case KeyEvent.VK_A:
                character.setLeft(true);
                break;
            case KeyEvent.VK_D:
                character.setRight(true);
                break;
            case KeyEvent.VK_ESCAPE:
                ITFighterAppController.getInstance().closeGame();

        }

    }

    /**
     * Gibt an was passiert, wenn die Tasten W, A, D und Esc losgelassen werden
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                character.setJump(false);
                break;
            case KeyEvent.VK_A:
                character.setLeft(false);
                break;
            case KeyEvent.VK_D:
                character.setRight(false);
                break;
        }
    }
}


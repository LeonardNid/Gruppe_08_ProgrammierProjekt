package IT_fighter.layers.ui;

import IT_fighter.layers.app.ITFighterAppController;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.layers.app.PlayerKeyHandler;
import IT_fighter.layers.ui.menuPanels.BasicPanel;

import java.awt.*;

public class ITFighterGamePanel3 extends BasicPanel {

    ITFighterCharacterPanel mcharacterPanel;

    public ITFighterGamePanel3() {
        setSize();
        mcharacterPanel = ITFighterGuiController.getInstance().getCharacterPanel();
        this.addKeyListener(new PlayerKeyHandler());
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        //ITFighterAppController.getInstance().startGameLoop();
        //getPlayerImage();
    }


    //##################################################################################################################
    //updating graphics



    public void repaintPanel() {
        mcharacterPanel.update();
        this.revalidate();
        this.repaint();
//System.out.println(SwingUtilities.isEventDispatchThread());

    }











    //##################################################################################################################
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        mcharacterPanel.render(g);

    }



}

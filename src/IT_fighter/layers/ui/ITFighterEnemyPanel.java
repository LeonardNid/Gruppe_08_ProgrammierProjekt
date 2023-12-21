package IT_fighter.layers.ui;

import IT_fighter.layers.app.EnemyManager;
import IT_fighter.layers.app.Entity.Virus;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;
import IT_fighter.utilities.LoadAndSaveData;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ITFighterEnemyPanel {
    private final String virusName = "ITF_Corona.jpg";
    private BufferedImage virus;
    private EnemyManager currentEnemyManager;

    public ITFighterEnemyPanel(EnemyManager currentEnemyManager) {
        this.currentEnemyManager = currentEnemyManager;
        virus = LoadAndSaveData.getImage(virusName);
    }
    public void drawEnemies(Graphics graphics, int levelOffset) {
        for (Virus v: currentEnemyManager.getVirusArrayList()) {
            graphics.drawImage(virus, v.getX()- levelOffset,
                    v.getY(), 30, 30, null);
        }
    }
}

package IT_fighter.layers.ui;

import IT_fighter.layers.app.EnemyManager;
import IT_fighter.layers.app.Entity.BinaryCode;
import IT_fighter.layers.app.Entity.Virus;
import IT_fighter.utilities.LoadAndSaveData;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ITFighterEnemyPanel {
    private BufferedImage virus, binaryCode;
    private EnemyManager currentEnemyManager;

    public ITFighterEnemyPanel(EnemyManager currentEnemyManager) {
        this.currentEnemyManager = currentEnemyManager;
        virus = LoadAndSaveData.getImage("ITF_Corona.jpg");
        binaryCode = LoadAndSaveData.getImage("ITF_BinaryCode.jpg");
    }
    public void drawEnemies(Graphics graphics, int levelOffset) {
        drawVirus(graphics, levelOffset);
        drawBinaryCodes(graphics,levelOffset);
    }
    public void drawVirus(Graphics graphics, int levelOffset) {
        for (Virus v: currentEnemyManager.getVirusList()) {
            graphics.drawImage(virus, v.getX()- levelOffset,
                    v.getY(), 30, 30, null);
        }
    }
    public void drawBinaryCodes(Graphics graphics, int levelOffset) {
        for (BinaryCode b: currentEnemyManager.getBinaryCodesList()) {
            graphics.drawImage(binaryCode, b.getX()- levelOffset,
                    b.getY(), 30, 30, null);
        }
    }
}

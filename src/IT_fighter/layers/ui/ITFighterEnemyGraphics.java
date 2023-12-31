package IT_fighter.layers.ui;

import IT_fighter.layers.app.ITFighterEnemyManager;
import IT_fighter.layers.app.Entity.ITFighterBinaryCode;
import IT_fighter.layers.app.Entity.ITFighterVirus;
import IT_fighter.utilities.LoadAndSaveData;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * Die Klasse EnemyGraphics verwaltet die grafischen Komponenten der Gegner
 */
public class ITFighterEnemyGraphics {
    private final BufferedImage virus, binaryCode;
    private ITFighterEnemyManager currentEnemyManager;

    /**
     * Konstruktor lädt die Bilder für die Gegner und legt die EnemyManager fest aus dem die Daten zum zeichen
     * der Gegener verwendet werden
     * @param currentEnemyManager der EnemyManger aus dem die Daten gelesen werden sollen
     */
    public ITFighterEnemyGraphics(ITFighterEnemyManager currentEnemyManager) {
        this.currentEnemyManager = currentEnemyManager;
        virus = LoadAndSaveData.getImage("ITF_Corona.jpg");
        binaryCode = LoadAndSaveData.getImage("ITF_BinaryCode.jpg");
    }

    /**
     * zeichnet alle Gegner
     * @param graphics grafische Komponente eines GamePanels
     * @param levelOffset größe des aktuellen Offsets des Spiels
     */
    public void drawEnemies(Graphics graphics, int levelOffset) {
        drawVirus(graphics, levelOffset);
        drawBinaryCodes(graphics,levelOffset);
    }

    /**
     * Zeichnet alle Viren des Spiels.
     * @param graphics grafische Komponente eines GamePanels
     * @param levelOffset größe des aktuellen Offsets des Spiels
     */
    public void drawVirus(Graphics graphics, int levelOffset) {
        for (ITFighterVirus v: currentEnemyManager.getVirusList()) {
            graphics.drawImage(virus, v.getX()- levelOffset,
                    v.getY(), 30, 30, null);
        }
    }

    /**
     * Zeichnet alle BinärCodes des Spiels.
     * Wird 120-mal pro Sekunde aufgerufen.
     * @param graphics grafische Komponente eines GamePanels
     * @param levelOffset größe des aktuellen Offsets des Spiels
     */
    public void drawBinaryCodes(Graphics graphics, int levelOffset) {
        for (ITFighterBinaryCode b: currentEnemyManager.getBinaryCodesList()) {
            graphics.drawImage(binaryCode, b.getX()- levelOffset,
                    b.getY(), 30, 30, null);
        }
    }
}

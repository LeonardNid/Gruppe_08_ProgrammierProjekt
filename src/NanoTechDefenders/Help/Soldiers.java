package NanoTechDefenders.Help;

/**
 * Die Enumeration {@code Soldiers} repräsentiert verschiedene Soldatentypen mit ihren Eigenschaften.
 */
public enum Soldiers {
    /**
     * Der normale Soldatentyp.
     */
    NORMAL(100, 5, 100, "NTD_Soldier1.png"),

    /**
     * Der Durchdringungsschütze Soldatentyp.
     */
    PIERCER(120, 25, 300, "NTD_BowSoldier.png"),

    /**
     * Der Scharfschützen Soldatentyp.
     */
    SNIPER(10000, 100, 400, "NTD_RocketSoldier.png"),

    /**
     * Der Flammenwerfer Soldatentyp.
     */
    FLAMETHROWER(100, 2, 600, "NTD_FlameThrowerSoldier.png"),

    /**
     * Der Strahlenkanonen Soldatentyp.
     */
    BEAM(500, 300, 700, "NTD_BeamSoldier.png");

    private final int enemyView;
    private final int maxReloadTime;
    private final int soldierCost;
    private final String fileName;

    /**
     * Konstruktor für die Soldiers-Enumeration.
     *
     * @param enemyView      Die Sichtweite des Soldaten.
     * @param maxReloadTime  Die maximale Nachladezeit des Soldaten.
     * @param soldierCost    Die Kosten des Soldaten.
     * @param fileName       Der Dateiname des Bildes, das den Soldaten repräsentiert.
     */
    Soldiers(int enemyView, int maxReloadTime, int soldierCost, String fileName) {
        this.enemyView = enemyView;
        this.maxReloadTime = maxReloadTime;
        this.soldierCost = soldierCost;
        this.fileName = fileName;
    }

    /**
     * Gibt die Sichtweite des Soldaten zurück.
     *
     * @return Die Sichtweite des Soldaten.
     */
    public int getEnemyView() {
        return enemyView;
    }

    /**
     * Gibt die maximale Nachladezeit des Soldaten zurück.
     *
     * @return Die maximale Nachladezeit des Soldaten.
     */
    public int getMaxReloadTime() {
        return maxReloadTime;
    }

    /**
     * Gibt die Kosten des Soldaten zurück.
     *
     * @return Die Kosten des Soldaten.
     */
    public int getSoldierCost() {
        return soldierCost;
    }

    /**
     * Gibt den Dateinamen des Bildes des Soldaten zurück.
     *
     * @return Der Dateiname des Bildes des Soldaten.
     */
    public String getFileName() {
        return fileName;
    }
}

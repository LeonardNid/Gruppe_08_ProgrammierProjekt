package NanoTechDefenders.Logic.Controlling;

/**
 * Die Klasse LevelManager verwaltet die Einstellungen für das Spiellevel.
 */
public class LevelManager {
    private static int currentWave = 0;
    private static int playerLifes;
    private static double enemySpawnScaling;
    private static int enemySpawnFrequency;
    private static double enemyFrequencyScaling;
    private static int enemyStartLife;
    private static double enemyLifeScaling;
    private static int startMoney = 400; //600
    private static int moneyPerKill;
    private static int moneyPerWave;

    /**
     * Setzt die Schwierigkeit des Spiels.
     *
     * @param difficulty Der Schwierigkeitsgrad (0 = Keine Änderung, 1 = Leicht, 2 = Normal, 3 = Schwer).
     */
    public static void setDifficulty(int difficulty) {
        switch (difficulty) {
            case 0 -> System.out.println("Keine Schwierigkeitsänderung");
            case 1 -> setLevelEasy();
            case 2 -> setLevelNormal();
            case 3 -> setLevelHard();
            default -> System.out.println("Fehler: Level-Einstellung - falsche Schwierigkeit");
        }
    }

    /**
     * Setzt die Spielparameter für den leichten Schwierigkeitsgrad.
     */
    private static void setLevelEasy() {
        playerLifes = 200;
        enemySpawnScaling = 0.5;
        enemySpawnFrequency = 60;
        enemyFrequencyScaling = 0.5;
        enemyStartLife = 15;
        enemyLifeScaling = 3;
        moneyPerWave = 10;
        moneyPerKill = 6;
        System.out.println("Level-Einstellung: Leicht");
    }

    /**
     * Setzt die Spielparameter für den normalen Schwierigkeitsgrad.
     */
    private static void setLevelNormal() {
        playerLifes = 100;
        enemySpawnScaling = 1;
        enemySpawnFrequency = 60;
        enemyFrequencyScaling = 1;
        enemyStartLife = 20;
        enemyLifeScaling = 5;
        moneyPerWave = 10;
        moneyPerKill = 5;
        System.out.println("Level-Einstellung: Normal");
    }

    /**
     * Setzt die Spielparameter für den schweren Schwierigkeitsgrad.
     */
    private static void setLevelHard() {
        playerLifes = 50;
        enemySpawnScaling = 2;
        enemySpawnFrequency = 60;
        enemyFrequencyScaling = 2;
        enemyStartLife = 20;
        enemyLifeScaling = 7;
        moneyPerWave = 5;
        moneyPerKill = 4;
        System.out.println("Level-Einstellung: Schwer");
    }

    /**
     * Erhöht die aktuelle Welle und passt ggf. die Feindlebensskalierung an.
     */
    public static void addWave() {
        ++currentWave;
        if (currentWave % 10 == 0) {
            ++enemyLifeScaling;
        }
    }

    // ########### Getters ###########

    /**
     * Gibt die aktuelle Welle zurück.
     *
     * @return Die aktuelle Welle.
     */
    public static int getCurrentWave() {
        return currentWave;
    }

    /**
     * Gibt die Anzahl der Spielerleben zurück.
     *
     * @return Die Anzahl der Spielerleben.
     */
    public static int getPlayerLifes() {
        return playerLifes;
    }

    /**
     * Gibt die Anzahl der Feinde pro Welle zurück, unter Berücksichtigung der Skalierung.
     *
     * @return Die Anzahl der Feinde pro Welle.
     */
    public static int getEnemiesPerWave() {
        return (int) Math.ceil(currentWave * enemySpawnScaling);
    }

    /**
     * Gibt die Frequenz zurück, mit der Feinde spawnen, unter Berücksichtigung der Skalierung.
     *
     * @return Die Feindspawn-Frequenz.
     */
    public static int getEnemySpawnFrequency() {
        int temp = (int) (enemySpawnFrequency - currentWave * enemyFrequencyScaling);
        return Math.max(temp, 15);
    }

    /**
     * Gibt das Startleben der Feinde zurück, unter Berücksichtigung der Skalierung.
     *
     * @return Das Startleben der Feinde.
     */
    public static int getEnemyStartLife() {
        return (int) (enemyStartLife + currentWave * enemyLifeScaling);
    }

    /**
     * Gibt das Startgeld zurück.
     *
     * @return Das Startgeld.
     */
    public static int getStartMoney() {
        return startMoney;
    }

    /**
     * Gibt die Belohnung für einen getöteten Feind zurück, unter Berücksichtigung der Skalierung.
     *
     * @return Die Belohnung für einen getöteten Feind.
     */
    public static int getMoneyPerKill() {
        int temp = moneyPerKill - currentWave / 10;
        if (temp <= 0) {
            return 1;
        }
        return temp;
    }

    /**
     * Gibt die Geldbelohnung pro Welle zurück.
     *
     * @return Die Geldbelohnung pro Welle.
     */
    public static int getMoneyPerWave() {
        return moneyPerWave;
    }

    /**
     * Setzt die aktuelle Welle zurück.
     */
    public static void reset() {
        currentWave = 0;
    }
}

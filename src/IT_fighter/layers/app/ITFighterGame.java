package IT_fighter.layers.app;

import IT_fighter.utilities.LoadAndSaveData;

/**
 * Verwaltet konstante Werte des Spiels
 */
public class ITFighterGame {
    //allgemeine Daten über das Spiel
    private static final String LEVEL_ONE_DATA = "ITF_level_one_data.jpg";
    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1f;
    public static final int TILES_IN_WIDTH = 60;
    public static final int TILES_IN_HEIGHT = 33;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public static final int [][] levelOneData = LoadAndSaveData.getLevelData(LEVEL_ONE_DATA);
}

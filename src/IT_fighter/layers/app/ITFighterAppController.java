package IT_fighter.layers.app;


import IT_fighter.layers.app.Entity.ITFighterCharacter;
import IT_fighter.layers.app.Sound.ITFighterSoundManager;
import IT_fighter.layers.ui.ctrl.ITFighterGuiController;

import java.awt.geom.Rectangle2D;

/**
 * Der AppController kontrolliert die Logik der gesamten Anwendung und stellt die Schnittstelle zwischen GameController
 * GuiController und dem SoundManager da
 */
public class ITFighterAppController {
    private static volatile ITFighterAppController instance;
    private ITFighterGameController currentGameController;
    private ITFighterGuiController mGuiController;
    //##################################################################################################################
    /**
     * Konstruktor mit singleton-Pattern
     * der AppController kann somit nur einmal instanziiert werden
     */
    private ITFighterAppController() {
        //singleton
    }
    /**
     * @return gibt eine Instanz auf den AppController zurück
     */
    public static synchronized ITFighterAppController getInstance() {
        if (instance == null)
            instance = new ITFighterAppController();
        return instance;
    }
    //##################################################################################################################
    //getter und setter
    /**
     * setzt den guiController
     * @param guiController eine Instanz des GUI-Controllers
     */
    public void setmGuiController(ITFighterGuiController guiController) {
        this.mGuiController = guiController;
    }
    /**
     * @return gibt das Objekt der aktuellen Spielfigur zurück
     */
    public ITFighterCharacter getActualCharacter() {
        return currentGameController.getmCharacter();
    }
    /**
     * @return gibt das Objekt des aktuellen EnemyMangers zurück
     */
    public ITFighterEnemyManager getCurrentEnemyManager() {return currentGameController.getmEnemyManager();}
    /**
     * @return gibt die aktuelle Logik des Spiels zurück
     */
    public ITFighterGameController getCurrentGameController() {
        return currentGameController;
    }
    /**
     * @return gibt den xLevelOffset zurück
     */
    public int getLevelOffset() {
        return currentGameController.getxLevelOffset();
    }
    //##################################################################################################################
    //Zugriff auf Player
    /**
     * @return gibt die aktuelle Position der Spielfigur in Form ihrer hitbox zurück
     */
    public Rectangle2D.Float getCurrentPlayerPosition() {
        return currentGameController.getmCharacter().getHitbox();
    }
    /**
     * spielt den killSound für die Spielfigur ab, beendet die Spiellogik, setzt den GameOverScreen
     */
    public void killPlayer() {
        ITFighterSoundManager.playKillSound();
        currentGameController.stopGame();
        mGuiController.setGameOverScreen();
    }
    /**
     * verändert die Lautstärke eines bestimmten Sounds dessen Index als int-Wert übergeben wird
     * mit dem 2. Parameter down wird reguliert, ob die Lautstärke erhöht oder verringert wird
     * @param sound 0 oder Soundmanager.GAMEMUSIC für die Hintergrundmusik
     *              1 oder Soundmanager.GAMESOUND für die Sound-Effekte
     * @param down true um die Lautstärke zu verringern und false um sie zu erhöhen
     */
    public void setSoundVolume(int sound, boolean down) {
        if(sound == ITFighterSoundManager.GAMEMUSIC) {
            ITFighterSoundManager.setGameMusicVolume(down);
        } else {
            ITFighterSoundManager.setGameSoundVolume(down);
        }
    }

    //##################################################################################################################
    /**
     * startet einen neuen Thread, der die Komponenten für das TiktokPanel lädt und dieses anzeigt
     */
    public void touchedTiktok() {
        new Thread(() ->{
            ITFighterSoundManager.stopGameMusic();
            ITFighterSoundManager.playTiktokSound();
            mGuiController.setTiktokScreen();
            currentGameController.getmIconManager().setShowingTiktokIcon(true);
        }).start();
    }
    /**
     * stoppt den Tiktok Sound, startet die Spielmusik und setzt die Logik für das TiktokPanel zurück
     */
    public void removeTiktok() {
        ITFighterSoundManager.stopTiktokSound();
        ITFighterSoundManager.playGameMusic();
        currentGameController.getmIconManager().setShowingTiktokIcon(false);
    }
    //##################################################################################################################
    /**
     * stoppt die GameLoop und zeigt das erfolgreiche Bestehen des Levels an
     */
    public void levelFinished() {
        currentGameController.stopGame();
        mGuiController.setGameFinishedScreen();
    }
    /**
     * Beendet die Logic eines Spiels und schließt das GamePanel
     */
    public void closeGame() {
        currentGameController.stopGame();
        mGuiController.closeGame();
    }
    //##################################################################################################################
    //level creation
    /**
     * erstellt das einfache Level mit den Parametern für die Laufgeschwindigkeit der Spielfigur,
     * dem Zeitintervall, in dem neue Viren und neue Binärcodes erstellt werden
     */
    public void setUpLevelOne() {
        setUpLevel(1.0f, 25000, 25000);
    }
    /**
     * erstellt das mittel schwere Level mit den Parametern für die Laufgeschwindigkeit der Spielfigur,
     * dem Zeitintervall, in dem neue Viren und neue Binärcodes erstellt werden
     */
    public void setUpLevelTwo() {
        setUpLevel(1.0f, 10000, 15000);
    }
    /**
     * erstellt das schwere Level mit den Parametern für die Laufgeschwindigkeit der Spielfigur,
     * dem Zeitintervall, in dem neue Viren und neue Binärcodes erstellt werden
     */
    public void setUpLevelThree() {
        setUpLevel(2.0f, 10000, 10000);
    }
    /**
     * creates the logic and the gui for a new Game
     * @param playerSpeed Geschwindigkeit der Spielfigur in X-Richtung
     * @param virusSpeed Zeitraum in dem ein Virus neu erstellt wird
     * @param binaryCodeSpeed Zeitraum in dem ein BinärCode neu erstellt wird
     */
    public void setUpLevel(float playerSpeed, long virusSpeed, long binaryCodeSpeed) {
        currentGameController = new ITFighterGameController();
        currentGameController.setPlayerSpeed(playerSpeed);
        currentGameController.setVirusSpeed(virusSpeed);
        currentGameController.setBinaryCodeSpeed(binaryCodeSpeed);
        mGuiController.createLevelGraphics();
        currentGameController.startGame();
    }

}

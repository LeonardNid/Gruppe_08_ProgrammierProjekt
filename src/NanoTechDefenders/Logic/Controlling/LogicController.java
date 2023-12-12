package NanoTechDefenders.Logic.Controlling;

import NanoTechDefenders.GUI.Controlling.GUIController;
import NanoTechDefenders.GUI.Controlling.UIManager;
import NanoTechDefenders.Help.Soldiers;
import NanoTechDefenders.Logic.Enemy;
import NanoTechDefenders.Logic.Projectiles.Projectile;
import NanoTechDefenders.Logic.Soldier;
import NanoTechDefenders.Sound.SoundController;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Der LogicController verwaltet die Logik des Spiels, einschließlich der Bewegung der Gegner und der Anzahl der Leben.
 */
public class LogicController {

    private static Long startTimeOfLevel;
    private static int enemyKilledAmount = 0;
    private static double xFactor = GUIController.getXFactor();
    private static double yFactor = GUIController.getYFactor();
    private static volatile boolean working;
    private static int playerLifes;
    private static int moneyAmount = 0;
    private static boolean autoplay;
    private static int waitTillNextEnemy = 0;
    private static Thread spawnEnemyThread = new Thread();
    private static CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Enemy> removableEnemies = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Soldier> soldiers = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Projectile> projectiles = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Projectile> removableProjectiles = new CopyOnWriteArrayList<>();

    /**
     * Startet die Spiellogik.
     */
    public static void start() {
        working = true;
    }

    /**
     * Stoppt die Spiellogik.
     */
    public static void stop() {
        working = false;
    }

    /**
     * Gibt den horizontalen Skalierungsfaktor zurück.
     *
     * @return Der horizontale Skalierungsfaktor.
     */
    public static double getXFactor() {
        return xFactor;
    }

    /**
     * Gibt den vertikalen Skalierungsfaktor zurück.
     *
     * @return Der vertikale Skalierungsfaktor.
     */
    public static double getYFactor() {
        return yFactor;
    }

    /**
     * Überprüft, ob das Spiel vorbei ist.
     * Zeigt den Game-Over Screen und stoppt das Spiel, wenn keine Leben mehr vorhanden sind.
     */
    public static void checkGameOver() {
        if (playerLifes <= 0) {
            String[] stats = new String[3];
            stats[0] = String.valueOf(LevelManager.getCurrentWave() - 1);
            stats[1] = String.valueOf(enemyKilledAmount);

            long elapsedTime = System.currentTimeMillis() - startTimeOfLevel;
            // Calculate minutes and seconds
            long minutes = (elapsedTime / (1000 * 60)) % 60;
            long seconds = (elapsedTime / 1000) % 60;
            // Format minutes and seconds with leading zeros
            stats[2] = String.format("%02d,%02d", minutes, seconds);

            GameManager.stopGameLoop();
            stop();
            resetLogic();
            GUIController.switchGameOverScreen(stats);
            System.out.println("GAME OVER");
            SoundController.playGameOver();
        }
    }

    /**
     * Erstellt einen neuen Level-Controller mit der angegebenen Schwierigkeit.
     * Setzt die Startzeit, die Anzahl der Leben, das Startgeld und aktualisiert die Benutzeroberfläche.
     *
     * @param difficulty Die Schwierigkeitsstufe des Levels.
     */
    public static void createLevelController(int difficulty) {
        startTimeOfLevel = System.currentTimeMillis();
        LevelManager.setDifficulty(difficulty);
        playerLifes = LevelManager.getPlayerLifes();
        moneyAmount = LevelManager.getStartMoney();
        UIManager.updateMoneyCount(moneyAmount);
        UIManager.updateLifeLabel(playerLifes);
        updateSoldierBtn();
    }

    /**
     * Setzt die Spiellogik zurück, stoppt das Spiel und entfernt alle Elemente auf dem Spielfeld.
     */
    public static void resetLogic() {
        stop();
        if (spawnEnemyThread != null && spawnEnemyThread.isAlive()) {
            spawnEnemyThread.interrupt();
        }
        LevelManager.reset();
        GUIController.clearSoldier();
        autoplay = false;
        enemies.clear();
        removableEnemies.clear();
        soldiers.clear();
        projectiles.clear();
        removableProjectiles.clear();
    }

    /**
     * Ändert den Autoplay-Modus.
     */
    public static void changeAutoplay() {
        autoplay = !autoplay;
    }

    /**
     * Reagiert auf das Drücken der Soldaten-Schaltfläche.
     * Verringert das verfügbare Geld und aktualisiert die Benutzeroberfläche.
     *
     * @param soldierVariant Die ausgewählte Soldatenvariante.
     */
    public static void soldierBtnPressed(Soldiers soldierVariant) {
        moneyAmount -= soldierVariant.getSoldierCost();
        updateSoldierBtn();
        UIManager.updateMoneyCount(moneyAmount);
    }

    /**
     * Aktualisiert den Status der Soldaten-Schaltflächen in der Benutzeroberfläche
     * basierend auf dem verfügbaren Geld und den Kosten jeder Soldatenvariante.
     */
    private static void updateSoldierBtn() {
        for (Soldiers soldierVariant : Soldiers.values()) {
            UIManager.toggleSoldierBtn(soldierVariant, moneyAmount >= soldierVariant.getSoldierCost());
        }
    }


    /**
     * Zählt den Countdown bis zum nächsten Gegner.
     */
    public static void countDownWaiting() {
        --waitTillNextEnemy;
    }

    /**
     * Aktualisiert die Anzeige des verfügbaren Geldes in der Benutzeroberfläche und
     * aktualisiert den Status der Soldaten-Schaltflächen entsprechend dem aktuellen Geldbetrag.
     */
    private static void updateMoney() {
        UIManager.updateMoneyCount(moneyAmount);
        updateSoldierBtn();
    }


    /**
     * Startet automatisch die nächste Welle, wenn die Autoplay-Funktion aktiviert ist.
     */
    public static void autoStartNextWave() {
        if (autoplay && !spawnEnemyThread.isAlive()) {
            startNextWave();
        }
    }

    /**
     * Startet die nächste Welle, erhöht das verfügbare Geld und aktualisiert die Benutzeroberfläche.
     */
    public static void startNextWave() {
        LevelManager.addWave();
        moneyAmount += LevelManager.getMoneyPerWave();
        updateMoney();
        waitTillNextEnemy = LevelManager.getEnemySpawnFrequency();
        start();
        spawnEnemyThread = new Thread(() -> {
            for (int i = 0; i < LevelManager.getEnemiesPerWave(); ++i) {
                if (working) {
                    createEnemy();

                    while(waitTillNextEnemy > 0) {
                        Thread.yield();
                    }
                    waitTillNextEnemy = LevelManager.getEnemySpawnFrequency();
                } else {
                    --i;
                    // Wenn working auf false gesetzt wurde, breche die Schleife ab
                    while (!working) {
                        Thread.yield();
                    }
                }
            }
            while (true) {
                if (enemies.isEmpty() && projectiles.isEmpty()) {
                    UIManager.currentWaveFinished(String.valueOf(LevelManager.getCurrentWave() + 1));
                    if (!autoplay) {
                        UIManager.enableSpawnBtn();
                        stop();
                    }
                    break;
                }
                Thread.yield();
            }
        });
        spawnEnemyThread.start();
    }

    /**
     * Erstellt einen neuen Gegner und fügt ihn der Liste hinzu.
     */
    public static void createEnemy() {
        Enemy newEnemy = new Enemy();
        enemies.add(newEnemy);
        GUIController.addNewEnemy(newEnemy);
    }

    /**
     * Bewegt die Gegner und aktualisiert die Spiellogik.
     * Ruft die move-Methode für jeden Gegner auf.
     * Entfernt Gegner, die ihr Ziel erreicht haben und aktualisiert die Lebensanzeige.
     */
    public static void moveEnemies() {
        if (working) {
            checkTouchingSoldier();
            for (Enemy e : enemies) {
                e.move();
                if (e.getRemovableStatus()) {
                    removableEnemies.add(e);
                }
            }
        }
    }

    /**
     * Überprüft, ob ein Soldat einen Gegner berührt, basierend auf deren aktuellen Positionen.
     * Falls ein Kontakt festgestellt wird, wird der Soldat entfernt und aus der Soldatenliste entfernt.
     */
    private static void checkTouchingSoldier() {
        if (!enemies.isEmpty()) {
            // Ermittelt die Position des ersten Gegners
            Point firstEnemyLocation = enemies.get(0).getLocation();

            // Überprüft jeden Soldaten auf Berührung mit dem ersten Gegner
            for (Soldier soldier : soldiers) {
                if ((Math.abs(soldier.getlocation().getX() - firstEnemyLocation.getX()) < 45) &&
                        (Math.abs(soldier.getlocation().getY() - firstEnemyLocation.getY()) < 45)) {
                    // Entfernt den Soldaten aus der NanoTechDefenders.GUI und der Soldatenliste
                    GUIController.removeSoldier(soldier);
                    soldiers.remove(soldier);
                }
            }
        }
    }


    /**
     * Entfernt Gegner aus der Liste der aktuellen Gegner. Aktualisiert die Anzahl der verbleibenden Leben,
     * die Anzahl der besiegten Gegner und das verfügbare Geld entsprechend.
     * Spielt auch Soundeffekte ab, basierend auf dem Status des entfernten Gegners.
     */
    private static void removeEnemy() {
        for (Enemy enemy : removableEnemies) {
            enemies.remove(enemy);
            if (enemy.getFinishedStatus()) {
                --playerLifes;
                UIManager.updateLifeLabel(playerLifes);
                SoundController.playAudioLifelost();
            } else {
                moneyAmount = moneyAmount + LevelManager.getMoneyPerKill();
                updateMoney();
                ++enemyKilledAmount;
                SoundController.playEnemyDeath();
            }
        }
        removableEnemies.clear();
    }


    /**
     * Erstellt einen neuen Soldaten an der angegebenen Position und mit der ausgewählten Variante.
     *
     * @param soldierLocation Die Position des Soldaten.
     * @param soldierVariant  Die ausgewählte Soldatenvariante.
     * @return Der erstellte Soldat.
     */
    public static Soldier createSoldier(Point soldierLocation, Soldiers soldierVariant) {
        Soldier soldier = new Soldier(soldierLocation, soldierVariant);
        soldiers.add(soldier);
        return soldier;
    }

    /**
     * Überprüft, ob ein Soldat auf einen Gegner schießen kann, und erstellt bei Bedarf ein Projektil.
     * Entfernt besiegte Gegner und aktualisiert die Spiellogik.
     */
    public static void shootingEnemies() {
        if (working) {
            for (Soldier soldier : soldiers) {
                for (Enemy enemy : enemies) {
                    if (soldier.canShoot(enemy.getLocation())) {
                        soldier.calcAngle(enemy.getLocation());

                        SoldierManager.createProjectile(
                                soldier,
                                enemy.getLocation()
                        );
                        break;
                    }
                }
                soldier.reload();
            }
            removeEnemy();
        }
    }

    /**
     * Erstellt ein neues Projektil und fügt es der Liste hinzu.
     *
     * @param projectile Das zu erstellende Projektil.
     */
    public static void createProjectile(Projectile projectile) {
        projectiles.add(projectile);
        GUIController.createProjectileLabel(projectile);
    }

    /**
     * Bewegt die Projektil und aktualisiert die Spiellogik.
     * Entfernt Projektil, die ihr Ziel erreicht haben.
     */
    public static void moveProjectiles() {
        if (working) {
            for (Projectile projectile : projectiles) {
                projectile.moveProjectile();
                if (projectile.getStatus()) {
                    removableProjectiles.add(projectile);
                }
            }
            removeProjectile();
        }
    }

    private static void removeProjectile() {
        for (Projectile projectile : removableProjectiles) {
            projectiles.remove(projectile);
        }
        removableProjectiles.clear();
    }

    /**
     * Überprüft Kollisionen zwischen Projektilen und Gegnern und aktualisiert die Spiellogik entsprechend.
     */
    public static void checkCollision() {
        if (working) {
            for (Enemy enemy : enemies) {
                for (Projectile projectile : projectiles) {
                    Rectangle p = projectile.getRectangle();
                    Rectangle e = enemy.getRectangle();

                    if (p.intersects(e)) {
                        if (projectile.ready()) {
                            enemy.lostxLife(projectile.getDamage());
                            projectile.countDamage(enemy.getEnemyLifes());
                        }
                    }
                    if (projectile.getStatus()) {
                        removableProjectiles.add(projectile);
                    }
                }
                removeProjectile();
            }
        }
    }
}

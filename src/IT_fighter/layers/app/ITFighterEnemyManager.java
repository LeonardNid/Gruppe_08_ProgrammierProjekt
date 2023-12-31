package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.ITFighterBinaryCode;
import IT_fighter.layers.app.Entity.ITFighterEnemy;
import IT_fighter.layers.app.Entity.ITFighterVirus;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Der EnemyManager verwaltet alle Gegner des Spiels
 */
public class ITFighterEnemyManager {
    private CopyOnWriteArrayList<ITFighterVirus> virusList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<ITFighterBinaryCode> binaryCodesList = new CopyOnWriteArrayList<>();
    private boolean spawn;
    private long virusSpawnTime = 10000;
    private long binaryCodeSpawnTime = 10000;
    private final Random random = new Random();
    private Thread virusSpawnThread;
    private Thread binaryCodeSpawnThread;

    /**
     * erstellt einen Thread, der neue Viren erstellt die vom Himmel fallen und startet diesen
     * @param sleepTime gibt die Zeit, an die maximal vergeht, bis ein neuer Virus erzeugt wird
     */
    private void startVirusSpawnThread(long sleepTime) {
        virusSpawnThread = new Thread(()-> {
            while(spawn) {
                virusList.add(new ITFighterVirus(100, 100, 32,32));
                virusList.add(new ITFighterVirus(3936, 152, 32, 32));
                try {
                    Thread.sleep((random.nextLong(sleepTime)+50)/2);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception gefangen");
                }
                virusList.add(new ITFighterVirus(2594, 132, 32,32));
                virusList.add(new ITFighterVirus(1094, 172, 32, 32));
                try {
                    Thread.sleep((random.nextLong(sleepTime)+50)/2);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception gefangen");
                }
            }
        });
        virusSpawnThread.start();
    }

    /**
     * erstellt einen Thread, der neue Binär-Codes erstellt, die aus einer Kanone geschossen werden
     * und startet diesen
     * @param sleepTime gibt die Zeit, an die maximal vergeht, bis ein neuer Binär-Code erzeugt wird
     */
    private void startBinaryCodeSpawnThread(long sleepTime) {
        binaryCodeSpawnThread = new Thread(()-> {
            while(spawn) {
                binaryCodesList.add(new ITFighterBinaryCode(2790, 734, 32, 64));
                try {
                    Thread.sleep(random.nextLong(sleepTime));
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception gefangen");
                }
            }
        });
        binaryCodeSpawnThread.start();
    }

    /**
     * startet die SpawnThreads für die Gegner des Spiels
     */
    public void startSpawnThread() {
        spawn = true;
        startVirusSpawnThread(virusSpawnTime);
        startBinaryCodeSpawnThread(binaryCodeSpawnTime);

    }

    /**
     * stoppt die Threads, die die Gegner erstellen
     */
    public void stopSpawnThread() {
        spawn = false;
        virusSpawnThread.interrupt();
        binaryCodeSpawnThread.interrupt();

    }

    /**
     * updated alle Gegner, die in der übergebenen Liste verwaltet werden
     * @param enemyList Liste mit Gegnern deren Positionen und Existenz validiert werden soll
     */
    private void updateEnemyList(CopyOnWriteArrayList<? extends ITFighterEnemy> enemyList) {
        boolean stayInlist;
        for(ITFighterEnemy e: enemyList) {
            stayInlist = e.updatePosition();
            if (!stayInlist) {
                enemyList.remove(e);
            }
        }
    }

    /**
     * Stellt die Schnittstelle nach außen dar, über die die Gegner des Spiel geupdated werden können.
     * Die Methode wird von der GameLoop 200-mal pro Sekunde aufgerufen
     */
    public void updateEnemies() {
        updateEnemyList(virusList);
        updateEnemyList(binaryCodesList);
    }

    /**
     *
     * @param spawnTime gibt die Zeit, an die maximal vergeht, bis ein neues Virus erzeugt wird
     */
    public void setVirusSpawnTime(long spawnTime) {
        this.virusSpawnTime = spawnTime;
    }

    /**
     *
     * @param binaryCodeSpawnTime gibt die Zeit, an die maximal vergeht, bis ein neuer Binär-Code erzeugt wird
     */
    public void setBinaryCodeSpawnTime(long binaryCodeSpawnTime) {
        this.binaryCodeSpawnTime = binaryCodeSpawnTime;
    }

    /**
     * @return gibt threadsichere Liste zurück, in der die Viren des Spiels verwaltet werden
     */
    public CopyOnWriteArrayList<ITFighterVirus> getVirusList() {
        return virusList;
    }

    /**
     * @return gibt threadsichere Liste zurück, in der die Viren des Spiels verwaltet werden
     */
    public CopyOnWriteArrayList<ITFighterBinaryCode> getBinaryCodesList(){
        return binaryCodesList;
    }
}

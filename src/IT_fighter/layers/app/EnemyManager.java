package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.BinaryCode;
import IT_fighter.layers.app.Entity.Enemy;
import IT_fighter.layers.app.Entity.Virus;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyManager {
    private CopyOnWriteArrayList<Virus> virusList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<BinaryCode> binaryCodesList = new CopyOnWriteArrayList<>();
    private boolean spawn;
    private long virusSpawnTime = 10000;
    private long binaryCodeSpawnTime = 10000;
    private Random random = new Random();

    Thread virusSpawnThread;
    Thread binaryCodeSpawnThread;
    public void stopSpawnThread() {
        spawn = false;
        virusSpawnThread.interrupt();
        binaryCodeSpawnThread.interrupt();

    }
    private void startVirusSpawnThread(long sleepTime) {
        virusSpawnThread = new Thread(()-> {
            while(spawn) {
                virusList.add(new Virus(100, 100, 32,32));
                virusList.add(new Virus(3936, 152, 32, 32));
                try {
                    Thread.sleep(random.nextLong(sleepTime)/2);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception gefangen");
                }
                virusList.add(new Virus(2594, 132, 32,32));
                virusList.add(new Virus(1094, 172, 32, 32));
                System.out.println("neuer Gegner erstellt");
                try {
                    Thread.sleep(random.nextLong(sleepTime)/2);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception gefangen");
                }
            }
        });
        virusSpawnThread.start();
    }
    private void startBinaryCodeSpawnThread(long sleepTime) {
        binaryCodeSpawnThread = new Thread(()-> {
            while(spawn) {
                binaryCodesList.add(new BinaryCode(2790, 734, 32, 64));
                try {
                    Thread.sleep(random.nextLong(sleepTime));
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception gefangen");
                }
            }
        });
        binaryCodeSpawnThread.start();
    }

    public void startSpawnThread() {
        spawn = true;
        startVirusSpawnThread(virusSpawnTime);
        startBinaryCodeSpawnThread(binaryCodeSpawnTime);

    }

    public void updateEnemyList(CopyOnWriteArrayList<? extends Enemy> enemyList) {
        boolean stayInlist;
        for(Enemy e: enemyList) {
            stayInlist = e.updatePosition();
            if (!stayInlist) {
                enemyList.remove(e);
            }
        }
    }

    public void updateEnemies() {
        updateEnemyList(virusList);
        updateEnemyList(binaryCodesList);
    }

    public void setVirusSpawnTime(long spawnTime) {
        this.virusSpawnTime = spawnTime;
    }
    public void setBinaryCodeSpawnTime(long binaryCodeSpawnTime) {
        this.binaryCodeSpawnTime = binaryCodeSpawnTime;
    }

    public CopyOnWriteArrayList<Virus> getVirusList() {
        return virusList;
    }
    public CopyOnWriteArrayList<BinaryCode> getBinaryCodesList(){
        return binaryCodesList;
    }
}

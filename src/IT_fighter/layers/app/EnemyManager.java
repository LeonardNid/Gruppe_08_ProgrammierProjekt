package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.BinaryCode;
import IT_fighter.layers.app.Entity.Enemy;
import IT_fighter.layers.app.Entity.Virus;

import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyManager {
    private CopyOnWriteArrayList<Virus> virusList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<BinaryCode> binaryCodesList = new CopyOnWriteArrayList<>();
    private boolean spawn;
    private long spawnTime = 10000;
    Thread spawnThread;
    public void stopSpawnThread() {
        spawn = false;
        spawnThread.interrupt();

    }

    public void startSpawnThread() {
        spawn = true;
        spawnThread = new Thread(()-> {
            while(spawn) {
                virusList.add(new Virus(100, 100, 32,32));
                binaryCodesList.add(new BinaryCode(2816, 734, 32, 64));
                System.out.println("neuer Gegner erstellt");
                try {
                    Thread.sleep(spawnTime);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception gefangen");
                }
            }
        });
        spawnThread.start();
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


    public void updateVirusList() {
        boolean stayInlist;
        for(Virus v : virusList) {
            stayInlist = v.updatePosition();
            if (!stayInlist) {
                virusList.remove(v);
            }
        }
    }

    public void setSpawnTime(long spawnTime) {
        this.spawnTime = spawnTime;
    }

    public CopyOnWriteArrayList<Virus> getVirusList() {
        return virusList;
    }
    public CopyOnWriteArrayList<BinaryCode> getBinaryCodesList(){
        return binaryCodesList;
    }
}

package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.Virus;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyManager {
    private CopyOnWriteArrayList<Virus> virusArrayList = new CopyOnWriteArrayList<>();
    private boolean spawn;
    private long spawnTime;
    Thread spawnThread;
    public void stopSpawnThread() {
        spawn = false;
        spawnThread.interrupt();

    }

    public void startSpawnThread() {
        spawn = true;
        spawnThread = new Thread(()-> {
            while(spawn) {
                virusArrayList.add(new Virus(100, 100, 32,32));
                System.out.println("neuer Gegner erstellt");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception gefangen");
                }
            }
        });
        spawnThread.start();
    }


    public void updateVirusList() {
        boolean stayInlist;
        for(Virus v : virusArrayList) {
            stayInlist = v.updatePosition();
            if (!stayInlist) {
                virusArrayList.remove(v);
            }
        }
    }

    public void setSpawnTime(long spawnTime) {
        this.spawnTime = spawnTime;
    }

    public CopyOnWriteArrayList<Virus> getVirusArrayList() {
        return virusArrayList;
    }
}

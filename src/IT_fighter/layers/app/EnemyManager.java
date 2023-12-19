package IT_fighter.layers.app;

import IT_fighter.layers.app.Entity.Virus;

import java.util.ArrayList;

public class EnemyManager {
    private ArrayList<Virus> virusArrayList = new ArrayList<>();

    public void spawnEnemies() {

    }

    public void updateVirus() {
        for(Virus v : virusArrayList) {
            v.updatePosition();
        }
    }


    public void draw() {
        for(Virus v: virusArrayList) {

        }
    }
}

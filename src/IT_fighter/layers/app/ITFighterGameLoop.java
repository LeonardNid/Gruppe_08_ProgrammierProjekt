package IT_fighter.layers.app;

/**
 * Die GameLoop ist ein Thread der sicherstellt, dass alle Komponenten des Spiels mit Updates versorgt werden.
 */
public class ITFighterGameLoop extends Thread{
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private boolean run = true;
    private ITFighterGameController game;

    /**
     * @param gameController ist der GameController dessen Methoden aufgerufen werden, um die Logik und die Grafik
     *                       des Spiels mit Updates zu versorgen.
     */
    public ITFighterGameLoop(ITFighterGameController gameController) {
        game = gameController;
    }

    /**
     * Run-Methode des Threads, indem die GameLoop als while-Schleife läuft.
     */
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (run) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                game.update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                game.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }
    }

    /**
     * Beendet den Thread der GameLoop
     */
    public void stopGameLoop() {
        this.run = false;
        this.interrupt();
    }
}

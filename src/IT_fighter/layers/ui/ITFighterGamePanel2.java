package IT_fighter.layers.ui;

import IT_fighter.layers.ui.ctrl.KeyHandler;
import IT_fighter.layers.ui.menuPanels.BasicPanel;

import java.awt.*;

public class ITFighterGamePanel2 extends BasicPanel implements Runnable {
    final static int FPS = 60;
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    int playerY = 100;
    int playerX = 100;
    int playerSpeed = 4;

    public ITFighterGamePanel2() {
        this.addKeyListener(keyHandler);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        startGameThread();
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        System.out.println("Hier");

        while(gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();

                this.repaint();
                //System.out.println(delta);
                delta--;
            }


        }

    }
    public void update() {
        if(keyHandler.upPressed) {
            playerY -= playerSpeed;
        } else if (keyHandler.downPressed) {
            playerY += playerSpeed;
        } else if (keyHandler.leftPressed) {
            playerX -= playerSpeed;
        } else if (keyHandler.rightPressed) {
            playerX += playerSpeed;
        }

    }

    public void paintComponent(Graphics g) {
        Image image = getImage("../../pictures/character/character_standing_left.jpg");
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        System.out.println("works here");
        g2.drawImage(image, playerX, playerY, null);
        //g2.setColor(Color.WHITE);
        //g2.fillRect(playerX, playerY, 32, 32);
        g2.dispose();
    }
}

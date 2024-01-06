package Escape_Directory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Die GUI uuuuuuuuuuuuuuuuund das Herz von dem Spiel. Hier wird auch jeder Frame neu gezeichnet.
 */
@SuppressWarnings("serial")
public class frame extends JFrame implements KeyListener {
	JFrame frame;

	JLabel playerLabel;
	JLabel enemyLabel;
	JLabel world[][] = new JLabel[(int) FilePaths.windowSize.getX()
			/ FilePaths.pixelSize][(int) FilePaths.windowSize.getY() / FilePaths.pixelSize - 1];
	JLabel background;
	ImageIcon image;
	ImageIcon image2;
	ImageIcon image3;
	ImageIcon image4;
	ImageIcon backgroundImage;
	Level lev = new Level(new Position(2, 100), 0);

	Player p = new Player(lev.getPlayerStartPos(), Position.ZERO(), 400, new Position(800, 400),
			System.currentTimeMillis());
	Camera c = new Camera(0.005, 500,
			new Position(p.getPos().getX() - (0.4 * FilePaths.windowSize.getX()), p.getPos().getY()));
	Enemy e = new Enemy(Position.ZERO(), Position.ZERO(), FilePaths.enemyVel);
	ArrayList<Enemy> es = new ArrayList<Enemy>();
	ArrayList<JLabel> eslabel = new ArrayList<JLabel>();

	Logic l = new Logic(p, lev, e);

	long lastCycle;

	public frame(String activationTyp) {
		if (activationTyp.equals("play")) {
			lev.setLev(1);
			lev.drawWorld();
		}
		frame = new JFrame("test");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			configFrame();
			drawWorld();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		frame.setVisible(true);
		/**
		 * Erstellt einen Timer, welcher run() 60/s oder alle 16 ms aufruft, wenn
		 * möglich.
		 */
		Timer t = new Timer();
		lastCycle = System.currentTimeMillis();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (l.getWin() != gameStates.PAUSE) {

					if (keyEscape) {
						l.setWin(gameStates.PAUSE);
					}
					double deltaTime = (double) (System.currentTimeMillis() - lastCycle) / 1000;
					l.update("", deltaTime, lev.getWorld(), c, keyE);
					lastCycle = System.currentTimeMillis();
					if (keyDown) {
						l.updatePlayerVelocity("DOWN", deltaTime);
					}
					if (keyUp) {
						l.updatePlayerVelocity("UP", deltaTime);
					}
					if (keyRight) {
						l.updatePlayerVelocity("RIGHT", deltaTime);
					}
					if (keyLeft) {
						l.updatePlayerVelocity("LEFT", deltaTime);
					}
					if (!(keyRight || keyLeft)) {
						l.updatePlayerVelocity("", deltaTime);
					}
					switch (l.getWin()) {
					case LOSE:
						p.setPos(lev.getPlayerStartPos());
						p.setVel(Position.ZERO());
						undrawWorld();
						restart();
						break;
					case WIN:
						undrawWorld();
						lev.setLev(lev.getLev() + 1);
						restart();
						break;
					default:
						break;
					}
				} else {
					lastCycle = System.currentTimeMillis();
					if (!keyEscape) {
						l.setWin(gameStates.RUNNING);
					}
				}
				updatePositions();
			}
		}, 0, 16);
	}
 /* 
  * restarted die map & die positions der entities
  * */
	private void restart() {
		lev.drawWorld();
		backgroundImage = new ImageIcon(lev.getBackgroundPath());
		drawWorld();
		es = lev.getEnemies();
		p.setPos(lev.getPlayerStartPos());
		p.setVel(Position.ZERO());
		e.setPos(Position.ZERO());
		l.setWin(gameStates.RUNNING);
		c.restart();
	}

	/*
	 * * Updated alle Positions abhängig von der Kameraposition
	 *  */
	private void updatePositions() {
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				if (world[i][j] != null) {
					world[i][j].setBounds((int) (i * FilePaths.pixelSize - c.getPos().getX()), j * FilePaths.pixelSize,
							FilePaths.pixelSize, FilePaths.pixelSize);
					world[i][j].setBounds((int) (i * FilePaths.pixelSize - c.getPos().getX()), j * FilePaths.pixelSize,
							FilePaths.pixelSize, FilePaths.pixelSize);
				}
			}
		}
		playerLabel.setBounds((int) (p.getPos().getX() - c.getPos().getX()), (int) (p.getPos().getY()),
				FilePaths.pixelSize, FilePaths.pixelSize);
		enemyLabel.setBounds((int) (e.getPos().getX() - c.getPos().getX()), (int) (e.getPos().getY()),
				FilePaths.pixelSize, FilePaths.pixelSize);
		repaint();
	}

	private void drawWorld() {
		blockStates[][] w = lev.getWorld();
		world = new JLabel[w.length][w[0].length];

		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i].length; j++) {
				if (w[i][j] == blockStates.BLOCK) {
					world[i][j] = new JLabel(image2);
					world[i][j].setBounds((int) (i * FilePaths.pixelSize - c.getPos().getX()), j * FilePaths.pixelSize,
							FilePaths.pixelSize, FilePaths.pixelSize);
					frame.add(world[i][j]);
					world[i][j].setVisible(true);
				} else if (w[i][j] == blockStates.DOOR) {
					world[i][j] = new JLabel(image4);
					world[i][j].setBounds((int) (i * FilePaths.pixelSize - c.getPos().getX()), j * FilePaths.pixelSize,
							FilePaths.pixelSize, FilePaths.pixelSize);
					frame.add(world[i][j]);
					world[i][j].setVisible(true);

				}
			}
		}

		backgroundImage = new ImageIcon(getClass().getResource(lev.getBackgroundPath()));
		background = new JLabel(backgroundImage);
		background.setBounds(0, -300, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
		frame.add(background);
		background.setVisible(true);
		frame.update(frame.getGraphics());

	}

	private void undrawWorld() {
		frame.remove(background);
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				if (world[i][j] != null) {
					frame.remove(world[i][j]);
					world[i][j] = null;
				}
			}
		}
	}

	boolean keyUp, keyDown, keyLeft, keyRight, keyEscape, keyE;

	/**
	 * Überschreibt KeyListener. Sobald WASD oder Pfeiltasten gedrückt werden,
	 * werden deren gedrücktBooleans auf wahr gesetzt.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			keyDown = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			keyUp = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			keyLeft = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			keyRight = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			keyEscape = !keyEscape;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			keyE = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			l.playSound(FilePaths.quack);
		}
	}

	/**
	 * Überschreibt KeyListener. Sobald WASD oder Pfeiltasten losgelassen werden,
	 * werden deren gedrücktBooleans auf false gesetzt.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			keyDown = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			keyUp = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			keyLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			keyRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			keyE = false;
		}
	}

	private void configFrame() throws NullPointerException {
		image = new ImageIcon(getClass().getResource(FilePaths.playerImage));
		image2 = new ImageIcon(getClass().getResource(FilePaths.blockImage));
		image3 = new ImageIcon(getClass().getResource(FilePaths.enemyImage));
		image4 = new ImageIcon(getClass().getResource(FilePaths.doorImage));
		backgroundImage = new ImageIcon(lev.getBackgroundPath());
		playerLabel = new JLabel(image);
		enemyLabel = new JLabel(image3);
		frame.addKeyListener(this);
		frame.add(enemyLabel);
		frame.add(playerLabel);
		frame.setLayout(null);
		frame.setSize((int) FilePaths.windowSize.getX(), (int) FilePaths.windowSize.getY());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}

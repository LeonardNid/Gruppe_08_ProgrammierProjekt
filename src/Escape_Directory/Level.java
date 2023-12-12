package Escape_Directory;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Eine Klasse, welche alle Informationen des Levels aufrufen & weitergeben soll
 */
public class Level {
	private Position playerStartPos;
	private blockStates world[][];
	private int lev = 0;
	private String backgroundPath;
	private ArrayList<Enemy> enemies = new ArrayList<>();

	
	public Level(Position playerStartPos, int level) {
		this.playerStartPos = playerStartPos;
		drawWorld();
	}

	public String getBackgroundPath() {
		return backgroundPath;
	}

	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}

	public Position getPlayerStartPos() {
		return playerStartPos;
	}

	public void setPlayerStartPos(Position playerStartPos) {
		this.playerStartPos = playerStartPos;
	}

	public int getLev() {
		return lev;
	}

	public void setLev(int lev) {
		this.lev = lev;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	public void setWorld(blockStates[][] world) {
		this.world = world;
	}

	public blockStates[][] getWorld() {
		return world;
	}

	/**
	 * Sucht ein png-File auf via gespeicherter String, darf nicht ein nicht
	 * existierendes Level aufrufen
	 */
	public void drawWorld() {
		String s = FilePaths.levelFilePaths[lev];
		backgroundPath = FilePaths.backgroundImageFilePaths[lev];
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(getClass().getResource(s));
			world = new blockStates[bi.getWidth()][bi.getHeight()];
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				world[i][j] = blockStates.NOTHING;
				switch (bi.getRGB(i, j)) {
				case -12171706:
					world[i][j] = blockStates.DOOR;
					break;
				case -16777216:
					world[i][j] = blockStates.BLOCK;
					break;
				case -14503604:
					world[i][j] = blockStates.ENEMY;
					enemies.add(new Enemy(new Position(FilePaths.pixelSize * i, FilePaths.pixelSize * j),
							playerStartPos, FilePaths.enemyVel));
					break;
				}
			}
		}

	}
}

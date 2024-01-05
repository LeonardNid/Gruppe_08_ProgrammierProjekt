package Escape_Directory;

/** Updated die Tiles und ihre Interaktionen zwischeneinander */
public class Logic {
	private Player player;
	private Level level;
	private Enemy enemy;
	private gameStates win = gameStates.RUNNING;

	public Player getPlayer() {
		return player;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public gameStates getWin() {
		return win;
	}

	public void setWin(gameStates win) {
		this.win = win;
	}

	public Logic(Player p, Level l, Enemy e) {
		player = p;
		level = l;
		enemy = e;
	}

	/** Updated Spieler und Gegner */
	public void update(String input, double deltaTime, blockStates[][] world, Camera c, boolean keyE) {
		updatePlayer(input, deltaTime, world, keyE);
		updateEnemy(deltaTime);
		c.updateCamera(deltaTime,
				new Position(player.getPos().getX() - (0.4 * FilePaths.windowSize.getX()), player.getPos().getY()));
	}

	/**
	 * Updated den Gegner. Dabei bewegt sich der Gegner zum Spieler & Kollision mit
	 * dem Spieler wird beachtet
	 */
	private void updateEnemy(double deltaTime) {
		Position p = player.getPos().plus(enemy.getPos().times(-1));
		double tmp = enemy.getMaxvel() / p.length();
		enemy.setVel(new Position(p.getX() * tmp, p.getY() * tmp));
		enemy.calcNewPosition(deltaTime);
		if (enemiesCollideWithPlayer()) {
			win = gameStates.LOSE;
		}
	}

//	private void printMatrix(int[][] worldAroundPlayer) {
//		System.out.println();
//		for (int i = 0; i < 3; i++) {
//			System.out.print("(");
//			for (int j = 0; j < 3; j++) {
//
//				System.out.print(worldAroundPlayer[i][j]);
//				if (j != 2) {
//					System.out.print(" ");
//				}
//			}
//			System.out.println(")");
//		}
//	}

	/**
	 * Updated den Spieler. Dabei bewegt sich der Spieler & Kollision mit der Welt
	 * wird beachtet
	 */
	private void updatePlayer(String direction, double deltaTime, blockStates[][] world, boolean keyE) {
		player.getVel().setY(player.getVel().getY() + FilePaths.gravity * deltaTime);
		player.calcNewVel(direction, deltaTime, System.currentTimeMillis());
		player.isInAir = true;
		boolean[] collisionPoints = playerCollidesWithWorld(world);

		if (collisionPoints[0] == true || collisionPoints[3] == true || collisionPoints[6] == true
				|| collisionPoints[9] == true) {
			if ((collisionPoints[6] && collisionPoints[7]) || (collisionPoints[8] && collisionPoints[9])) { // unten
				player.isInAir = false;
				if (player.getVel().getY() > 0) {
					player.getVel().setY(0);
				}
				player.getPos().setY(this.collisionPoints[2].getY() - 32);
			}
			if ((collisionPoints[0] && collisionPoints[1]) || (collisionPoints[2] && collisionPoints[3])) { // oben
				if (player.getVel().getY() < 0) {
					player.getVel().setY(0);
				}
			}
			if ((collisionPoints[3] && collisionPoints[4]) || (collisionPoints[5] && collisionPoints[6])) { // rechts
				if (player.getVel().getX() > 0) {
					player.getVel().setX(0);
				}
				player.getPos().setX(this.collisionPoints[1].getX() - 32.5);

			}
			if ((collisionPoints[0] && collisionPoints[11]) || (collisionPoints[10] && collisionPoints[9])) { // links
				if (player.getVel().getX() < 0) {
					player.getVel().setX(0);
				}
				player.getPos().setX(this.collisionPoints[3].getX() + 32.5);
			}
			Position mittelPunkt = new Position(player.getPos().getX() + FilePaths.pixelSize / 2,
					player.getPos().getY() - FilePaths.pixelSize / 2);
			if (keyE) {
				if (world[(int) (mittelPunkt.getX() / FilePaths.pixelSize)][(int) (mittelPunkt.getY()
						/ FilePaths.pixelSize)] == blockStates.DOOR) {
					win = gameStates.WIN;

				}
			}
		}

		player.calcNewPosition(deltaTime);
		playerOutOfBounds();
	}

	/** Wird in Frame aufgerufen. Schlechte Lösung. Lässt sich besser umgehen */
	public void updatePlayerVelocity(String s, double dT) {
		player.calcNewVel(s, dT, System.currentTimeMillis());
		if (s.equals("")) {
			player.velXToward0(dT);
		}
	}

	/**
	 * Guckt, ob der Spieler in diesem Frame innerhalb eines Blocks ist. Falls ja:
	 * Gib die Position des Blocks zurück Falls nein: Gib null zurück
	 */

	private double distanceFromCorner = 10;
	private int sqrsize = FilePaths.pixelSize;
	// Lokale (Nullpunkt ist NW vom player)hitbox ändert sich bei Veränderung von
	// playerSize & distanceFromCorner nicht
	private Position[] hitBoxPlayer = { new Position(0, 0), new Position(distanceFromCorner, 0),
			new Position(sqrsize - distanceFromCorner, 0), new Position(sqrsize, 0),
			new Position(sqrsize, distanceFromCorner), new Position(sqrsize, sqrsize - distanceFromCorner),
			new Position(sqrsize, sqrsize), new Position(sqrsize - distanceFromCorner, sqrsize),
			new Position(distanceFromCorner, sqrsize), new Position(0, sqrsize),
			new Position(0, sqrsize - distanceFromCorner), new Position(0, sqrsize), };
	// Im Falle wo der Spieler mit 2 Blöcken gleichzeitig kollidiert, reicht eine
	// Kollisionsposition nicht aus! Deshalb 4
	private Position[] collisionPoints = { null, null, null, null };

	public boolean[] playerCollidesWithWorld(blockStates[][] world) {
		Position[][] worldAroundPlayer = new Position[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				try {
					if (world[(int) ((j - 1) + (player.getPos().getX() + 16) / 32)][(int) ((i - 1)
							+ (player.getPos().getY() + 16) / 32)] == blockStates.BLOCK) {
						worldAroundPlayer[i][j] = new Position((int) ((j - 1) + (player.getPos().getX() + 16) / 32),
								(int) ((i - 1) + (player.getPos().getY() + 16) / 32));
					} else {
						worldAroundPlayer[i][j] = null;
					}
				} catch (Exception e) {
					worldAroundPlayer[i][j] = null;
				}
			}
		}
		// FÜR OPTIMIERUNGSZWECKE SPAETER NUTZEN WENN NOTWENDIG,

		boolean output[] = new boolean[12];
		for (int a = 0; a < output.length; a++) {
			output[a] = false;
			for (int i = 0; i < worldAroundPlayer.length; i++) {
				for (int j = 0; j < worldAroundPlayer[i].length; j++) {
					if (worldAroundPlayer[i][j] != null) {
						double blockX = worldAroundPlayer[i][j].getX() * sqrsize;
						double blockY = worldAroundPlayer[i][j].getY() * sqrsize;
						if ((hitBoxPlayer[a].getX() + player.getPos().getX() >= blockX
								&& hitBoxPlayer[a].getX() + player.getPos().getX() <= blockX + sqrsize)
								&& (hitBoxPlayer[a].getY() + player.getPos().getY() >= blockY
										&& hitBoxPlayer[a].getY() + player.getPos().getY() <= blockY + sqrsize)
								&& !(blockX == 0 && blockY == 0)) {
							output[a] = true;
							switch (a) {
							case 11: // links = 4.stelle
							case 10:
								collisionPoints[3] = new Position(blockX, blockY);
								break;
							case 8: // unten = 3.stelle
							case 7:

								collisionPoints[2] = new Position(blockX, blockY);
								break;
							case 5: // rechts = 2.stelle
							case 4:

								collisionPoints[1] = new Position(blockX, blockY);
								break;
							case 1: // oben = 1.stelle
							case 2:

								collisionPoints[0] = new Position(blockX, blockY);
								break;
							}
						}
					}
				}
			}
		}

		return output;
	}

	/*
	 * returns true falls Spieler und Gegner kollidieren returns false falls Spieler
	 * und Gegner nicht kollidieren
	 **/
	public boolean enemiesCollideWithPlayer() {
		Position p = enemy.getPos();
		for (int a = 0; a < hitBoxPlayer.length; a++) {
			if ((hitBoxPlayer[a].getX() + player.getPos().getX() >= p.getX()
					&& hitBoxPlayer[a].getX() + player.getPos().getX() <= p.getX() + sqrsize)
					&& (hitBoxPlayer[a].getY() + player.getPos().getY() >= p.getY()
							&& hitBoxPlayer[a].getY() + player.getPos().getY() <= p.getY() + sqrsize)
					&& !(p.getX() == 0 && p.getY() == 0)) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Guckt, ob der Spieler in diesem Frame innerhalb des MapBounds ist Falls
	 * rechts: win = 1 Falls unten || links: win = -1 sonst: win = 0
	 */
	public void playerOutOfBounds() {
		int playerSize = FilePaths.pixelSize;
		Position windowSize = FilePaths.windowSize;
		if (player.getPos().getX() + playerSize + 98 > windowSize.getX()) {
		}
		if (player.getPos().getX() < 0) {
			win = gameStates.LOSE;
		}
		if (player.getPos().getY() < 0) {
			// nichts
		}
		if (player.getPos().getY() + playerSize > windowSize.getY() - 7) { // 7 ist die Größe des Fensters außerhalb des
																			// // // Spiels
			win = gameStates.LOSE;
		}
	}
}

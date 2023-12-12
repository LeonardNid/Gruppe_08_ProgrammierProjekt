package Escape_Directory;

public class Enemy {
    private Position pos = new Position(0, 0);
    private Position vel = new Position(0, 0);
    private double maxvel = 0;

    public Enemy(Position pos, Position vel, double maxvel) {
        this.pos = pos;
        this.vel = vel;
        this.maxvel = maxvel;
    }

    public void calcNewPosition(double deltaTime) {
        pos = pos.plus(vel.times(deltaTime));
    }

    public Position getVel() {
        return vel;
    }




    public void setVel(Position vel) {
        this.vel = vel;
    }

    public double getMaxvel() {
        return maxvel;
    }

    public void setMaxvel(int maxvel) {
        this.maxvel = maxvel;
    }
    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }
}

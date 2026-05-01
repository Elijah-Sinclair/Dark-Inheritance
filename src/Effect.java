import java.awt.*;

public abstract class Effect {
    protected double x, y;
    protected int lifetime;
    protected boolean alive = true;

    public Effect(double x, double y, int lifetime) {
        this.x = x;
        this.y = y;
        this.lifetime = lifetime;
    }

    public void update() {
        lifetime--;
        if (lifetime <= 0) alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public abstract void draw(Graphics g, Camera cam);
}

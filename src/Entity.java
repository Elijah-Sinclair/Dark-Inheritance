import java.awt.*;

public abstract class Entity {
    protected double worldX, worldY;
    protected boolean alive = true;

    public void update(GameState gameState) {

    }

    public void draw(Graphics g, Camera camera) {

    }

    public boolean isAlive() {
        return alive;
    }
}

import java.awt.*;

public abstract class Entity {
    protected double worldX, worldY;
    protected boolean alive = true;

    protected int width = 50;
    protected int height = 50;

    public void update(GameState gameState) {

    }

    public void draw(Graphics g, Camera camera) {

    }

    //Hitbox magic
    public Rectangle getBounds() {
        return new Rectangle((int)worldX, (int)worldY, width, height);
    }

    public boolean isAlive() {
        return alive;
    }
}

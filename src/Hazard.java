import java.awt.*;

public class Hazard extends Entity{

    private int lifetime = 1800;
    private int damage = 2;

    public Hazard(double x, double y) {
        this.worldX = x;
        this.worldY = y;

        this.width = 60;
        this.height = 60;
    }

    @Override
    public void update(GameState state) {
        lifetime--;
        if (lifetime <= 0) alive = false;
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int x = cam.getScreenX(worldX);
        int y = cam.getScreenY(worldY);

        // Glow
        g.setColor(new Color(0, 255, 255, 80));
        g.fillOval(x - 5, y - 5, 30, 30);

        // Core
        g.setColor(Color.CYAN);
        g.fillOval(x, y, 20, 20);

        // Inner pulse
        g.setColor(Color.WHITE);
        g.fillOval(x + 6, y + 6, 8, 8);
    }

    public int getDamage() {
        return damage;
    }
}

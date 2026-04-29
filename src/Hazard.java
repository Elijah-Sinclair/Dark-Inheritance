import java.awt.*;

public class Hazard extends Entity{
    private int lifetime = 180;

    @Override
    public void update(GameState state) {
        lifetime--;
        if (lifetime <= 0) alive = false;
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int x = cam.getScreenX(worldX);
        int y = cam.getScreenY(worldY);

        //Please fix this future me
        g.setColor(Color.CYAN);
        g.fillRect(x, y, 20, 20);
    }

    public int getDamage() {
        return 1;
    }
}

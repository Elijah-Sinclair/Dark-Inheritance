import java.awt.*;
import java.util.Random;

public class ExplosionEffect extends Effect {

    private Random rand = new Random();

    public ExplosionEffect(double x, double y) {
        super(x, y, 20);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int sx = cam.getScreenX(x);
        int sy = cam.getScreenY(y);

        int radius = 40 - lifetime * 2;

        g.setColor(new Color(255, 100, 0, 150));
        g.fillOval(sx - radius / 2, sy - radius / 2, radius, radius);
    }
}
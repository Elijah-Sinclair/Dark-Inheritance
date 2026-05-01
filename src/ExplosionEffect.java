import java.awt.*;

public class ExplosionEffect extends Effect {

    public ExplosionEffect(double x, double y) {
        super(x, y, 30);
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int screenX = cam.getScreenX(x);
        int screenY = cam.getScreenY(y);

        int size = 60 - lifetime * 2;

        g.setColor(new Color(255, 100, 0, 180));
        g.fillOval(screenX - size/2, screenY - size/2, size, size);
    }
}
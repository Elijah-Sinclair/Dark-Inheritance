import java.awt.*;

public class HitEffect extends Effect {

    public HitEffect(double x, double y) {
        super(x, y, 15);
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int screenX = cam.getScreenX(x);
        int screenY = cam.getScreenY(y);

        int size = 20 - lifetime; // shrink over time

        g.setColor(new Color(255, 200, 50, 200));
        g.fillOval(screenX - size/2, screenY - size/2, size, size);
    }
}
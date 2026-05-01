import java.awt.*;

public class TrailEffect extends Effect {

    private int size;

    public TrailEffect(double x, double y, int size) {
        super(x, y, 15);
        this.size = size;
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int sx = cam.getScreenX(x);
        int sy = cam.getScreenY(y);

        int alpha = Math.max(0, lifetime * 10);

        g.setColor(new Color(255, 0, 0, alpha));
        g.fillOval(sx, sy, size, size);
    }
}
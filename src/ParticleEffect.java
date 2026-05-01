import java.awt.*;

public class ParticleEffect extends Effect {

    private double dx, dy;
    private int size;
    private Color color;

    public ParticleEffect(double x, double y, double dx, double dy, int size, int lifetime, Color color) {
        super(x, y, lifetime);
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.color = color;
    }

    @Override
    public void update() {
        super.update();
        x += dx;
        y += dy;

        dx *= 0.95;
        dy *= 0.95;
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int sx = cam.getScreenX(x);
        int sy = cam.getScreenY(y);

        g.setColor(color);
        g.fillOval(sx, sy, size, size);
    }
}
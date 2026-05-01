import java.awt.*;

public class TrailEffect extends Effect {

    public TrailEffect(double x, double y) {
        super(x, y, 10);
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int screenX = cam.getScreenX(x);
        int screenY = cam.getScreenY(y);

        g.setColor(new Color(255, 50, 50, 120));
        g.fillOval(screenX, screenY, 8, 8);
    }
}
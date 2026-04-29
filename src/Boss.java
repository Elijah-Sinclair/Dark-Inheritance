import java.awt.*;

public class Boss extends Enemy{

    private int phase = 1;
    private int attackTimer = 0;

    public Boss(double x, double y) {
        worldX = x;
        worldY = y;

        //Tweak with the sprite and gameplay till it feels right
        health = 200;
        damage = 3;
        speed = 2; //May end up being stationary boss
        width = 80;
        height = 80;

        scoreValue = 2500;
    }


    @Override
    public void update(GameState state) {

        Player p = state.getPlayer();

        double dx = p.worldX - worldX;
        double dy = p.worldY - worldY;

        double len = Math.sqrt(dx * dx + dy * dy);

        if (len != 0) {
            dx /= len;
            dy /= len;
        }

        // Movement (slow tracking)
        worldX += dx * speed;
        worldY += dy * speed;

        // Phase change
        if (health < 100) phase = 2;

        attackTimer--;

        if (attackTimer <= 0) {
            //Insert attack method
            attackTimer = (phase == 1) ? 60 : 30;
        }
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int x = cam.getScreenX(worldX);
        int y = cam.getScreenY(worldY);

        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }

}

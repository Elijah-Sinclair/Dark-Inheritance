import java.awt.*;
import java.awt.image.BufferedImage;

public class BasicGrunt extends Enemy{
    public BasicGrunt(double x, double y) {
        worldX = x;
        worldY = y;

        health = 5;
        damage = 1;
        speed = 2;

        scoreValue = 300;

        sprite = ImageManager.loadImage("Basic_Grunt.png");
    }

    public void update(GameState state) {
        Player p = state.getPlayer();

        double dx = p.worldX - worldX;
        double dy = p.worldY - worldY;
        double len = Math.sqrt(dx*dx + dy*dy);

        dx /= len;
        dy /= len;

        worldX += dx * speed;
        worldY += dy * speed;
    }

    public void draw(Graphics g, Camera cam) {

//        g.setColor(Color.RED);
//        g.fillRect(x, y, 30, 30);

        g.drawImage(sprite, cam.getScreenX(worldX), cam.getScreenY(worldY), width, height, null);
    }
}

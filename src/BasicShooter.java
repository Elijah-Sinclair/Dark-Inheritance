import java.awt.*;

public class BasicShooter extends Enemy{

    private int fireCooldown = 0;

    public BasicShooter(double x, double y) {
        worldX = x;
        worldY = y;

        health = 3;
        damage = 1;
        speed = 1;

        scoreValue = 30;

        sprite = ImageManager.loadImage("Basic_Shooter.png");
    }

    public void update(GameState state) {
        Player p = state.getPlayer();

        double dx = p.worldX - worldX;
        double dy = p.worldY - worldY;

        double len = Math.sqrt(dx * dx + dy * dy);
        dx /= len;
        dy /= len;

        worldX += dx * speed;
        worldY += dy * speed;

        if (fireCooldown > 0) fireCooldown--;

        if (fireCooldown == 0) {
            state.addProjectile(new Projectile(worldX, worldY, dx * 5, dy * 5, damage, "enemy"));
            fireCooldown = 60;
        }
    }

    public void draw(Graphics g, Camera cam) {
//        g.setColor(Color.MAGENTA);
//        g.fillRect(cam.getScreenX(worldX), cam.getScreenY(worldY), width, height);

        g.drawImage(sprite, cam.getScreenX(worldX), cam.getScreenY(worldY), width, height, null);
    }
}

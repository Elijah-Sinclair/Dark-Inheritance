import java.awt.*;

public class BasicBerserker extends Enemy{

    private int decayTimer = 300;

    public BasicBerserker(double x, double y) {
        worldX = x;
        worldY = y;

        health = 10;
        damage = 2;
        speed = 4;

        scoreValue = 50;

        sprite = ImageManager.loadImage("Basic_Berserker.png");
    }

    public void update(GameState state) {
        Player p = state.getPlayer();

        if (Math.abs(p.worldX - worldX) > Math.abs(p.worldY - worldY)) {
            worldX += (p.worldX > worldX ? speed : -speed);
        } else {
            worldY += (p.worldY > worldY ? speed : -speed);
        }

        decayTimer--;
        if (decayTimer % 60 == 0 && health > 1) health--;
    }

    public void draw(Graphics g, Camera cam) {
//        g.setColor(Color.ORANGE);
//        g.fillRect(cam.getScreenX(worldX), cam.getScreenY(worldY), width, height);

        g.drawImage(sprite, cam.getScreenX(worldX), cam.getScreenY(worldY), width, height, null);
    }
}

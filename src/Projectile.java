import java.awt.*;

public class Projectile extends Entity{

    private double dx, dy;
    private int damage;
    private String owner; // note to self can utilize enum instead 0-0

    private int lifetime = 240; // value means frames of existence

    private int bouncesRemaining = 4; // tweak later
    private boolean canBounce = false;

    private static final int SIZE = 8; //Projectile size here, future me

    public Projectile(double x, double y, double dx, double dy, int damage, String owner) {
        this.worldX = x;
        this.worldY = y;
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
        this.owner = owner;

        //hitboxing
        this.width = (damage > 0) ? 14 : 20;
        this.height = (damage > 0) ? 14 : 20;
    }

    @Override
    public void update(GameState gameState) {
        //Movement handling
        worldX += dx;
        worldY += dy;

        // Bounce logic
        if (canBounce) {

            boolean bounced = false;

            // LEFT
            if (worldX <= 0) {
                worldX = 0;
                dx *= -1;
                bounced = true;
            }

            // RIGHT
            else if (worldX + width >= gameState.getArenaWidth()) {
                worldX = gameState.getArenaWidth() - width;
                dx *= -1;
                bounced = true;
            }

            // TOP
            if (worldY <= 0) {
                worldY = 0;
                dy *= -1;
                bounced = true;
            }

            // BOTTOM
            else if (worldY + height >= gameState.getArenaHeight()) {
                worldY = gameState.getArenaHeight() - height;
                dy *= -1;
                bounced = true;
            }

            if (bounced) {
                // add chaos + speed
                dx += (Math.random() - 0.5) * 0.3;
                dy += (Math.random() - 0.5) * 0.3;

                dx *= 1.1;
                dy *= 1.1;

                bouncesRemaining--;
            }

            if (bouncesRemaining <= 0) {
                alive = false;
            }
            gameState.getFxManager().addEffect(
                    new TrailEffect(worldX, worldY, width)
            );
        }


        //lifetime handling
        lifetime--;
        if (lifetime <= 0) {
            alive = false;
        }

        dx *= 0.999;
        dy *= 0.999;

    }

    public void setBounce(boolean value) {
        this.canBounce = value;
    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int screenX = cam.getScreenX(worldX);
        int screenY = cam.getScreenY(worldY);

        if (owner.equals("player")) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.RED);
        }

        g.fillOval(screenX, screenY, width, height);
    }

    public int getDamage() {
        return damage;
    }

    public String getOwner() {
        return owner;
    }

}

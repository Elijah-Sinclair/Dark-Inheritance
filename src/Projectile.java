import java.awt.*;

public class Projectile extends Entity{

    private double dx, dy;
    private int damage;
    private String owner; // note to self can utilize enum instead 0-0

    private int lifetime; // value means frames of existence

    private static final int SIZE = 8; //Projectile size here, future me

    public Projectile(double x, double y, double dx, double dy, int damage, String owner) {
        this.worldX = x;
        this.worldY = y;
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
        this.owner = owner;
        this.lifetime = 120;

        //hitboxing
        this.width = 8;
        this.height = 8;
    }

    @Override
    public void update(GameState gameState) {
        //Movement handling
        worldX += dx;
        worldY += dy;

        //lifetime handling
        lifetime--;
        if (lifetime <= 0) {
            alive = false;
        }

        //Collision handling
//        handleCollisions(gameState);
    }

//    private void handleCollisions(GameState gameState) {
//
//        //Player projectile hits enemy
//        if (owner.equals("player")) {
//            for (Entity e : gameState.getEntities()) {
//                if (e instanceof Enemy enemy && e.isAlive()) {
//                    if (intersects(enemy)) {
//                        enemy.takeDamage(damage);
//                        alive = false;
//
//                        //Add FX for Hit here
//                        break;
//                    }
//                }
//            }
//        }
//
//        //Enemy projectile hits player
//        if (owner.equals("enemy")) {
//            Player player = gameState.getPlayer();
//            if(intersects(player)) {
//                player.takeDamage(damage);
//                alive = false;
//
//                //add hit FX
//            }
//        }
//    }

//    private boolean intersects(Entity other) {
//        Rectangle r1 = new Rectangle((int)worldX, (int)worldY, SIZE, SIZE);
//        Rectangle r2 = new Rectangle((int)other.worldX, (int)other.worldY, 20, 20);
//        return r1.intersects(r2);
//    }

    @Override
    public void draw(Graphics g, Camera cam) {
        int screenX = cam.getScreenX(worldX);
        int screenY = cam.getScreenY(worldY);

        //Note if lag becomes an issue Culling implemented here
        if (screenX < -20 || screenX > 820 || screenY < -20 || screenY > 620) {
            return;
        }

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

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    //Coordinate and movement information
    private int width, height;

    //Sprite, Frame and technical variables
    private BufferedImage spriteSheet;
    private int frame = 0;
    private int frameDelay = 0;

    //Player Attributes
    private int maxHealth;
    private int health;
    private int movementSpeed;

    private int attackDamage;
    private int fireRate;
    private int fireCooldown;

//    private ProjectileBehaviour projectileBehaviour; // This is an additional game feature that may or may not be implemented by the due date

    public Player(int x, int y) {
        this.worldX = x;
        this.worldY = y;

        this.maxHealth = 30;
        this.health = maxHealth;
        this.movementSpeed = 4;

        this.attackDamage = 1;
        this.fireRate = 20;
        this.fireCooldown = 0;

//        this.projectileBehaviour = new NormalShot();
    }

    @Override
    public void update(GameState gameState) {
        InputHandler input;
    }

    private void shoot(GameState gameState, int mouseX, int mouseY) {
        Camera cam = gameState.getCamera();

        double targetX = mouseX + cam.getScreenX();
        double targetY = mouseY + cam.getScreenY();

        double dx = targetX - worldX;
        double dy = targetY - worldY;

        double length = Math.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;

        Projectile p = new Projectile(worldX, worldY, dx * 8, dy * 8, attackDamage, "player");

        gameState.addProjectile(p);
    }

    @Override
    public void draw(Graphics g, Camera camera) {
        int screenX = camera.getScreenX(worldX);
        int screenY = camera.getScreenY(worldY);

        //Just some default test graphics
        g.setColor(Color.BLUE);
        g.fillRect(screenX, screenY, 30, 30);
    }

}

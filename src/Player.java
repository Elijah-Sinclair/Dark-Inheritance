import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    //Coordinate and movement information
    private int width = 30, height = 30;

    //Animation
    private BufferedImage spriteSheet;
    private int frame = 0;
    private int frameDelay = 0;

    //Player Attributes
    private int maxHealth = 3;
    private int health = maxHealth;
    private int movementSpeed = 4;

    private int attackDamage = 1;
    private int fireRate = 20;
    private int fireCooldown = 0;

//    private ProjectileBehaviour projectileBehaviour; // This is an additional game feature that may or may not be implemented by the due date

    public Player(int x, int y) {
        this.worldX = x;
        this.worldY = y;

//        this.projectileBehaviour = new NormalShot();
    }

    @Override
    public void update(GameState gameState) {
        InputHandler input = gameState.getInput();

        //Movemente (Espanol accent)
        if (input.up) worldY -= movementSpeed;
        if (input.down) worldY += movementSpeed;
        if (input.left) worldX -= movementSpeed;
        if (input.right) worldX += movementSpeed;

        //Shooting (Pew Pew)
        if (fireCooldown > 0) fireCooldown--;

        if (input.shooting && fireCooldown == 0) {
            shoot(gameState, input.mouseX, input.mouseY);
            fireCooldown = fireRate;
        }
    }

    private void shoot(GameState gameState, int mouseX, int mouseY) {
        Camera cam = gameState.getCamera();

        double targetX = mouseX + cam.getOffsetX();
        double targetY = mouseY + cam.getOffsetY();

        double dx = targetX - worldX;
        double dy = targetY - worldY;

        double length = Math.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;

        Projectile p = new Projectile(worldX, worldY, dx * 8, dy * 8, attackDamage, "player");

        gameState.addProjectile(p);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)worldX, (int)worldY, width, height);
    }

    @Override
    public void draw(Graphics g, Camera camera) {
        int screenX = camera.getScreenX(worldX);
        int screenY = camera.getScreenY(worldY);

        //Just some default test graphics
        g.setColor(Color.BLUE);
        g.fillRect(screenX, screenY, 30, 30);
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health <= 0) alive = false;
    }

    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
}

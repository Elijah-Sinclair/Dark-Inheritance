import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    //Coordinate and movement information
    private int worldX, worldY;
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

        this.fireRate = 20;
        this.fireCooldown = 0;

//        this.projectileBehaviour = new NormalShot();
    }

    @Override
    public void update(GameState gameState) {
        InputHandler input;
    }

    private void shoot(GameState gameState, int targetX, int targetY) {
        java.util.List<Projectile> shots =
                projectile
    }

}

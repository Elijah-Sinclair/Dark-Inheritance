import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    //Coordinate and movement information
    private int width = 64, height = 64;

    //Animation
    private BufferedImage spriteSheet;
    private BufferedImage[][] animations;

    private int direction = 0; //For if the spritesheet loads wonky
    private int frame = 0;
    private int frameCounter = 0;

    private int frameDelay = 10;


    //Player Attributes
    private int maxHealth = 100;
    private int health = maxHealth;
    private int movementSpeed = 4;

    private int attackDamage = 5;
    private int fireRate = 20;
    private int fireCooldown = 0;

//    private ProjectileBehaviour projectileBehaviour; // This is an additional game feature that may or may not be implemented by the due date

    public Player(int x, int y) {
        this.worldX = x;
        this.worldY = y;

//        this.projectileBehaviour = new NormalShot();
        try {
            spriteSheet = javax.imageio.ImageIO.read(getClass().getResource("/playerA.png"));
            System.out.println(getClass().getResource("/player.png"));

            int rows = 4;
            int cols = 6;

            int sheetWidth = spriteSheet.getWidth();
            int sheetHeight = spriteSheet.getHeight();

            int frameWidth = sheetWidth / cols;
            int frameHeight = sheetHeight / rows;

            animations = new BufferedImage[rows][cols];

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    animations[row][col] = spriteSheet.getSubimage(
                            col * frameWidth, //128
                            row * frameHeight, //256 or the other way around
                            frameWidth,
                            frameHeight
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameState gameState) {
        InputHandler input = gameState.getInput();
        boolean moving = false;

        //Movemente (Espanol accent)
        if (input.up) {
            worldY -= movementSpeed;
            direction = 0;
            moving = true;
        }
        if (input.down) {
            worldY += movementSpeed;
            direction = 1;
            moving = true;
        }
        if (input.left) {
            worldX -= movementSpeed;
            direction = 2;
            moving = true;
        }
        if (input.right) {
            worldX += movementSpeed;
            direction = 3;
            moving = true;
        }

        if (moving) {
            frameCounter++;

            if (frameCounter >= frameDelay) {
                frame++;
                if (frame >= animations[direction].length) {
                    frame = 0;
                }
                frameCounter = 0;
            }
        } else {
            frame = 0;
        }

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
        return new Rectangle((int)worldX, (int)worldY, 32, 32);
    }

    @Override
    public void draw(Graphics g, Camera camera) {
        int screenX = camera.getScreenX(worldX);
        int screenY = camera.getScreenY(worldY);

        int drawX = screenX - width / 2;
        int drawY = screenY - height / 2;

        //Just some default test graphics
        if (animations == null || animations[direction][frame] == null) {
            g.setColor(Color.BLUE);
            g.fillRect(screenX, screenY, 30, 30);
            return;
        }

        BufferedImage img = animations[direction][frame];
        if (img == null) return;

        g.drawImage(img, drawX, drawY, width, height, null);
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health <= 0) alive = false;
    }

    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
}
